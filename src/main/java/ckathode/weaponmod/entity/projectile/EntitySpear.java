package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EntitySpear extends EntityMaterialProjectile<EntitySpear> {
    public static final String NAME = "spear";

    public EntitySpear(EntityType<EntitySpear> entityType, World world) {
        super(entityType, world);
    }

    public EntitySpear(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entitySpear, world);
        setPos(d, d1, d2);
    }

    public EntitySpear(World world, LivingEntity shooter, ItemStack itemstack) {
        this(world, shooter.getX(), shooter.getY() + shooter.getEyeHeight() - 0.1, shooter.getZ());
        setOwner(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
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
    public void onEntityHit(Entity entity) {
        if (level.isClientSide) {
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        Item item = thrownItem.getItem();
        if (item instanceof IItemWeapon && entity.hurt(damagesource,
                ((IItemWeapon) item).getMeleeComponent().getEntityDamage() + 1.0f + getMeleeHitDamage(entity))) {
            applyEntityHitEffects(entity);
            playHitSound();
            if (thrownItem.getDamageValue() + 1 > thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove();
            } else {
                Entity shooter = getOwner();
                if (shooter instanceof LivingEntity) {
                    thrownItem.hurtAndBreak(1, (LivingEntity) shooter, s -> {
                    });
                } else {
                    thrownItem.hurt(1, random, null);
                }
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
