package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityProjectile<T extends EntityProjectile<T>> extends AbstractArrowEntity
        implements IEntityAdditionalSpawnData {
    private static final Predicate<Entity> WEAPON_TARGETS = EntityPredicates.NO_SPECTATORS.and(
            EntityPredicates.ENTITY_STILL_ALIVE).and(Entity::isPickable);
    private static final DataParameter<Byte> WEAPON_CRITICAL = EntityDataManager.defineId(EntityProjectile.class,
            DataSerializers.BYTE);
    protected int xTile;
    protected int yTile;
    protected int zTile;
    @Nullable
    protected BlockState inBlockState;
    protected boolean inGround;
    public PickupStatus pickupStatus;
    protected int ticksInGround;
    protected int ticksInAir;
    public boolean beenInGround;
    public float extraDamage;
    public int knockBack;
    private Entity shooter;

    public EntityProjectile(EntityType<T> type, World world) {
        super(type, world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inBlockState = null;
        inGround = false;
        shakeTime = 0;
        ticksInAir = 0;
        pickupStatus = PickupStatus.DISALLOWED;
        extraDamage = 0.0f;
        knockBack = 0;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(WEAPON_CRITICAL, (byte) 0);
    }

    @Nonnull
    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        Entity shooter = getOwner();
        buffer.writeInt(shooter != null ? shooter.getId() : -1);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        int shooterId = additionalData.readInt();
        if (shooterId >= 0) setOwner(level.getEntity(shooterId));
    }

    @Override
    public void setOwner(@Nullable Entity shooter) {
        this.shooter = shooter;
        super.setOwner(shooter);
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (shooter != null) return shooter;
        return super.getOwner();
    }

    protected void setPickupStatusFromEntity(LivingEntity entityliving) {
        if (entityliving instanceof PlayerEntity) {
            if (((PlayerEntity) entityliving).abilities.instabuild) {
                setPickupStatus(PickupStatus.CREATIVE_ONLY);
            } else {
                setPickupStatus(BalkonsWeaponMod.instance.modConfig.allCanPickup.get()
                        ? PickupStatus.ALLOWED : PickupStatus.OWNER_ONLY);
            }
        } else {
            setPickupStatus(PickupStatus.DISALLOWED);
        }
    }

    public Entity getDamagingEntity() {
        Entity shooter = getOwner();
        return shooter != null ? shooter : this;
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float deviation) {
        Vector3d v = new Vector3d(x, y, z).normalize()
                .add(random.nextGaussian() * 0.0075 * deviation,
                        random.nextGaussian() * 0.0075 * deviation,
                        random.nextGaussian() * 0.0075 * deviation)
                .scale(speed);
        setDeltaMovement(v);
        float f2 = MathHelper.sqrt(getHorizontalDistanceSqr(v));
        float n = (float) (MathHelper.atan2(v.x, v.z) * 180.0 / Math.PI);
        yRot = n;
        yRotO = n;
        float n2 = (float) (MathHelper.atan2(v.y, f2) * 180.0 / Math.PI);
        xRot = n2;
        xRotO = n2;
        ticksInGround = 0;
    }

    @Override
    public void lerpMotion(double d, double d1, double d2) {
        Vector3d v = new Vector3d(d, d1, d2);
        setDeltaMovement(v);
        if (aimRotation() && xRotO == 0.0f && yRotO == 0.0f) {
            float f = MathHelper.sqrt(getHorizontalDistanceSqr(v));
            float n = (float) (MathHelper.atan2(d, d2) * 180.0 / Math.PI);
            yRot = n;
            yRotO = n;
            float n2 = (float) (MathHelper.atan2(d1, f) * 180.0 / Math.PI);
            xRot = n2;
            xRotO = n2;
            moveTo(getX(), getY(), getZ(), yRot, xRot);
            ticksInGround = 0;
        }
    }

    @Override
    public void tick() {
        baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        Vector3d motion = getDeltaMovement();
        if (aimRotation() && xRotO == 0.0f && yRotO == 0.0f) {
            float f = MathHelper.sqrt(Entity.getHorizontalDistanceSqr(motion));
            yRot = (float) (MathHelper.atan2(motion.x, motion.z) * 180.0 / Math.PI);
            xRot = (float) (MathHelper.atan2(motion.y, f) * 180.0 / Math.PI);
            yRotO = yRot;
            xRotO = xRot;
        }
        BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
        BlockState iblockstate = level.getBlockState(blockpos);
        if (iblockstate.getMaterial() != Material.AIR) {
            VoxelShape voxelShape = iblockstate.getCollisionShape(level, blockpos);
            if (!voxelShape.isEmpty() && voxelShape.bounds().move(blockpos).contains(
                    new Vector3d(getX(), getY(), getZ()))) {
                inGround = true;
            }
        }
        if (shakeTime > 0) {
            --shakeTime;
        }

        if (isInWaterOrRain()) {
            clearFire();
        }

        if (inGround) {
            if (!iblockstate.equals(inBlockState) &&
                level.noCollision(getBoundingBox().inflate(0.06))) {
                inGround = false;
                setDeltaMovement(motion.multiply(random.nextFloat() * 0.2f, random.nextFloat() * 0.2f,
                        random.nextFloat() * 0.2f));
                ticksInGround = 0;
                ticksInAir = 0;
            } else if (!level.isClientSide) {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t) {
                    remove();
                }
            }
            ++inGroundTime;
            return;
        }
        inGroundTime = 0;
        ++ticksInAir;
        Vector3d vec3d = position();
        Vector3d vec3d2 = vec3d.add(getDeltaMovement());
        RayTraceResult raytraceresult = level.clip(new RayTraceContext(vec3d, vec3d2,
                RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            vec3d2 = raytraceresult.getLocation();
        }

        while (isAlive()) {
            EntityRayTraceResult entityraytraceresult = findHitEntity(vec3d, vec3d2);
            if (entityraytraceresult != null) {
                raytraceresult = entityraytraceresult;
            }
            if (raytraceresult instanceof EntityRayTraceResult) {
                final Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
                final Entity entity2 = getOwner();
                if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity) entity2).canHarmPlayer((PlayerEntity) entity)) {
                    raytraceresult = null;
                    entityraytraceresult = null;
                }
            }
            if (raytraceresult != null && raytraceresult.getType() != RayTraceResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                if (raytraceresult instanceof EntityRayTraceResult) {
                    onEntityHit(((EntityRayTraceResult) raytraceresult).getEntity());
                } else {
                    onGroundHit((BlockRayTraceResult) raytraceresult);
                }
                hasImpulse = true;
            }
            if (entityraytraceresult == null) {
                break;
            }
            if (getPierceLevel() <= 0) {
                break;
            }
            raytraceresult = null;
        }

        if (isCritArrow()) {
            Vector3d motion2 = getDeltaMovement();
            for (int i1 = 0; i1 < 2; ++i1) {
                Vector3d pos = position().add(motion2.scale(i1 / 4.0));
                level.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, -motion2.x,
                        -motion2.y + 0.2, -motion2.z);
            }
        }
        Vector3d newPos = position().add(getDeltaMovement());
        setPos(newPos.x, newPos.y, newPos.z);
        if (aimRotation()) {
            Vector3d motion2 = getDeltaMovement();
            double f2 = MathHelper.sqrt(getHorizontalDistanceSqr(motion2));
            float n3 = (float) (MathHelper.atan2(motion2.x, motion2.z) * 180.0 / Math.PI);
            yRot = n3;
            yRotO = n3;
            float n4 = (float) (MathHelper.atan2(motion2.y, f2) * 180.0 / Math.PI);
            xRot = n4;
            xRotO = n4;
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isInWater()) {
            Vector3d motion2 = getDeltaMovement();
            beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                float f3 = 0.25f;
                Vector3d pos = position().subtract(motion2.scale(f3));
                level.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, motion2.x,
                        motion2.y + 0.2, motion2.z);
            }
            res *= 0.6f;
        }
        setDeltaMovement(getDeltaMovement().scale(res).subtract(0, isNoGravity() ? 0 : grav, 0));
        setPos(getX(), getY(), getZ());
        checkInsideBlocks();
    }

    public void onEntityHit(Entity entity) {
        bounceBack();
        applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(Entity entity) {
        if (isOnFire() && !(entity instanceof EndermanEntity)) {
            entity.setSecondsOnFire(5);
        }
        if (entity instanceof LivingEntity) {
            LivingEntity entityliving = (LivingEntity) entity;
            if (knockBack > 0) {
                double f = getHorizontalDistanceSqr(getDeltaMovement());
                if (f > 0.0) {
                    Vector3d v = getDeltaMovement().scale(knockBack * 0.6 / f);
                    entity.push(v.x, 0.1, v.z);
                }
            }
            Entity shooter = getOwner();
            if (shooter instanceof LivingEntity) {
                EnchantmentHelper.doPostHurtEffects(entityliving, shooter);
                EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, entityliving);
            }
            if (shooter instanceof ServerPlayerEntity && !entity.equals(getOwner()) && entity instanceof PlayerEntity) {
                ((ServerPlayerEntity) shooter).connection.send(new SChangeGameStatePacket(SChangeGameStatePacket.ARROW_HIT_PLAYER, 0.0f));
            }
        }
    }

    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level.getBlockState(blockpos);
        setDeltaMovement(raytraceResult.getLocation().subtract(position()));
        double f1 = getDeltaMovement().length();
        Vector3d pos = position().subtract(getDeltaMovement().scale(0.05 / f1));
        setPos(pos.x, pos.y, pos.z);
        inGround = true;
        beenInGround = true;
        setCritArrow(false);
        shakeTime = getMaxArrowShake();
        playHitSound();
        if (inBlockState != null) {
            inBlockState.entityInside(level, blockpos, this);
        }
    }

    protected void bounceBack() {
        setDeltaMovement(getDeltaMovement().scale(-0.1));
        yRot += 180.0f;
        yRotO += 180.0f;
        ticksInAir = 0;
    }

    public double getTotalVelocity() {
        return getDeltaMovement().length();
    }

    public boolean aimRotation() {
        return true;
    }

    public int getMaxLifetime() {
        return 1200;
    }

    public float getAirResistance() {
        return 0.99f;
    }

    public float getGravity() {
        return 0.05f;
    }

    public int getMaxArrowShake() {
        return 7;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    public void playHitSound() {
    }

    public boolean canBeCritical() {
        return false;
    }

    @Override
    public void setCritArrow(boolean flag) {
        if (canBeCritical()) {
            entityData.set(WEAPON_CRITICAL, (byte) (flag ? 1 : 0));
        }
    }

    @Override
    public boolean isCritArrow() {
        return canBeCritical() && entityData.get(WEAPON_CRITICAL) != 0;
    }

    public void setExtraDamage(float f) {
        extraDamage = f;
    }

    @Override
    public void setKnockback(int i) {
        knockBack = i;
    }

    public void setPickupStatus(PickupStatus i) {
        pickupStatus = i;
    }

    public PickupStatus getPickupStatus() {
        return pickupStatus;
    }

    public boolean canPickup(PlayerEntity entityplayer) {
        if (pickupStatus == PickupStatus.ALLOWED) {
            return true;
        }
        if (pickupStatus == PickupStatus.CREATIVE_ONLY) {
            return entityplayer.abilities.instabuild;
        }
        return pickupStatus == PickupStatus.OWNER_ONLY && entityplayer.equals(getOwner());
    }

    @Override
    public void playerTouch(@Nonnull PlayerEntity entityplayer) {
        if (inGround && shakeTime <= 0 && canPickup(entityplayer) && !level.isClientSide) {
            ItemStack item = getPickupItem();
            if (item.isEmpty()) return;
            if ((pickupStatus == PickupStatus.CREATIVE_ONLY && entityplayer.abilities.instabuild) ||
                entityplayer.inventory.add(item)) {
                playSound(SoundEvents.ITEM_PICKUP, 0.2f,
                        ((random.nextFloat() - random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                remove();
            }
        }
    }

    protected void onItemPickup(PlayerEntity entityplayer) {
        entityplayer.take(this, 1);
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        nbttagcompound.putInt("xTile", xTile);
        nbttagcompound.putInt("yTile", yTile);
        nbttagcompound.putInt("zTile", zTile);
        if (inBlockState != null) {
            nbttagcompound.put("inBlockState", NBTUtil.writeBlockState(inBlockState));
        }
        nbttagcompound.putByte("shake", (byte) shakeTime);
        nbttagcompound.putBoolean("inGround", inGround);
        nbttagcompound.putBoolean("beenInGround", beenInGround);
        nbttagcompound.putByte("pickup", (byte) pickupStatus.ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        xTile = nbttagcompound.getInt("xTile");
        yTile = nbttagcompound.getInt("yTile");
        zTile = nbttagcompound.getInt("zTile");
        if (nbttagcompound.contains("inBlockState", 10)) {
            inBlockState = NBTUtil.readBlockState(nbttagcompound.getCompound("inBlockState"));
        }
        shakeTime = (nbttagcompound.getByte("shake") & 0xFF);
        inGround = nbttagcompound.getBoolean("inGround");
        beenInGround = nbttagcompound.getBoolean("beenInGrond");
        pickupStatus = PickupStatus.getByOrdinal(nbttagcompound.getByte("pickup"));
    }

    public enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY,
        OWNER_ONLY;

        public static PickupStatus getByOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > values().length) {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

}
