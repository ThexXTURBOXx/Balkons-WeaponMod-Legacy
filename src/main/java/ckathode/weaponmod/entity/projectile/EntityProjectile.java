package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public abstract class EntityProjectile extends EntityArrow implements IThrowableEntity {
    @SuppressWarnings("unchecked")
    private static final Predicate<Entity> WEAPON_TARGETS = Predicates.and(EntitySelectors.NOT_SPECTATING,
            EntitySelectors.IS_ALIVE, Entity::canBeCollidedWith);
    private static final DataParameter<Byte> WEAPON_CRITICAL = EntityDataManager.createKey(EntityProjectile.class,
            DataSerializers.BYTE);
    public static final int NO_PICKUP = 0;
    public static final int PICKUP_ALL = 1;
    public static final int PICKUP_CREATIVE = 2;
    public static final int PICKUP_OWNER = 3;
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected Block inTile;
    protected int inData;
    protected boolean inGround;
    public int pickupMode;
    protected int ticksInGround;
    protected int ticksInAir;
    public boolean beenInGround;
    public float extraDamage;
    public int knockBack;

    public EntityProjectile(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.inTile = null;
        this.inData = 0;
        this.inGround = false;
        this.arrowShake = 0;
        this.ticksInAir = 0;
        this.pickupMode = 0;
        this.extraDamage = 0.0f;
        this.knockBack = 0;
        this.setSize(0.5f, 0.5f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityProjectile.WEAPON_CRITICAL, (byte) 0);
    }

    @Override
    public Entity getThrower() {
        return this.shootingEntity;
    }

    @Override
    public void setThrower(final Entity entity) {
        this.shootingEntity = entity;
    }

    protected void setPickupModeFromEntity(final EntityLivingBase entityliving) {
        if (entityliving instanceof EntityPlayer) {
            if (((EntityPlayer) entityliving).capabilities.isCreativeMode) {
                this.setPickupMode(2);
            } else {
                this.setPickupMode(BalkonsWeaponMod.instance.modConfig.allCanPickup ? 1 : 3);
            }
        } else {
            this.setPickupMode(0);
        }
    }

    @Override
    public void shoot(double x, double y, double z, final float speed, final float deviation) {
        final float f = MathHelper.sqrt(x * x + y * y + z * z);
        x /= f;
        y /= f;
        z /= f;
        x += this.rand.nextGaussian() * 0.007499999832361937 * deviation;
        y += this.rand.nextGaussian() * 0.007499999832361937 * deviation;
        z += this.rand.nextGaussian() * 0.007499999832361937 * deviation;
        x *= speed;
        y *= speed;
        z *= speed;
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
        final float f2 = MathHelper.sqrt(x * x + z * z);
        final float n = (float) (MathHelper.atan2(x, z) * 180.0 / 3.141592653589793);
        this.rotationYaw = n;
        this.prevRotationYaw = n;
        final float n2 = (float) (MathHelper.atan2(y, f2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n2;
        this.prevRotationPitch = n2;
        this.ticksInGround = 0;
    }

    @Override
    public void setVelocity(final double d, final double d1, final double d2) {
        this.motionX = d;
        this.motionY = d1;
        this.motionZ = d2;
        if (this.aimRotation() && this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float f = MathHelper.sqrt(d * d + d2 * d2);
            final float n = (float) (MathHelper.atan2(d, d2) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float) (MathHelper.atan2(d1, f) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    @Override
    public void onUpdate() {
        this.onEntityUpdate();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.aimRotation() && this.prevRotationPitch == 0.0f && this.prevRotationYaw == 0.0f) {
            final float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n = (float) (MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n;
            this.prevRotationYaw = n;
            final float n2 = (float) (MathHelper.atan2(this.motionY, f) * 180.0 / 3.141592653589793);
            this.rotationPitch = n2;
            this.prevRotationPitch = n2;
        }
        final BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
        final IBlockState iblockstate = this.world.getBlockState(blockpos);
        final Block block = iblockstate.getBlock();
        if (iblockstate.getMaterial() != Material.AIR) {
            final AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);
            if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX,
                    this.posY, this.posZ))) {
                this.inGround = true;
            }
        }
        if (this.arrowShake > 0) {
            --this.arrowShake;
        }
        if (this.inGround) {
            final Block j = iblockstate.getBlock();
            final int k = block.getMetaFromState(iblockstate);
            if ((j != this.inTile || k != this.inData) && !this.world.collidesWithAnyBlock(this.getEntityBoundingBox().grow(0.05))) {
                this.inGround = false;
                this.motionX *= this.rand.nextFloat() * 0.2f;
                this.motionY *= this.rand.nextFloat() * 0.2f;
                this.motionZ *= this.rand.nextFloat() * 0.2f;
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            } else {
                ++this.ticksInGround;
                final int t = this.getMaxLifetime();
                if (t != 0 && this.ticksInGround >= t) {
                    this.setDead();
                }
            }
            ++this.timeInGround;
            return;
        }
        this.timeInGround = 0;
        ++this.ticksInAir;
        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d2, false, true, false);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d2 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (raytraceresult != null) {
            vec3d2 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
        }
        final Entity entity = this.findEntity(vec3d, vec3d2);
        if (entity != null) {
            raytraceresult = new RayTraceResult(entity);
        }
        if (raytraceresult != null) {
            if (raytraceresult.entityHit != null) {
                this.onEntityHit(raytraceresult.entityHit);
            } else {
                this.onGroundHit(raytraceresult);
            }
        }
        if (this.getIsCritical()) {
            for (int i1 = 0; i1 < 2; ++i1) {
                this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * i1 / 4.0,
                        this.posY + this.motionY * i1 / 4.0, this.posZ + this.motionZ * i1 / 4.0, -this.motionX,
                        -this.motionY + 0.2, -this.motionZ);
            }
        }
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        if (this.aimRotation()) {
            final float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            final float n3 = (float) (MathHelper.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
            this.rotationYaw = n3;
            this.prevRotationYaw = n3;
            final float n4 = (float) (MathHelper.atan2(this.motionY, f2) * 180.0 / 3.141592653589793);
            this.rotationPitch = n4;
            this.prevRotationPitch = n4;
        }
        float res = this.getAirResistance();
        final float grav = this.getGravity();
        if (this.isInWater()) {
            this.beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                final float f3 = 0.25f;
                this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25,
                        this.posY - this.motionY * 0.25, this.posZ - this.motionZ * 0.25, this.motionX, this.motionY,
                        this.motionZ);
            }
            res *= 0.6f;
        }
        this.motionX *= res;
        this.motionY *= res;
        this.motionZ *= res;
        if (!this.hasNoGravity()) {
            this.motionY -= grav;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.doBlockCollisions();
    }

    public void onEntityHit(final Entity entity) {
        this.bounceBack();
        this.applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(final Entity entity) {
        if (this.isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setFire(5);
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityliving = (EntityLivingBase) entity;
            if (this.knockBack > 0) {
                final float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                if (f > 0.0f) {
                    entity.addVelocity(this.motionX * this.knockBack * 0.6 / f, 0.1,
                            this.motionZ * this.knockBack * 0.6 / f);
                }
            }
            if (this.shootingEntity instanceof EntityLivingBase) {
                EnchantmentHelper.applyThornEnchantments(entityliving, this.shootingEntity);
                EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entityliving);
            }
            if (this.shootingEntity instanceof EntityPlayerMP && this.shootingEntity != entity && entity instanceof EntityPlayer) {
                ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0f));
            }
        }
    }

    public void onGroundHit(final RayTraceResult raytraceResult) {
        final BlockPos blockpos = raytraceResult.getBlockPos();
        this.xTile = blockpos.getX();
        this.yTile = blockpos.getY();
        this.zTile = blockpos.getZ();
        final IBlockState iblockstate = this.world.getBlockState(blockpos);
        this.inTile = iblockstate.getBlock();
        this.inData = this.inTile.getMetaFromState(iblockstate);
        this.motionX = raytraceResult.hitVec.x - this.posX;
        this.motionY = raytraceResult.hitVec.y - this.posY;
        this.motionZ = raytraceResult.hitVec.z - this.posZ;
        final float f1 =
                MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05;
        this.posY -= this.motionY / f1 * 0.05;
        this.posZ -= this.motionZ / f1 * 0.05;
        this.inGround = true;
        this.beenInGround = true;
        this.setIsCritical(false);
        this.arrowShake = this.getMaxArrowShake();
        this.playHitSound();
        if (this.inTile != null) {
            this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
        }
    }

    protected void bounceBack() {
        this.motionX *= -0.1;
        this.motionY *= -0.1;
        this.motionZ *= -0.1;
        this.rotationYaw += 180.0f;
        this.prevRotationYaw += 180.0f;
        this.ticksInAir = 0;
    }

    @Nullable
    protected Entity findEntity(final Vec3d vec3d, final Vec3d vec3d1) {
        Entity entity = null;
        final List<Entity> list = this.world.getEntitiesInAABBexcluding(this,
                this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0),
                EntityProjectile.WEAPON_TARGETS);
        double d = 0.0;
        for (final Entity entity2 : list) {
            if (entity2 != this.shootingEntity || this.ticksInAir >= 5) {
                final AxisAlignedBB axisalignedbb = entity2.getEntityBoundingBox().grow(0.3);
                final RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d1);
                if (raytraceresult != null) {
                    final double d2 = vec3d.squareDistanceTo(raytraceresult.hitVec);
                    if (d2 < d || d == 0.0) {
                        entity = entity2;
                        d = d2;
                    }
                }
            }
        }
        return entity;
    }

    public final double getTotalVelocity() {
        return MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
    }

    public boolean aimRotation() {
        return true;
    }

    public int getMaxLifetime() {
        return 1200;
    }

    public ItemStack getPickupItem() {
        return null;
    }

    public float getAirResistance() {
        return 0.99f;
    }

    public float getGravity() {
        return 0.05f;
    }

    public int getMaxArrowShake() {
        return 7;
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return ItemStack.EMPTY;
    }

    public void playHitSound() {
    }

    public boolean canBeCritical() {
        return false;
    }

    @Override
    public void setIsCritical(final boolean flag) {
        if (this.canBeCritical()) {
            this.dataManager.set(EntityProjectile.WEAPON_CRITICAL, (byte) (flag ? 1 : 0));
        }
    }

    @Override
    public boolean getIsCritical() {
        return this.canBeCritical() && this.dataManager.get(EntityProjectile.WEAPON_CRITICAL) != 0;
    }

    public void setExtraDamage(final float f) {
        this.extraDamage = f;
    }

    @Override
    public void setKnockbackStrength(final int i) {
        this.knockBack = i;
    }

    public void setPickupMode(final int i) {
        this.pickupMode = i;
    }

    public int getPickupMode() {
        return this.pickupMode;
    }

    public boolean canPickup(final EntityPlayer entityplayer) {
        if (this.pickupMode == 1) {
            return true;
        }
        if (this.pickupMode == 2) {
            return entityplayer.capabilities.isCreativeMode;
        }
        return this.pickupMode == 3 && entityplayer == this.shootingEntity;
    }

    @Override
    public void onCollideWithPlayer(@Nonnull final EntityPlayer entityplayer) {
        if (this.inGround && this.arrowShake <= 0 && this.canPickup(entityplayer) && !this.world.isRemote) {
            final ItemStack item = this.getPickupItem();
            if (item == null) {
                return;
            }
            if ((this.pickupMode == 2 && entityplayer.capabilities.isCreativeMode) || entityplayer.inventory.addItemStackToInventory(item)) {
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                        ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                this.onItemPickup(entityplayer);
                this.setDead();
            }
        }
    }

    protected void onItemPickup(final EntityPlayer entityplayer) {
        entityplayer.onItemPickup(this, 1);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public float getEyeHeight() {
        return 0.0f;
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("xTile", this.xTile);
        nbttagcompound.setInteger("yTile", this.yTile);
        nbttagcompound.setInteger("zTile", this.zTile);
        final ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
        nbttagcompound.setString("inTile", resourcelocation.toString());
        nbttagcompound.setByte("inData", (byte) this.inData);
        nbttagcompound.setByte("shake", (byte) this.arrowShake);
        nbttagcompound.setBoolean("inGround", this.inGround);
        nbttagcompound.setBoolean("beenInGround", this.beenInGround);
        nbttagcompound.setByte("pickup", (byte) this.pickupMode);
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        this.xTile = nbttagcompound.getInteger("xTile");
        this.yTile = nbttagcompound.getInteger("yTile");
        this.zTile = nbttagcompound.getInteger("zTile");
        if (nbttagcompound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(nbttagcompound.getString("inTile"));
        } else {
            this.inTile = Block.getBlockById(nbttagcompound.getByte("inTile") & 0xFF);
        }
        this.inData = (nbttagcompound.getByte("inData") & 0xFF);
        this.arrowShake = (nbttagcompound.getByte("shake") & 0xFF);
        this.inGround = nbttagcompound.getBoolean("inGround");
        this.beenInGround = nbttagcompound.getBoolean("beenInGrond");
        this.pickupMode = nbttagcompound.getByte("pickup");
    }

}
