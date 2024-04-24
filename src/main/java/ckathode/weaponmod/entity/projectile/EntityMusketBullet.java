package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityMusketBullet extends EntityProjectile<EntityMusketBullet> {
    public static final String NAME = "bullet";

    public EntityMusketBullet(EntityType<EntityMusketBullet> entityType, World world) {
        super(entityType, world);
        setPickupStatus(PickupStatus.DISALLOWED);
    }

    public EntityMusketBullet(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityMusketBullet, world);
        setPos(d, d1, d2);
    }

    public EntityMusketBullet(World world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
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
    public void tick() {
        super.tick();
        if (inGround) {
            if (random.nextInt(4) == 0) {
                level.addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
            return;
        }
        double speed = getTotalVelocity();
        double amount = 16.0;
        if (speed > 2.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                Vector3d pos = position().add(getDeltaMovement().scale(i1 / amount));
                level.addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        float damage = 20.0f + extraDamage;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
        }
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxLifetime() {
        return 200;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public float getGravity() {
        return (getTotalVelocity() < 3.0) ? 0.07f : 0.0f;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.musketBullet);
    }
}
