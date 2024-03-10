package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
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
    public void shoot(Entity entity, float f, float f1, float f2, float f3,
                      float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        double speed =
                MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + motionX * i1 / amount,
                        posY + motionY * i1 / amount, posZ + motionZ * i1 / amount, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (world.isRemote || !inGround || isInWater()) {
            return;
        }
        setDead();
        Entity shooter = getThrower();
        if (!(shooter instanceof EntityLivingBase)) return;
        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, (EntityLivingBase) shooter) > 0) {
            float f1 = (float) EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER,
                    (EntityLivingBase) shooter);
            explosiveSize += f1 / 4.0f;
        }
        boolean flag =
                EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, (EntityLivingBase) shooter) > 0;
        PhysHelper.createAdvancedExplosion(world, this, posX, posY, posZ, explosiveSize,
                BalkonsWeaponMod.instance.modConfig.mortarDoesBlockDamage, true, flag, false);
    }

    @Override
    public void onEntityHit(Entity entity) {
        motionX -= motionX / 2.0;
        motionZ -= motionZ / 2.0;
        motionY -= motionY / 2.0;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 5.0f)) {
            playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0f, 1.2f / (rand.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(RayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        motionX = raytraceResult.hitVec.x - posX;
        motionY = raytraceResult.hitVec.y - posY;
        motionZ = raytraceResult.hitVec.z - posZ;
        float f1 =
                MathHelper.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05;
        posY -= motionY / f1 * 0.05;
        posZ -= motionZ / f1 * 0.05;
        inGround = true;
        if (inBlockState != null) {
            inBlockState.getBlock().onEntityCollision(world, blockpos, inBlockState, this);
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

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.mortarShell);
    }
}
