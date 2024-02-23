package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMusketBullet extends EntityProjectile {
    public EntityMusketBullet(final World world) {
        super(world);
        this.setPickupMode(0);
    }

    public EntityMusketBullet(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntityMusketBullet(final World world, final EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.shootingEntity = shooter;
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
    public void onUpdate() {
        super.onUpdate();
        if (this.inGround) {
            if (this.rand.nextInt(4) == 0) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0, 0.0,
                        0.0);
            }
            return;
        }
        final double speed = this.getTotalVelocity();
        final double amount = 16.0;
        if (speed > 2.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.motionX * i1 / amount,
                        this.posY + this.motionY * i1 / amount, this.posZ + this.motionZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(final Entity entity) {
        final float damage = 20.0f + this.extraDamage;
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, damage)) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.setDead();
        }
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxLifetime() {
        return 200;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public float getGravity() {
        return (this.getTotalVelocity() < 3.0) ? 0.07f : 0.0f;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.musketBullet);
    }
}
