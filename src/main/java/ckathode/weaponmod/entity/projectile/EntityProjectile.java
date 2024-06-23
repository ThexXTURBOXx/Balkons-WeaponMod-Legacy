package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import cpw.mods.fml.common.registry.IThrowableEntity;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityProjectile extends EntityArrow implements IThrowableEntity {
    private static final int WEAPON_CRITICAL = 17;
    protected int xTile;
    protected int yTile;
    protected int zTile;
    @Nullable
    protected Block inTile;
    protected int inData;
    protected boolean inGround;
    public PickupStatus pickupStatus;
    protected int ticksInGround;
    protected int ticksInAir;
    public boolean beenInGround;
    public float extraDamage;
    public int knockBack;

    public EntityProjectile(World world) {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = null;
        inGround = false;
        arrowShake = 0;
        ticksInAir = 0;
        pickupStatus = PickupStatus.DISALLOWED;
        extraDamage = 0.0f;
        knockBack = 0;
        setSize(0.5f, 0.5f);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataWatcher.addObject(WEAPON_CRITICAL, (byte) 0);
    }

    @Override
    public Entity getThrower() {
        return shootingEntity;
    }

    @Override
    public void setThrower(Entity entity) {
        shootingEntity = entity;
    }

    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
    }

    protected void setPickupStatusFromEntity(EntityLivingBase entityliving) {
        if (entityliving instanceof EntityPlayer) {
            if (((EntityPlayer) entityliving).capabilities.isCreativeMode) {
                setPickupStatus(PickupStatus.CREATIVE_ONLY);
            } else {
                setPickupStatus(BalkonsWeaponMod.instance.modConfig.allCanPickup
                        ? PickupStatus.ALLOWED : PickupStatus.OWNER_ONLY);
            }
        } else {
            setPickupStatus(PickupStatus.DISALLOWED);
        }
    }

    public Entity getDamagingEntity() {
        Entity shooter = getThrower();
        return shooter != null ? shooter : this;
    }

    @Override
    public void setThrowableHeading(double x, double y, double z, float speed, float deviation) {
        float f = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f;
        y /= f;
        z /= f;
        x += rand.nextGaussian() * 0.007499999832361937 * deviation;
        y += rand.nextGaussian() * 0.007499999832361937 * deviation;
        z += rand.nextGaussian() * 0.007499999832361937 * deviation;
        x *= speed;
        y *= speed;
        z *= speed;
        motionX = x;
        motionY = y;
        motionZ = z;
        float f2 = MathHelper.sqrt_double(x * x + z * z);
        float n = (float) (Math.atan2(x, z) * 180.0 / 3.141592653589793);
        rotationYaw = n;
        prevRotationYaw = n;
        float n2 = (float) (Math.atan2(y, f2) * 180.0 / 3.141592653589793);
        rotationPitch = n2;
        prevRotationPitch = n2;
        ticksInGround = 0;
    }

    @Override
    public void setVelocity(double d, double d1, double d2) {
        motionX = d;
        motionY = d1;
        motionZ = d2;
        if (aimRotation() && prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt_double(d * d + d2 * d2);
            float n = (float) (Math.atan2(d, d2) * 180.0 / 3.141592653589793);
            rotationYaw = n;
            prevRotationYaw = n;
            float n2 = (float) (Math.atan2(d1, f) * 180.0 / 3.141592653589793);
            rotationPitch = n2;
            prevRotationPitch = n2;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
            ticksInGround = 0;
        }
    }

    @Override
    public void onUpdate() {
        onEntityUpdate();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (aimRotation() && prevRotationPitch == 0.0f && prevRotationYaw == 0.0f) {
            float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            float n = (float) (Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
            rotationYaw = n;
            prevRotationYaw = n;
            float n2 = (float) (Math.atan2(motionY, f) * 180.0 / 3.141592653589793);
            rotationPitch = n2;
            prevRotationPitch = n2;
        }
        Block block = worldObj.getBlock(xTile, yTile, zTile);
        if (block != null) {
            block.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
            AxisAlignedBB axisAlignedBB = block.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
            if (axisAlignedBB != null && axisAlignedBB.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
                inGround = true;
            }
        }
        if (arrowShake > 0) {
            --arrowShake;
        }
        if (isWet()) {
            extinguish();
        }
        if (inGround) {
            int i = worldObj.getBlockMetadata(xTile, yTile, zTile);
            if (block != inTile || i != inData) {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2f;
                motionY *= rand.nextFloat() * 0.2f;
                motionZ *= rand.nextFloat() * 0.2f;
                ticksInGround = 0;
                ticksInAir = 0;
            } else if (!worldObj.isRemote) {
                ++ticksInGround;
                int t = getMaxLifetime();
                if (t != 0 && ticksInGround >= t) {
                    setDead();
                }
            }
            ++ticksInGround;
            return;
        }
        ticksInGround = 0;
        ++ticksInAir;
        Vec3 vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec3d2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition raytraceresult = worldObj.func_147447_a(vec3d, vec3d2, false, true, false);
        vec3d = Vec3.createVectorHelper(posX, posY, posZ);
        vec3d2 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if (raytraceresult != null) {
            vec3d2 = Vec3.createVectorHelper(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord,
                    raytraceresult.hitVec.zCoord);
        }
        Entity entity = findEntity(vec3d, vec3d2);
        if (entity != null) {
            raytraceresult = new MovingObjectPosition(entity);
        }
        if (raytraceresult != null) {
            if (raytraceresult.entityHit != null) {
                onEntityHit(raytraceresult.entityHit);
            } else {
                onGroundHit(raytraceresult);
            }
        }
        if (getIsCritical()) {
            for (int i1 = 0; i1 < 2; ++i1) {
                worldObj.spawnParticle("crit", posX + motionX * i1 / 4.0,
                        posY + motionY * i1 / 4.0, posZ + motionZ * i1 / 4.0, -motionX,
                        -motionY + 0.2, -motionZ);
            }
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        if (aimRotation()) {
            float f2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
            float n3 = (float) (Math.atan2(motionX, motionZ) * 180.0 / 3.141592653589793);
            rotationYaw = n3;
            prevRotationYaw = n3;
            float n4 = (float) (Math.atan2(motionY, f2) * 180.0 / 3.141592653589793);
            rotationPitch = n4;
            prevRotationPitch = n4;
        }
        float res = getAirResistance();
        float grav = getGravity();
        if (isInWater()) {
            beenInGround = true;
            for (int i2 = 0; i2 < 4; ++i2) {
                float f3 = 0.25f;
                worldObj.spawnParticle("bubble", posX - motionX * f3,
                        posY - motionY * f3, posZ - motionZ * f3, motionX, motionY,
                        motionZ);
            }
            res *= 0.6f;
        }
        motionX *= res;
        motionY *= res;
        motionZ *= res;
        motionY -= grav;
        setPosition(posX, posY, posZ);
        func_145775_I();
    }

    public void onEntityHit(Entity entity) {
        bounceBack();
        applyEntityHitEffects(entity);
    }

    public void applyEntityHitEffects(Entity entity) {
        if (isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setFire(5);
        }
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityliving = (EntityLivingBase) entity;
            if (knockBack > 0) {
                float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                if (f > 0.0f) {
                    entity.addVelocity(motionX * knockBack * 0.6 / f, 0.1,
                            motionZ * knockBack * 0.6 / f);
                }
            }
            if (shootingEntity instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a(entityliving, shootingEntity);
                EnchantmentHelper.func_151385_b((EntityLivingBase) shootingEntity, entityliving);
            }
            if (shootingEntity instanceof EntityPlayerMP && shootingEntity != entity && entity instanceof EntityPlayer) {
                ((EntityPlayerMP) shootingEntity).playerNetServerHandler.sendPacket(
                        new S2BPacketChangeGameState(6, 0.0f));
            }
        }
    }

    public void onGroundHit(MovingObjectPosition raytraceResult) {
        xTile = raytraceResult.blockX;
        yTile = raytraceResult.blockY;
        zTile = raytraceResult.blockZ;
        inTile = worldObj.getBlock(xTile, yTile, zTile);
        inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
        motionX = raytraceResult.hitVec.xCoord - posX;
        motionY = raytraceResult.hitVec.yCoord - posY;
        motionZ = raytraceResult.hitVec.zCoord - posZ;
        float f1 =
                MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05;
        posY -= motionY / f1 * 0.05;
        posZ -= motionZ / f1 * 0.05;
        inGround = true;
        beenInGround = true;
        setIsCritical(false);
        arrowShake = getMaxArrowShake();
        playHitSound();
        if (inTile != null) {
            inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
        }
    }

    protected void bounceBack() {
        motionX *= -0.1;
        motionY *= -0.1;
        motionZ *= -0.1;
        rotationYaw += 180.0f;
        prevRotationYaw += 180.0f;
        ticksInAir = 0;
    }

    @Nullable
    protected Entity findEntity(Vec3 vec3d, Vec3 vec3d1) {
        Entity entity = null;
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
                EntityProjectile.getBoundingBox(this).addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
        double d = 0.0;
        for (Entity entity2 : list) {
            if (entity2 != shootingEntity || ticksInAir >= 5) {
                AxisAlignedBB axisalignedbb = EntityProjectile.getBoundingBox(entity2).expand(0.3, 0.3, 0.3);
                MovingObjectPosition raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d1);
                if (raytraceresult != null) {
                    double d2 = vec3d.squareDistanceTo(raytraceresult.hitVec);
                    if (d2 < d || d == 0.0) {
                        entity = entity2;
                        d = d2;
                    }
                }
            }
        }
        return entity;
    }

    public double getTotalVelocity() {
        return MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
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

    public void playHitSound() {
    }

    public boolean canBeCritical() {
        return false;
    }

    @Override
    public void setIsCritical(boolean flag) {
        if (canBeCritical()) {
            dataWatcher.updateObject(WEAPON_CRITICAL, (byte) (flag ? 1 : 0));
        }
    }

    @Override
    public boolean getIsCritical() {
        return canBeCritical() && dataWatcher.getWatchableObjectByte(WEAPON_CRITICAL) != 0;
    }

    public void setExtraDamage(float f) {
        extraDamage = f;
    }

    @Override
    public void setKnockbackStrength(int i) {
        knockBack = i;
    }

    public void setPickupStatus(PickupStatus i) {
        pickupStatus = i;
    }

    public PickupStatus getPickupStatus() {
        return pickupStatus;
    }

    public boolean canPickup(EntityPlayer entityplayer) {
        if (pickupStatus == PickupStatus.ALLOWED) {
            return true;
        }
        if (pickupStatus == PickupStatus.CREATIVE_ONLY) {
            return entityplayer.capabilities.isCreativeMode;
        }
        return pickupStatus == PickupStatus.OWNER_ONLY && entityplayer == shootingEntity;
    }

    @Override
    public void onCollideWithPlayer(@Nonnull EntityPlayer entityplayer) {
        if (inGround && arrowShake <= 0 && canPickup(entityplayer) && !worldObj.isRemote) {
            ItemStack item = getPickupItem();
            if (item == null) {
                return;
            }
            if ((pickupStatus == PickupStatus.CREATIVE_ONLY && entityplayer.capabilities.isCreativeMode) ||
                entityplayer.inventory.addItemStackToInventory(item)) {
                playSound("random.pop", 0.2f, ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                setDead();
            }
        }
    }

    protected void onItemPickup(EntityPlayer entityplayer) {
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
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInteger("xTile", xTile);
        nbttagcompound.setInteger("yTile", yTile);
        nbttagcompound.setInteger("zTile", zTile);
        if (inTile != null) {
            nbttagcompound.setByte("inTile", (byte) Block.getIdFromBlock(inTile));
        }
        nbttagcompound.setByte("inData", (byte) inData);
        nbttagcompound.setByte("shake", (byte) arrowShake);
        nbttagcompound.setBoolean("inGround", inGround);
        nbttagcompound.setBoolean("beenInGround", beenInGround);
        nbttagcompound.setByte("pickup", (byte) pickupStatus.ordinal());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        xTile = nbttagcompound.getInteger("xTile");
        yTile = nbttagcompound.getInteger("yTile");
        zTile = nbttagcompound.getInteger("zTile");
        inTile = nbttagcompound.hasKey("inTile", 8) ? Block.getBlockFromName(nbttagcompound.getString("inTile")) :
                Block.getBlockById(nbttagcompound.getByte("inTile") & 0xFF);
        inData = nbttagcompound.getByte("inData") & 0xFF;
        arrowShake = (nbttagcompound.getByte("shake") & 0xFF);
        inGround = nbttagcompound.getBoolean("inGround");
        beenInGround = nbttagcompound.getBoolean("beenInGrond");
        pickupStatus = PickupStatus.getByOrdinal(nbttagcompound.getByte("pickup"));
    }

    public static AxisAlignedBB getBoundingBox(Entity entity) {
        AxisAlignedBB box = entity == null ? null : entity.getBoundingBox();
        box = box != null || entity == null ? box : entity.boundingBox;
        return box == null ? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : box;
    }

    public enum PickupStatus {
        DISALLOWED,
        ALLOWED,
        CREATIVE_ONLY,
        OWNER_ONLY;

        public static PickupStatus getByOrdinal(int ordinal) {
            if (ordinal < 0 || ordinal > values().length) {
                ordinal = 0;
            }

            return values()[ordinal];
        }
    }

}
