package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityProjectile {
    public static final String NAME = "dynamite";

    private int explodefuse;
    private boolean extinguished;

    public EntityDynamite(World world) {
        super(world);
        setPickupStatus(PickupStatus.DISALLOWED);
        extinguished = false;
        explodefuse = rand.nextInt(30) + 20;
    }

    public EntityDynamite(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityDynamite(World world, EntityLivingBase shooter, int i) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setThrower(shooter);
        explodefuse = i;
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
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!inGround && !beenInGround) {
            rotationPitch -= 50.0f;
        } else {
            rotationPitch = 180.0f;
        }
        if (isInWater() && !extinguished) {
            extinguished = true;
            worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX - motionX * f6,
                        posY - motionY * f6, posZ - motionZ * f6, motionX, motionY,
                        motionZ);
            }
        }
        --explodefuse;
        if (!extinguished) {
            if (explodefuse <= 0) {
                detonate();
                setDead();
            } else {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f)) {
            applyEntityHitEffects(entity);
            playHitSound();
            setVelocity(0.0, 0.0, 0.0);
            ticksInAir = 0;
        }
    }

    @Override
    public void onGroundHit(MovingObjectPosition raytraceResult) {
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
        posX -= motionX / f1 * 0.05;
        posY -= motionY / f1 * 0.05;
        posZ -= motionZ / f1 * 0.05;
        motionX *= -0.2;
        motionZ *= -0.2;
        if (raytraceResult.sideHit == EnumFacing.UP) {
            inGround = true;
            beenInGround = true;
        } else {
            inGround = false;
            worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
        }
        if (inTile.getMaterial() != Material.air) {
            inTile.onEntityCollidedWithBlock(worldObj, blockpos, iBlockState, this);
        }
    }

    private void detonate() {
        if (worldObj.isRemote) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            setDead();
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(worldObj, this, posX, posY, posZ, f,
                BalkonsWeaponMod.instance.modConfig.dynamiteDoesBlockDamage, true, false, false);
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dynamite, 1);
    }

    @Override
    public void playHitSound() {
        worldObj.playSoundAtEntity(this, "random.fizz", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("fuse", (byte) explodefuse);
        nbttagcompound.setBoolean("off", extinguished);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        explodefuse = nbttagcompound.getByte("fuse");
        extinguished = nbttagcompound.getBoolean("off");
    }
}
