package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
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
import org.jetbrains.annotations.Nullable;

public class EntityKnife extends EntityMaterialProjectile<EntityKnife> {

    public static final String ID = "knife";
    public static final EntityType<EntityKnife> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityKnife::new);

    private int soundTimer;

    public EntityKnife(EntityType<EntityKnife> entityType, Level world) {
        super(entityType, world);
    }

    public EntityKnife(Level world, double d, double d1, double d2, @Nullable ItemStack firedFromWeapon) {
        super(TYPE, world, firedFromWeapon);
        setPos(d, d1, d2);
    }

    public EntityKnife(Level world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), itemstack);
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        soundTimer = 0;
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
        if (inGround || beenInGround) {
            return;
        }
        setXRot(getXRot() - 70.0f);
        if (soundTimer >= 3) {
            if (!isInWater()) {
                playSound(SoundEvents.ARROW_SHOOT, 0.6f,
                        1.0f / (random.nextFloat() * 0.2f + 0.6f + ticksInAir / 15.0f));
            }
            soundTimer = 0;
        }
        ++soundTimer;
    }

    @NotNull
    @Override
    public DamageSource getDamageSource() {
        return damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (level().isClientSide) {
            return;
        }
        ItemStack thrownItem = getWeapon();
        Item item = thrownItem.getItem();
        if (!(item instanceof IItemWeapon iweapon)) {
            bounceBack();
            return;
        }
        float damage = iweapon.getMeleeComponent().getEntityDamage() + 1.0f;
        damage = getMeleeHitDamage(entity, damage);
        if (entity.hurt(getDamageSource(), damage)) {
            applyEntityHitEffects(entity);
            if (thrownItem.getDamageValue() + 2 >= thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove(RemovalReason.DISCARDED);
            } else {
                Entity shooter = getOwner();
                Level level = level();
                if (level instanceof ServerLevel serverLevel) {
                    thrownItem.hurtAndBreak(2, serverLevel, shooter instanceof ServerPlayer player ? player : null,
                            i -> {
                            });
                }
                lerpMotion(0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    public boolean aimRotation() {
        return beenInGround;
    }

    @Override
    public int getMaxLifetime() {
        return (pickupStatus == PickupStatus.ALLOWED || pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Override
    public double getDefaultGravity() {
        return 0.03f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @NotNull
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(WMRegistries.ITEM_KNIFE_WOOD.get());
    }

}
