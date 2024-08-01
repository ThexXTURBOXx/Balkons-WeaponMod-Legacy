package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModConfig;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityDynamite extends EntityProjectile<EntityDynamite> {

    public static final String ID = "dynamite";
    public static final EntityType<EntityDynamite> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityDynamite::new);

    private int explodefuse;
    private boolean extinguished;

    public EntityDynamite(EntityType<EntityDynamite> entityType, Level world) {
        super(entityType, world);
        setPickupStatus(PickupStatus.DISALLOWED);
        extinguished = false;
        explodefuse = random.nextInt(30) + 20;
    }

    public EntityDynamite(Level world, double d, double d1, double d2) {
        this(TYPE, world);
        setPos(d, d1, d2);
    }

    public EntityDynamite(Level world, LivingEntity shooter, int i) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        explodefuse = i;
    }

    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3 entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.onGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    public void tick() {
        super.tick();
        if (!inGround && !beenInGround) {
            setXRot(getXRot() - 50.0f);
        } else {
            setXRot(180.0f);
        }
        if (isInWater() && !extinguished) {
            extinguished = true;
            playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (random.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                Vec3 motion = getDeltaMovement();
                Vec3 pos = position().subtract(motion.scale(f6));
                if (level().isClientSide()) {
                    level().addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
                }
            }
        }
        --explodefuse;
        if (!extinguished) {
            if (explodefuse <= 0) {
                detonate();
                remove(RemovalReason.DISCARDED);
            } else if (level().isClientSide()) {
                level().addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
        if (entity.hurt(damagesource, 1.0f)) {
            applyEntityHitEffects(entity);
            playHitSound();
            lerpMotion(0.0, 0.0, 0.0);
            ticksInAir = 0;
        }
    }

    @Override
    public void onGroundHit(BlockHitResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level().getBlockState(blockpos);
        Vec3 motion = raytraceResult.getLocation().subtract(position());
        setDeltaMovement(motion);
        Vec3 newPos = position().subtract(motion.normalize().scale(0.05));
        setPos(newPos.x, newPos.y, newPos.z);
        setDeltaMovement(-0.2 * motion.x, motion.y, -0.2 * motion.z);
        if (raytraceResult.getDirection() == Direction.UP) {
            inGround = true;
            beenInGround = true;
        } else {
            inGround = false;
            playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (random.nextFloat() * 0.2f + 0.9f));
        }
        if (inBlockState != null) {
            inBlockState.entityInside(level(), blockpos, this);
        }
    }

    private void detonate() {
        if (level().isClientSide) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            remove(RemovalReason.DISCARDED);
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(level(), this, getX(), getY(), getZ(), f,
                WeaponModConfig.get().dynamiteDoesBlockDamage, true, false, Explosion.BlockInteraction.DESTROY);
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @NotNull
    @Override
    protected ItemStack getPickupItem() {
        return getDefaultPickupItem();
    }

    @NotNull
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(WMRegistries.ITEM_DYNAMITE.get());
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.9f));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putByte("fuse", (byte) explodefuse);
        nbttagcompound.putBoolean("off", extinguished);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        explodefuse = nbttagcompound.getByte("fuse");
        extinguished = nbttagcompound.getBoolean("off");
    }
}
