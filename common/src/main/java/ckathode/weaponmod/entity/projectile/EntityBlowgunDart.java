package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.item.ItemBlowgunDart;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityBlowgunDart extends EntityMaterialProjectile<EntityBlowgunDart> {

    public static final String ID = "dart";
    public static final EntityType<EntityBlowgunDart> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityBlowgunDart::new);

    public EntityBlowgunDart(EntityType<EntityBlowgunDart> entityType, Level world) {
        super(entityType, world);
    }

    public EntityBlowgunDart(Level world, double d, double d1, double d2, @Nullable ItemStack firedFromWeapon) {
        super(TYPE, world, firedFromWeapon);
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(d, d1, d2);
    }

    public EntityBlowgunDart(Level world, LivingEntity shooter, ItemStack dart, @Nullable ItemStack firedFromWeapon) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), firedFromWeapon);
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(dart);
    }

    @Override
    protected boolean isDisabled() {
        return !WeaponModConfig.get().isEnabled("blowgun");
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

    @NotNull
    @Override
    public DamageSource getDamageSource(@Nullable Entity entity) {
        return damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
    }

    @Override
    public float getDamage(@Nullable Entity entity) {
        return 1.0f + extraDamage;
    }

    @Override
    public void onHitEntity(EntityHitResult result) {
        Entity entity = result.getEntity();
        if (hurtOrSimulate(entity)) {
            if (entity instanceof LivingEntity living && level() instanceof ServerLevel level) {
                if (living.isAffectedByPotions()) {
                    for (MobEffectInstance mei : ItemBlowgunDart.getEffects(getWeapon())) {
                        MobEffect effect = mei.getEffect().value();
                        if (effect.isInstantaneous()) {
                            effect.applyInstantaneousEffect(level, this, getOwner(), living, mei.getAmplifier(), 1);
                        } else {
                            living.addEffect(new MobEffectInstance(mei), getEffectSource());
                        }
                    }
                }
            }
            applyEntityHitEffects(entity);
            playHitSound();
            remove(RemovalReason.DISCARDED);
        } else {
            bounceBack();
        }
    }

    @Override
    public double getDefaultGravity() {
        return 0.05f;
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.2f / (random.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

}
