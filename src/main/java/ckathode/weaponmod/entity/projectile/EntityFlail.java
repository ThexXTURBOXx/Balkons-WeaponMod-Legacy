package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.ItemFlail;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFlail extends EntityMaterialProjectile<EntityFlail> {
    public static final String NAME = "flail";

    public boolean isSwinging;
    private float flailDamage;
    private double distanceTotal;
    private double distanceX;
    private double distanceY;
    private double distanceZ;

    public EntityFlail(EntityType<EntityFlail> entityType, World world) {
        super(entityType, world);
        ignoreFrustumCheck = true;
        flailDamage = 1.0f;
        distanceTotal = 0.0;
        distanceZ = distanceTotal;
        distanceY = distanceTotal;
        distanceX = distanceTotal;
    }

    public EntityFlail(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityFlail, world);
        setPosition(d, d1, d2);
    }

    public EntityFlail(World worldIn, LivingEntity shooter, ItemStack itemstack) {
        this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.3, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        distanceTotal = 0.0;
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3, float f4) {
        Vec3d entityMotion = entity.getMotion();
        setMotion(getMotion().add(entityMotion.x, entity.onGround ? 0 : entityMotion.y, entityMotion.z));
        swing(f, f1, f3, f4);
    }

    @Override
    public void tick() {
        super.tick();
        Entity shooter = getShooter();
        if (shooter != null) {
            distanceX = shooter.posX - posX;
            distanceY = shooter.posY - posY;
            distanceZ = shooter.posZ - posZ;
            distanceTotal =
                    Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
            if (distanceTotal > 3.0) {
                returnToOwner(true);
            }
            if (shooter instanceof PlayerEntity) {
                ItemStack itemstack = ((PlayerEntity) shooter).getHeldItemMainhand();
                if (itemstack.isEmpty() || (thrownItem != null && itemstack.getItem() != thrownItem.getItem()) || !shooter.isAlive()) {
                    pickUpByOwner();
                }
            }
        } else if (!world.isRemote) {
            remove();
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
        Entity shooter = getShooter();
        if (shooter == null) {
            return;
        }
        double targetPosX = shooter.posX;
        double targetPosY = shooter.getBoundingBox().minY + 0.4000000059604645;
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
            setMotion(getMotion().scale(-0.5));
        }
        if (!isSwinging) {
            float f3 = 0.2f;
            setMotion(distanceX * f3 * distanceTotal, distanceY * f3 * distanceTotal, distanceZ * f3 * distanceTotal);
        }
    }

    public void pickUpByOwner() {
        remove();
        Entity shooter = getShooter();
        if (shooter instanceof PlayerEntity && thrownItem != null) {
            PlayerWeaponData.setFlailThrown((PlayerEntity) shooter, false);
        }
    }

    public void swing(float f, float f1, float f2, float f3) {
        if (isSwinging) {
            return;
        }
        playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5f, 0.4f / (rand.nextFloat() * 0.4f + 0.8f));
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f2, f3);
        isSwinging = true;
        inGround = false;
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (entity.getUniqueID().equals(shootingEntity)) {
            return;
        }
        Entity shooter = getDamagingEntity();
        DamageSource damagesource;
        if (shooter instanceof LivingEntity) {
            damagesource = DamageSource.causeMobDamage((LivingEntity) shooter);
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
        setMotion(getMotion().scale(-0.8));
        rotationYaw += 180.0f;
        prevRotationYaw += 180.0f;
        ticksInAir = 0;
    }

    @Override
    public void playHitSound() {
        if (inGround) {
            return;
        }
        playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0f, rand.nextFloat() * 0.4f + 0.8f);
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
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putFloat("fDmg", flailDamage);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        flailDamage = nbttagcompound.getFloat("fDmg");
    }

    @Override
    public void onCollideWithPlayer(@Nonnull PlayerEntity entityplayer) {
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }
}
