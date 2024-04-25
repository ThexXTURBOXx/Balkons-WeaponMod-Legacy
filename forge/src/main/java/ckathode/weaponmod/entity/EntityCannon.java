package ckathode.weaponmod.entity;

import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityCannon extends Boat {
    public static final String NAME = "cannon";

    private static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> ROCK_DIRECTION = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> CURRENT_DAMAGE = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> LOADED = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> LOAD_TIMER = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> SUPER_POWERED = SynchedEntityData.defineId(EntityCannon.class,
            EntityDataSerializers.BYTE);

    public EntityCannon(EntityType<EntityCannon> entityType, Level world) {
        super(entityType, world);
        blocksBuilding = true;
        xRot = -20.0f;
        setRot(yRot = -180.0f, xRot);
    }

    public EntityCannon(Level world, double d, double d1, double d2) {
        this(BalkonsWeaponModForge.entityCannon, world);
        setPos(d, d1, d2);
        setDeltaMovement(Vec3.ZERO);
        xo = d;
        yo = d1;
        zo = d2;
    }

    @Nonnull
    @Override
    public EntityType<?> getType() {
        return BalkonsWeaponModForge.entityCannon;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TIME_SINCE_HIT, 0);
        entityData.define(ROCK_DIRECTION, (byte) 1);
        entityData.define(CURRENT_DAMAGE, 0);
        entityData.define(LOADED, (byte) 0);
        entityData.define(LOAD_TIMER, 0);
        entityData.define(SUPER_POWERED, (byte) 0);
    }

    @Nonnull
    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.35;
    }

    @Override
    public boolean hurt(@Nonnull DamageSource damagesource, float damage) {
        if (level.isClientSide || !isAlive()) {
            return true;
        }
        if (damagesource instanceof IndirectEntityDamageSource) {
            if (hasPassenger(damagesource.getEntity())) {
                return true;
            }
        } else if (damagesource instanceof EntityDamageSource && damagesource.msgId.equals("player")) {
            Player player = (Player) damagesource.getEntity();
            if (player.inventory.getSelected().isEmpty()) {
                if (!player.abilities.instabuild) {
                    spawnAtLocation(BalkonsWeaponModForge.cannon, 1);
                    if (isLoaded() || isLoading()) {
                        spawnAtLocation(BalkonsWeaponModForge.cannonBall, 1);
                        spawnAtLocation(Items.GUNPOWDER, 1);
                    }
                }
                remove();
                return true;
            }
        }
        setRockDirection(-getRockDirection());
        setHurtTime(10);
        setCurrentDamage(getCurrentDamage() + (int) damage * 5);
        markHurt();
        if (getCurrentDamage() > 100) {
            for (int j = 0; j < 6; ++j) {
                dropItemWithChance(Items.IRON_INGOT, (int) damage, 1);
            }
            dropItemWithChance(Items.FLINT, (int) damage, 1);
            dropItemWithChance(Blocks.OAK_LOG.asItem(), (int) damage, 1);
            if (isLoaded() || isLoading()) {
                spawnAtLocation(BalkonsWeaponModForge.cannonBall, 1);
                spawnAtLocation(Items.GUNPOWDER, 1);
            }
            remove();
        }
        return true;
    }

    public void dropItemWithChance(Item item, int chance, int amount) {
        if (random.nextInt(chance) < 10) {
            spawnAtLocation(item, amount);
        }
    }

    @Override
    public void animateHurt() {
        setRockDirection(-getRockDirection());
        setHurtTime(10);
        setCurrentDamage(getCurrentDamage() + 10);
    }

    @Override
    public boolean isPickable() {
        return isAlive();
    }

    @Override
    public void tick() {
        baseTick();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        int i = getHurtTime();
        if (i > 0) {
            setHurtTime(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - random.nextInt(2));
        }
        Vec3 motion = getDeltaMovement().subtract(0, 0.1, 0);
        if (onGround) {
            motion = new Vec3(0.1 * motion.x, motion.y, 0.1 * motion.z);
        }
        motion = motion.scale(0.98);
        if (!onGround) {
            fallDistance += (float) (-motion.y);
        }
        setDeltaMovement(motion);
        if (isVehicle()) {
            LivingEntity entitylivingbase = (LivingEntity) getControllingPassenger();
            float yaw = entitylivingbase.yRot;
            float pitch = entitylivingbase.xRot;
            yRot = yaw % 360.0f;
            xRot = pitch;
        }
        setRot(yRot, xRot);
        move(MoverType.SELF, getDeltaMovement());
        List<Entity> list = level.getEntities(this, getBoundingBox().inflate(0.2,
                0.0, 0.2), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.hasPassenger(this) && entity.getVehicle() == null) {
                    push(entity);
                }
            }
        }
        if (isLoading()) {
            setLoadTimer(getLoadTimer() - 1);
            handleReloadTime();
        }
    }

    @Override
    public boolean causeFallDamage(float f, float f1) {
        super.causeFallDamage(f, f1);
        int i = Mth.floor(f);
        i *= 2;
        hurt(DamageSource.FALL, (float) i);
        return false;
    }

    public void handleReloadTime() {
        int l = getLoadTimer();
        if (l > 0) {
            if (l == 80 || l == 70 || l == 60) {
                playSound(SoundEvents.PISTON_CONTRACT, 0.5f, 1.2f / (random.nextFloat() * 0.8f + 0.6f));
            } else if (l == 40) {
                playSound(SoundEvents.PLAYER_BREATH, 0.7f, 1.2f / (random.nextFloat() * 0.2f + 10.0f));
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
        if (!level.isClientSide) {
            EntityCannonBall entitycannonball = new EntityCannonBall(level, this,
                    entityPassenger.xRot, entityPassenger.yRot, isSuperPowered());
            level.addFreshEntity(entitycannonball);
        }
        setReloadInfo(false, 0);
        fireEffects();
    }

    public void fireEffects() {
        playSound(SoundEvents.GENERIC_EXPLODE, 8.0f, 1.0f / (random.nextFloat() * 0.8f + 0.9f));
        playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 8.0f, 1.0f / (random.nextFloat() * 0.4f + 0.6f));
        float yaw = (float) Math.toRadians(yRot);
        double d = -Mth.sin(yaw) * -1.0f;
        double d2 = Mth.cos(yaw) * -1.0f;
        for (int i = 0; i < 20; ++i) {
            level.addParticle(ParticleTypes.SMOKE,
                    getX() + d + random.nextDouble() * 0.5 - 0.25, getY() + random.nextDouble() * 0.5,
                    getZ() + d2 + random.nextDouble() * 0.5 - 0.25, random.nextDouble() * 0.1 - 0.05,
                    random.nextDouble() * 0.1 - 0.05, random.nextDouble() * 0.1 - 0.05);
        }
        if (isVehicle()) {
            for (Entity entity2 : getPassengers()) {
                entity2.xRot += 10.0f;
            }
        }
        hurt(DamageSource.GENERIC, 2.0f);
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
    public void positionRider(@Nonnull Entity passenger) {
        if (hasPassenger(passenger)) {
            float f = -0.85f;
            float f2 = (float) ((isAlive() ? getPassengersRidingOffset() : 0.01) + passenger.getMyRidingOffset());
            Vec3 vec3d = new Vec3(f, 0.0, 0.0).yRot(-yRot * 0.017453292f - 1.5707964f);
            passenger.setPos(getX() + vec3d.x, getY() + f2, getZ() + vec3d.z);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbttagcompound) {
        nbttagcompound.putFloat("falld", fallDistance);
        nbttagcompound.putBoolean("load", isLoaded());
        nbttagcompound.putShort("ldtime", (short) getLoadTimer());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbttagcompound) {
        setPos(getX(), getY(), getZ());
        setRot(yRot, xRot);
        fallDistance = nbttagcompound.getFloat("falld");
        setLoaded(nbttagcompound.getBoolean("load"));
        setLoadTimer(nbttagcompound.getShort("ldtime"));
    }

    @Nonnull
    @Override
    public InteractionResult interact(Player entityplayer, @Nonnull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (itemstack.getItem() == BalkonsWeaponModForge.cannonBall && !isLoaded() && !isLoading()
            && (entityplayer.abilities.instabuild || consumeAmmo(entityplayer, Items.GUNPOWDER))) {
            if (entityplayer.abilities.instabuild || consumeAmmo(entityplayer, BalkonsWeaponModForge.cannonBall)) {
                startLoadingCannon();
                return InteractionResult.PASS;
            }
            spawnAtLocation(Items.GUNPOWDER, 1);
        } else {
            if (isVehicle() && riddenByPlayer() && notThisPlayer(entityplayer)) {
                return InteractionResult.PASS;
            }
            if (!level.isClientSide && !entityplayer.isShiftKeyDown()) {
                entityplayer.startRiding(this);
            }
        }
        return InteractionResult.PASS;
    }

    private ItemStack findAmmo(Player player, Item itemAmmo) {
        for (int i = 0; i < player.inventory.getContainerSize(); ++i) {
            ItemStack itemstack = player.inventory.getItem(i);
            if (isAmmo(itemstack, itemAmmo)) {
                return itemstack;
            }
        }
        return ItemStack.EMPTY;
    }

    protected boolean isAmmo(ItemStack stack, Item itemAmmo) {
        return stack.getItem() == itemAmmo;
    }

    protected boolean consumeAmmo(Player entityplayer, Item itemAmmo) {
        ItemStack stackAmmo = findAmmo(entityplayer, itemAmmo);
        if (stackAmmo.isEmpty()) {
            return false;
        }
        stackAmmo.shrink(1);
        if (stackAmmo.isEmpty()) {
            entityplayer.inventory.removeItem(stackAmmo);
        }
        return true;
    }

    public boolean riddenByPlayer() {
        Entity entity = getControllingPassenger();
        return entity instanceof Player;
    }

    public boolean notThisPlayer(Entity player) {
        Entity entity = getControllingPassenger();
        return entity != player;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void thunderHit(ServerLevel world, LightningBolt entitylightningbolt) {
        hurt(DamageSource.LIGHTNING_BOLT, 100.0f);
        setSuperPowered(true);
    }

    public void setLoaded(boolean flag) {
        entityData.set(LOADED, (byte) (flag ? 1 : 0));
    }

    public void setLoadTimer(int time) {
        entityData.set(LOAD_TIMER, time);
    }

    public void setSuperPowered(boolean flag) {
        entityData.set(SUPER_POWERED, (byte) (flag ? 1 : 0));
    }

    public boolean isLoading() {
        return getLoadTimer() > 0;
    }

    public boolean isLoaded() {
        return entityData.get(LOADED) != 0;
    }

    public int getLoadTimer() {
        return entityData.get(LOAD_TIMER);
    }

    public boolean isSuperPowered() {
        return entityData.get(SUPER_POWERED) != 0;
    }

    @Override
    public void setHurtTime(int i) {
        entityData.set(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        entityData.set(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        entityData.set(CURRENT_DAMAGE, i);
    }

    @Override
    public int getHurtTime() {
        return entityData.get(TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return entityData.get(ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return entityData.get(CURRENT_DAMAGE);
    }

}
