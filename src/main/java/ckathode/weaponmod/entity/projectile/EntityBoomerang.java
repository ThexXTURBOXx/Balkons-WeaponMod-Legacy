package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBoomerang extends EntityMaterialProjectile<EntityBoomerang> {
    public static final String NAME = "boomerang";

    private static final DataParameter<Float> BOOMERANG = EntityDataManager.createKey(EntityBoomerang.class,
            DataSerializers.FLOAT);
    public static final double RETURN_STRENGTH = 0.05;
    public static final float MIN_FLOAT_STRENGTH = 0.4f;
    private float soundTimer;
    public float floatStrength;

    public EntityBoomerang(EntityType<EntityBoomerang> entityType, World world) {
        super(entityType, world);
    }

    public EntityBoomerang(World world, double x, double y, double z) {
        this(BalkonsWeaponMod.entityBoomerang, world);
        setPosition(x, y, z);
    }

    public EntityBoomerang(World world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        soundTimer = 0.0f;
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
        floatStrength = Math.min(1.5f, f3);
        dataManager.set(BOOMERANG, floatStrength);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(BOOMERANG, 0.0f);
    }

    @Override
    public void tick() {
        super.tick();
        floatStrength = dataManager.get(BOOMERANG);
        if (inGround) {
            return;
        }
        floatStrength *= 0.994f;
        if (floatStrength < MIN_FLOAT_STRENGTH) {
            if (getIsCritical()) {
                setIsCritical(false);
            }
            floatStrength = 0.0f;
        }
        float limitedStrength = Math.min(1.0f, floatStrength);
        if (!beenInGround) {
            rotationYaw += 20.0f * floatStrength;
        }
        Entity shooter;
        if (!beenInGround && (shooter = getShooter()) != null && floatStrength > 0.0f) {
            Vec3d d = getPositionVec().subtract(shooter.getPositionVec())
                    .subtract(0, shooter.getEyeHeight(), 0)
                    .normalize();
            setMotion(getMotion().subtract(d.scale(RETURN_STRENGTH)));
            soundTimer += limitedStrength;
            if (soundTimer > 3.0f) {
                playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f,
                        1.0f / (rand.nextFloat() * 0.2f + 2.2f - limitedStrength));
                soundTimer %= 3.0f;
            }
        }
        dataManager.set(BOOMERANG, floatStrength);
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (world.isRemote || floatStrength < MIN_FLOAT_STRENGTH) {
            return;
        }
        Entity shooter = getShooter();
        if (entity == shooter) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                ItemStack item = getPickupItem();
                if (item.isEmpty()) {
                    return;
                }
                if (player.isCreative() || player.inventory.addItemStackToInventory(item)) {
                    playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                            ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                    onItemPickup(player);
                    remove();
                }
            }
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        float damage =
                ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 2.0f + extraDamage;
        damage += getMeleeHitDamage(entity);
        if (getIsCritical()) {
            damage += 2.0f;
        }
        if (entity.attackEntityFrom(damagesource, damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamage() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove();
            } else {
                thrownItem.attemptDamageItem(1, rand,
                        shooter instanceof ServerPlayerEntity ? (ServerPlayerEntity) shooter : null);
                setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        BlockPos blockpos = raytraceResult.getPos();
        xTile = blockpos.getX();
        yTile = blockpos.getY();
        zTile = blockpos.getZ();
        inBlockState = world.getBlockState(blockpos);
        Vec3d motion = raytraceResult.getHitVec().subtract(getPositionVec());
        setMotion(motion);
        Vec3d newPos = getPositionVec().subtract(motion.normalize().scale(RETURN_STRENGTH));
        setPosition(newPos.x, newPos.y, newPos.z);
        setMotion(-rand.nextFloat() * 0.5f * motion.x, rand.nextFloat() * 0.1f, -rand.nextFloat() * 0.5f * motion.z);
        inGround = raytraceResult.getFace() == Direction.UP;
        setIsCritical(false);
        beenInGround = true;
        floatStrength = 0.0f;
        if (inBlockState != null) {
            inBlockState.onEntityCollision(world, blockpos, this);
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.9f));
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
    public void onCollideWithPlayer(@Nonnull PlayerEntity entityplayer) {
        if (!beenInGround && ticksInAir > 5 && !world.isRemote && floatStrength >= MIN_FLOAT_STRENGTH &&
            entityplayer.getUniqueID().equals(shootingEntity)) {
            ItemStack item = getPickupItem();
            if (item.isEmpty()) {
                return;
            }
            if (entityplayer.isCreative() || entityplayer.inventory.addItemStackToInventory(item)) {
                playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2f,
                        ((rand.nextFloat() - rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                onItemPickup(entityplayer);
                remove();
                return;
            }
        }
        super.onCollideWithPlayer(entityplayer);
    }

}
