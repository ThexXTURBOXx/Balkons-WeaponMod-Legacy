package ckathode.weaponmod.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import ckathode.weaponmod.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;

public class EntityDynamite extends EntityProjectile
{
    private int explodefuse;
    private boolean extinguished;
    
    public EntityDynamite(final World world) {
        super(world);
        this.setPickupMode(0);
        this.extinguished = false;
        this.explodefuse = this.rand.nextInt(30) + 20;
    }
    
    public EntityDynamite(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }
    
    public EntityDynamite(final World world, final EntityLivingBase shooter, final int i) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.shootingEntity = shooter;
        this.explodefuse = i;
    }
    
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4) {
        final float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        final float y = -MathHelper.sin(f * 0.017453292f);
        final float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        this.shoot(x, y, z, f3, f4);
        this.motionX += entity.motionX;
        this.motionZ += entity.motionZ;
        if (!entity.onGround) {
            this.motionY += entity.motionY;
        }
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.inGround && !this.beenInGround) {
            this.rotationPitch -= 50.0f;
        }
        else {
            this.rotationPitch = 180.0f;
        }
        if (this.isInWater() && !this.extinguished) {
            this.extinguished = true;
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                final float f6 = 0.25f;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX - this.motionX * f6, this.posY - this.motionY * f6, this.posZ - this.motionZ * f6, this.motionX, this.motionY, this.motionZ, new int[0]);
            }
        }
        --this.explodefuse;
        if (!this.extinguished) {
            if (this.explodefuse <= 0) {
                this.detonate();
                this.setDead();
            }
            else if (this.explodefuse > 0) {
                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    @Override
    public void onEntityHit(final Entity entity) {
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        }
        else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, 1.0f)) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.setVelocity(0.0, 0.0, 0.0);
            this.ticksInAir = 0;
        }
    }
    
    @Override
    public void onGroundHit(final RayTraceResult raytraceResult) {
        final BlockPos blockpos = raytraceResult.getBlockPos();
        this.xTile = blockpos.getX();
        this.yTile = blockpos.getY();
        this.zTile = blockpos.getZ();
        final IBlockState iblockstate = this.world.getBlockState(blockpos);
        this.inTile = iblockstate.getBlock();
        this.motionX = raytraceResult.hitVec.x - this.posX;
        this.motionY = raytraceResult.hitVec.y - this.posY;
        this.motionZ = raytraceResult.hitVec.z - this.posZ;
        final float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05;
        this.posY -= this.motionY / f1 * 0.05;
        this.posZ -= this.motionZ / f1 * 0.05;
        this.motionX *= -0.2;
        this.motionZ *= -0.2;
        if (raytraceResult.sideHit == EnumFacing.UP) {
            this.inGround = true;
            this.beenInGround = true;
        }
        else {
            this.inGround = false;
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
        }
        if (this.inTile != null) {
            this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
        }
    }
    
    private void detonate() {
        if (this.world.isRemote) {
            return;
        }
        if (this.extinguished && (this.ticksInGround >= 200 || this.ticksInAir >= 200)) {
            this.setDead();
        }
        final float f = 2.0f;
        PhysHelper.createAdvancedExplosion(this.world, this, this.posX, this.posY, this.posZ, f, BalkonsWeaponMod.instance.modConfig.dynamiteDoesBlockDamage, true, false, false);
    }
    
    @Override
    public boolean aimRotation() {
        return false;
    }
    
    @Override
    public int getMaxArrowShake() {
        return 0;
    }
    
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dynamite, 1);
    }
    
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.dynamite);
    }
    
    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("fuse", (byte)this.explodefuse);
        nbttagcompound.setBoolean("off", this.extinguished);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.explodefuse = nbttagcompound.getByte("fuse");
        this.extinguished = nbttagcompound.getBoolean("off");
    }
}
