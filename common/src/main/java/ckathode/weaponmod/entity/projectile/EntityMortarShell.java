package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.PhysHelper;
import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModConfig;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityMortarShell extends EntityProjectile<EntityMortarShell> {

    public static final String ID = "shell";
    public static final EntityType<EntityMortarShell> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityMortarShell::new);

    public float explosiveSize;

    public EntityMortarShell(EntityType<EntityMortarShell> entityType, Level world) {
        super(entityType, world);
        explosiveSize = 2.0f;
    }

    public EntityMortarShell(Level world, double d, double d1, double d2, @Nullable ItemStack firedFromWeapon) {
        super(TYPE, world, firedFromWeapon);
        explosiveSize = 2.0f;
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(d, d1, d2);
    }

    public EntityMortarShell(Level world, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), firedFromWeapon);
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
        return NetworkManager.createAddEntityPacket(this, serverEntity);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3 entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.onGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    public void tick() {
        super.tick();
        double speed = getDeltaMovement().length();
        double amount = 8.0;
        if (speed > 1.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                Vec3 pos = position().add(getDeltaMovement().scale(i1 / amount));
                if (level().isClientSide()) {
                    level().addParticle(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public void createCrater() {
        if (level().isClientSide || !inGround || isInWater()) {
            return;
        }
        remove(RemovalReason.DISCARDED);
        Entity shooter = getOwner();
        if (!(shooter instanceof LivingEntity)) return;
        Registry<Enchantment> enchRegistry = registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        Holder<Enchantment> power = enchRegistry.getHolderOrThrow(Enchantments.POWER);
        Holder<Enchantment> flame = enchRegistry.getHolderOrThrow(Enchantments.FLAME);
        if (EnchantmentHelper.getEnchantmentLevel(power, (LivingEntity) shooter) > 0) {
            float f1 = (float) EnchantmentHelper.getEnchantmentLevel(power, (LivingEntity) shooter);
            explosiveSize += f1 / 4.0f;
        }
        boolean flag =
                EnchantmentHelper.getEnchantmentLevel(flame, (LivingEntity) shooter) > 0;
        PhysHelper.createAdvancedExplosion(level(), this, getX(), getY(), getZ(), explosiveSize,
                WeaponModConfig.get().mortarDoesBlockDamage, true, flag,
                Explosion.BlockInteraction.DESTROY);
    }

    @NotNull
    @Override
    public DamageSource getDamageSource() {
        return damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
    }

    @Override
    public void onEntityHit(Entity entity) {
        setDeltaMovement(getDeltaMovement().scale(0.5));
        if (entity.hurt(getDamageSource(), 5.0f)) {
            playSound(SoundEvents.PLAYER_HURT, 1.0f, 1.2f / (random.nextFloat() * 0.4f + 0.7f));
        }
    }

    @Override
    public void onGroundHit(BlockHitResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level().getBlockState(blockpos);
        setDeltaMovement(raytraceResult.getLocation().subtract(position()));
        double f1 = getDeltaMovement().length();
        Vec3 pos = position().subtract(getDeltaMovement().scale(0.05 / f1));
        setPos(pos.x, pos.y, pos.z);
        inGround = true;
        if (inBlockState != null) {
            inBlockState.entityInside(level(), blockpos, this);
        }
        createCrater();
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public double getDefaultGravity() {
        return 0.04f;
    }

    @NotNull
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(WMRegistries.ITEM_MORTAR_SHELL.get());
    }

}
