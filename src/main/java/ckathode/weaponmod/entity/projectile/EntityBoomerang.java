package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBoomerang extends EntityMaterialProjectile {
    public static final String NAME = "boomerang";

    private static final DataParameter<Float> BOOMERANG = EntityDataManager.createKey(EntityBoomerang.class,
            DataSerializers.FLOAT);
    public static final double RETURN_STRENGTH = 0.05;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;
    private float soundTimer;
    public float floatStrength;

    public EntityBoomerang(World world) {
        super(world);
    }

    public EntityBoomerang(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
    }

    public EntityBoomerang(World world, EntityLivingBase shooter, ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        soundTimer = 0.0f;
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setThrowableHeading(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
        floatStrength = Math.min(1.5f, f3);
        dataManager.set(BOOMERANG, floatStrength);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        dataManager.register(BOOMERANG, 0.0f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        floatStrength = dataManager.get(BOOMERANG);
        if (inGround) {
            return;
        }
        floatStrength *= 0.994f;
        if (floatStrength < MIN_FLOAT_STRENGTH) {
            if (getIsCritical()) {
                setIsCritical(false);
            }
            floatStrength = 0.0f;
        }
        float limitedStrength = Math.min(1.0f, floatStrength);
        if (!beenInGround) {
            rotationYaw += 20.0f * floatStrength;
        }
        Entity shooter;
        if (!beenInGround && (shooter = getThrower()) != null && floatStrength > 0.0f) {
            double dx = posX - shooter.posX;
            double dy = posY - shooter.posY - shooter.getEyeHeight();
            double dz = posZ - shooter.posZ;
            double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= d;
            dy /= d;
            dz /= d;
            motionX -= RETURN_STRENGTH * dx;
            motionY -= RETURN_STRENGTH * dy;
            motionZ -= RETURN_STRENGTH * dz;
            soundTimer += limitedStrength;
            if (soundTimer > 3.0f) {
                playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f,
                        1.0f / (rand.nextFloat() * 0.2f + 2.2f - limitedStrength));
                soundTimer %= 3.0f;
            }
        }
        dataManager.set(BOOMERANG, floatStrength);
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (worldObj.isRemote || floatStrength < MIN_FLOAT_STRENGTH) {
            return;
        }
        Entity shooter = getThrower();
        if (entity == shooter) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ItemStack item = getPickupItem();
                if (item == null) {
                    return;
                }
                if (player.isCreative() || player.inventory.addItemStackToInventory(item)) {
                    playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                            ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    onItemPickup(player);
                    setDead();
                }
            }
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        float damage =
                ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 2.0f + extraDamage;
        damage += getMeleeHitDamage(entity);
        if (getIsCritical()) {
            damage += 2.0f;
        }
        if (entity.attackEntityFrom(damagesource, damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getItemDamage() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.splitStack(1);
                if (thrownItem.stackSize <= 0) {
                    setThrownItemStack(null);
                }
                setDead();
            } else {
                thrownItem.attemptDamageItem(1, rand);
                if (thrownItem.stackSize <= 0) {
                    setThrownItemStack(null);
                }
                setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void onGroundHit(RayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        IBlockState iBlockState = worldObj.getBlockState(blockpos);
        inTile = iBlockState.getBlock();
        inData = inTile.getMetaFromState(iBlockState);
        motionX = raytraceResult.hitVec.xCoord - posX;
        motionY = raytraceResult.hitVec.yCoord - posY;
        motionZ = raytraceResult.hitVec.zCoord - posZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * RETURN_STRENGTH;
        posY -= motionY / f1 * RETURN_STRENGTH;
        posZ -= motionZ / f1 * RETURN_STRENGTH;
        motionX *= -rand.nextFloat() * 0.5f;
        motionZ *= -rand.nextFloat() * 0.5f;
        motionY = rand.nextFloat() * 0.1f;
        inGround = raytraceResult.sideHit == EnumFacing.UP;
        setIsCritical(false);
        beenInGround = true;
        floatStrength = 0.0f;
        if (iBlockState.getMaterial() != Material.AIR) {
            inTile.onEntityCollidedWithBlock(worldObj, blockpos, iBlockState, this);
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public boolean aimRotation() {
        return beenInGround || floatStrength < MIN_FLOAT_STRENGTH;
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Override
    public float getGravity() {
        return (beenInGround || floatStrength < MIN_FLOAT_STRENGTH) ? (float) RETURN_STRENGTH : 0.0f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public void onCollideWithPlayer(@Nonnull EntityPlayer entityplayer) {
        if (!beenInGround && ticksInAir > 5 && !worldObj.isRemote && floatStrength >= MIN_FLOAT_STRENGTH &&
            entityplayer == shootingEntity) {
            ItemStack item = getPickupItem();
            if (item == null) {
                return;
            }
            if (entityplayer.isCreative() || entityplayer.inventory.addItemStackToInventory(item)) {
                playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                        ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                setDead();
                return;
            }
        }
        super.onCollideWithPlayer(entityplayer);
    }

}
