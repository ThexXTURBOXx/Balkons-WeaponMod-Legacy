package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpear extends EntityMaterialProjectile<EntitySpear> {
    public static final String NAME = "spear";

    public EntitySpear(final World world) {
        super(BalkonsWeaponMod.entitySpear, world);
    }

    public EntitySpear(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntitySpear(final World world, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        this.setPickupStatusFromEntity(shooter);
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
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource,
                ((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 1.0f + this.getMeleeHitDamage(entity))) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            if (this.thrownItem.getDamage() + 1 > this.thrownItem.getMaxDamage()) {
                this.thrownItem.shrink(1);
                this.remove();
            } else {
                Entity shooter = this.getShooter();
                if (shooter instanceof EntityLivingBase) {
                    this.thrownItem.damageItem(1, (EntityLivingBase) shooter);
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
        return (this.pickupStatus == PickupStatus.ALLOWED || this.pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }
}
