package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDynamite extends EntityProjectile<EntityDynamite> {
    public static final String NAME = "dynamite";

    private int explodefuse;
    private boolean extinguished;

    public EntityDynamite(EntityType<EntityDynamite> entityType, World world) {
        super(entityType, world);
        setPickupStatus(PickupStatus.DISALLOWED);
        extinguished = false;
        explodefuse = random.nextInt(30) + 20;
    }

    public EntityDynamite(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityDynamite, world);
        setPos(d, d1, d2);
    }

    public EntityDynamite(World world, LivingEntity shooter, int i) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        explodefuse = i;
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vector3d entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.isOnGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (!inGround && !beenInGround) {
            xRot -= 50.0f;
        } else {
            xRot = 180.0f;
        }
        if (isInWater() && !extinguished) {
            extinguished = true;
            playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (random.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                Vector3d motion = getDeltaMovement();
                Vector3d pos = position().subtract(motion.scale(f6));
                level.addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
            }
        }
        --explodefuse;
        if (!extinguished) {
            if (explodefuse <= 0) {
                detonate();
                remove();
            } else {
                level.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, 1.0f)) {
            applyEntityHitEffects(entity);
            playHitSound();
            lerpMotion(0.0, 0.0, 0.0);
            ticksInAir = 0;
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level.getBlockState(blockpos);
        Vector3d motion = raytraceResult.getLocation().subtract(position());
        setDeltaMovement(motion);
        Vector3d newPos = position().subtract(motion.normalize().scale(0.05));
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
            inBlockState.entityInside(level, blockpos, this);
        }
    }

    private void detonate() {
        if (level.isClientSide) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            remove();
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(level, this, getX(), getY(), getZ(), f,
                BalkonsWeaponMod.instance.modConfig.dynamiteDoesBlockDamage.get(), true, false, Explosion.Mode.DESTROY);
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dynamite);
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.9f));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putByte("fuse", (byte) explodefuse);
        nbttagcompound.putBoolean("off", extinguished);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        explodefuse = nbttagcompound.getByte("fuse");
        extinguished = nbttagcompound.getBoolean("off");
    }
}
