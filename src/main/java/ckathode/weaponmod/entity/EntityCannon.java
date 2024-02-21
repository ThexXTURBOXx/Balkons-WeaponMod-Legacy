package ckathode.weaponmod.entity;

import net.minecraft.entity.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import ckathode.weaponmod.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.util.math.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.datasync.*;

public class EntityCannon extends EntityBoat
{
    private static final DataParameter<Integer> TIME_SINCE_HIT;
    private static final DataParameter<Byte> ROCK_DIRECTION;
    private static final DataParameter<Integer> CURRENT_DAMAGE;
    private static final DataParameter<Byte> LOADED;
    private static final DataParameter<Integer> LOAD_TIMER;
    private static final DataParameter<Byte> SUPER_POWERED;

    public EntityCannon(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.rotationPitch = -20.0f;
        this.setRotation(this.rotationYaw = -180.0f, this.rotationPitch);
        this.setSize(1.5f, 1.0f);
    }

    public EntityCannon(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = d;
        this.prevPosY = d1;
        this.prevPosZ = d2;
    }

    protected void entityInit() {
        this.dataManager.register(EntityCannon.TIME_SINCE_HIT, 0);
        this.dataManager.register(EntityCannon.ROCK_DIRECTION, (byte)1);
        this.dataManager.register(EntityCannon.CURRENT_DAMAGE, 0);
        this.dataManager.register(EntityCannon.LOADED, (byte)0);
        this.dataManager.register(EntityCannon.LOAD_TIMER, 0);
        this.dataManager.register(EntityCannon.SUPER_POWERED, (byte)0);
    }

    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getEntityBoundingBox();
    }

    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 0.35;
    }

    public boolean attackEntityFrom(final DamageSource damagesource, final float damage) {
        if (this.world.isRemote || this.isDead) {
            return true;
        }
        if (damagesource instanceof EntityDamageSourceIndirect) {
            if (this.isPassenger(damagesource.getTrueSource())) {
                return true;
            }
        }
        else if (damagesource instanceof EntityDamageSource && damagesource.damageType.equals("player")) {
            final EntityPlayer player = (EntityPlayer) damagesource.getTrueSource();
            if (player.inventory.getCurrentItem().isEmpty()) {
                if (!player.capabilities.isCreativeMode) {
                    this.dropItem(BalkonsWeaponMod.cannon, 1);
                    if (this.isLoaded() || this.isLoading()) {
                        this.dropItem(BalkonsWeaponMod.cannonBall, 1);
                        this.dropItem(Items.GUNPOWDER, 1);
                    }
                }
                this.setDead();
                return true;
            }
        }
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        this.setCurrentDamage(this.getCurrentDamage() + (int)damage * 5);
        this.markVelocityChanged();
        if (this.getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                this.dropItemWithChance(Items.IRON_INGOT, (int)damage, 1);
            }
            this.dropItemWithChance(Items.FLINT, (int)damage, 1);
            this.dropItemWithChance(Item.getItemFromBlock(Blocks.LOG), (int)damage, 1);
            if (this.isLoaded() || this.isLoading()) {
                this.dropItem(BalkonsWeaponMod.cannonBall, 1);
                this.dropItem(Items.GUNPOWDER, 1);
            }
            this.setDead();
        }
        return true;
    }

    public void dropItemWithChance(final Item item, final int chance, final int amount) {
        if (this.rand.nextInt(chance) < 10) {
            this.dropItem(item, amount);
        }
    }

    public void performHurtAnimation() {
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        this.setCurrentDamage(this.getCurrentDamage() + 10);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    public void onUpdate() {
        this.onEntityUpdate();
    }

    public void onEntityUpdate() {
        super.onEntityUpdate();
        int i = this.getTimeSinceHit();
        if (i > 0) {
            this.setTimeSinceHit(i - 1);
        }
        i = this.getCurrentDamage();
        if (i > 0) {
            this.setCurrentDamage(i - this.rand.nextInt(2));
        }
        this.motionY -= 0.1;
        if (this.onGround) {
            this.motionX *= 0.1;
            this.motionZ *= 0.1;
        }
        this.motionX *= 0.98;
        this.motionY *= 0.98;
        this.motionZ *= 0.98;
        if (!this.onGround) {
            this.fallDistance += (float)(-this.motionY);
        }
        if (this.isBeingRidden()) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();
            final float yaw = entitylivingbase.rotationYaw;
            final float pitch = entitylivingbase.rotationPitch;
            this.rotationYaw = yaw % 360.0f;
            this.rotationPitch = pitch;
        }
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        final List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.2, 0.0, 0.2), EntitySelectors.getTeamCollisionPredicate(this));
        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if (!entity.isPassenger(this) && !entity.isRiding()) {
                    this.applyEntityCollision(entity);
                }
            }
        }
        if (this.isLoading()) {
            this.setLoadTimer(this.getLoadTimer() - 1);
            this.handleReloadTime();
        }
    }

    public void fall(final float f, final float f1) {
        super.fall(f, f1);
        int i = MathHelper.floor(f);
        i *= 2;
        this.attackEntityFrom(DamageSource.FALL, (float)i);
    }

    public void handleReloadTime() {
        final int l = this.getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                this.playSound(SoundEvents.BLOCK_PISTON_CONTRACT, 0.5f, 1.2f / (this.rand.nextFloat() * 0.8f + 0.6f));
            }
            else if (l == 40) {
                this.playSound(SoundEvents.ENTITY_PLAYER_BREATH, 0.7f, 1.2f / (this.rand.nextFloat() * 0.2f + 10.0f));
            }
        }
        else {
            this.setReloadInfo(true, 0);
        }
    }

    public void fireCannon() {
        if (!this.isLoaded()) {
            return;
        }
        final Entity entityPassenger = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        if (!this.world.isRemote) {
            final EntityCannonBall entitycannonball = new EntityCannonBall(this.world, this, entityPassenger.rotationPitch, entityPassenger.rotationYaw, this.isSuperPowered());
            this.world.spawnEntity(entitycannonball);
        }
        this.setReloadInfo(false, 0);
        this.fireEffects();
    }

    public void fireEffects() {
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 8.0f, 1.0f / (this.rand.nextFloat() * 0.8f + 0.9f));
        this.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 8.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.6f));
        final float yaw = (float)Math.toRadians(this.rotationYaw);
        final double d = -MathHelper.sin(yaw) * -1.0f;
        final double d2 = MathHelper.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + d + this.rand.nextDouble() * 0.5 - 0.25, this.posY + this.rand.nextDouble() * 0.5, this.posZ + d2 + this.rand.nextDouble() * 0.5 - 0.25, this.rand.nextDouble() * 0.1 - 0.05, this.rand.nextDouble() * 0.1 - 0.05, this.rand.nextDouble() * 0.1 - 0.05, new int[0]);
        }
        if (this.isBeingRidden()) {
            for (final Entity entity2 : this.getPassengers()) {
                final Entity entity = entity2;
                entity2.rotationPitch += 10.0f;
            }
        }
        this.attackEntityFrom(DamageSource.GENERIC, 2.0f);
    }

    public void setReloadInfo(final boolean loaded, final int reloadtime) {
        this.setLoaded(loaded);
        this.setLoadTimer(reloadtime);
    }

    public void startLoadingCannon() {
        if (this.isLoaded() && !this.isLoading()) {
            return;
        }
        this.setReloadInfo(false, 100);
    }

    public void updatePassenger(final Entity passenger) {
        if (this.isPassenger(passenger)) {
            final float f = -0.85f;
            final float f2 = (float)((this.isDead ? 0.01 : this.getMountedYOffset()) + passenger.getYOffset());
            final Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateYaw(-this.rotationYaw * 0.017453292f - 1.5707964f);
            passenger.setPosition(this.posX + vec3d.x, this.posY + f2, this.posZ + vec3d.z);
        }
    }

    protected void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setFloat("falld", this.fallDistance);
        nbttagcompound.setBoolean("load", this.isLoaded());
        nbttagcompound.setShort("ldtime", (short)this.getLoadTimer());
    }

    protected void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.fallDistance = nbttagcompound.getFloat("falld");
        this.setLoaded(nbttagcompound.getBoolean("load"));
        this.setLoadTimer(nbttagcompound.getShort("ldtime"));
    }

    public boolean processInitialInteract(final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.getItem() == BalkonsWeaponMod.cannonBall && !this.isLoaded() && !this.isLoading() && (entityplayer.capabilities.isCreativeMode || this.consumeAmmo(entityplayer, Items.GUNPOWDER))) {
            if (entityplayer.capabilities.isCreativeMode || this.consumeAmmo(entityplayer, BalkonsWeaponMod.cannonBall)) {
                this.startLoadingCannon();
                return true;
            }
            this.dropItem(Items.GUNPOWDER, 1);
            return true;
        }
        else {
            if (this.isBeingRidden() && this.riddenByPlayer() && this.notThisPlayer(entityplayer)) {
                return true;
            }
            if (!this.world.isRemote && !entityplayer.isSneaking()) {
                entityplayer.startRiding(this);
            }
            return true;
        }
    }

    private ItemStack findAmmo(final EntityPlayer player, final Item itemAmmo) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            final ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (this.isAmmo(itemstack, itemAmmo)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean isAmmo(final ItemStack stack, final Item itemAmmo) {
        return stack.getItem() == itemAmmo;
    }

    protected boolean consumeAmmo(final EntityPlayer entityplayer, final Item itemAmmo) {
        final ItemStack stackAmmo = this.findAmmo(entityplayer, itemAmmo);
        if (stackAmmo.isEmpty()) {
            return false;
        }
        stackAmmo.shrink(1);
        if (stackAmmo.isEmpty()) {
            entityplayer.inventory.deleteStack(stackAmmo);
        }
        return true;
    }

    public boolean riddenByPlayer() {
        final Entity entity = this.getControllingPassenger();
        return entity instanceof EntityPlayer;
    }

    public boolean notThisPlayer(final Entity player) {
        final Entity entity = this.getControllingPassenger();
        return entity != player;
    }

    public void onStruckByLightning(final EntityLightningBolt entitylightningbolt) {
        this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 100.0f);
        this.setSuperPowered(true);
    }

    public void setLoaded(final boolean flag) {
        this.dataManager.set(EntityCannon.LOADED, (byte)(flag ? 1 : 0));
    }

    public void setLoadTimer(final int time) {
        this.dataManager.set(EntityCannon.LOAD_TIMER, time);
    }

    public void setSuperPowered(final boolean flag) {
        this.dataManager.set(EntityCannon.SUPER_POWERED, (byte)(flag ? 1 : 0));
    }

    public boolean isLoading() {
        return this.getLoadTimer() > 0;
    }

    public boolean isLoaded() {
        return this.dataManager.get(EntityCannon.LOADED) != 0;
    }

    public int getLoadTimer() {
        return this.dataManager.get(EntityCannon.LOAD_TIMER);
    }

    public boolean isSuperPowered() {
        return this.dataManager.get(EntityCannon.SUPER_POWERED) != 0;
    }

    public void setTimeSinceHit(final int i) {
        this.dataManager.set(EntityCannon.TIME_SINCE_HIT, i);
    }

    public void setRockDirection(final int i) {
        this.dataManager.set(EntityCannon.ROCK_DIRECTION, (byte)i);
    }

    public void setCurrentDamage(final int i) {
        this.dataManager.set(EntityCannon.CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(EntityCannon.TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return this.dataManager.get(EntityCannon.ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return this.dataManager.get(EntityCannon.CURRENT_DAMAGE);
    }

    static {
        TIME_SINCE_HIT = EntityDataManager.createKey(EntityCannon.class, DataSerializers.VARINT);
        ROCK_DIRECTION = EntityDataManager.createKey(EntityCannon.class, DataSerializers.BYTE);
        CURRENT_DAMAGE = EntityDataManager.createKey(EntityCannon.class, DataSerializers.VARINT);
        LOADED = EntityDataManager.createKey(EntityCannon.class, DataSerializers.BYTE);
        LOAD_TIMER = EntityDataManager.createKey(EntityCannon.class, DataSerializers.VARINT);
        SUPER_POWERED = EntityDataManager.createKey(EntityCannon.class, DataSerializers.BYTE);
    }
}
