package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.entity.EntityCannon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
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

public class EntityCannonBall extends EntityProjectile<EntityCannonBall> {

    public static final String ID = "cannonball";
    public static final EntityType<EntityCannonBall> TYPE = WMRegistries.createEntityType(
            ID, new EntityDimensions(0.5f, 0.5f, false), EntityCannonBall::new);

    public EntityCannonBall(EntityType<EntityCannonBall> entityType, Level world) {
        super(entityType, world);
    }

    public EntityCannonBall(Level world, double d, double d1, double d2) {
        this(TYPE, world);
        setPos(d, d1, d2);
    }

    public EntityCannonBall(Level world, EntityCannon entitycannon, float f, float f1,
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
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, superPowered ? 4.0f : 2.0f, superPowered ? 0.1f : 2.0f);
        Vec3 cannonMotion = entitycannon.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(cannonMotion.x, 0, cannonMotion.z));
        setCritArrow(superPowered);
    }

    @NotNull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getDeltaMovement().length();
        double amount = 8.0;
        if (speed > 1.0) {
            Vec3 motion = getDeltaMovement();
            for (int i1 = 1; i1 < amount; ++i1) {
                Vec3 pos = position().add(motion.scale(i1 / amount));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (level.isClientSide || !inGround || isInWater()) {
            return;
        }
        remove(RemovalReason.DISCARDED);
        float f = isCritArrow() ? 5.0f : 2.5f;
        PhysHelper.createAdvancedExplosion(level, this, getX(), getY(), getZ(), f,
                WeaponModConfig.get().cannonDoesBlockDamage, true, false,
                Explosion.BlockInteraction.DESTROY);
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, 30.0f)) {
            playSound(SoundEvents.PLAYER_HURT, 1.0f, 1.2f / (random.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(BlockHitResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level.getBlockState(blockpos);
        setDeltaMovement(raytraceResult.getLocation().subtract(position()));
        double f1 = getDeltaMovement().length();
        Vec3 pos = position().subtract(getDeltaMovement().scale(0.05 / f1));
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

    @NotNull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(WMRegistries.ITEM_CANNON_BALL.get());
    }

}
