package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemFlail;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlail extends EntityMaterialProjectile {
    public static final String NAME = "flail";

    public boolean isSwinging;
    private float flailDamage;
    private double distanceTotal;
    private double distanceX;
    private double distanceY;
    private double distanceZ;

    public EntityFlail(World world) {
        super(world);
        ignoreFrustumCheck = true;
        flailDamage = 1.0f;
        distanceTotal = 0.0;
        distanceZ = distanceTotal;
        distanceY = distanceTotal;
        distanceX = distanceTotal;
    }

    public EntityFlail(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityFlail(World worldIn, EntityLivingBase shooter, ItemStack itemstack) {
        this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.3, shooter.posZ);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        distanceTotal = 0.0;
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
        swing(f, f1, f3, f4);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Entity shooter = getThrower();
        if (shooter != null) {
            distanceX = shooter.posX - posX;
            distanceY = shooter.posY - posY;
            distanceZ = shooter.posZ - posZ;
            distanceTotal =
                    Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
            if (distanceTotal > 3.0) {
                returnToOwner(true);
            }
            if (shooter instanceof EntityPlayer) {
                ItemStack itemstack = ((EntityPlayer) shootingEntity).getCurrentEquippedItem();
                if (itemstack == null || (thrownItem != null && itemstack.getItem() != thrownItem.getItem()) || !shooter.isEntityAlive()) {
                    pickUpByOwner();
                }
            }
        } else if (!worldObj.isRemote) {
            setDead();
        }
        if (inGround) {
            inGround = false;
            return;
        }
        returnToOwner(false);
    }

    public void returnToOwner(boolean looseFromGround) {
        if (looseFromGround) {
            inGround = false;
        }
        Entity shooter = getThrower();
        if (shooter == null) {
            return;
        }
        double targetPosX = shooter.posX;
        double targetPosY = EntityProjectile.getBoundingBox(shooter).minY + 0.4000000059604645;
        double targetPosZ = shooter.posZ;
        float f = 27.0f;
        float f2 = 2.0f;
        targetPosX += -Math.sin((shooter.rotationYaw + f) * 0.017453292f) * Math.cos(shooter.rotationPitch * 0.017453292f) * f2;
        targetPosZ += Math.cos((shooter.rotationYaw + f) * 0.017453292f) * Math.cos(shooter.rotationPitch * 0.017453292f) * f2;
        distanceX = targetPosX - posX;
        distanceY = targetPosY - posY;
        distanceZ = targetPosZ - posZ;
        distanceTotal =
                Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
        if (distanceTotal > 3.0) {
            posX = targetPosX;
            posY = targetPosY;
            posZ = targetPosZ;
        } else if (distanceTotal > 2.5) {
            isSwinging = false;
            motionX *= -0.5;
            motionY *= -0.5;
            motionZ *= -0.5;
        }
        if (!isSwinging) {
            float f3 = 0.2f;
            motionX = distanceX * f3 * distanceTotal;
            motionY = distanceY * f3 * distanceTotal;
            motionZ = distanceZ * f3 * distanceTotal;
        }
    }

    public void pickUpByOwner() {
        setDead();
        Entity shooter = getThrower();
        if (shooter instanceof EntityPlayer && thrownItem != null) {
            PlayerWeaponData.setFlailThrown((EntityPlayer) shooter, false);
        }
    }

    public void swing(float f, float f1, float f2, float f3) {
        if (isSwinging) {
            return;
        }
        worldObj.playSoundAtEntity(shootingEntity, "random.bow", 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setThrowableHeading(x, y, z, f2, f3);
        isSwinging = true;
        inGround = false;
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (entity == shootingEntity) {
            return;
        }
        Entity shooter = getDamagingEntity();
        DamageSource damagesource;
        if (shooter instanceof EntityLivingBase) {
            damagesource = DamageSource.causeMobDamage((EntityLivingBase) shooter);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, shooter);
        }
        if (entity.attackEntityFrom(damagesource, flailDamage + extraDamage)) {
            playHitSound();
            returnToOwner(true);
        } else {
            bounceBack();
        }
    }

    @Override
    public void bounceBack() {
        motionX *= -0.8;
        motionY *= -0.8;
        motionZ *= -0.8;
        rotationYaw += 180.0f;
        prevRotationYaw += 180.0f;
        ticksInAir = 0;
    }

    @Override
    public void playHitSound() {
        if (inGround) {
            return;
        }
        worldObj.playSoundAtEntity(this, "game.player.hurt", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
    }

    @Override
    public void setThrownItemStack(@Nonnull ItemStack itemstack) {
        if (!(itemstack.getItem() instanceof ItemFlail)) {
            return;
        }
        super.setThrownItemStack(itemstack);
        flailDamage = ((ItemFlail) itemstack.getItem()).getFlailDamage();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setFloat("fDmg", flailDamage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        flailDamage = nbttagcompound.getFloat("fDmg");
    }

    @Override
    public void onCollideWithPlayer(@Nonnull EntityPlayer entityplayer) {
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }
}
