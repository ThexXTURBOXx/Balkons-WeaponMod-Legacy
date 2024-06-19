package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
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
            playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (rand.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                float f6 = 0.25f;
                world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX - motionX * f6,
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
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, 0.0, 0.0,
                        0.0);
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
    public void onGroundHit(RayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        motionX = raytraceResult.hitVec.xCoord - posX;
        motionY = raytraceResult.hitVec.yCoord - posY;
        motionZ = raytraceResult.hitVec.zCoord - posZ;
        float f1 =
                MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
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
            playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (rand.nextFloat() * 0.2f + 0.9f));
        }
        if (inBlockState != null) {
            inBlockState.getBlock().onEntityCollidedWithBlock(world, blockpos, inBlockState, this);
        }
    }

    private void detonate() {
        if (world.isRemote) {
            return;
        }
        if (extinguished && (ticksInGround >= 200 || ticksInAir >= 200)) {
            setDead();
        }
        float f = 2.0f;
        PhysHelper.createAdvancedExplosion(world, this, posX, posY, posZ, f,
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

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.dynamite);
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.9f));
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
