package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IProjectile;
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
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityProjectile<T extends EntityProjectile<T>> extends AbstractArrowEntity
        implements IProjectile, IEntityAdditionalSpawnData {
    private static final DataParameter<Byte> WEAPON_CRITICAL = EntityDataManager.createKey(EntityProjectile.class,
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
        arrowShake = 0;
        ticksInAir = 0;
        pickupStatus = PickupStatus.DISALLOWED;
        extraDamage = 0.0f;
        knockBack = 0;
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(WEAPON_CRITICAL, (byte) 0);
    }

    @NotNull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        Entity shooter = getShooter();
        buffer.writeInt(shooter != null ? shooter.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        int shooterId = additionalData.readInt();
        if (shooterId >= 0) setShooter(world.getEntityByID(shooterId));
    }

    @Override
    public void setShooter(@Nullable Entity shooter) {
        this.shooter = shooter;
        super.setShooter(shooter);
    }

    @Nullable
    @Override
    public Entity getShooter() {
        if (shooter != null) return shooter;
        return super.getShooter();
    }

    protected void setPickupStatusFromEntity(LivingEntity entityliving) {
        if (entityliving instanceof PlayerEntity) {
            if (((PlayerEntity) entityliving).isCreative()) {
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
        Entity shooter = getShooter();
        return shooter != null ? shooter : this;
    }

    @Override
    public void shoot(double x, double y, double z, float speed, float deviation) {
        Vec3d v = new Vec3d(x, y, z).normalize()
                .add(rand.nextGaussian() * 0.0075 * deviation,
                        rand.nextGaussian() * 0.0075 * deviation,
                        rand.nextGaussian() * 0.0075 * deviation)
                .scale(speed);
        setMotion(v);
        float f2 = MathHelper.sqrt(horizontalMag(v));
        float n = (float) (MathHelper.atan2(v.x, v.z) * 180.0 / Math.PI);
        rotationYaw = n;
        prevRotationYaw = n;
        float n2 = (float) (MathHelper.atan2(v.y, f2) * 180.0 / Math.PI);
        rotationPitch = n2;
        prevRotationPitch = n2;
        ticksInGround = 0;
    }

    @Override
    public void setVelocity(double d, double d1, double d2) {
        Vec3d v = new Vec3d(d, d1, d2);
        setMotion(v);
        if (aimRotation() && prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt(horizontalMag(v));
            float n = (float) (MathHelper.atan2(d, d2) * 180.0 / Math.PI);
            rotationYaw = n;
            prevRotationYaw = n;
            float n2 = (float) (MathHelper.atan2(d1, f) * 180.0 / Math.PI);
            rotationPitch = n2;
            prevRotationPitch = n2;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
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
        Vec3d motion = getMotion();
        if (aimRotation() && prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt(Entity.horizontalMag(motion));
            rotationYaw = (float) (MathHelper.atan2(motion.x, motion.z) * 180.0 / Math.PI);
            rotationPitch = (float) (MathHelper.atan2(motion.y, f) * 180.0 / Math.PI);
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }
        BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
        BlockState iblockstate = world.getBlockState(blockpos);
        if (iblockstate.getMaterial() != Material.AIR) {
            VoxelShape voxelShape = iblockstate.getCollisionShape(world, blockpos);
            if (!voxelShape.isEmpty() && voxelShape.getBoundingBox().offset(blockpos).contains(
                    new Vec3d(posX, posY, posZ))) {
                inGround = true;
            }
        }
        if (arrowShake > 0) {
            --arrowShake;
        }
        if (isWet()) {
            extinguish();
        }
        if (inGround) {
            if (!iblockstate.equals(inBlockState) &&
                !world.areCollisionShapesEmpty(this.getBoundingBox().grow(0.05))) {
                inGround = false;
                setMotion(motion.mul(rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f, rand.nextFloat() * 0.2f));
                ticksInGround = 0;
                ticksInAir = 0;
            } else if (!world.isRemote) {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t) {
                    remove();
                }
            }
            ++timeInGround;
            return;
        }
        timeInGround = 0;
        ++ticksInAir;
        Vec3d vec3d = getPositionVector();
        Vec3d vec3d2 = vec3d.add(getMotion());
        RayTraceResult raytraceresult = world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d2,
                RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this));
        if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
            vec3d2 = raytraceresult.getHitVec();
        }

        while (isAlive()) {
            EntityRayTraceResult entityraytraceresult = rayTraceEntities(vec3d, vec3d2);
            if (entityraytraceresult != null) {
                raytraceresult = entityraytraceresult;
            }
            if (raytraceresult instanceof EntityRayTraceResult) {
                final Entity entity = ((EntityRayTraceResult) raytraceresult).getEntity();
                final Entity entity2 = getShooter();
                if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity) entity2).canAttackPlayer((PlayerEntity) entity)) {
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
                isAirBorne = true;
            }
            if (entityraytraceresult == null) {
                break;
            }
            if (getPierceLevel() <= 0) {
                break;
            }
            raytraceresult = null;
        }

        if (getIsCritical()) {
            Vec3d motion2 = getMotion();
            for (int i1 = 0; i1 < 2; ++i1) {
                Vec3d pos = getPositionVector().add(motion2.scale(i1 / 4.0));
                world.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, -motion2.x,
                        -motion2.y + 0.2, -motion2.z);
            }
        }
        Vec3d newPos = getPositionVec().add(getMotion());
        posX = newPos.x;
        posY = newPos.y;
        posZ = newPos.z;
        if (aimRotation()) {
            Vec3d motion2 = getMotion();
            double f2 = MathHelper.sqrt(horizontalMag(motion2));
            float n3 = (float) (MathHelper.atan2(motion2.x, motion2.z) * 180.0 / Math.PI);
            rotationYaw = n3;
            prevRotationYaw = n3;
            float n4 = (float) (MathHelper.atan2(motion2.y, f2) * 180.0 / Math.PI);
            rotationPitch = n4;
            prevRotationPitch = n4;
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isInWater()) {
            Vec3d motion2 = getMotion();
            beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                float f3 = 0.25f;
                Vec3d pos = getPositionVector().subtract(motion2.scale(f3));
                world.addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, motion2.x,
                        motion2.y + 0.2, motion2.z);
            }
            res *= 0.6f;
        }
        setMotion(getMotion().scale(res).subtract(0, hasNoGravity() ? 0 : grav, 0));
        setPosition(posX, posY, posZ);
        doBlockCollisions();
    }

    public void onEntityHit(Entity entity) {
        bounceBack();
        applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(Entity entity) {
        if (isBurning() && !(entity instanceof EndermanEntity)) {
            entity.setFire(5);
        }
        if (entity instanceof LivingEntity) {
            LivingEntity entityliving = (LivingEntity) entity;
            if (knockBack > 0) {
                double f = horizontalMag(getMotion());
                if (f > 0.0) {
                    Vec3d v = getMotion().scale(knockBack * 0.6 / f);
                    entity.addVelocity(v.x, 0.1, v.z);
                }
            }
            Entity shooter = getShooter();
            if (shooter instanceof LivingEntity) {
                EnchantmentHelper.applyThornEnchantments(entityliving, shooter);
                EnchantmentHelper.applyArthropodEnchantments((LivingEntity) shooter, entityliving);
            }
            if (shooter instanceof ServerPlayerEntity && !shootingEntity.equals(entity.getUniqueID()) && entity instanceof PlayerEntity) {
                ((ServerPlayerEntity) shooter).connection.sendPacket(new SChangeGameStatePacket(6, 0.0f));
            }
        }
    }

    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        setMotion(raytraceResult.getHitVec().subtract(getPositionVec()));
        double f1 = getMotion().length();
        Vec3d pos = getPositionVector().subtract(getMotion().scale(0.05 / f1));
        posX = pos.x;
        posY = pos.y;
        posZ = pos.z;
        inGround = true;
        beenInGround = true;
        setIsCritical(false);
        arrowShake = getMaxArrowShake();
        playHitSound();
        if (inBlockState != null) {
            inBlockState.onEntityCollision(world, blockpos, this);
        }
    }

    protected void bounceBack() {
        setMotion(getMotion().scale(-0.1));
        rotationYaw += 180.0f;
        prevRotationYaw += 180.0f;
        ticksInAir = 0;
    }

    public double getTotalVelocity() {
        return getMotion().length();
    }

    public boolean aimRotation() {
        return true;
    }

    public int getMaxLifetime() {
        return 1200;
    }

    public ItemStack getPickupItem() {
        return null;
    }

    @NotNull
    @Override
    public ItemStack getPickedResult(@NotNull RayTraceResult target) {
        return getPickupItem();
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

    @NotNull
    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    public void playHitSound() {
    }

    public boolean canBeCritical() {
        return false;
    }

    @Override
    public void setIsCritical(boolean flag) {
        if (canBeCritical()) {
            dataManager.set(WEAPON_CRITICAL, (byte) (flag ? 1 : 0));
        }
    }

    @Override
    public boolean getIsCritical() {
        return canBeCritical() && dataManager.get(WEAPON_CRITICAL) != 0;
    }

    public void setExtraDamage(float f) {
        extraDamage = f;
    }

    @Override
    public void setKnockbackStrength(int i) {
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
            return entityplayer.isCreative();
        }
        return pickupStatus == PickupStatus.OWNER_ONLY && entityplayer.getUniqueID().equals(shootingEntity);
    }

    @Override
    public void onCollideWithPlayer(@NotNull PlayerEntity entityplayer) {
        if (inGround && arrowShake <= 0 && canPickup(entityplayer) && !world.isRemote) {
            ItemStack item = getPickupItem();
            if (item == null) {
                return;
            }
            if ((pickupStatus == PickupStatus.CREATIVE_ONLY && entityplayer.isCreative()) ||
                entityplayer.inventory.addItemStackToInventory(item)) {
                playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                        ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                remove();
            }
        }
    }

    protected void onItemPickup(PlayerEntity entityplayer) {
        entityplayer.onItemPickup(this, 1);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        nbttagcompound.putInt("xTile", xTile);
        nbttagcompound.putInt("yTile", yTile);
        nbttagcompound.putInt("zTile", zTile);
        if (inBlockState != null) {
            nbttagcompound.put("inBlockState", NBTUtil.writeBlockState(inBlockState));
        }
        nbttagcompound.putByte("shake", (byte) arrowShake);
        nbttagcompound.putBoolean("inGround", inGround);
        nbttagcompound.putBoolean("beenInGround", beenInGround);
        nbttagcompound.putByte("pickup", (byte) pickupStatus.ordinal());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        xTile = nbttagcompound.getInt("xTile");
        yTile = nbttagcompound.getInt("yTile");
        zTile = nbttagcompound.getInt("zTile");
        if (nbttagcompound.contains("inBlockState", 10)) {
            inBlockState = NBTUtil.readBlockState(nbttagcompound.getCompound("inBlockState"));
        }
        arrowShake = (nbttagcompound.getByte("shake") & 0xFF);
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
