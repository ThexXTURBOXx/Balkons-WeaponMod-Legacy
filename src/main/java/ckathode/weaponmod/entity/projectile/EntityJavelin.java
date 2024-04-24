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

public class EntityJavelin extends EntityProjectile<EntityJavelin> {
    public static final String NAME = "javelin";

    public EntityJavelin(EntityType<EntityJavelin> entityType, World world) {
        super(entityType, world);
    }

    public EntityJavelin(World world, double x, double y, double z) {
        this(BalkonsWeaponMod.entityJavelin, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(x, y, z);
    }

    public EntityJavelin(World world, LivingEntity shooter) {
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
        shoot(x, y, z, f3 * 1.1f, f4);
        Vector3d entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.isOnGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    public void onEntityHit(Entity entity) {
        double vel = getTotalVelocity();
        int damage = MathHelper.ceil(vel * (3.0 + extraDamage));
        if (isCritArrow()) {
            damage += random.nextInt(damage / 2 + 2);
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, (float) damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }

    @Override
    public float getGravity() {
        return 0.03f;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.javelin);
    }
}
