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

    public EntityKnife(final World world) {
        super(BalkonsWeaponMod.entityKnife, world);
    }

    public EntityKnife(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPosition(d, d1, d2);
    }

    public EntityKnife(final World world, final EntityLivingBase shooter, final ItemStack itemstack) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        this.setPickupStatusFromEntity(shooter);
        this.setThrownItemStack(itemstack);
        this.soundTimer = 0;
    }

    @Override
    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3,
                      final float f4) {
        final float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        final float y = -MathHelper.sin(f * 0.017453292f);
        final float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        this.shoot(x, y, z, f3, f4);
        this.motionX += entity.motionX;
        this.motionZ += entity.motionZ;
        if (!entity.onGround) {
            this.motionY += entity.motionY;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround || this.beenInGround) {
            return;
        }
        this.rotationPitch -= 70.0f;
        if (this.soundTimer >= 3) {
            if (!this.areEyesInFluid(FluidTags.WATER)) {
                this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.6f,
                        1.0f / (this.rand.nextFloat() * 0.2f + 0.6f + this.ticksInAir / 15.0f));
            }
            this.soundTimer = 0;
        }
        ++this.soundTimer;
    }

    @Override
    public void onEntityHit(final Entity entity) {
        if (this.world.isRemote) {
            return;
        }
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource,
                ((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent().getEntityDamage() + 1.0f + this.getMeleeHitDamage(entity))) {
            this.applyEntityHitEffects(entity);
            if (this.thrownItem.getDamage() + 2 > this.thrownItem.getMaxDamage()) {
                this.thrownItem.shrink(1);
                this.remove();
            } else {
                Entity shooter = getShooter();
                if (shooter instanceof EntityLivingBase) {
                    this.thrownItem.damageItem(2, (EntityLivingBase) shooter);
                } else {
                    this.thrownItem.attemptDamageItem(2, this.rand, null);
                }
                this.setVelocity(0.0, 0.0, 0.0);
            }
        } else {
            this.bounceBack();
        }
    }

    @Override
    public boolean aimRotation() {
        return this.beenInGround;
    }

    @Override
    public int getMaxLifetime() {
        return (this.pickupStatus == PickupStatus.ALLOWED || this.pickupStatus == PickupStatus.OWNER_ONLY) ? 0 : 1200;
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
