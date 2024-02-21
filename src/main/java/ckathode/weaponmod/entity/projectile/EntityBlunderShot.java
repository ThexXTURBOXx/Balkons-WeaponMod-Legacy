package ckathode.weaponmod.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import ckathode.weaponmod.*;
import ckathode.weaponmod.item.*;
import net.minecraft.entity.player.*;

public class EntityBlunderShot extends EntityProjectile
{
    public EntityBlunderShot(final World world) {
        super(world);
        this.setPickupMode(0);
    }
    
    public EntityBlunderShot(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPosition(x, y, z);
    }
    
    public EntityBlunderShot(final World world, final EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.shootingEntity = shooter;
    }
    
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4) {
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
        if (this.ticksInAir > 4) {
            this.setDead();
        }
    }
    
    @Override
    public void onEntityHit(final Entity entity) {
        final float damage = 4.0f + this.extraDamage;
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        }
        else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        final int prevhurtrestime = entity.hurtResistantTime;
        if (entity.attackEntityFrom(damagesource, damage)) {
            entity.hurtResistantTime = prevhurtrestime;
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
    public int getMaxArrowShake() {
        return 0;
    }
    
    @Override
    public float getGravity() {
        return (this.getTotalVelocity() < 2.0) ? 0.04f : 0.0f;
    }
    
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.blunderShot);
    }
    
    public static void fireSpreadShot(final World world, final EntityLivingBase entityliving, final RangedComponent item, final ItemStack itemstack) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        for (int i = 0; i < 10; ++i) {
            final EntityBlunderShot entity = new EntityBlunderShot(world, entityliving);
            entity.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 5.0f, 15.0f);
            if (item != null && !itemstack.isEmpty()) {
                item.applyProjectileEnchantments(entity, itemstack);
            }
            world.spawnEntity(entity);
        }
    }
    
    public static void fireSpreadShot(final World world, final double x, final double y, final double z) {
        for (int i = 0; i < 10; ++i) {
            world.spawnEntity(new EntityBlunderShot(world, x, y, z));
        }
    }
    
    public static void fireFromDispenser(final World world, final double d, final double d1, final double d2, final int i, final int j, final int k) {
        for (int i2 = 0; i2 < 10; ++i2) {
            final EntityBlunderShot entityblundershot = new EntityBlunderShot(world, d, d1, d2);
            entityblundershot.shoot(i, j, k, 5.0f, 15.0f);
            world.spawnEntity(entityblundershot);
        }
    }
}
