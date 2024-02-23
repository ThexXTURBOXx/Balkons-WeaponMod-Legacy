package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import javax.annotation.Nonnull;
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
    private static final DataParameter<Integer> BOOMERANG;
    public static final double RETURN_STRENGTH = 0.05;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;
    private float soundTimer;
    public float floatStrength;

    public EntityBoomerang(final World world) {
        super(world);
    }

    public EntityBoomerang(final World world, final double x, final double y, final double z) {
        this(world);
        this.setPosition(x, y, z);
    }

    public EntityBoomerang(final World world, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.setPickupModeFromEntity((EntityLivingBase) (this.shootingEntity = shooter));
        this.setThrownItemStack(itemstack);
        this.soundTimer = 0.0f;
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
        this.floatStrength = Math.min(1.5f, f3);
        this.dataManager.set(EntityBoomerang.BOOMERANG, Float.floatToRawIntBits(this.floatStrength));
    }

    @Override
    public void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBoomerang.BOOMERANG, Float.floatToRawIntBits(0.0f));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.floatStrength = Float.intBitsToFloat(this.dataManager.get(EntityBoomerang.BOOMERANG));
        if (this.inGround) {
            return;
        }
        this.floatStrength *= 0.994f;
        if (this.floatStrength < 0.4f) {
            if (this.getIsCritical()) {
                this.setIsCritical(false);
            }
            this.floatStrength = 0.0f;
        }
        final float limitedStrength = Math.min(1.0f, this.floatStrength);
        if (!this.beenInGround) {
            this.rotationYaw += 20.0f * this.floatStrength;
        }
        if (!this.beenInGround && this.shootingEntity != null && this.floatStrength > 0.0f) {
            double dx = this.posX - this.shootingEntity.posX;
            double dy = this.posY - this.shootingEntity.posY - this.shootingEntity.getEyeHeight();
            double dz = this.posZ - this.shootingEntity.posZ;
            final double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= d;
            dy /= d;
            dz /= d;
            this.motionX -= 0.05 * dx;
            this.motionY -= 0.05 * dy;
            this.motionZ -= 0.05 * dz;
            this.soundTimer += limitedStrength;
            if (this.soundTimer > 3.0f) {
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f,
                        1.0f / (this.rand.nextFloat() * 0.2f + 2.2f - limitedStrength));
                this.soundTimer %= 3.0f;
            }
        }
        this.dataManager.set(EntityBoomerang.BOOMERANG, Float.floatToRawIntBits(this.floatStrength));
    }

    @Override
    public void onEntityHit(final Entity entity) {
        if (this.world.isRemote || this.floatStrength < 0.4f) {
            return;
        }
        if (entity == this.shootingEntity) {
            if (entity instanceof EntityPlayer) {
                final EntityPlayer player = (EntityPlayer) entity;
                final ItemStack item = this.getPickupItem();
                if (item.isEmpty()) {
                    return;
                }
                if (player.capabilities.isCreativeMode || player.inventory.addItemStackToInventory(item)) {
                    this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                            ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    this.onItemPickup(player);
                    this.setDead();
                }
            }
            return;
        }
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        } else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        float damage =
                ((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 3.0f + this.extraDamage;
        damage += this.getMeleeHitDamage(entity);
        if (this.getIsCritical()) {
            damage += 2.0f;
        }
        if (entity.attackEntityFrom(damagesource, damage)) {
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            if (this.thrownItem.getItemDamage() + 1 > this.thrownItem.getMaxDamage()) {
                this.thrownItem.shrink(1);
                this.setDead();
            } else {
                if (this.shootingEntity instanceof EntityLivingBase) {
                    this.thrownItem.damageItem(1, (EntityLivingBase) this.shootingEntity);
                } else {
                    this.thrownItem.attemptDamageItem(1, this.rand, null);
                }
                this.setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            this.bounceBack();
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
        final float f1 =
                MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.posX -= this.motionX / f1 * 0.05;
        this.posY -= this.motionY / f1 * 0.05;
        this.posZ -= this.motionZ / f1 * 0.05;
        this.motionX *= -this.rand.nextFloat() * 0.5f;
        this.motionZ *= -this.rand.nextFloat() * 0.5f;
        this.motionY = this.rand.nextFloat() * 0.1f;
        this.inGround = raytraceResult.sideHit == EnumFacing.UP;
        this.setIsCritical(false);
        this.beenInGround = true;
        this.floatStrength = 0.0f;
        if (this.inTile != null) {
            this.inTile.onEntityCollision(this.world, blockpos, iblockstate, this);
        }
    }

    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public boolean aimRotation() {
        return this.beenInGround || this.floatStrength < 0.4f;
    }

    @Override
    public int getMaxLifetime() {
        return (this.pickupMode == 1 || this.pickupMode == 3) ? 0 : 1200;
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
        return (this.beenInGround || this.floatStrength < 0.4f) ? 0.05f : 0.0f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public void onCollideWithPlayer(@Nonnull final EntityPlayer entityplayer) {
        if (!this.beenInGround && this.ticksInAir > 5 && this.floatStrength >= 0.4f && entityplayer == this.shootingEntity) {
            final ItemStack item = this.getPickupItem();
            if (item == null) {
                return;
            }
            if (entityplayer.capabilities.isCreativeMode || entityplayer.inventory.addItemStackToInventory(item)) {
                this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                        ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                this.onItemPickup(entityplayer);
                this.setDead();
                return;
            }
        }
        super.onCollideWithPlayer(entityplayer);
    }

    static {
        BOOMERANG = EntityDataManager.createKey(EntityBoomerang.class, DataSerializers.VARINT);
    }
}
