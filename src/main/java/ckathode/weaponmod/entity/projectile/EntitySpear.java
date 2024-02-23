package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpear extends EntityMaterialProjectile {
    public EntitySpear(final World world) {
        super(world);
    }

    public EntitySpear(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntitySpear(final World world, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.setPickupModeFromEntity((EntityLivingBase) (this.shootingEntity = shooter));
        this.setThrownItemStack(itemstack);
    }

    @Override
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3,
                      final float f4) {
        final float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        final float y = -MathHelper.sin(f * 0.017453292f);
        final float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        this.shoot(x, y, z, f3, f4);
        this.motionX += entity.motionX;
        this.motionZ += entity.motionZ;
        if (!entity.onGround) {
            this.motionY += entity.motionY;
        }
    }

    @Override
    public void onEntityHit(final Entity entity) {
        if (this.world.isRemote) {
            return;
        }
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource,
                ((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 1.0f + this.getMeleeHitDamage(entity))) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            if (this.thrownItem.getItemDamage() + 1 > this.thrownItem.getMaxDamage()) {
                this.thrownItem.shrink(1);
                this.setDead();
            } else {
                if (this.shootingEntity instanceof EntityLivingBase) {
                    this.thrownItem.damageItem(1, (EntityLivingBase) this.shootingEntity);
                } else {
                    this.thrownItem.attemptDamageItem(1, this.rand, null);
                }
                this.setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            this.bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public int getMaxLifetime() {
        return (this.pickupMode == 1 || this.pickupMode == 3) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }
}
