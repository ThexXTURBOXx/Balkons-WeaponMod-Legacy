package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityCannon extends EntityBoat {
    public static final String NAME = "cannon";

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

    @Override
    protected void registerData() {
        this.dataManager.register(EntityCannon.TIME_SINCE_HIT, 0);
        this.dataManager.register(EntityCannon.ROCK_DIRECTION, (byte) 1);
        this.dataManager.register(EntityCannon.CURRENT_DAMAGE, 0);
        this.dataManager.register(EntityCannon.LOADED, (byte) 0);
        this.dataManager.register(EntityCannon.LOAD_TIMER, 0);
        this.dataManager.register(EntityCannon.SUPER_POWERED, (byte) 0);
    }

    @Override
    public AxisAlignedBB getCollisionBox(final Entity entity) {
        return entity.getBoundingBox();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
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
    public boolean attackEntityFrom(@Nonnull final DamageSource damagesource, final float damage) {
        if (this.world.isRemote || !this.isAlive()) {
            return true;
        }
        if (damagesource instanceof EntityDamageSourceIndirect) {
            if (this.isPassenger(damagesource.getTrueSource())) {
                return true;
            }
        } else if (damagesource instanceof EntityDamageSource && damagesource.damageType.equals("player")) {
            final EntityPlayer player = (EntityPlayer) damagesource.getTrueSource();
            if (player.inventory.getCurrentItem().isEmpty()) {
                if (!player.abilities.isCreativeMode) {
                    this.entityDropItem(BalkonsWeaponMod.cannon, 1);
                    if (this.isLoaded() || this.isLoading()) {
                        this.entityDropItem(BalkonsWeaponMod.cannonBall, 1);
                        this.entityDropItem(Items.GUNPOWDER, 1);
                    }
                }
                this.remove();
                return true;
            }
        }
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        this.setCurrentDamage(this.getCurrentDamage() + (int) damage * 5);
        this.markVelocityChanged();
        if (this.getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                this.dropItemWithChance(Items.IRON_INGOT, (int) damage, 1);
            }
            this.dropItemWithChance(Items.FLINT, (int) damage, 1);
            this.dropItemWithChance(Blocks.OAK_LOG.asItem(), (int) damage, 1);
            if (this.isLoaded() || this.isLoading()) {
                this.entityDropItem(BalkonsWeaponMod.cannonBall, 1);
                this.entityDropItem(Items.GUNPOWDER, 1);
            }
            this.remove();
        }
        return true;
    }

    public void dropItemWithChance(final Item item, final int chance, final int amount) {
        if (this.rand.nextInt(chance) < 10) {
            this.entityDropItem(item, amount);
        }
    }

    @Override
    public void performHurtAnimation() {
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        this.setCurrentDamage(this.getCurrentDamage() + 10);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.isAlive();
    }

    @Override
    public void tick() {
        this.baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
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
            this.fallDistance += (float) (-this.motionY);
        }
        if (this.isBeingRidden()) {
            final EntityLivingBase entitylivingbase = (EntityLivingBase) this.getControllingPassenger();
            final float yaw = entitylivingbase.rotationYaw;
            final float pitch = entitylivingbase.rotationPitch;
            this.rotationYaw = yaw % 360.0f;
            this.rotationPitch = pitch;
        }
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        final List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2,
                0.0, 0.2), EntitySelectors.pushableBy(this));
        if (!list.isEmpty()) {
            for (final Entity entity : list) {
                if (!entity.isPassenger(this) && entity.getRidingEntity() == null) {
                    this.applyEntityCollision(entity);
                }
            }
        }
        if (this.isLoading()) {
            this.setLoadTimer(this.getLoadTimer() - 1);
            this.handleReloadTime();
        }
    }

    @Override
    public void fall(final float f, final float f1) {
        super.fall(f, f1);
        int i = MathHelper.floor(f);
        i *= 2;
        this.attackEntityFrom(DamageSource.FALL, (float) i);
    }

    public void handleReloadTime() {
        final int l = this.getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                this.playSound(SoundEvents.BLOCK_PISTON_CONTRACT, 0.5f, 1.2f / (this.rand.nextFloat() * 0.8f + 0.6f));
            } else if (l == 40) {
                this.playSound(SoundEvents.ENTITY_PLAYER_BREATH, 0.7f, 1.2f / (this.rand.nextFloat() * 0.2f + 10.0f));
            }
        } else {
            this.setReloadInfo(true, 0);
        }
    }

    public void fireCannon() {
        if (!this.isLoaded()) {
            return;
        }
        final Entity entityPassenger = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        if (!this.world.isRemote) {
            final EntityCannonBall entitycannonball = new EntityCannonBall(this.world, this,
                    entityPassenger.rotationPitch, entityPassenger.rotationYaw, this.isSuperPowered());
            this.world.spawnEntity(entitycannonball);
        }
        this.setReloadInfo(false, 0);
        this.fireEffects();
    }

    public void fireEffects() {
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 8.0f, 1.0f / (this.rand.nextFloat() * 0.8f + 0.9f));
        this.playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 8.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.6f));
        final float yaw = (float) Math.toRadians(this.rotationYaw);
        final double d = -MathHelper.sin(yaw) * -1.0f;
        final double d2 = MathHelper.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            this.world.addParticle(Particles.SMOKE,
                    this.posX + d + this.rand.nextDouble() * 0.5 - 0.25, this.posY + this.rand.nextDouble() * 0.5,
                    this.posZ + d2 + this.rand.nextDouble() * 0.5 - 0.25, this.rand.nextDouble() * 0.1 - 0.05,
                    this.rand.nextDouble() * 0.1 - 0.05, this.rand.nextDouble() * 0.1 - 0.05);
        }
        if (this.isBeingRidden()) {
            for (final Entity entity2 : this.getPassengers()) {
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

    @Override
    public void updatePassenger(@Nonnull final Entity passenger) {
        if (this.isPassenger(passenger)) {
            final float f = -0.85f;
            final float f2 = (float) ((this.isAlive() ? this.getMountedYOffset() : 0.01) + passenger.getYOffset());
            final Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateYaw(-this.rotationYaw * 0.017453292f - 1.5707964f);
            passenger.setPosition(this.posX + vec3d.x, this.posY + f2, this.posZ + vec3d.z);
        }
    }

    @Override
    protected void writeAdditional(final NBTTagCompound nbttagcompound) {
        nbttagcompound.putFloat("falld", this.fallDistance);
        nbttagcompound.putBoolean("load", this.isLoaded());
        nbttagcompound.putShort("ldtime", (short) this.getLoadTimer());
    }

    @Override
    protected void readAdditional(final NBTTagCompound nbttagcompound) {
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.fallDistance = nbttagcompound.getFloat("falld");
        this.setLoaded(nbttagcompound.getBoolean("load"));
        this.setLoadTimer(nbttagcompound.getShort("ldtime"));
    }

    @Override
    public boolean processInitialInteract(final EntityPlayer entityplayer, @Nonnull final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.getItem() == BalkonsWeaponMod.cannonBall && !this.isLoaded() && !this.isLoading() && (entityplayer.abilities.isCreativeMode || this.consumeAmmo(entityplayer, Items.GUNPOWDER))) {
            if (entityplayer.abilities.isCreativeMode || this.consumeAmmo(entityplayer,
                    BalkonsWeaponMod.cannonBall)) {
                this.startLoadingCannon();
                return true;
            }
            this.entityDropItem(Items.GUNPOWDER, 1);
            return true;
        } else {
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

    @Override
    public void onStruckByLightning(@Nonnull final EntityLightningBolt entitylightningbolt) {
        this.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 100.0f);
        this.setSuperPowered(true);
    }

    public void setLoaded(final boolean flag) {
        this.dataManager.set(EntityCannon.LOADED, (byte) (flag ? 1 : 0));
    }

    public void setLoadTimer(final int time) {
        this.dataManager.set(EntityCannon.LOAD_TIMER, time);
    }

    public void setSuperPowered(final boolean flag) {
        this.dataManager.set(EntityCannon.SUPER_POWERED, (byte) (flag ? 1 : 0));
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

    @Override
    public void setTimeSinceHit(final int i) {
        this.dataManager.set(EntityCannon.TIME_SINCE_HIT, i);
    }

    public void setRockDirection(final int i) {
        this.dataManager.set(EntityCannon.ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(final int i) {
        this.dataManager.set(EntityCannon.CURRENT_DAMAGE, i);
    }

    @Override
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
