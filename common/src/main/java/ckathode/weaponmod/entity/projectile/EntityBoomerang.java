package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityBoomerang extends EntityMaterialProjectile<EntityBoomerang> {

    public static final String ID = "boomerang";
    public static final EntityType<EntityBoomerang> TYPE = WMRegistries.createEntityType(
            ID, new EntityDimensions(0.5f, 0.5f, false), EntityBoomerang::new);

    private static final EntityDataAccessor<Float> BOOMERANG = SynchedEntityData.defineId(EntityBoomerang.class,
            EntityDataSerializers.FLOAT);
    public static final double RETURN_STRENGTH = 0.05;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;
    private float soundTimer;
    public float floatStrength;

    public EntityBoomerang(EntityType<EntityBoomerang> entityType, Level world) {
        super(entityType, world);
    }

    public EntityBoomerang(Level world, double x, double y, double z) {
        this(TYPE, world);
        setPos(x, y, z);
    }

    public EntityBoomerang(Level world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        soundTimer = 0.0f;
    }

    @NotNull
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    @Override
    public void shootFromRotation(Entity entity, float f, float f1, float f2, float f3,
                                  float f4) {
        float x = -Mth.sin(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        float y = -Mth.sin(f * 0.017453292f);
        float z = Mth.cos(f1 * 0.017453292f) * Mth.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3 entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.isOnGround() ? 0 : entityMotion.y,
                entityMotion.z));
        floatStrength = Math.min(1.5f, f3);
        entityData.set(BOOMERANG, floatStrength);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(BOOMERANG, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        floatStrength = entityData.get(BOOMERANG);
        if (inGround) {
            return;
        }
        floatStrength *= 0.994f;
        if (floatStrength < MIN_FLOAT_STRENGTH) {
            if (isCritArrow()) {
                setCritArrow(false);
            }
            floatStrength = 0.0f;
        }
        float limitedStrength = Math.min(1.0f, floatStrength);
        if (!beenInGround) {
            setYRot(getYRot() + 20.0f * floatStrength);
        }
        Entity shooter;
        if (!beenInGround && (shooter = getOwner()) != null && floatStrength > 0.0f) {
            Vec3 d = position().subtract(shooter.position())
                    .subtract(0, shooter.getEyeHeight(), 0)
                    .normalize();
            setDeltaMovement(getDeltaMovement().subtract(d.scale(RETURN_STRENGTH)));
            soundTimer += limitedStrength;
            if (soundTimer > 3.0f) {
                playSound(SoundEvents.ARROW_SHOOT, 0.6f,
                        1.0f / (random.nextFloat() * 0.2f + 2.2f - limitedStrength));
                soundTimer %= 3.0f;
            }
        }
        entityData.set(BOOMERANG, floatStrength);
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (level.isClientSide || floatStrength < MIN_FLOAT_STRENGTH) {
            return;
        }
        Entity shooter = getOwner();
        if (entity == shooter) {
            if (entity instanceof Player player) {
                ItemStack item = getPickupItem();
                if (item.isEmpty()) {
                    return;
                }
                if (player.isCreative() || player.getInventory().add(item)) {
                    playSound(SoundEvents.ITEM_PICKUP, 0.2f,
                            ((random.nextFloat() - random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    onItemPickup(player);
                    remove(RemovalReason.DISCARDED);
                }
            }
            return;
        }
        DamageSource damagesource = damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
        ItemStack thrownItem = getWeapon();
        float damage =
                ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 2.0f + extraDamage;
        damage += getMeleeHitDamage(entity);
        if (isCritArrow()) {
            damage += 2.0f;
        }
        if (entity.hurt(damagesource, damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamageValue() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove(RemovalReason.DISCARDED);
            } else {
                thrownItem.hurt(1, random,
                        shooter instanceof ServerPlayer ? (ServerPlayer) shooter : null);
                lerpMotion(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void onGroundHit(BlockHitResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getBlockPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = level.getBlockState(blockpos);
        Vec3 motion = raytraceResult.getLocation().subtract(position());
        setDeltaMovement(motion);
        Vec3 newPos = position().subtract(motion.normalize().scale(RETURN_STRENGTH));
        setPos(newPos.x, newPos.y, newPos.z);
        setDeltaMovement(-random.nextFloat() * 0.5f * motion.x, random.nextFloat() * 0.1f,
                -random.nextFloat() * 0.5f * motion.z);
        inGround = raytraceResult.getDirection() == Direction.UP;
        setCritArrow(false);
        beenInGround = true;
        floatStrength = 0.0f;
        if (inBlockState != null) {
            inBlockState.entityInside(level, blockpos, this);
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public boolean aimRotation() {
        return beenInGround || floatStrength < MIN_FLOAT_STRENGTH;
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
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
        return (beenInGround || floatStrength < MIN_FLOAT_STRENGTH) ? (float) RETURN_STRENGTH : 0.0f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public void playerTouch(@NotNull Player entityplayer) {
        if (!beenInGround && ticksInAir > 5 && !level.isClientSide && floatStrength >= MIN_FLOAT_STRENGTH && entityplayer.equals(getOwner())) {
            ItemStack item = getPickupItem();
            if (item.isEmpty()) {
                return;
            }
            if (entityplayer.isCreative() || entityplayer.getInventory().add(item)) {
                playSound(SoundEvents.ITEM_PICKUP, 0.2f,
                        ((random.nextFloat() - random.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                remove(RemovalReason.DISCARDED);
                return;
            }
        }
        super.playerTouch(entityplayer);
    }

}
