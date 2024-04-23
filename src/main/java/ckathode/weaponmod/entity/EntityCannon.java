package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCannon extends BoatEntity {
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

    public EntityCannon(EntityType<EntityCannon> entityType, World world) {
        super(entityType, world);
        preventEntitySpawning = true;
        rotationPitch = -20.0f;
        setRotation(rotationYaw = -180.0f, rotationPitch);
    }

    public EntityCannon(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityCannon, world);
        setPosition(d, d1, d2);
        setMotion(Vec3d.ZERO);
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
    }

    @Nonnull
    @Override
    public EntityType<?> getType() {
        return BalkonsWeaponMod.entityCannon;
    }

    @Override
    protected void registerData() {
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(ROCK_DIRECTION, (byte) 1);
        dataManager.register(CURRENT_DAMAGE, 0);
        dataManager.register(LOADED, (byte) 0);
        dataManager.register(LOAD_TIMER, 0);
        dataManager.register(SUPER_POWERED, (byte) 0);
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entity) {
        return entity.getBoundingBox();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return getBoundingBox();
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
        if (world.isRemote || !isAlive()) {
            return true;
        }
        if (damagesource instanceof IndirectEntityDamageSource) {
            if (isPassenger(damagesource.getTrueSource())) {
                return true;
            }
        } else if (damagesource instanceof EntityDamageSource && damagesource.damageType.equals("player")) {
            PlayerEntity player = (PlayerEntity) damagesource.getTrueSource();
            if (player.inventory.getCurrentItem().isEmpty()) {
                if (!player.abilities.isCreativeMode) {
                    entityDropItem(BalkonsWeaponMod.cannon, 1);
                    if (isLoaded() || isLoading()) {
                        entityDropItem(BalkonsWeaponMod.cannonBall, 1);
                        entityDropItem(Items.GUNPOWDER, 1);
                    }
                }
                remove();
                return true;
            }
        }
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + (int) damage * 5);
        markVelocityChanged();
        if (getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                dropItemWithChance(Items.IRON_INGOT, (int) damage, 1);
            }
            dropItemWithChance(Items.FLINT, (int) damage, 1);
            dropItemWithChance(Blocks.OAK_LOG.asItem(), (int) damage, 1);
            if (isLoaded() || isLoading()) {
                entityDropItem(BalkonsWeaponMod.cannonBall, 1);
                entityDropItem(Items.GUNPOWDER, 1);
            }
            remove();
        }
        return true;
    }

    public void dropItemWithChance(Item item, int chance, int amount) {
        if (rand.nextInt(chance) < 10) {
            entityDropItem(item, amount);
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
        return isAlive();
    }

    @Override
    public void tick() {
        baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - rand.nextInt(2));
        }
        Vec3d motion = getMotion().subtract(0, 0.1, 0);
        if (onGround) {
            motion = new Vec3d(0.1 * motion.x, motion.y, 0.1 * motion.z);
        }
        motion = motion.scale(0.98);
        if (!onGround) {
            fallDistance += (float) (-motion.y);
        }
        setMotion(motion);
        if (isBeingRidden()) {
            LivingEntity entitylivingbase = (LivingEntity) getControllingPassenger();
            float yaw = entitylivingbase.rotationYaw;
            float pitch = entitylivingbase.rotationPitch;
            rotationYaw = yaw % 360.0f;
            rotationPitch = pitch;
        }
        setRotation(rotationYaw, rotationPitch);
        move(MoverType.SELF, getMotion());
        List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(0.2,
                0.0, 0.2), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.isPassenger(this) && entity.getRidingEntity() == null) {
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
    public boolean onLivingFall(float f, float f1) {
        super.onLivingFall(f, f1);
        int i = MathHelper.floor(f);
        i *= 2;
        attackEntityFrom(DamageSource.FALL, (float) i);
        return false;
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
        if (!world.isRemote) {
            EntityCannonBall entitycannonball = new EntityCannonBall(world, this,
                    entityPassenger.rotationPitch, entityPassenger.rotationYaw, isSuperPowered());
            world.addEntity(entitycannonball);
        }
        setReloadInfo(false, 0);
        fireEffects();
    }

    public void fireEffects() {
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 8.0f, 1.0f / (rand.nextFloat() * 0.8f + 0.9f));
        playSound(SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
        float yaw = (float) Math.toRadians(rotationYaw);
        double d = -MathHelper.sin(yaw) * -1.0f;
        double d2 = MathHelper.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            world.addParticle(ParticleTypes.SMOKE,
                    posX + d + rand.nextDouble() * 0.5 - 0.25, posY + rand.nextDouble() * 0.5,
                    posZ + d2 + rand.nextDouble() * 0.5 - 0.25, rand.nextDouble() * 0.1 - 0.05,
                    rand.nextDouble() * 0.1 - 0.05, rand.nextDouble() * 0.1 - 0.05);
        }
        if (isBeingRidden()) {
            for (Entity entity2 : getPassengers()) {
                entity2.rotationPitch += 10.0f;
            }
        }
        attackEntityFrom(DamageSource.GENERIC, 2.0f);
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
            float f2 = (float) ((isAlive() ? getMountedYOffset() : 0.01) + passenger.getYOffset());
            Vec3d vec3d = new Vec3d(f, 0.0, 0.0).rotateYaw(-rotationYaw * 0.017453292f - 1.5707964f);
            passenger.setPosition(posX + vec3d.x, posY + f2, posZ + vec3d.z);
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT nbttagcompound) {
        nbttagcompound.putFloat("falld", fallDistance);
        nbttagcompound.putBoolean("load", isLoaded());
        nbttagcompound.putShort("ldtime", (short) getLoadTimer());
    }

    @Override
    protected void readAdditional(CompoundNBT nbttagcompound) {
        setPosition(posX, posY, posZ);
        setRotation(rotationYaw, rotationPitch);
        fallDistance = nbttagcompound.getFloat("falld");
        setLoaded(nbttagcompound.getBoolean("load"));
        setLoadTimer(nbttagcompound.getShort("ldtime"));
    }

    @Override
    public boolean processInitialInteract(PlayerEntity entityplayer, @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (itemstack.getItem() == BalkonsWeaponMod.cannonBall && !isLoaded() && !isLoading()
            && (entityplayer.abilities.isCreativeMode || consumeAmmo(entityplayer, Items.GUNPOWDER))) {
            if (entityplayer.abilities.isCreativeMode || consumeAmmo(entityplayer, BalkonsWeaponMod.cannonBall)) {
                startLoadingCannon();
                return true;
            }
            entityDropItem(Items.GUNPOWDER, 1);
        } else {
            if (isBeingRidden() && riddenByPlayer() && notThisPlayer(entityplayer)) {
                return true;
            }
            if (!world.isRemote && !entityplayer.isSneaking()) {
                entityplayer.startRiding(this);
            }
        }
        return true;
    }

    private ItemStack findAmmo(PlayerEntity player, Item itemAmmo) {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (isAmmo(itemstack, itemAmmo)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean isAmmo(ItemStack stack, Item itemAmmo) {
        return stack.getItem() == itemAmmo;
    }

    protected boolean consumeAmmo(PlayerEntity entityplayer, Item itemAmmo) {
        ItemStack stackAmmo = findAmmo(entityplayer, itemAmmo);
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
        Entity entity = getControllingPassenger();
        return entity instanceof PlayerEntity;
    }

    public boolean notThisPlayer(Entity player) {
        Entity entity = getControllingPassenger();
        return entity != player;
    }

    @Override
    public void onStruckByLightning(@Nonnull LightningBoltEntity entitylightningbolt) {
        attackEntityFrom(DamageSource.LIGHTNING_BOLT, 100.0f);
        setSuperPowered(true);
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
