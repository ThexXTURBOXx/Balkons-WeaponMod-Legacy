package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.entity.projectile.EntityProjectile;
import ckathode.weaponmod.item.WMItem;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EntityCannon extends EntityBoat {
    public static final String NAME = "cannon";

    private static final int TIME_SINCE_HIT = 20;
    private static final int ROCK_DIRECTION = 21;
    private static final int CURRENT_DAMAGE = 22;
    private static final int LOADED = 23;
    private static final int LOAD_TIMER = 24;
    private static final int SUPER_POWERED = 25;

    public EntityCannon(World world) {
        super(world);
        preventEntitySpawning = true;
        rotationPitch = -20.0f;
        setRotation(rotationYaw = -180.0f, rotationPitch);
        setSize(1.5f, 1.0f);
    }

    public EntityCannon(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1 + yOffset, d2);
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
        dataWatcher.addObject(LOADED, (byte) 0);
        dataWatcher.addObject(LOAD_TIMER, 0);
        dataWatcher.addObject(SUPER_POWERED, (byte) 0);
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return EntityProjectile.getBoundingBox(entity);
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return 0.15;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damagesource, float damage) {
        if (worldObj.isRemote || isDead) {
            return true;
        }
        if (damagesource instanceof EntityDamageSourceIndirect && damagesource.getSourceOfDamage() != null) {
            if (damagesource.getSourceOfDamage() == riddenByEntity) {
                return true;
            }
        } else if (damagesource instanceof EntityDamageSource && damagesource.damageType.equals("player")) {
            EntityPlayer player = (EntityPlayer) damagesource.getSourceOfDamage();
            if (player != null && player.inventory.getCurrentItem() == null) {
                if (!player.capabilities.isCreativeMode) {
                    dropItem(BalkonsWeaponMod.cannon, 1);
                    if (isLoaded() || isLoading()) {
                        dropItem(BalkonsWeaponMod.cannonBall, 1);
                        dropItem(Items.gunpowder, 1);
                    }
                }
                setDead();
                return true;
            }
        }
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + (int) damage * 5);
        setBeenAttacked();
        if (getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                dropItemWithChance(Items.iron_ingot, (int) damage, 1);
            }
            dropItemWithChance(Items.flint, (int) damage, 1);
            dropItemWithChance(Item.getItemFromBlock(Blocks.log), (int) damage, 1);
            if (isLoaded() || isLoading()) {
                dropItem(BalkonsWeaponMod.cannonBall, 1);
                dropItem(Items.gunpowder, 1);
            }
            setDead();
        }
        return true;
    }

    public void dropItemWithChance(Item item, int chance, int amount) {
        if (rand.nextInt(chance) < 10) {
            dropItem(item, amount);
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
        onEntityUpdate();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - rand.nextInt(2));
        }
        motionY -= 0.1;
        if (onGround) {
            motionX *= 0.1;
            motionZ *= 0.1;
        }
        motionX *= 0.98;
        motionY *= 0.98;
        motionZ *= 0.98;
        if (!onGround) {
            fallDistance += (float) (-motionY);
        }
        if (riddenByEntity != null) {
            float yaw = riddenByEntity.rotationYaw;
            float pitch = riddenByEntity.rotationPitch;
            rotationYaw = yaw % 360.0f;
            rotationPitch = pitch;
        }
        setRotation(rotationYaw, rotationPitch);
        moveEntity(motionX, motionY, motionZ);
        @SuppressWarnings("unchecked")
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                EntityProjectile.getBoundingBox(this).expand(0.2, 0.0, 0.2));
        if (list != null) {
            for (Entity entity : list) {
                if (entity != riddenByEntity && entity.canBePushed()) {
                    applyEntityCollision(entity);
                }
            }
        }
        if (riddenByEntity != null) {
            if (riddenByEntity.isDead) {
                riddenByEntity = null;
            }
        }
        if (isLoading()) {
            setLoadTimer(getLoadTimer() - 1);
            handleReloadTime();
        }
    }

    @Override
    protected void fall(float f) {
        super.fall(f);
        int i = MathHelper.floor_float(f);
        i *= 2;
        attackEntityFrom(DamageSource.fall, (float) i);
    }

    @Override
    protected void updateFallState(double tickFallDist, boolean isOnGround) {
        int x = MathHelper.floor_double(posX);
        int y = MathHelper.floor_double(posY);
        int z = MathHelper.floor_double(posZ);
        if (isOnGround) {
            if (fallDistance > 3.0F) {
                fall(fallDistance);
                if (!worldObj.isRemote && !isDead) {
                    setDead();
                    for (int j = 0; j < 5; ++j) {
                        // Yes, one iron ingot should vanish as penalty...
                        func_145778_a(Items.iron_ingot, 1, 0.0F);
                    }
                    func_145778_a(Items.flint, 1, 0.0F);
                    func_145778_a(Item.getItemFromBlock(Blocks.log), 1, 0.0F);
                    if (isLoaded() || isLoading()) {
                        func_145778_a(BalkonsWeaponMod.cannonBall, 1, 0.0F);
                        func_145778_a(Items.gunpowder, 1, 0.0F);
                    }
                }

                fallDistance = 0.0F;
            }
        } else if (worldObj.getBlock(x, y - 1, z).getMaterial() != Material.water && tickFallDist < 0.0) {
            fallDistance = (float) ((double) fallDistance - tickFallDist);
        }
    }

    public void handleReloadTime() {
        int l = getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                worldObj.playSoundAtEntity(this, "tile.piston.in", 0.5f, 1.2f / (rand.nextFloat() * 0.8f + 0.6f));
            } else if (l == 40) {
                worldObj.playSoundAtEntity(this, "random.breath", 0.7f,
                        1.2f / (rand.nextFloat() * 0.2f + 10.0f));
            }
        } else {
            setReloadInfo(true, 0);
        }
    }

    public void fireCannon() {
        if (!isLoaded()) {
            return;
        }
        if (!worldObj.isRemote) {
            EntityCannonBall entitycannonball = new EntityCannonBall(worldObj, this,
                    riddenByEntity.rotationPitch, riddenByEntity.rotationYaw, isSuperPowered());
            worldObj.spawnEntityInWorld(entitycannonball);
        }
        setReloadInfo(false, 0);
        fireEffects();
    }

    public void fireEffects() {
        worldObj.playSoundAtEntity(this, "random.explode", 8.0F, 1.0F / (rand.nextFloat() * 0.8F + 0.9F));
        worldObj.playSoundAtEntity(this, "ambient.weather.thunder", 8.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
        float yaw = (float) Math.toRadians(rotationYaw);
        double d = -MathHelper.sin(yaw) * -1.0f;
        double d2 = MathHelper.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            worldObj.spawnParticle("smoke",
                    posX + d + rand.nextDouble() * 0.5 - 0.25, posY + rand.nextDouble() * 0.5,
                    posZ + d2 + rand.nextDouble() * 0.5 - 0.25, rand.nextDouble() * 0.1 - 0.05,
                    rand.nextDouble() * 0.1 - 0.05, rand.nextDouble() * 0.1 - 0.05);
        }
        if (riddenByEntity != null) {
            riddenByEntity.rotationPitch += 10.0f;
        }
        attackEntityFrom(DamageSource.generic, 2.0f);
    }

    public void setReloadInfo(boolean loaded, int reloadtime) {
        setLoaded(loaded);
        setLoadTimer(reloadtime);
    }

    public void startLoadingCannon() {
        if (isLoaded() && !isLoading()) {
            return;
        }
        setReloadInfo(false, 100);
    }

    @Override
    public void updateRiderPosition() {
        if (riddenByEntity != null) {
            float f = -0.85f;
            float f2 = (float) ((isDead ? 0.01 : getMountedYOffset()) + riddenByEntity.getYOffset());
            Vec3 vec3d = Vec3.createVectorHelper(f, 0.0, 0.0);
            vec3d.rotateAroundY(-rotationYaw * 0.017453292f - 1.5707964f);
            riddenByEntity.setPosition(posX + vec3d.xCoord, posY + f2, posZ + vec3d.zCoord);
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setFloat("falld", fallDistance);
        nbttagcompound.setBoolean("load", isLoaded());
        nbttagcompound.setShort("ldtime", (short) getLoadTimer());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        setPosition(posX, posY, posZ);
        setRotation(rotationYaw, rotationPitch);
        fallDistance = nbttagcompound.getFloat("falld");
        setLoaded(nbttagcompound.getBoolean("load"));
        setLoadTimer(nbttagcompound.getShort("ldtime"));
    }

    @Override
    public boolean interactFirst(@Nonnull EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.getCurrentEquippedItem();
        if (itemstack != null && itemstack.getItem() == BalkonsWeaponMod.cannonBall && !isLoaded() && !isLoading()
            && (entityplayer.capabilities.isCreativeMode || consumeAmmo(entityplayer, Items.gunpowder))) {
            if (entityplayer.capabilities.isCreativeMode || consumeAmmo(entityplayer, BalkonsWeaponMod.cannonBall)) {
                startLoadingCannon();
                return true;
            }
            dropItem(Items.gunpowder, 1);
        } else {
            if (riddenByEntity != null && riddenByPlayer() && notThisPlayer(entityplayer)) {
                return true;
            }
            if (!worldObj.isRemote && !entityplayer.isSneaking()) {
                entityplayer.mountEntity(this);
            }
        }
        return true;
    }

    private ItemStack findAmmo(EntityPlayer player, Item itemAmmo) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (isAmmo(itemstack, itemAmmo)) {
                return itemstack;
            }
        }
        return null;
    }

    protected boolean isAmmo(@Nullable ItemStack stack, Item itemAmmo) {
        return stack != null && stack.getItem() == itemAmmo;
    }

    protected boolean consumeAmmo(EntityPlayer entityplayer, Item itemAmmo) {
        ItemStack stackAmmo = findAmmo(entityplayer, itemAmmo);
        if (stackAmmo == null) {
            return false;
        }
        stackAmmo.splitStack(1);
        if (stackAmmo.stackSize <= 0) {
            WMItem.deleteStack(entityplayer.inventory, stackAmmo);
        }
        return true;
    }

    public boolean riddenByPlayer() {
        Entity entity = riddenByEntity;
        return entity instanceof EntityPlayer;
    }

    public boolean notThisPlayer(Entity player) {
        Entity entity = riddenByEntity;
        return entity != player;
    }

    @Override
    public void onStruckByLightning(@Nonnull EntityLightningBolt entitylightningbolt) {
        dealFireDamage(100);
        setSuperPowered(true);
    }

    @Override
    public ItemStack getPickedResult(MovingObjectPosition target) {
        return new ItemStack(BalkonsWeaponMod.cannon);
    }

    public void setLoaded(boolean flag) {
        dataWatcher.updateObject(LOADED, (byte) (flag ? 1 : 0));
    }

    public void setLoadTimer(int time) {
        dataWatcher.updateObject(LOAD_TIMER, time);
    }

    public void setSuperPowered(boolean flag) {
        dataWatcher.updateObject(SUPER_POWERED, (byte) (flag ? 1 : 0));
    }

    public boolean isLoading() {
        return getLoadTimer() > 0;
    }

    public boolean isLoaded() {
        return dataWatcher.getWatchableObjectByte(LOADED) != 0;
    }

    public int getLoadTimer() {
        return dataWatcher.getWatchableObjectInt(LOAD_TIMER);
    }

    public boolean isSuperPowered() {
        return dataWatcher.getWatchableObjectByte(SUPER_POWERED) != 0;
    }

    @Override
    public void setTimeSinceHit(int i) {
        dataWatcher.updateObject(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        dataWatcher.updateObject(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        dataWatcher.updateObject(CURRENT_DAMAGE, i);
    }

    @Override
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
