package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCrossbowBolt extends EntityProjectile<EntityCrossbowBolt> {
    public static final String NAME = "bolt";

    public EntityCrossbowBolt(final World world) {
        super(BalkonsWeaponMod.entityCrossbowBolt, world);
    }

    public EntityCrossbowBolt(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPickupStatus(PickupStatus.ALLOWED);
        this.setPosition(d, d1, d2);
    }

    public EntityCrossbowBolt(final World world, final EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        this.setPickupStatusFromEntity(shooter);
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
    public void onEntityHit(final Entity entity) {
        final float vel = (float) this.getTotalVelocity();
        final float damage = vel * 4.0f + this.extraDamage;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, damage)) {
            if (entity instanceof EntityLivingBase && this.world.isRemote) {
                ((EntityLivingBase) entity).setArrowCountInEntity(((EntityLivingBase) entity).getArrowCountInEntity() + 1);
            }
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.remove();
        } else {
            this.bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.4f));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.bolt, 1);
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.bolt);
    }
}
