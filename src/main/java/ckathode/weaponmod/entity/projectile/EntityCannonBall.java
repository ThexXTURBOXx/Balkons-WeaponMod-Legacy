package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.entity.EntityCannon;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityCannonBall extends EntityProjectile<EntityCannonBall> {
    public static final String NAME = "cannonball";

    public EntityCannonBall(EntityType<EntityCannonBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityCannonBall(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityCannonBall, world);
        setPos(d, d1, d2);
    }

    public EntityCannonBall(World world, EntityCannon entitycannon, float f, float f1,
                            boolean superPowered) {
        this(world, entitycannon.getX(), entitycannon.getY() + 1.0, entitycannon.getZ());
        Entity entityPassenger = entitycannon.getPassengers().isEmpty() ? null :
                entitycannon.getPassengers().get(0);
        setOwner(entitycannon);
        if (entityPassenger instanceof LivingEntity) {
            setPickupStatusFromEntity((LivingEntity) entityPassenger);
        } else {
            setPickupStatus(PickupStatus.ALLOWED);
        }
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, superPowered ? 4.0f : 2.0f, superPowered ? 0.1f : 2.0f);
        Vector3d cannonMotion = entitycannon.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(cannonMotion.x, 0, cannonMotion.z));
        setCritArrow(superPowered);
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getDeltaMovement().length();
        double amount = 8.0;
        if (speed > 1.0) {
            Vector3d motion = getDeltaMovement();
            for (int i1 = 1; i1 < amount; ++i1) {
                Vector3d pos = position().add(motion.scale(i1 / amount));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (level.isClientSide || !inGround || isInWater()) {
            return;
        }
        remove();
        float f = isCritArrow() ? 5.0f : 2.5f;
        PhysHelper.createAdvancedExplosion(level, this, getX(), getY(), getZ(), f,
                BalkonsWeaponMod.instance.modConfig.cannonDoesBlockDamage.get(), true, false, Explosion.Mode.DESTROY);
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, 30.0f)) {
            playSound(SoundEvents.PLAYER_HURT, 1.0f, 1.2f / (random.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
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
        if (inBlockState != null) {
            inBlockState.entityInside(level, blockpos, this);
        }
        createCrater();
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.cannonBall);
    }
}
