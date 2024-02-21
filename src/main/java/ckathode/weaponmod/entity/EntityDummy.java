package ckathode.weaponmod.entity;

import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import ckathode.weaponmod.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import ckathode.weaponmod.item.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;

public class EntityDummy extends Entity
{
    private static final DataParameter<Integer> TIME_SINCE_HIT;
    private static final DataParameter<Byte> ROCK_DIRECTION;
    private static final DataParameter<Integer> CURRENT_DAMAGE;
    private int durability;

    public EntityDummy(final World world) {
        super(world);
        this.preventEntitySpawning = true;
        this.rotationPitch = -20.0f;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.setSize(0.5f, 1.9f);
        this.durability = 50;
    }

    public EntityDummy(final World world, final double d, final double d1, final double d2) {
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
        this.dataManager.register(EntityDummy.TIME_SINCE_HIT, 0);
        this.dataManager.register(EntityDummy.ROCK_DIRECTION, (byte)1);
        this.dataManager.register(EntityDummy.CURRENT_DAMAGE, 0);
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

    public boolean attackEntityFrom(final DamageSource damagesource, final float damage) {
        if (this.world.isRemote || this.isDead || damage <= 0.0f) {
            return false;
        }
        this.setRockDirection(-this.getRockDirection());
        this.setTimeSinceHit(10);
        int i = this.getCurrentDamage();
        i += (int)(damage * 5.0f);
        if (i > 50) {
            i = 50;
        }
        this.setCurrentDamage(i);
        this.markVelocityChanged();
        if (!(damagesource instanceof EntityDamageSource)) {
            this.durability -= (int)damage;
        }
        else if (damagesource instanceof WeaponDamageSource) {
            final Entity entity = ((WeaponDamageSource)damagesource).getProjectile();
            if (MathHelper.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ) > 0.5) {
                final Entity entity2 = entity;
                entity2.motionX *= 0.10000000149011612;
                final Entity entity3 = entity;
                entity3.motionY *= 0.10000000149011612;
                final Entity entity4 = entity;
                entity4.motionZ *= 0.10000000149011612;
                this.playRandomHitSound();
            }
            else {
                entity.motionX = this.rand.nextFloat() - 0.5f;
                entity.motionY = this.rand.nextFloat() - 0.5f;
                entity.motionZ = this.rand.nextFloat() - 0.5f;
            }
        }
        else {
            this.playRandomHitSound();
        }
        if (this.durability <= 0 && this.world.getGameRules().getBoolean("doEntityDrops")) {
            this.dropAsItem(true, true);
        }
        this.markVelocityChanged();
        return false;
    }

    public void playRandomHitSound() {
        final int i = this.rand.nextInt(2);
        if (i == 0) {
            this.playSound(SoundEvents.BLOCK_CLOTH_STEP, 0.7f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.4f));
        }
        else if (i == 1) {
            this.playSound(SoundEvents.BLOCK_WOOD_STEP, 0.7f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.2f));
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
        super.onUpdate();
        int i = this.getTimeSinceHit();
        if (i > 0) {
            this.setTimeSinceHit(i - 1);
        }
        i = this.getCurrentDamage();
        if (i > 0) {
            this.setCurrentDamage(i - this.rand.nextInt(2));
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.onGround) {
            this.motionX = 0.0;
            this.motionY = 0.0;
            this.motionZ = 0.0;
        }
        else {
            this.motionX *= 0.99;
            this.motionZ *= 0.99;
            this.motionY -= 0.05;
            this.fallDistance += (float)(-this.motionY);
        }
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.move(MoverType.SELF, 0.0, this.motionY, 0.0);
        final List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.2, 0.0, 0.2), EntitySelectors.getTeamCollisionPredicate(this));
        if (!list.isEmpty()) {
            for (int j = 0; j < list.size(); ++j) {
                final Entity entity = list.get(j);
                if (!entity.isPassenger(this)) {
                    this.applyEntityCollision(entity);
                }
            }
        }
    }

    public void fall(final float f, final float f1) {
        super.fall(f, f1);
        if (!this.onGround) {
            return;
        }
        final int i = MathHelper.floor(f);
        this.attackEntityFrom(DamageSource.FALL, (float)i);
    }

    public void dropAsItem(final boolean destroyed, final boolean noCreative) {
        if (this.world.isRemote) {
            return;
        }
        if (destroyed) {
            for (int i = 0; i < this.rand.nextInt(8); ++i) {
                this.dropItem(Items.LEATHER, 1);
            }
        }
        else if (noCreative) {
            this.dropItem(BalkonsWeaponMod.dummy, 1);
        }
        this.setDead();
    }

    public boolean processInitialInteract(final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack.isEmpty()) {
            if (entityplayer.capabilities.isCreativeMode) {
                this.dropAsItem(false, false);
                return true;
            }
            this.dropAsItem(false, true);
            return true;
        }
        else {
            if (itemstack.getItem() instanceof IItemWeapon || itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof ItemShield) {
                return false;
            }
            if (entityplayer.capabilities.isCreativeMode) {
                this.dropAsItem(false, false);
                return true;
            }
            this.dropAsItem(false, true);
            return true;
        }
    }

    protected void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
    }

    protected void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        this.setPosition(this.posX, this.posY, this.posZ);
        this.setRotation(this.rotationYaw, this.rotationPitch);
    }

    public void setTimeSinceHit(final int i) {
        this.dataManager.set(EntityDummy.TIME_SINCE_HIT, i);
    }

    public void setRockDirection(final int i) {
        this.dataManager.set(EntityDummy.ROCK_DIRECTION, (byte)i);
    }

    public void setCurrentDamage(final int i) {
        this.dataManager.set(EntityDummy.CURRENT_DAMAGE, i);
    }

    public int getTimeSinceHit() {
        return this.dataManager.get(EntityDummy.TIME_SINCE_HIT);
    }

    public int getRockDirection() {
        return this.dataManager.get(EntityDummy.ROCK_DIRECTION);
    }

    public int getCurrentDamage() {
        return this.dataManager.get(EntityDummy.CURRENT_DAMAGE);
    }

    static {
        TIME_SINCE_HIT = EntityDataManager.createKey(EntityDummy.class, DataSerializers.VARINT);
        ROCK_DIRECTION = EntityDataManager.createKey(EntityDummy.class, DataSerializers.BYTE);
        CURRENT_DAMAGE = EntityDataManager.createKey(EntityDummy.class, DataSerializers.VARINT);
    }
}
