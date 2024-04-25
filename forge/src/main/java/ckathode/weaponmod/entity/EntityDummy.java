package ckathode.weaponmod.entity;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import ckathode.weaponmod.item.IItemWeapon;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityDummy extends Entity {
    public static final String NAME = "dummy";

    private static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.defineId(EntityDummy.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> ROCK_DIRECTION = SynchedEntityData.defineId(EntityDummy.class,
            EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> CURRENT_DAMAGE = SynchedEntityData.defineId(EntityDummy.class,
            EntityDataSerializers.INT);
    private int durability;

    public EntityDummy(EntityType<EntityDummy> entityType, Level world) {
        super(entityType, world);
        blocksBuilding = true;
        xRot = -20.0f;
        setRot(yRot, xRot);
        durability = 50;
    }

    public EntityDummy(Level world, double d, double d1, double d2) {
        this(BalkonsWeaponModForge.entityDummy, world);
        setPos(d, d1, d2);
        setDeltaMovement(Vec3.ZERO);
        xo = d;
        yo = d1;
        zo = d2;
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(TIME_SINCE_HIT, 0);
        entityData.define(ROCK_DIRECTION, (byte) 1);
        entityData.define(CURRENT_DAMAGE, 0);
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
    public boolean hurt(@Nonnull DamageSource damagesource, float damage) {
        if (level.isClientSide || !isAlive() || damage <= 0.0f) {
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
        markHurt();
        if (!(damagesource instanceof EntityDamageSource)) {
            durability -= (int) damage;
        } else if (damagesource instanceof WeaponDamageSource) {
            Entity entity = ((WeaponDamageSource) damagesource).getProjectile();
            if (entity.getDeltaMovement().length() > 0.5) {
                entity.setDeltaMovement(entity.getDeltaMovement().scale(0.10000000149011612));
                playRandomHitSound();
            } else {
                entity.setDeltaMovement(random.nextFloat() - 0.5f, random.nextFloat() - 0.5f,
                        random.nextFloat() - 0.5f);
            }
        } else {
            playRandomHitSound();
        }
        if (durability <= 0 && level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            dropAsItem(true, true);
        }
        markHurt();
        return false;
    }

    public void playRandomHitSound() {
        int i = random.nextInt(2);
        if (i == 0) {
            playSound(SoundEvents.WOOL_STEP, 0.7f, 1.0f / (random.nextFloat() * 0.2f + 0.4f));
        } else {
            playSound(SoundEvents.WOOD_STEP, 0.7f, 1.0f / (random.nextFloat() * 0.2f + 0.2f));
        }
    }

    @Override
    public void animateHurt() {
        setRockDirection(-getRockDirection());
        setTimeSinceHit(10);
        setCurrentDamage(getCurrentDamage() + 10);
    }

    @Override
    public boolean isPickable() {
        return isAlive();
    }

    @Override
    public void tick() {
        super.tick();
        int i = getTimeSinceHit();
        if (i > 0) {
            setTimeSinceHit(i - 1);
        }
        i = getCurrentDamage();
        if (i > 0) {
            setCurrentDamage(i - random.nextInt(2));
        }
        xo = getX();
        yo = getY();
        zo = getZ();
        if (onGround) {
            setDeltaMovement(Vec3.ZERO);
        } else {
            Vec3 motion = getDeltaMovement();
            double motionX = motion.x * 0.99;
            double motionZ = motion.z * 0.99;
            double motionY = motion.y - 0.05;
            fallDistance += (float) (-motionY);
            setDeltaMovement(motionX, motionY, motionZ);
        }
        setRot(yRot, xRot);
        move(MoverType.SELF, new Vec3(0.0, getDeltaMovement().y, 0.0));
        List<Entity> list = level.getEntities(this, getBoundingBox().inflate(0.2,
                0.0, 0.2), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.hasPassenger(this)) {
                    push(entity);
                }
            }
        }
    }

    @Override
    public boolean causeFallDamage(float f, float f1) {
        super.causeFallDamage(f, f1);
        if (!onGround) {
            return false;
        }
        int i = Mth.floor(f);
        hurt(DamageSource.FALL, (float) i);
        return false;
    }

    public void dropAsItem(boolean destroyed, boolean noCreative) {
        if (level.isClientSide) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < random.nextInt(8); ++i) {
                spawnAtLocation(Items.LEATHER, 1);
            }
        } else if (noCreative) {
            spawnAtLocation(BalkonsWeaponModForge.dummy, 1);
        }
        remove();
    }

    @Nonnull
    @Override
    public InteractionResult interact(Player entityplayer, @Nonnull InteractionHand hand) {
        ItemStack itemstack = entityplayer.inventory.getSelected();
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof BowItem || itemstack.getItem() instanceof ShieldItem) {
                return InteractionResult.FAIL;
            }
        }
        if (entityplayer.abilities.instabuild) {
            dropAsItem(false, false);
            return InteractionResult.PASS;
        }
        dropAsItem(false, true);
        return InteractionResult.PASS;
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundTag nbttagcompound) {
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundTag nbttagcompound) {
        setPos(getX(), getY(), getZ());
        setRot(yRot, xRot);
    }

    public void setTimeSinceHit(int i) {
        entityData.set(TIME_SINCE_HIT, i);
    }

    public void setRockDirection(int i) {
        entityData.set(ROCK_DIRECTION, (byte) i);
    }

    public void setCurrentDamage(int i) {
        entityData.set(CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return entityData.get(TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return entityData.get(ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return entityData.get(CURRENT_DAMAGE);
    }

}
