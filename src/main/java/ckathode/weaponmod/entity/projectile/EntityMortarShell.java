package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMortarShell extends EntityProjectile {
    public static final String NAME = "shell";

    public float explosiveSize;

    public EntityMortarShell(World world) {
        super(world);
        explosiveSize = 2.0f;
    }

    public EntityMortarShell(World world, double d, double d1, double d2) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityMortarShell(World world, EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
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
    public void onUpdate() {
        super.onUpdate();
        double speed = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + motionX * i1 / amount,
                        posY + motionY * i1 / amount, posZ + motionZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (worldObj.isRemote || !inGround || isInWater()) {
            return;
        }
        setDead();
        Entity shooter = getThrower();
        if (!(shooter instanceof EntityLivingBase)) return;
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId,
                ((EntityLivingBase) shooter).getHeldItem()) > 0) {
            float f1 = (float) EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId,
                    ((EntityLivingBase) shooter).getHeldItem());
            explosiveSize += f1 / 4.0f;
        }
        boolean flag = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId,
                ((EntityLivingBase) shooter).getHeldItem()) > 0;
        PhysHelper.createAdvancedExplosion(worldObj, this, posX, posY, posZ, explosiveSize,
                BalkonsWeaponMod.instance.modConfig.mortarDoesBlockDamage, true, flag, false);
    }

    @Override
    public void onEntityHit(Entity entity) {
        motionX -= motionX / 2.0;
        motionZ -= motionZ / 2.0;
        motionY -= motionY / 2.0;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 5.0f)) {
            worldObj.playSoundAtEntity(this, "damage.hurtflesh", 1.0f, 1.2f / (rand.nextFloat() * 0.4f + 0.7f));
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
        inGround = true;
        if (inTile.getMaterial() != Material.air) {
            inTile.onEntityCollidedWithBlock(worldObj, blockpos, iBlockState, this);
        }
        createCrater();
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public float getGravity() {
        return 0.04f;
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.mortarShell, 1);
    }
}
