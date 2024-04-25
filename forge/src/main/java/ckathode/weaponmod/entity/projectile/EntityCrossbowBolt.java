package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityCrossbowBolt extends EntityProjectile<EntityCrossbowBolt> {
    public static final String NAME = "bolt";

    public EntityCrossbowBolt(EntityType<EntityCrossbowBolt> entityType, Level world) {
        super(entityType, world);
    }

    public EntityCrossbowBolt(Level world, double d, double d1, double d2) {
        this(BalkonsWeaponModForge.entityCrossbowBolt, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(d, d1, d2);
    }

    public EntityCrossbowBolt(Level world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3 entityMotion = entity.getDeltaMovement();
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
        return new ItemStack(BalkonsWeaponModForge.bolt);
    }
}
