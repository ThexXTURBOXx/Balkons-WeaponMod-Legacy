package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityCrossbowBolt extends EntityProjectile<EntityCrossbowBolt> {
    public static final String NAME = "bolt";

    public EntityCrossbowBolt(EntityType<EntityCrossbowBolt> entityType, World world) {
        super(entityType, world);
    }

    public EntityCrossbowBolt(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityCrossbowBolt, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(d, d1, d2);
    }

    public EntityCrossbowBolt(World world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
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
    public void onEntityHit(Entity entity) {
        float vel = (float) getTotalVelocity();
        float damage = vel * 4.0f + extraDamage;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, damage)) {
            if (entity instanceof LivingEntity && level.isClientSide) {
                ((LivingEntity) entity).setArrowCount(((LivingEntity) entity).getArrowCount() + 1);
            }
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.4f));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.bolt);
    }
}
