package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemBlowgunDart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
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
    public void onHitEntity(MovingObjectPosition mop) {
        Entity entity = mop.entityHit;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + extraDamage)) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase) entity;
                for (PotionEffect pe : ItemBlowgunDart.getEffects(getWeapon())) {
                    Potion type = Potion.potionTypes[pe.getPotionID()];
                    if (type.isInstant()) {
                        Entity shooter = getThrower();
                        type.affectEntity(shooter instanceof EntityLivingBase ? (EntityLivingBase) shooter : null,
                                living, pe.getAmplifier(), 1);
                    } else {
                        living.addPotionEffect(new PotionEffect(pe));
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
        worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.2F));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

}
