package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemBlowgunDart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBlowgunDart extends EntityMaterialProjectile {
    public static final String NAME = "dart";

    public EntityBlowgunDart(World world) {
        super(world);
    }

    public EntityBlowgunDart(World world, double d, double d1, double d2) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(World world, EntityLivingBase shooter, ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setThrowableHeading(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
    }

    @Override
    public void onHitEntity(RayTraceResult raytraceResult) {
        Entity entity = raytraceResult.entityHit;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + extraDamage)) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) entity;
                if (living.canBeHitWithPotion()) {
                    for (PotionEffect pe : ItemBlowgunDart.getEffects(getWeapon().orNull())) {
                        Potion potion = pe.getPotion();
                        if (potion.isInstant()) {
                            potion.affectEntity(this, getThrower(), living, pe.getAmplifier(), 1);
                        } else {
                            living.addPotionEffect(new PotionEffect(pe));
                        }
                    }
                }
            }
            applyEntityHitEffects(entity);
            playHitSound();
            setDead();
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
