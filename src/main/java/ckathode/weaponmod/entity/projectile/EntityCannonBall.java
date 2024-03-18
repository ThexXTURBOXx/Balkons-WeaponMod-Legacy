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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityCannonBall extends EntityProjectile<EntityCannonBall> {
    public static final String NAME = "cannonball";

    public EntityCannonBall(EntityType<EntityCannonBall> entityType, World world) {
        super(entityType, world);
    }

    public EntityCannonBall(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityCannonBall, world);
        setPosition(d, d1, d2);
    }

    public EntityCannonBall(World world, EntityCannon entitycannon, float f, float f1,
                            boolean superPowered) {
        this(world, entitycannon.posX, entitycannon.posY + 1.0, entitycannon.posZ);
        Entity entityPassenger = entitycannon.getPassengers().isEmpty() ? null :
                entitycannon.getPassengers().get(0);
        setShooter(entitycannon);
        if (entityPassenger instanceof LivingEntity) {
            setPickupStatusFromEntity((LivingEntity) entityPassenger);
        } else {
            setPickupStatus(PickupStatus.ALLOWED);
        }
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, superPowered ? 4.0f : 2.0f, superPowered ? 0.1f : 2.0f);
        Vec3d cannonMotion = entitycannon.getMotion();
        setMotion(getMotion().add(cannonMotion.x, 0, cannonMotion.z));
        setIsCritical(superPowered);
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getMotion().length();
        double amount = 8.0;
        if (speed > 1.0) {
            Vec3d motion = getMotion();
            for (int i1 = 1; i1 < amount; ++i1) {
                Vec3d pos = getPositionVector().add(motion.scale(i1 / amount));
                world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (world.isRemote || !inGround || isInWater()) {
            return;
        }
        remove();
        float f = getIsCritical() ? 5.0f : 2.5f;
        PhysHelper.createAdvancedExplosion(world, this, posX, posY, posZ, f,
                BalkonsWeaponMod.instance.modConfig.cannonDoesBlockDamage.get(), true, false, Explosion.Mode.DESTROY);
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 30.0f)) {
            playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0f, 1.2f / (rand.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        setMotion(raytraceResult.getHitVec().subtract(getPositionVec()));
        double f1 = getMotion().length();
        Vec3d pos = getPositionVec().subtract(getMotion().scale(0.05 / f1));
        posX = pos.x;
        posY = pos.y;
        posZ = pos.z;
        inGround = true;
        if (inBlockState != null) {
            inBlockState.onEntityCollision(world, blockpos, this);
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
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.cannonBall, 1);
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.cannonBall);
    }
}
