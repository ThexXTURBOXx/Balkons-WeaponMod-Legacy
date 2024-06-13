package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.WMDamageSources;
import ckathode.weaponmod.WMRegistries;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
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
import org.jetbrains.annotations.Nullable;

public class EntityMusketBullet extends EntityProjectile<EntityMusketBullet> {

    public static final String ID = "bullet";
    public static final EntityType<EntityMusketBullet> TYPE = WMRegistries.createEntityType(
            ID, EntityDimensions.fixed(0.5f, 0.5f).withEyeHeight(0.0f), EntityMusketBullet::new);

    public EntityMusketBullet(EntityType<EntityMusketBullet> entityType, Level world) {
        super(entityType, world);
        setPickupStatus(PickupStatus.DISALLOWED);
    }

    public EntityMusketBullet(Level world, double d, double d1, double d2, @Nullable ItemStack firedFromWeapon) {
        super(TYPE, world, firedFromWeapon);
        setPickupStatus(PickupStatus.DISALLOWED);
        setPos(d, d1, d2);
    }

    public EntityMusketBullet(Level world, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ(), firedFromWeapon);
        setOwner(shooter);
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
        if (inGround) {
            if (random.nextInt(4) == 0) {
                level().addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), 0.0, 0.0, 0.0);
            }
            return;
        }
        double speed = getTotalVelocity();
        double amount = 16.0;
        if (speed > 2.0) {
            for (int i1 = 1; i1 < amount; ++i1) {
                Vec3 pos = position().add(getDeltaMovement().scale(i1 / amount));
                level().addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
            }
        }
    }

    @NotNull
    @Override
    public DamageSource getDamageSource() {
        return damageSources().source(WMDamageSources.WEAPON, this, getDamagingEntity());
    }

    @Override
    public void onEntityHit(Entity entity) {
        float damage = 20.0f + extraDamage;
        if (entity.hurt(getDamageSource(), damage)) {
            applyEntityHitEffects(entity);
            playHitSound();
            remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    public boolean aimRotation() {
        return false;
    }

    @Override
    public int getMaxLifetime() {
        return 200;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }

    @Override
    public double getDefaultGravity() {
        return (getTotalVelocity() < 3.0) ? 0.07f : 0.0f;
    }

    @Override
    public int getMaxArrowShake() {
        return 0;
    }

    @NotNull
    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(WMRegistries.ITEM_MUSKET_BULLET.get());
    }

}
