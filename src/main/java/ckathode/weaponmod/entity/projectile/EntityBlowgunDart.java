package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.DartType;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBlowgunDart extends EntityProjectile {
    public static final String NAME = "dart";

    private static final int DART_EFFECT_TYPE = 18;
    private static final float[][] DART_COLORS = new float[][]{{0.2f, 0.8f, 0.3f}, {0.9f, 0.7f, 1.0f},
            {0.6f, 1.0f, 0.9f}, {0.8f, 0.5f, 0.2f}};

    public EntityBlowgunDart(World world) {
        super(world);
    }

    public EntityBlowgunDart(World world, double d, double d1, double d2) {
        this(world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(World world, EntityLivingBase shooter) {
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
    public void entityInit() {
        super.entityInit();
        dataWatcher.addObject(DART_EFFECT_TYPE, (byte) 0);
    }

    public void setDartEffectType(DartType type) {
        setDartEffectType(type.typeID);
    }

    public void setDartEffectType(byte i) {
        dataWatcher.updateObject(DART_EFFECT_TYPE, i);
    }

    public DartType getDartEffectType() {
        return DartType.dartTypes[getDartEffectId()];
    }

    public byte getDartEffectId() {
        return dataWatcher.getWatchableObjectByte(DART_EFFECT_TYPE);
    }

    public float[] getDartColor() {
        return DART_COLORS[getDartEffectId()];
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + extraDamage)) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(DartType.dartTypes[getDartEffectId()].potionEffect));
            }
            applyEntityHitEffects(entity);
            playHitSound();
            setDead();
        } else {
            bounceBack();
        }
    }

    @Override
    public float getGravity() {
        return 0.05f;
    }

    @Override
    public void playHitSound() {
        worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.2F));
    }

    @Override
    public int getMaxArrowShake() {
        return 4;
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return new ItemStack(BalkonsWeaponMod.dart, 1, getDartEffectId());
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("darttype", getDartEffectId());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setDartEffectType(nbttagcompound.getByte("darttype"));
    }

}
