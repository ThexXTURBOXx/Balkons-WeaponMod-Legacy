package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntitySpear extends EntityMaterialProjectile {
    public static final String NAME = "spear";

    public EntitySpear(World world) {
        super(world);
    }

    public EntitySpear(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntitySpear(World world, EntityLivingBase shooter, ItemStack itemstack) {
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
    public void onEntityHit(Entity entity) {
        if (worldObj.isRemote) {
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        Item item = thrownItem.getItem();
        if (item instanceof IItemWeapon && entity.attackEntityFrom(damagesource,
                ((IItemWeapon) item).getMeleeComponent().getEntityDamage() + getMeleeHitDamage(entity))) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getItemDamage() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.splitStack(1);
                if (thrownItem.stackSize <= 0) {
                    setThrownItemStack(null);
                }
                setDead();
            } else {
                thrownItem.attemptDamageItem(1, rand);
                if (thrownItem.stackSize <= 0) {
                    setThrownItemStack(null);
                }
                setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }
}
