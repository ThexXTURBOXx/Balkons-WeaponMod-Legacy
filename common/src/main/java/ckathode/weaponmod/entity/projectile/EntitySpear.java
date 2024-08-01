package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntitySpear extends EntityMaterialProjectile<EntitySpear> {

    public static final String ID = "spear";
    public static final EntityType<EntitySpear> TYPE = WMRegistries.createEntityType(
            ID, new EntityDimensions(0.5f, 0.5f, false), EntitySpear::new);

    public EntitySpear(EntityType<EntitySpear> entityType, Level world) {
        super(entityType, world);
    }

    public EntitySpear(Level world, double d, double d1, double d2) {
        this(TYPE, world);
        setPos(d, d1, d2);
    }

    public EntitySpear(Level world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.getX(), shooter.getY() + shooter.getEyeHeight() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
    }

    @NotNull
    @Override
    public Packet<?> getAddEntityPacket() {
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
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (level.isClientSide) {
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        ItemStack thrownItem = getWeapon();
        Item item = thrownItem.getItem();
        if (item instanceof IItemWeapon && entity.hurt(damagesource,
                ((IItemWeapon) item).getMeleeComponent().getEntityDamage() + getMeleeHitDamage(entity))) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamageValue() + 1 >= thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove(RemovalReason.DISCARDED);
            } else {
                Entity shooter = getOwner();
                thrownItem.hurt(1, random,
                        shooter instanceof ServerPlayer ? (ServerPlayer) shooter : null);
                lerpMotion(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }
}
