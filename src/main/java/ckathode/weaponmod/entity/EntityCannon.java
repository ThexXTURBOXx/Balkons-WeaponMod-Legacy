package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityCannon extends EntityBoat {
    public static final String NAME = "cannon";

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> ROCK_DIRECTION = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> LOADED = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> LOAD_TIMER = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> SUPER_POWERED = EntityDataManager.createKey(EntityCannon.class,
            DataSerializers.BYTE);

    public EntityCannon(World world) {
        super(world);
        preventEntitySpawning = true;
        rotationPitch = -20.0f;
        setRotation(rotationYaw = -180.0f, rotationPitch);
        setSize(1.5f, 1.0f);
    }

    public EntityCannon(World world, double d, double d1, double d2) {
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
        dataManager.register(LOADED, (byte) 0);
        dataManager.register(LOAD_TIMER, 0);
        dataManager.register(SUPER_POWERED, (byte) 0);
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
    public double getMountedYOffset() {
        return 0.35;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource damagesource, float damage) {
        if (worldObj.isRemote || isDead) {
            return true;
        }
        if (damagesource instanceof EntityDamageSourceIndirect && damagesource.getSourceOfDamage() != null) {
            if (isPassenger(damagesource.getSourceOfDamage())) {
                return true;
            }
        } else if (damagesource instanceof EntityDamageSource && damagesource.damageType.equals("player")) {
            EntityPlayer player = (EntityPlayer) damagesource.getSourceOfDamage();
            if (player != null && player.inventory.getCurrentItem() == null) {
                if (!player.isCreative()) {
                    dropItem(BalkonsWeaponMod.cannon, 1);
                    if (isLoaded() || isLoading()) {
                        dropItem(BalkonsWeaponMod.cannonBall, 1);
                        dropItem(Items.GUNPOWDER, 1);
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
                dropItemWithChance(Items.IRON_INGOT, (int) damage, 1);
            }
            dropItemWithChance(Items.FLINT, (int) damage, 1);
            dropItemWithChance(Item.getItemFromBlock(Blocks.LOG), (int) damage, 1);
            if (isLoaded() || isLoading()) {
                dropItem(BalkonsWeaponMod.cannonBall, 1);
                dropItem(Items.GUNPOWDER, 1);
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
        if (isBeingRidden()) {
            EntityLivingBase entitylivingbase = (EntityLivingBase) getControllingPassenger();
            float yaw = entitylivingbase.rotationYaw;
            float pitch = entitylivingbase.rotationPitch;
            rotationYaw = yaw % 360.0f;
            rotationPitch = pitch;
        }
        setRotation(rotationYaw, rotationPitch);
        moveEntity(motionX, motionY, motionZ);
        List<Entity> list = worldObj.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.2,
                0.0, 0.2), EntitySelectors.getTeamCollisionPredicate(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.isPassenger(this) && !entity.isRiding()) {
                    applyEntityCollision(entity);
                }
            }
        }
        if (isLoading()) {
            setLoadTimer(getLoadTimer() - 1);
            handleReloadTime();
        }
    }

    @Override
    public void fall(float f, float f1) {
        super.fall(f, f1);
        int i = MathHelper.floor_float(f);
        i *= 2;
        attackEntityFrom(DamageSource.fall, (float) i);
    }

    @Override
    protected void updateFallState(double y, boolean isOnGround, @NotNull IBlockState state, @NotNull BlockPos pos) {
        if (isRiding()) return;
        if (isOnGround) {
            if (fallDistance > 3.0f) {
                fall(fallDistance, 1.0f);
                if (!worldObj.isRemote && !isDead) {
                    setDead();
                    if (worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        for (int j = 0; j < 5; ++j) {
                            // Yes, one iron ingot should vanish as penalty...
                            dropItemWithOffset(Items.IRON_INGOT, 1, 0.0F);
                        }
                        dropItemWithOffset(Items.FLINT, 1, 0.0F);
                        dropItemWithOffset(Item.getItemFromBlock(Blocks.LOG), 1, 0.0F);
                        if (isLoaded() || isLoading()) {
                            dropItemWithOffset(BalkonsWeaponMod.cannonBall, 1, 0.0F);
                            dropItemWithOffset(Items.GUNPOWDER, 1, 0.0F);
                        }
                    }
                }
            }
            fallDistance = 0.0f;
        } else if (worldObj.getBlockState(new BlockPos(this).down()).getMaterial() != Material.WATER && y < 0.0) {
            fallDistance = (float) ((double) fallDistance - y);
        }
    }

    public void handleReloadTime() {
        int l = getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                playSound(SoundEvents.BLOCK_PISTON_CONTRACT, 0.5f, 1.2f / (rand.nextFloat() * 0.8f + 0.6f));
            } else if (l == 40) {
                playSound(SoundEvents.ENTITY_PLAYER_BREATH, 0.7f, 1.2f / (rand.nextFloat() * 0.2f + 10.0f));
            }
        } else {
            setReloadInfo(true, 0);
        }
    }

    public void fireCannon() {
        if (!isLoaded()) {
            return;
        }
        Entity entityPassenger = getPassengers().isEmpty() ? null : getPassengers().get(0);
        if (!worldObj.isRemote) {
            EntityCannonBall entitycannonball = new EntityCannonBall(worldObj, this,
                    entityPassenger.rotationPitch, entityPassenger.rotationYaw, isSuperPowered());
            worldObj.spawnEntityInWorld(entitycannonball);
        }
        setReloadInfo(false, 0);
        fireEffects();
    }

    public void fireEffects() {
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 8.0f, 1.0f / (rand.nextFloat() * 0.8f + 0.9f));
        playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
        float yaw = (float) Math.toRadians(rotationYaw);
        double d = -MathHelper.sin(yaw) * -1.0f;
        double d2 = MathHelper.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    posX + d + rand.nextDouble() * 0.5 - 0.25, posY + rand.nextDouble() * 0.5,
                    posZ + d2 + rand.nextDouble() * 0.5 - 0.25, rand.nextDouble() * 0.1 - 0.05,
                    rand.nextDouble() * 0.1 - 0.05, rand.nextDouble() * 0.1 - 0.05);
        }
        if (isBeingRidden()) {
            for (Entity entity2 : getPassengers()) {
                entity2.rotationPitch += 10.0f;
            }
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
    public void updatePassenger(@Nonnull Entity passenger) {
        if (isPassenger(passenger)) {
            float f = -0.85f;
            float f2 = (float) ((isDead ? 0.01 : getMountedYOffset()) + passenger.getYOffset());
            Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateYaw(-rotationYaw * 0.017453292f - 1.5707964f);
            passenger.setPosition(posX + vec3d.xCoord, posY + f2, posZ + vec3d.zCoord);
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
    public boolean processInitialInteract(@Nonnull EntityPlayer entityplayer, @Nullable ItemStack stack,
                                          @Nonnull EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack != null && itemstack.getItem() == BalkonsWeaponMod.cannonBall && !isLoaded() && !isLoading()
            && (entityplayer.isCreative() || consumeAmmo(entityplayer, Items.GUNPOWDER))) {
            if (entityplayer.isCreative() || consumeAmmo(entityplayer, BalkonsWeaponMod.cannonBall)) {
                startLoadingCannon();
                return true;
            }
            dropItem(Items.GUNPOWDER, 1);
        } else {
            if (isBeingRidden() && riddenByPlayer() && notThisPlayer(entityplayer)) {
                return true;
            }
            if (!worldObj.isRemote && !entityplayer.isSneaking()) {
                entityplayer.startRiding(this);
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
            entityplayer.inventory.deleteStack(stackAmmo);
        }
        return true;
    }

    public boolean riddenByPlayer() {
        Entity entity = getControllingPassenger();
        return entity instanceof EntityPlayer;
    }

    public boolean notThisPlayer(Entity player) {
        Entity entity = getControllingPassenger();
        return entity != player;
    }

    @Override
    public void onStruckByLightning(@Nonnull EntityLightningBolt entitylightningbolt) {
        attackEntityFrom(DamageSource.lightningBolt, 100.0f);
        setSuperPowered(true);
    }

    @NotNull
    @Override
    public ItemStack getPickedResult(@NotNull RayTraceResult target) {
        return new ItemStack(BalkonsWeaponMod.cannon);
    }

    public void setLoaded(boolean flag) {
        dataManager.set(LOADED, (byte) (flag ? 1 : 0));
    }

    public void setLoadTimer(int time) {
        dataManager.set(LOAD_TIMER, time);
    }

    public void setSuperPowered(boolean flag) {
        dataManager.set(SUPER_POWERED, (byte) (flag ? 1 : 0));
    }

    public boolean isLoading() {
        return getLoadTimer() > 0;
    }

    public boolean isLoaded() {
        return dataManager.get(LOADED) != 0;
    }

    public int getLoadTimer() {
        return dataManager.get(LOAD_TIMER);
    }

    public boolean isSuperPowered() {
        return dataManager.get(SUPER_POWERED) != 0;
    }

    @Override
    public void setTimeSinceHit(int i) {
        dataManager.set(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        dataManager.set(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        dataManager.set(CURRENT_DAMAGE, i);
    }

    @Override
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
