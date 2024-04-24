package ckathode.weaponmod.entity;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityDummy extends Entity {
    public static final String NAME = "dummy";

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.defineId(EntityDummy.class,
            DataSerializers.INT);
    private static final DataParameter<Byte> ROCK_DIRECTION = EntityDataManager.defineId(EntityDummy.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.defineId(EntityDummy.class,
            DataSerializers.INT);
    private int durability;

    public EntityDummy(EntityType<EntityDummy> entityType, World world) {
        super(entityType, world);
        blocksBuilding = true;
        xRot = -20.0f;
        setRot(yRot, xRot);
        durability = 50;
    }

    public EntityDummy(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityDummy, world);
        setPos(d, d1, d2);
        setDeltaMovement(Vector3d.ZERO);
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
    public IPacket<?> getAddEntityPacket() {
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
            setDeltaMovement(Vector3d.ZERO);
        } else {
            Vector3d motion = getDeltaMovement();
            double motionX = motion.x * 0.99;
            double motionZ = motion.z * 0.99;
            double motionY = motion.y - 0.05;
            fallDistance += (float) (-motionY);
            setDeltaMovement(motionX, motionY, motionZ);
        }
        setRot(yRot, xRot);
        move(MoverType.SELF, new Vector3d(0.0, getDeltaMovement().y, 0.0));
        List<Entity> list = level.getEntities(this, getBoundingBox().inflate(0.2,
                0.0, 0.2), EntityPredicates.pushableBy(this));
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
        int i = MathHelper.floor(f);
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
            spawnAtLocation(BalkonsWeaponMod.dummy, 1);
        }
        remove();
    }

    @Nonnull
    @Override
    public ActionResultType interact(PlayerEntity entityplayer, @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.inventory.getSelected();
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof BowItem || itemstack.getItem() instanceof ShieldItem) {
                return ActionResultType.FAIL;
            }
        }
        if (entityplayer.abilities.instabuild) {
            dropAsItem(false, false);
            return ActionResultType.PASS;
        }
        dropAsItem(false, true);
        return ActionResultType.PASS;
    }

    @Override
    protected void addAdditionalSaveData(@Nonnull CompoundNBT nbttagcompound) {
    }

    @Override
    protected void readAdditionalSaveData(@Nonnull CompoundNBT nbttagcompound) {
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
