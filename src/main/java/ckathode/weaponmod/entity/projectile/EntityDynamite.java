package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDynamite extends EntityProjectile<EntityDynamite> {
    public static final String NAME = "dynamite";

    private int explodefuse;
    private boolean extinguished;

    public EntityDynamite(final World world) {
        super(BalkonsWeaponMod.entityDynamite, world);
        this.setPickupStatus(PickupStatus.DISALLOWED);
        this.extinguished = false;
        this.explodefuse = this.rand.nextInt(30) + 20;
    }

    public EntityDynamite(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntityDynamite(final World world, final EntityLivingBase shooter, final int i) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        this.explodefuse = i;
    }

    @Override
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3,
                      final float f4) {
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
    protected void registerData() {
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.inGround && !this.beenInGround) {
            this.rotationPitch -= 50.0f;
        } else {
            this.rotationPitch = 180.0f;
        }
        if (this.isInWater() && !this.extinguished) {
            this.extinguished = true;
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
            for (int k = 0; k < 8; ++k) {
                final float f6 = 0.25f;
                this.world.addParticle(Particles.POOF, this.posX - this.motionX * f6,
                        this.posY - this.motionY * f6, this.posZ - this.motionZ * f6, this.motionX, this.motionY,
                        this.motionZ);
            }
        }
        --this.explodefuse;
        if (!this.extinguished) {
            if (this.explodefuse <= 0) {
                this.detonate();
                this.remove();
            } else {
                this.world.addParticle(Particles.SMOKE, this.posX, this.posY, this.posZ, 0.0, 0.0,
                        0.0);
            }
        }
    }

    @Override
    public void onEntityHit(final Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
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
        this.inBlockState = this.world.getBlockState(blockpos);
        this.motionX = raytraceResult.hitVec.x - this.posX;
        this.motionY = raytraceResult.hitVec.y - this.posY;
        this.motionZ = raytraceResult.hitVec.z - this.posZ;
        final float f1 =
                MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05;
        this.posY -= this.motionY / f1 * 0.05;
        this.posZ -= this.motionZ / f1 * 0.05;
        this.motionX *= -0.2;
        this.motionZ *= -0.2;
        if (raytraceResult.sideHit == EnumFacing.UP) {
            this.inGround = true;
            this.beenInGround = true;
        } else {
            this.inGround = false;
            this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f,
                    1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
        }
        if (this.inBlockState != null) {
            this.inBlockState.onEntityCollision(this.world, blockpos, this);
        }
    }

    private void detonate() {
        if (this.world.isRemote) {
            return;
        }
        if (this.extinguished && (this.ticksInGround >= 200 || this.ticksInAir >= 200)) {
            this.remove();
        }
        final float f = 2.0f;
        PhysHelper.createAdvancedExplosion(this.world, this, this.posX, this.posY, this.posZ, f,
                BalkonsWeaponMod.instance.modConfig.dynamiteDoesBlockDamage.get(), true, false, false);
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
        this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.9f));
    }

    @Override
    public void writeAdditional(final NBTTagCompound nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putByte("fuse", (byte) this.explodefuse);
        nbttagcompound.putBoolean("off", this.extinguished);
    }

    @Override
    public void readAdditional(final NBTTagCompound nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.explodefuse = nbttagcompound.getByte("fuse");
        this.extinguished = nbttagcompound.getBoolean("off");
    }
}
