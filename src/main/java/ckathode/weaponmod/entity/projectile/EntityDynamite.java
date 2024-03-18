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
import net.minecraft.util.math.Vec3d;
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
        explodefuse = rand.nextInt(30) + 20;
    }

    public EntityDynamite(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityDynamite, world);
        setPosition(d, d1, d2);
    }

    public EntityDynamite(World world, LivingEntity shooter, int i) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        explodefuse = i;
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3,
                      float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3d entityMotion = entity.getMotion();
        setMotion(getMotion().add(entityMotion.x, entity.onGround ? 0 : entityMotion.y, entityMotion.z));
    }

    @Override
    protected void registerData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (!inGround && !beenInGround) {
            rotationPitch -= 50.0f;
        } else {
            rotationPitch = 180.0f;
        }
        if (isInWater() && !extinguished) {
            extinguished = true;
            playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (rand.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                Vec3d motion = getMotion();
                Vec3d pos = getPositionVector().subtract(motion.scale(f6));
                world.addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, motion.x, motion.y, motion.z);
            }
        }
        --explodefuse;
        if (!extinguished) {
            if (explodefuse <= 0) {
                detonate();
                remove();
            } else {
                world.addParticle(ParticleTypes.SMOKE, posX, posY, posZ, 0.0, 0.0,
                        0.0);
            }
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f)) {
            applyEntityHitEffects(entity);
            playHitSound();
            setVelocity(0.0, 0.0, 0.0);
            ticksInAir = 0;
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        Vec3d motion = raytraceResult.getHitVec().subtract(getPositionVec());
        setMotion(motion);
        Vec3d newPos = getPositionVec().subtract(motion.normalize().scale(0.05));
        setPosition(newPos.x, newPos.y, newPos.z);
        setMotion(-0.2 * motion.x, motion.y, -0.2 * motion.z);
        if (raytraceResult.getFace() == Direction.UP) {
            inGround = true;
            beenInGround = true;
        } else {
            inGround = false;
            playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (rand.nextFloat() * 0.2f + 0.9f));
        }
        if (inBlockState != null) {
            inBlockState.onEntityCollision(world, blockpos, this);
        }
    }

    private void detonate() {
        if (world.isRemote) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            remove();
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(world, this, posX, posY, posZ, f,
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
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dynamite, 1);
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.dynamite);
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putByte("fuse", (byte) explodefuse);
        nbttagcompound.putBoolean("off", extinguished);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        explodefuse = nbttagcompound.getByte("fuse");
        extinguished = nbttagcompound.getBoolean("off");
    }
}
