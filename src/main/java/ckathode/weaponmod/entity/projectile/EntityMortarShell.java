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
import net.minecraft.util.math.vector.Vector3d;
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
        setPos(d, d1, d2);
    }

    public EntityMortarShell(World world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vector3d entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.isOnGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getDeltaMovement().length();
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                Vector3d pos = position().add(getDeltaMovement().scale(i1 / amount));
                level.addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    public void createCrater() {
        if (level.isClientSide || !inGround || isInWater()) {
            return;
        }
        remove();
        Entity shooter = getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS, (LivingEntity) shooter) > 0) {
            float f1 = (float) EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER_ARROWS,
                    (LivingEntity) shooter);
            explosiveSize += f1 / 4.0f;
        }
        boolean flag =
                EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAMING_ARROWS, (LivingEntity) shooter) > 0;
        PhysHelper.createAdvancedExplosion(level, this, getX(), getY(), getZ(), explosiveSize,
                BalkonsWeaponMod.instance.modConfig.mortarDoesBlockDamage.get(), true, flag, Explosion.Mode.DESTROY);
    }

    @Override
    public void onEntityHit(Entity entity) {
        setDeltaMovement(getDeltaMovement().scale(0.5));
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.hurt(damagesource, 5.0f)) {
            playSound(SoundEvents.PLAYER_HURT, 1.0f, 1.2f / (random.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level.getBlockState(blockpos);
        setDeltaMovement(raytraceResult.getLocation().subtract(position()));
        double f1 = getDeltaMovement().length();
        Vector3d pos = position().subtract(getDeltaMovement().scale(0.05 / f1));
        setPos(pos.x, pos.y, pos.z);
        inGround = true;
        if (inBlockState != null) {
            inBlockState.entityInside(level, blockpos, this);
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
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.mortarShell);
    }
}
