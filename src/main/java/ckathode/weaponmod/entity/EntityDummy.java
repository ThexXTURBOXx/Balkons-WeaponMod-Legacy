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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityDummy extends Entity {
    public static final String NAME = "dummy";

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
    private static final DataParameter<Byte> ROCK_DIRECTION = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.BYTE);
    private static final DataParameter<Integer> CURRENT_DAMAGE = EntityDataManager.createKey(EntityDummy.class,
            DataSerializers.VARINT);
    private int durability;

    public EntityDummy(EntityType<EntityDummy> entityType, World world) {
        super(entityType, world);
        preventEntitySpawning = true;
        rotationPitch = -20.0f;
        setRotation(rotationYaw, rotationPitch);
        durability = 50;
    }

    public EntityDummy(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityDummy, world);
        setPosition(d, d1, d2);
        setMotion(Vec3d.ZERO);
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
    }

    @Override
    protected void registerData() {
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(ROCK_DIRECTION, (byte) 1);
        dataManager.register(CURRENT_DAMAGE, 0);
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
    public boolean attackEntityFrom(@Nonnull DamageSource damagesource, float damage) {
        if (world.isRemote || !isAlive() || damage <= 0.0f) {
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
        markVelocityChanged();
        if (!(damagesource instanceof EntityDamageSource)) {
            durability -= (int) damage;
        } else if (damagesource instanceof WeaponDamageSource) {
            Entity entity = ((WeaponDamageSource) damagesource).getProjectile();
            if (entity.getMotion().length() > 0.5) {
                entity.setMotion(entity.getMotion().scale(0.10000000149011612));
                playRandomHitSound();
            } else {
                entity.setMotion(rand.nextFloat() - 0.5f, rand.nextFloat() - 0.5f, rand.nextFloat() - 0.5f);
            }
        } else {
            playRandomHitSound();
        }
        if (durability <= 0 && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            dropAsItem(true, true);
        }
        markVelocityChanged();
        return false;
    }

    public void playRandomHitSound() {
        int i = rand.nextInt(2);
        if (i == 0) {
            playSound(SoundEvents.BLOCK_WOOL_STEP, 0.7f, 1.0f / (rand.nextFloat() * 0.2f + 0.4f));
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
            setCurrentDamage(i - rand.nextInt(2));
        }
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (onGround) {
            setMotion(Vec3d.ZERO);
        } else {
            Vec3d motion = getMotion();
            double motionX = motion.x * 0.99;
            double motionZ = motion.z * 0.99;
            double motionY = motion.y - 0.05;
            fallDistance += (float) (-motionY);
            setMotion(motionX, motionY, motionZ);
        }
        setRotation(rotationYaw, rotationPitch);
        move(MoverType.SELF, new Vec3d(0.0, getMotion().y, 0.0));
        List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(0.2,
                0.0, 0.2), EntityPredicates.pushableBy(this));
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.isPassenger(this)) {
                    applyEntityCollision(entity);
                }
            }
        }
    }

    @Override
    public boolean onLivingFall(float f, float f1) {
        super.onLivingFall(f, f1);
        if (!onGround) {
            return false;
        }
        int i = MathHelper.floor(f);
        attackEntityFrom(DamageSource.FALL, (float) i);
        return false;
    }

    public void dropAsItem(boolean destroyed, boolean noCreative) {
        if (world.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < rand.nextInt(8); ++i) {
                entityDropItem(Items.LEATHER, 1);
            }
        } else if (noCreative) {
            entityDropItem(BalkonsWeaponMod.dummy, 1);
        }
        remove();
    }

    @Override
    public boolean processInitialInteract(PlayerEntity entityplayer, @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof SwordItem || itemstack.getItem() instanceof BowItem || itemstack.getItem() instanceof ShieldItem) {
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

    @Override
    protected void writeAdditional(@Nonnull CompoundNBT nbttagcompound) {
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT nbttagcompound) {
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
