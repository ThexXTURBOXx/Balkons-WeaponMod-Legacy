package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityJavelin extends EntityProjectile<EntityJavelin> {

    public static final String ID = "javelin";
    public static final EntityType<EntityJavelin> TYPE = WMRegistries.createEntityType(
            ID, new EntityDimensions(0.5f, 0.5f, false), EntityJavelin::new);

    public EntityJavelin(EntityType<EntityJavelin> entityType, Level world) {
        super(entityType, world);
    }

    public EntityJavelin(Level world, double x, double y, double z) {
        this(TYPE, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPos(x, y, z);
    }

    public EntityJavelin(Level world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
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
        shoot(x, y, z, f3 * 1.1f, f4);
        Vec3 entityMotion = entity.getDeltaMovement();
        setDeltaMovement(getDeltaMovement().add(entityMotion.x, entity.isOnGround() ? 0 : entityMotion.y,
                entityMotion.z));
    }

    @Override
    public void onEntityHit(Entity entity) {
        double vel = getTotalVelocity();
        int damage = Mth.ceil(vel * (3.0 + extraDamage));
        if (isCritArrow()) {
            damage += random.nextInt(damage / 2 + 2);
        }
        DamageSource damagesource = damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
        if (entity.hurt(damagesource, (float) damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            remove(RemovalReason.DISCARDED);
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        playSound(SoundEvents.ARROW_HIT, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.9f));
    }

    @Override
    public boolean canBeCritical() {
        return true;
    }

    @Override
    public int getMaxArrowShake() {
        return 10;
    }

    @Override
    public float getGravity() {
        return 0.03f;
    }

    @NotNull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(WMRegistries.ITEM_JAVELIN.get());
    }

}
