package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.entity.projectile.EntityProjectile;
import ckathode.weaponmod.item.IItemWeapon;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDummy extends Entity {
    public static final String NAME = "dummy";

    private static final int TIME_SINCE_HIT = 17;
    private static final int ROCK_DIRECTION = 18;
    private static final int CURRENT_DAMAGE = 19;
    private int durability;

    public EntityDummy(World world) {
        super(world);
        preventEntitySpawning = true;
        rotationPitch = -20.0f;
        setRotation(rotationYaw, rotationPitch);
        setSize(0.5f, 1.9f);
        durability = 50;
    }

    public EntityDummy(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
        motionX = 0.0;
        motionY = 0.0;
        motionZ = 0.0;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
    }

    @Override
    protected void entityInit() {
        dataWatcher.addObject(TIME_SINCE_HIT, 0);
        dataWatcher.addObject(ROCK_DIRECTION, (byte) 1);
        dataWatcher.addObject(CURRENT_DAMAGE, 0);
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.getBoundingBox();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damagesource, float damage) {
        if (worldObj.isRemote || isDead || damage <= 0.0f) {
            return false;
        }
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        int i = getCurrentDamage();
        i += (int) (damage * 5.0f);
        if (i > 50) {
            i = 50;
        }
        setCurrentDamage(i);
        setBeenAttacked();
        if (!(damagesource instanceof EntityDamageSource)) {
            durability -= (int) damage;
        } else if (damagesource instanceof WeaponDamageSource) {
            Entity entity = ((WeaponDamageSource) damagesource).getProjectile();
            if (MathHelper.sqrt_double(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ) > 0.5) {
                entity.motionX *= 0.10000000149011612;
                entity.motionY *= 0.10000000149011612;
                entity.motionZ *= 0.10000000149011612;
                playRandomHitSound();
            } else {
                entity.motionX = rand.nextFloat() - 0.5f;
                entity.motionY = rand.nextFloat() - 0.5f;
                entity.motionZ = rand.nextFloat() - 0.5f;
            }
        } else {
            playRandomHitSound();
        }
        if (durability <= 0 && worldObj.getGameRules().getGameRuleBooleanValue("doEntityDrops")) {
            dropAsItem(true, true);
        }
        setBeenAttacked();
        return false;
    }

    public void playRandomHitSound() {
        int i = rand.nextInt(2);
        if (i == 0) {
            worldObj.playSoundAtEntity(this, "step.cloth", 0.7F, 1F / rand.nextFloat() * 0.2F + 0.4F);
        } else {
            worldObj.playSoundAtEntity(this, "step.wood", 0.7F, 1F / rand.nextFloat() * 0.2F + 0.2F);
        }
    }

    @Override
    public void performHurtAnimation() {
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + 10);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - rand.nextInt(2));
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (onGround) {
            motionX = 0.0;
            motionY = 0.0;
            motionZ = 0.0;
        } else {
            motionX *= 0.99;
            motionZ *= 0.99;
            motionY -= 0.05;
            fallDistance += (float) (-motionY);
        }
        setRotation(rotationYaw, rotationPitch);
        moveEntity(0.0, motionY, 0.0);
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                EntityProjectile.getBoundingBox(this).expand(0.2D, 0.0D, 0.2D));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (entity != riddenByEntity && entity.canBePushed()) {
                    applyEntityCollision(entity);
                }
            }
        }
    }

    @Override
    public void fall(float f) {
        super.fall(f);
        if (!onGround) {
            return;
        }
        int i = MathHelper.floor_float(f);
        attackEntityFrom(DamageSource.fall, (float) i);
    }

    public void dropAsItem(boolean destroyed, boolean noCreative) {
        if (worldObj.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < rand.nextInt(8); ++i) {
                dropItem(Items.leather, 1);
            }
        } else if (noCreative) {
            dropItem(BalkonsWeaponMod.dummy, 1);
        }
        setDead();
    }

    @Override
    public boolean interactFirst(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null) {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemBow) {
                return false;
            }
        }
        if (entityplayer.capabilities.isCreativeMode) {
            dropAsItem(false, false);
            return true;
        }
        dropAsItem(false, true);
        return true;
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(BalkonsWeaponMod.dummy);
    }

    @Override
    protected void writeEntityToNBT(@Nonnull NBTTagCompound nbttagcompound) {
    }

    @Override
    protected void readEntityFromNBT(@Nonnull NBTTagCompound nbttagcompound) {
        setPosition(posX, posY, posZ);
        setRotation(rotationYaw, rotationPitch);
    }

    public void setTimeSinceHit(int i) {
        dataWatcher.updateObject(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        dataWatcher.updateObject(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        dataWatcher.updateObject(CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return dataWatcher.getWatchableObjectInt(TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return dataWatcher.getWatchableObjectByte(ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return dataWatcher.getWatchableObjectInt(CURRENT_DAMAGE);
    }

}
