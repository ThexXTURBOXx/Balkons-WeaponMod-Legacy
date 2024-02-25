package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.DartType;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBlowgunDart extends EntityProjectile<EntityBlowgunDart> {
    public static final String NAME = "dart";
    private static final DataParameter<Byte> DART_EFFECT_TYPE;
    private static final float[][] DART_COLORS;

    public EntityBlowgunDart(final World world) {
        super(BalkonsWeaponMod.entityBlowgunDart, world);
    }

    public EntityBlowgunDart(final World world, final double d, final double d1, final double d2) {
        this(world);
        this.setPickupStatus(PickupStatus.ALLOWED);
        this.setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(final World world, final EntityLivingBase shooter) {
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
    public void registerData() {
        super.registerData();
        this.dataManager.register(EntityBlowgunDart.DART_EFFECT_TYPE, (byte) 0);
    }

    public void setDartEffectType(final DartType type) {
        setDartEffectType(type.typeID);
    }

    public void setDartEffectType(final byte i) {
        this.dataManager.set(EntityBlowgunDart.DART_EFFECT_TYPE, i);
    }

    public DartType getDartEffectType() {
        return DartType.dartTypes[getDartEffectId()];
    }

    public byte getDartEffectId() {
        return this.dataManager.get(EntityBlowgunDart.DART_EFFECT_TYPE);
    }

    public float[] getDartColor() {
        return EntityBlowgunDart.DART_COLORS[this.getDartEffectId()];
    }

    @Override
    public void onEntityHit(final Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + this.extraDamage)) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(DartType.dartTypes[this.getDartEffectId()].potionEffect));
            }
            this.applyEntityHitEffects(entity);
            this.playHitSound();
            this.remove();
        } else {
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

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return getArrowStack();
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(BalkonsWeaponMod.darts.get(getDartEffectType()));
    }

    @Override
    public void writeAdditional(final NBTTagCompound nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putByte("darttype", this.getDartEffectId());
    }

    @Override
    public void readAdditional(final NBTTagCompound nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.setDartEffectType(nbttagcompound.getByte("darttype"));
    }

    static {
        DART_EFFECT_TYPE = EntityDataManager.createKey(EntityBlowgunDart.class, DataSerializers.BYTE);
        DART_COLORS = new float[][]{{0.2f, 0.8f, 0.3f}, {0.9f, 0.7f, 1.0f}, {0.6f, 1.0f, 0.9f}, {0.8f, 0.5f, 0.2f}};
    }
}
