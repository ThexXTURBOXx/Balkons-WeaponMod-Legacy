package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMortarShell extends EntityProjectile<EntityMortarShell> {
    public static final String NAME = "shell";

    public float explosiveSize;

    public EntityMortarShell(EntityType<EntityMortarShell> entityType, World world) {
        super(entityType, world);
        explosiveSize = 2.0f;
    }

    public EntityMortarShell(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityMortarShell, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityMortarShell(World world, LivingEntity shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3,
                      float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3d entityMotion = entity.getMotion();
        setMotion(getMotion().add(entityMotion.x, entity.onGround ? 0 : entityMotion.y, entityMotion.z));
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getMotion().length();
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                Vec3d pos = getPositionVector().add(getMotion().scale(i1 / amount));
                world.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (world.isRemote || !inGround || isInWater()) {
            return;
        }
        remove();
        Entity shooter = getShooter();
        if (!(shooter instanceof LivingEntity)) return;
        if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, (LivingEntity) shooter) > 0) {
            float f1 = (float) EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER,
                    (LivingEntity) shooter);
            explosiveSize += f1 / 4.0f;
        }
        boolean flag =
                EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, (LivingEntity) shooter) > 0;
        PhysHelper.createAdvancedExplosion(world, this, posX, posY, posZ, explosiveSize,
                BalkonsWeaponMod.instance.modConfig.mortarDoesBlockDamage.get(), true, flag, Explosion.Mode.DESTROY);
    }

    @Override
    public void onEntityHit(Entity entity) {
        setMotion(getMotion().scale(0.5));
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 5.0f)) {
            playSound(SoundEvents.ENTITY_PLAYER_HURT, 1.0f, 1.2f / (rand.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        setMotion(raytraceResult.getHitVec().subtract(getPositionVec()));
        double f1 = getMotion().length();
        Vec3d pos = getPositionVec().subtract(getMotion().scale(0.05 / f1));
        posX = pos.x;
        posY = pos.y;
        posZ = pos.z;
        inGround = true;
        if (inBlockState != null) {
            inBlockState.onEntityCollision(world, blockpos, this);
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
