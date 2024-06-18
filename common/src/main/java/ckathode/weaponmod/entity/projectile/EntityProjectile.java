package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponModConfig;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class EntityProjectile<T extends EntityProjectile<T>> extends AbstractArrow
        implements EntitySpawnExtension {
    private static final Predicate<Entity> WEAPON_TARGETS = EntitySelector.NO_SPECTATORS.and(
            EntitySelector.ENTITY_STILL_ALIVE).and(Entity::isPickable);
    private static final EntityDataAccessor<Byte> WEAPON_CRITICAL = SynchedEntityData.defineId(EntityProjectile.class,
            EntityDataSerializers.BYTE);
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

    public EntityProjectile(EntityType<T> type, Level world) {
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

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        Entity shooter = getOwner();
        buf.writeInt(shooter != null ? shooter.getId() : -1);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        int shooterId = buf.readInt();
        if (shooterId >= 0) setOwner(level().getEntity(shooterId));
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

    @Override
    protected float getEyeHeight(Pose pose, EntityDimensions dimension) {
        return 0;
    }

    protected void setPickupStatusFromEntity(LivingEntity entityliving) {
        if (entityliving instanceof Player) {
            if (((Player) entityliving).isCreative()) {
                setPickupStatus(PickupStatus.CREATIVE_ONLY);
            } else {
                setPickupStatus(WeaponModConfig.get().allCanPickup ? PickupStatus.ALLOWED : PickupStatus.OWNER_ONLY);
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
        Vec3 v = new Vec3(x, y, z).normalize()
                .add(random.nextGaussian() * 0.0075 * deviation,
                        random.nextGaussian() * 0.0075 * deviation,
                        random.nextGaussian() * 0.0075 * deviation)
                .scale(speed);
        setDeltaMovement(v);
        double f2 = v.horizontalDistance();
        float n = (float) (Mth.atan2(v.x, v.z) * 180.0 / Math.PI);
        setYRot(n);
        yRotO = n;
        float n2 = (float) (Mth.atan2(v.y, f2) * 180.0 / Math.PI);
        setXRot(n2);
        xRotO = n2;
        ticksInGround = 0;
    }

    @Override
    public void lerpMotion(double d, double d1, double d2) {
        Vec3 v = new Vec3(d, d1, d2);
        setDeltaMovement(v);
        if (aimRotation() && xRotO == 0.0f && yRotO == 0.0f) {
            double f = v.horizontalDistance();
            float n = (float) (Mth.atan2(d, d2) * 180.0 / Math.PI);
            setYRot(n);
            yRotO = n;
            float n2 = (float) (Mth.atan2(d1, f) * 180.0 / Math.PI);
            setXRot(n2);
            xRotO = n2;
            moveTo(getX(), getY(), getZ(), getYRot(), getXRot());
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
        Vec3 motion = getDeltaMovement();
        if (aimRotation() && xRotO == 0.0f && yRotO == 0.0f) {
            double f = motion.horizontalDistance();
            setYRot((float) (Mth.atan2(motion.x, motion.z) * 180.0 / Math.PI));
            setXRot((float) (Mth.atan2(motion.y, f) * 180.0 / Math.PI));
            yRotO = getYRot();
            xRotO = getXRot();
        }
        BlockPos blockpos = new BlockPos(xTile, yTile, zTile);
        BlockState iblockstate = level().getBlockState(blockpos);
        if (!iblockstate.isAir()) {
            VoxelShape voxelShape = iblockstate.getCollisionShape(level(), blockpos);
            if (!voxelShape.isEmpty() && voxelShape.bounds().move(blockpos).contains(
                    new Vec3(getX(), getY(), getZ()))) {
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
                level().noCollision(getBoundingBox().inflate(0.06))) {
                inGround = false;
                setDeltaMovement(motion.multiply(random.nextFloat() * 0.2f, random.nextFloat() * 0.2f,
                        random.nextFloat() * 0.2f));
                ticksInGround = 0;
                ticksInAir = 0;
            } else if (!level().isClientSide) {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t) {
                    remove(RemovalReason.DISCARDED);
                }
            }
            ++inGroundTime;
            return;
        }
        inGroundTime = 0;
        ++ticksInAir;
        Vec3 vec3d = position();
        Vec3 vec3d2 = vec3d.add(getDeltaMovement());
        HitResult raytraceresult = level().clip(new ClipContext(vec3d, vec3d2,
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (raytraceresult.getType() != HitResult.Type.MISS) {
            vec3d2 = raytraceresult.getLocation();
        }

        while (isAlive()) {
            EntityHitResult entityraytraceresult = findHitEntity(vec3d, vec3d2);
            if (entityraytraceresult != null) {
                raytraceresult = entityraytraceresult;
            }
            if (raytraceresult instanceof EntityHitResult) {
                final Entity entity = ((EntityHitResult) raytraceresult).getEntity();
                final Entity entity2 = getOwner();
                if (entity instanceof Player && entity2 instanceof Player && !((Player) entity2).canHarmPlayer((Player) entity)) {
                    raytraceresult = null;
                    entityraytraceresult = null;
                }
            }
            if (raytraceresult != null && raytraceresult.getType() != HitResult.Type.MISS
                && !onProjectileImpact(this, raytraceresult)) {
                if (raytraceresult instanceof EntityHitResult) {
                    onEntityHit(((EntityHitResult) raytraceresult).getEntity());
                } else {
                    onGroundHit((BlockHitResult) raytraceresult);
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
            Vec3 motion2 = getDeltaMovement();
            for (int i1 = 0; i1 < 2; ++i1) {
                Vec3 pos = position().add(motion2.scale(i1 / 4.0));
                level().addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, -motion2.x,
                        -motion2.y + 0.2, -motion2.z);
            }
        }
        Vec3 newPos = position().add(getDeltaMovement());
        setPos(newPos.x, newPos.y, newPos.z);
        if (aimRotation()) {
            Vec3 motion2 = getDeltaMovement();
            double f2 = motion2.horizontalDistance();
            float n3 = (float) (Mth.atan2(motion2.x, motion2.z) * 180.0 / Math.PI);
            setYRot(n3);
            yRotO = n3;
            float n4 = (float) (Mth.atan2(motion2.y, f2) * 180.0 / Math.PI);
            setXRot(n4);
            xRotO = n4;
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isInWater()) {
            Vec3 motion2 = getDeltaMovement();
            beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                float f3 = 0.25f;
                Vec3 pos = position().subtract(motion2.scale(f3));
                level().addParticle(ParticleTypes.CRIT, pos.x, pos.y, pos.z, motion2.x,
                        motion2.y + 0.2, motion2.z);
            }
            res *= 0.6f;
        }
        setDeltaMovement(getDeltaMovement().scale(res).subtract(0, isNoGravity() ? 0 : grav, 0));
        setPos(getX(), getY(), getZ());
        checkInsideBlocks();
    }

    @ExpectPlatform
    public static boolean onProjectileImpact(EntityProjectile<?> projectile, HitResult hitResult) {
        return false; // Will get replaced at run time
    }

    public void onEntityHit(Entity entity) {
        bounceBack();
        applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(Entity entity) {
        if (isOnFire() && !(entity instanceof EnderMan)) {
            entity.setSecondsOnFire(5);
        }
        if (entity instanceof LivingEntity entityliving) {
            if (knockBack > 0) {
                double f = getDeltaMovement().horizontalDistanceSqr();
                if (f > 0.0) {
                    Vec3 v = getDeltaMovement().scale(knockBack * 0.6 / f);
                    entity.push(v.x, 0.1, v.z);
                }
            }
            Entity shooter = getOwner();
            if (shooter instanceof LivingEntity) {
                EnchantmentHelper.doPostHurtEffects(entityliving, shooter);
                EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, entityliving);
            }
            if (shooter instanceof ServerPlayer && !entity.equals(getOwner()) && entity instanceof Player) {
                ((ServerPlayer) shooter).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0f));
            }
        }
    }

    public void onGroundHit(BlockHitResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level().getBlockState(blockpos);
        setDeltaMovement(raytraceResult.getLocation().subtract(position()));
        double f1 = getDeltaMovement().length();
        Vec3 pos = position().subtract(getDeltaMovement().scale(0.05 / f1));
        setPos(pos.x, pos.y, pos.z);
        inGround = true;
        beenInGround = true;
        setCritArrow(false);
        shakeTime = getMaxArrowShake();
        playHitSound();
        if (inBlockState != null) {
            inBlockState.entityInside(level(), blockpos, this);
        }
    }

    protected void bounceBack() {
        setDeltaMovement(getDeltaMovement().scale(-0.1));
        setYRot(getYRot() + 180.0f);
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

    @NotNull
    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return getPickupItem();
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

    public boolean canPickup(Player entityplayer) {
        if (pickupStatus == PickupStatus.ALLOWED) {
            return true;
        }
        if (pickupStatus == PickupStatus.CREATIVE_ONLY) {
            return entityplayer.isCreative();
        }
        return pickupStatus == PickupStatus.OWNER_ONLY && entityplayer.equals(getOwner());
    }

    @Override
    public void playerTouch(@NotNull Player entityplayer) {
        if (inGround && shakeTime <= 0 && canPickup(entityplayer) && !level().isClientSide) {
            ItemStack item = getPickupItem();
            if (item.isEmpty()) return;
            if ((pickupStatus == PickupStatus.CREATIVE_ONLY && entityplayer.isCreative()) ||
                entityplayer.getInventory().add(item)) {
                playSound(SoundEvents.ITEM_PICKUP, 0.2f,
                        ((random.nextFloat() - random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    protected void onItemPickup(Player entityplayer) {
        entityplayer.take(this, 1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        nbttagcompound.putInt("xTile", xTile);
        nbttagcompound.putInt("yTile", yTile);
        nbttagcompound.putInt("zTile", zTile);
        if (inBlockState != null) {
            nbttagcompound.put("inBlockState", NbtUtils.writeBlockState(inBlockState));
        }
        nbttagcompound.putByte("shake", (byte) shakeTime);
        nbttagcompound.putBoolean("inGround", inGround);
        nbttagcompound.putBoolean("beenInGround", beenInGround);
        nbttagcompound.putByte("pickup", (byte) pickupStatus.ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        xTile = nbttagcompound.getInt("xTile");
        yTile = nbttagcompound.getInt("yTile");
        zTile = nbttagcompound.getInt("zTile");
        if (nbttagcompound.contains("inBlockState", 10)) {
            inBlockState = NbtUtils.readBlockState(level().holderLookup(Registries.BLOCK),
                    nbttagcompound.getCompound("inBlockState"));
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
