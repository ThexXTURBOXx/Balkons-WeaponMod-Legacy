package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityKnife extends EntityMaterialProjectile<EntityKnife> {
    public static final String NAME = "knife";

    private int soundTimer;

    public EntityKnife(World world) {
        super(BalkonsWeaponMod.entityKnife, world);
    }

    public EntityKnife(World world, double d, double d1, double d2) {
        this(world);
        setPosition(d, d1, d2);
    }

    public EntityKnife(World world, EntityLivingBase shooter, ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
        setThrownItemStack(itemstack);
        soundTimer = 0;
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3,
                      float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (inGround || beenInGround) {
            return;
        }
        rotationPitch -= 70.0f;
        if (soundTimer >= 3) {
            if (!areEyesInFluid(FluidTags.WATER)) {
                playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f,
                        1.0f / (rand.nextFloat() * 0.2f + 0.6f + ticksInAir / 15.0f));
            }
            soundTimer = 0;
        }
        ++soundTimer;
    }

    @Override
    public void onEntityHit(Entity entity) {
        if (world.isRemote) {
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource,
                ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 1.0f + getMeleeHitDamage(entity))) {
            applyEntityHitEffects(entity);
            if (thrownItem.getDamage() + 2 > thrownItem.getMaxDamage()) {
                thrownItem.shrink(1);
                remove();
            } else {
                Entity shooter = getShooter();
                if (shooter instanceof EntityLivingBase) {
                    thrownItem.damageItem(2, (EntityLivingBase) shooter);
                } else {
                    thrownItem.attemptDamageItem(2, rand, null);
                }
                setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            bounceBack();
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
    public float getGravity() {
        return 0.03f;
    }

    @Override
    public float getAirResistance() {
        return 0.98f;
    }
}
