package ckathode.weaponmod.entity.projectile;

import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import ckathode.weaponmod.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import ckathode.weaponmod.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;

public class EntityBlowgunDart extends EntityProjectile
{
    private static final DataParameter<Byte> DART_EFFECT_TYPE;
    private static final float[][] DART_COLORS;

    public EntityBlowgunDart(final World world) {
        super(world);
    }

    public EntityBlowgunDart(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPickupMode(1);
        this.setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(final World world, final EntityLivingBase shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.setPickupModeFromEntity((EntityLivingBase)(this.shootingEntity = shooter));
    }

    public void shoot(final Entity entity, final float f, final float f1, final float f2, final float f3, final float f4) {
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

    public void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityBlowgunDart.DART_EFFECT_TYPE, (byte) 0);
    }

    public void setDartEffectType(final int i) {
        this.dataManager.set(EntityBlowgunDart.DART_EFFECT_TYPE, (byte) i);
    }

    public int getDartEffectType() {
        return this.dataManager.get(EntityBlowgunDart.DART_EFFECT_TYPE);
    }

    public float[] getDartColor() {
        return EntityBlowgunDart.DART_COLORS[this.getDartEffectType()];
    }

    @Override
    public void onEntityHit(final Entity entity) {
        DamageSource damagesource;
        if (this.shootingEntity == null) {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this);
        }
        else {
            damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, this.shootingEntity);
        }
        if (entity.attackEntityFrom(damagesource, 1.0f + this.extraDamage)) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(DartType.dartTypes[this.getDartEffectType()].potionEffect));
            }
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.setDead();
        }
        else {
            this.bounceBack();
        }
    }

    @Override
    public float getGravity() {
        return 0.05f;
    }

    @Override
    public void playHitSound() {
        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (this.rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dart, 1, this.getDartEffectType());
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.dart);
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("darttype", this.getDartEffectType());
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.setDartEffectType(nbttagcompound.getInteger("darttype"));
    }

    static {
        DART_EFFECT_TYPE = EntityDataManager.createKey(EntityBlowgunDart.class, DataSerializers.BYTE);
        DART_COLORS = new float[][] { { 0.2f, 0.8f, 0.3f }, { 0.9f, 0.7f, 1.0f }, { 0.6f, 1.0f, 0.9f }, { 0.8f, 0.5f, 0.2f } };
    }
}
