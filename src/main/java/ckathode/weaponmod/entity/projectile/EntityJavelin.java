package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityJavelin extends EntityProjectile {
    public EntityJavelin(final World world) {
        super(world);
    }

    public EntityJavelin(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPickupMode(1);
        this.setPosition(x, y, z);
    }

    public EntityJavelin(final World world, final EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.setPickupModeFromEntity((EntityLivingBase) (this.shootingEntity = shooter));
    }

    @Override
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3,
                      final float f4) {
        final float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        final float y = -MathHelper.sin(f * 0.017453292f);
        final float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        this.shoot(x, y, z, f3 * 1.1f, f4);
        this.motionX += entity.motionX;
        this.motionZ += entity.motionZ;
        if (!entity.onGround) {
            this.motionY += entity.motionY;
        }
    }

    @Override
    public void onEntityHit(final Entity entity) {
        final double vel = this.getTotalVelocity();
        int damage = MathHelper.ceil(vel * (3.0 + this.extraDamage));
        if (this.getIsCritical()) {
            damage += this.rand.nextInt(damage / 2 + 2);
        }
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, (float) damage)) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.setDead();
        } else {
            this.bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.9f));
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

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.javelin, 1);
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.javelin);
    }
}
