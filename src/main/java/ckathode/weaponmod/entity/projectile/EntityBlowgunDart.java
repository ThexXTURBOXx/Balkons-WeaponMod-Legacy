package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemBlowgunDart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBlowgunDart extends EntityMaterialProjectile<EntityBlowgunDart> {
    public static final String NAME = "dart";

    public EntityBlowgunDart(EntityType<EntityBlowgunDart> entityType, World world) {
        super(entityType, world);
    }

    public EntityBlowgunDart(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityBlowgunDart, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(World world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
    }

    @Override
    protected boolean isDisabled() {
        return !BalkonsWeaponMod.instance.modConfig.isEnabled("blowgun");
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
    public void onEntityHit(EntityRayTraceResult rayTraceResult) {
        Entity entity = rayTraceResult.getEntity();
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + extraDamage)) {
            if (entity instanceof LivingEntity) {
                LivingEntity living = (LivingEntity) entity;
                if (living.canBeHitWithPotion()) {
                    for (EffectInstance ei : ItemBlowgunDart.getEffects(getWeapon())) {
                        Effect effect = ei.getPotion();
                        if (effect.isInstant()) {
                            effect.affectEntity(this, getShooter(), living, ei.getAmplifier(), 1);
                        } else {
                            living.addPotionEffect(new EffectInstance(ei));
                        }
                    }
                }
            }
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
        } else {
            bounceBack();
        }
    }

    @Override
    public float getGravity() {
        return 0.05f;
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

}
