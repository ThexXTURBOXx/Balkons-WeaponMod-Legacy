package ckathode.weaponmod.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import ckathode.weaponmod.*;
import ckathode.weaponmod.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class EntityKnife extends EntityMaterialProjectile
{
    private int soundTimer;
    
    public EntityKnife(final World world) {
        super(world);
    }
    
    public EntityKnife(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }
    
    public EntityKnife(final World world, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.setPickupModeFromEntity((EntityLivingBase)(this.shootingEntity = shooter));
        this.setThrownItemStack(itemstack);
        this.soundTimer = 0;
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
        if (this.inGround || this.beenInGround) {
            return;
        }
        this.rotationPitch -= 70.0f;
        if (this.soundTimer >= 3) {
            if (!this.isInsideOfMaterial(Material.WATER)) {
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.6f + this.ticksInAir / 15.0f));
            }
            this.soundTimer = 0;
        }
        ++this.soundTimer;
    }
    
    @Override
    public void onEntityHit(final Entity entity) {
        if (this.world.isRemote) {
            return;
        }
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        }
        else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, ((IItemWeapon)this.thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 1.0f + this.getMeleeHitDamage(entity))) {
            this.applyEntityHitEffects(entity);
            if (this.thrownItem.getItemDamage() + 2 > this.thrownItem.getMaxDamage()) {
                this.thrownItem.shrink(1);
                this.setDead();
            }
            else {
                if (this.shootingEntity instanceof EntityLivingBase) {
                    this.thrownItem.damageItem(2, (EntityLivingBase)this.shootingEntity);
                }
                else {
                    this.thrownItem.attemptDamageItem(2, this.rand, null);
                }
                this.setVelocity(0.0, 0.0, 0.0);
            }
        }
        else {
            this.bounceBack();
        }
    }
    
    @Override
    public boolean aimRotation() {
        return this.beenInGround;
    }
    
    @Override
    public int getMaxLifetime() {
        return (this.pickupMode == 1 || this.pickupMode == 3) ? 0 : 1200;
    }
    
    @Override
    public int getMaxArrowShake() {
        return 4;
    }
    
    @Override
    public float getGravity() {
        return 0.03f;
    }
    
    @Override
    public float getAirResistance() {
        return 0.98f;
    }
}
