package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.RangedComponent;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntityBlunderShot extends EntityProjectile<EntityBlunderShot> {
    public static final String NAME = "shot";

    public EntityBlunderShot(EntityType<EntityBlunderShot> entityType, World world) {
        super(entityType, world);
        setPickupStatus(PickupStatus.DISALLOWED);
    }

    public EntityBlunderShot(World world, double x, double y, double z) {
        this(BalkonsWeaponMod.entityBlunderShot, world);
        setPos(x, y, z);
    }

    public EntityBlunderShot(World world, LivingEntity shooter) {
        this(world, shooter.getX(), shooter.getEyeY() - 0.1, shooter.getZ());
        setOwner(shooter);
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
        if (ticksInAir > 4) {
            remove();
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        float damage = 4.0f + extraDamage;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        int prevhurtrestime = entity.invulnerableTime;
        if (entity.hurt(damagesource, damage)) {
            entity.invulnerableTime = prevhurtrestime;
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
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
    public int getMaxArrowShake() {
        return 0;
    }

    @Override
    public float getGravity() {
        return (getTotalVelocity() < 2.0) ? 0.04f : 0.0f;
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.blunderShot);
    }

    public static void fireSpreadShot(World world, LivingEntity entityliving,
                                      RangedComponent item, ItemStack itemstack) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
        for (int i = 0; i < 10; ++i) {
            EntityBlunderShot entity = new EntityBlunderShot(world, entityliving);
            entity.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 5.0f, 15.0f);
            if (item != null && !itemstack.isEmpty()) {
                item.applyProjectileEnchantments(entity, itemstack);
            }
            world.addFreshEntity(entity);
        }
    }

    public static void fireSpreadShot(World world, double x, double y, double z) {
        for (int i = 0; i < 10; ++i) {
            world.addFreshEntity(new EntityBlunderShot(world, x, y, z));
        }
    }

    public static void fireFromDispenser(World world, double d, double d1, double d2,
                                         int i, int j, int k) {
        for (int i2 = 0; i2 < 10; ++i2) {
            EntityBlunderShot entityblundershot = new EntityBlunderShot(world, d, d1, d2);
            entityblundershot.shoot(i, j, k, 5.0f, 15.0f);
            world.addFreshEntity(entityblundershot);
        }
    }
}
