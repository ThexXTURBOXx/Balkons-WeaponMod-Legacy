package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrossbowBolt extends EntityProjectile {
    public static final String NAME = "bolt";

    public EntityCrossbowBolt(World world) {
        super(world);
    }

    public EntityCrossbowBolt(World world, double d, double d1, double d2) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityCrossbowBolt(World world, EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setThrower(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void setAim(Entity entity, float f, float f1, float f2, float f3, float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        setThrowableHeading(x, y, z, f3, f4);
        motionX += entity.motionX;
        motionZ += entity.motionZ;
        if (!entity.onGround) {
            motionY += entity.motionY;
        }
    }

    @Override
    public void onEntityHit(Entity entity) {
        float vel = (float) getTotalVelocity();
        float damage = vel * 4.0f + extraDamage;
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, damage)) {
            if (entity instanceof EntityLivingBase && worldObj.isRemote) {
                ((EntityLivingBase) entity).setArrowCountInEntity(((EntityLivingBase) entity).getArrowCountInEntity() + 1);
            }
            applyEntityHitEffects(entity);
            playHitSound();
            setDead();
        } else {
            bounceBack();
        }
    }

    @Override
    public void playHitSound() {
        worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.4F));
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
}
