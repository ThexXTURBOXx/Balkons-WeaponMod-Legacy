package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityDummy extends Entity {
    public static final String NAME = "dummy";

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> ROCK_DIRECTION = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
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
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(ROCK_DIRECTION, (byte) 1);
        dataManager.register(CURRENT_DAMAGE, 0);
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.getEntityBoundingBox();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return getEntityBoundingBox();
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(@NotNull DamageSource damagesource, float damage) {
        if (world.isRemote || isDead || damage <= 0.0f) {
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
            if (MathHelper.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ) > 0.5) {
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
        if (durability <= 0 && world.getGameRules().getBoolean("doEntityDrops")) {
            dropAsItem(true, true);
        }
        setBeenAttacked();
        return false;
    }

    public void playRandomHitSound() {
        int i = rand.nextInt(2);
        if (i == 0) {
            playSound(SoundEvents.BLOCK_CLOTH_STEP, 0.7f, 1.0f / (rand.nextFloat() * 0.2f + 0.4f));
        } else {
            playSound(SoundEvents.BLOCK_WOOD_STEP, 0.7f, 1.0f / (rand.nextFloat() * 0.2f + 0.2f));
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
        move(MoverType.SELF, 0.0, motionY, 0.0);
        List<Entity> list = world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(0.2,
                0.0, 0.2), EntitySelectors.getTeamCollisionPredicate(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.isPassenger(this)) {
                    applyEntityCollision(entity);
                }
            }
        }
    }

    @Override
    public void fall(float f, float f1) {
        super.fall(f, f1);
        if (!onGround) {
            return;
        }
        int i = MathHelper.floor(f);
        attackEntityFrom(DamageSource.FALL, (float) i);
    }

    public void dropAsItem(boolean destroyed, boolean noCreative) {
        if (world.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < rand.nextInt(8); ++i) {
                dropItem(Items.LEATHER, 1);
            }
        } else if (noCreative) {
            dropItem(BalkonsWeaponMod.dummy, 1);
        }
        setDead();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer entityplayer, @NotNull EnumHand hand) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof ItemShield) {
                return false;
            }
        }
        if (entityplayer.isCreative()) {
            dropAsItem(false, false);
            return true;
        }
        dropAsItem(false, true);
        return true;
    }

    @NotNull
    @Override
    public ItemStack getPickedResult(@NotNull RayTraceResult target) {
        return new ItemStack(BalkonsWeaponMod.dummy);
    }

    @Override
    protected void writeEntityToNBT(@NotNull NBTTagCompound nbttagcompound) {
    }

    @Override
    protected void readEntityFromNBT(@NotNull NBTTagCompound nbttagcompound) {
        setPosition(posX, posY, posZ);
        setRotation(rotationYaw, rotationPitch);
    }

    public void setTimeSinceHit(int i) {
        dataManager.set(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        dataManager.set(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        dataManager.set(CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return dataManager.get(TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return dataManager.get(ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return dataManager.get(CURRENT_DAMAGE);
    }

}
