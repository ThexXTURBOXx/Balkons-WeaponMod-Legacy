package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemFlail;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityFlail extends EntityMaterialProjectile<EntityFlail> {
    public static final String NAME = "flail";

    public boolean isSwinging;
    private float flailDamage;
    private double distanceTotal;
    private double distanceX;
    private double distanceY;
    private double distanceZ;

    public EntityFlail(final World world) {
        super(BalkonsWeaponMod.entityFlail, world);
        this.ignoreFrustumCheck = true;
        this.flailDamage = 1.0f;
        this.distanceTotal = 0.0;
        this.distanceZ = distanceTotal;
        this.distanceY = distanceTotal;
        this.distanceX = distanceTotal;
    }

    public EntityFlail(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntityFlail(final World worldIn, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.3, shooter.posZ);
        setShooter(shooter);
        this.setPickupStatusFromEntity(shooter);
        this.setThrownItemStack(itemstack);
        this.distanceTotal = 0.0;
    }

    @Override
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3,
                      final float f4) {
        this.motionX += entity.motionX;
        this.motionZ += entity.motionZ;
        if (!entity.onGround) {
            this.motionY += entity.motionY;
        }
        this.swing(f, f1, f3, f4);
    }

    @Override
    public void tick() {
        super.tick();
        Entity shooter = getShooter();
        if (shooter != null) {
            this.distanceX = shooter.posX - this.posX;
            this.distanceY = shooter.posY - this.posY;
            this.distanceZ = shooter.posZ - this.posZ;
            this.distanceTotal =
                    Math.sqrt(this.distanceX * this.distanceX + this.distanceY * this.distanceY + this.distanceZ * this.distanceZ);
            if (this.distanceTotal > 3.0) {
                this.returnToOwner(true);
            }
            if (shooter instanceof EntityPlayer) {
                final ItemStack itemstack = ((EntityPlayer) shooter).getHeldItemMainhand();
                if (itemstack.isEmpty() || (this.thrownItem != null && itemstack.getItem() != this.thrownItem.getItem()) || !shooter.isAlive()) {
                    this.pickUpByOwner();
                }
            }
        } else {
            this.remove();
        }
        if (this.inGround) {
            this.inGround = false;
            return;
        }
        this.returnToOwner(false);
    }

    public void returnToOwner(final boolean looseFromGround) {
        if (looseFromGround) {
            this.inGround = false;
        }
        Entity shooter = getShooter();
        if (shooter == null) {
            return;
        }
        double targetPosX = shooter.posX;
        final double targetPosY = shooter.getBoundingBox().minY + 0.4000000059604645;
        double targetPosZ = shooter.posZ;
        final float f = 27.0f;
        final float f2 = 2.0f;
        targetPosX += -Math.sin((shooter.rotationYaw + f) * 0.017453292f) * Math.cos(shooter.rotationPitch * 0.017453292f) * f2;
        targetPosZ += Math.cos((shooter.rotationYaw + f) * 0.017453292f) * Math.cos(shooter.rotationPitch * 0.017453292f) * f2;
        this.distanceX = targetPosX - this.posX;
        this.distanceY = targetPosY - this.posY;
        this.distanceZ = targetPosZ - this.posZ;
        this.distanceTotal =
                Math.sqrt(this.distanceX * this.distanceX + this.distanceY * this.distanceY + this.distanceZ * this.distanceZ);
        if (this.distanceTotal > 3.0) {
            this.posX = targetPosX;
            this.posY = targetPosY;
            this.posZ = targetPosZ;
        } else if (this.distanceTotal > 2.5) {
            this.isSwinging = false;
            this.motionX *= -0.5;
            this.motionY *= -0.5;
            this.motionZ *= -0.5;
        }
        if (!this.isSwinging) {
            final float f3 = 0.2f;
            this.motionX = this.distanceX * f3 * this.distanceTotal;
            this.motionY = this.distanceY * f3 * this.distanceTotal;
            this.motionZ = this.distanceZ * f3 * this.distanceTotal;
        }
    }

    public void pickUpByOwner() {
        this.remove();
        Entity shooter = getShooter();
        if (shooter instanceof EntityPlayer && this.thrownItem != null) {
            PlayerWeaponData.setFlailThrown((EntityPlayer) shooter, false);
        }
    }

    public void swing(final float f, final float f1, final float f2, final float f3) {
        if (this.isSwinging) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5f, 0.4f / (this.rand.nextFloat() * 0.4f + 0.8f));
        final float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        final float y = -MathHelper.sin(f * 0.017453292f);
        final float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        this.shoot(x, y, z, f2, f3);
        this.isSwinging = true;
        this.inGround = false;
    }

    @Override
    public void onEntityHit(final Entity entity) {
        if (entity.getUniqueID().equals(this.shootingEntity)) {
            return;
        }
        Entity shooter = getDamagingEntity();
        DamageSource damagesource;
        if (shooter instanceof EntityLivingBase) {
            damagesource = DamageSource.causeMobDamage((EntityLivingBase) shooter);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shooter);
        }
        if (entity.attackEntityFrom(damagesource, this.flailDamage + this.extraDamage)) {
            this.playHitSound();
            this.returnToOwner(true);
        } else {
            this.bounceBack();
        }
    }

    @Override
    public void bounceBack() {
        this.motionX *= -0.8;
        this.motionY *= -0.8;
        this.motionZ *= -0.8;
        this.rotationYaw += 180.0f;
        this.prevRotationYaw += 180.0f;
        this.ticksInAir = 0;
    }

    @Override
    public void playHitSound() {
        if (this.inGround) {
            return;
        }
        this.playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0f, this.rand.nextFloat() * 0.4f + 0.8f);
    }

    @Override
    public void setThrownItemStack(@Nonnull final ItemStack itemstack) {
        if (!(itemstack.getItem() instanceof ItemFlail)) {
            return;
        }
        super.setThrownItemStack(itemstack);
        this.flailDamage = ((ItemFlail) itemstack.getItem()).getFlailDamage();
    }

    @Override
    public void writeAdditional(final NBTTagCompound nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putFloat("fDmg", this.flailDamage);
    }

    @Override
    public void readAdditional(final NBTTagCompound nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.flailDamage = nbttagcompound.getFloat("fDmg");
    }

    @Override
    public void onCollideWithPlayer(@Nonnull final EntityPlayer entityplayer) {
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }
}
