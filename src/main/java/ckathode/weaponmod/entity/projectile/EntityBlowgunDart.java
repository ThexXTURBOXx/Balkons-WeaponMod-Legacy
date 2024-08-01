package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponDamageSource;
import ckathode.weaponmod.item.DartType;
import javax.annotation.Nonnull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBlowgunDart extends EntityProjectile<EntityBlowgunDart> {
    public static final String NAME = "dart";

    private static final DataParameter<Byte> DART_EFFECT_TYPE = EntityDataManager.createKey(EntityBlowgunDart.class,
            DataSerializers.BYTE);
    private static final float[][] DART_COLORS = new float[][]{{0.2f, 0.8f, 0.3f}, {0.9f, 0.7f, 1.0f},
            {0.6f, 1.0f, 0.9f}, {0.8f, 0.5f, 0.2f}};

    public EntityBlowgunDart(EntityType<EntityBlowgunDart> entityType, World world) {
        super(entityType, world);
    }

    public EntityBlowgunDart(World world, double d, double d1, double d2) {
        this(BalkonsWeaponMod.entityBlowgunDart, world);
        setPickupStatus(PickupStatus.ALLOWED);
        setPosition(d, d1, d2);
    }

    public EntityBlowgunDart(World world, LivingEntity shooter) {
        this(world, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        setShooter(shooter);
        setPickupStatusFromEntity(shooter);
    }

    @Override
    public void shoot(Entity entity, float f, float f1, float f2, float f3,
                      float f4) {
        float x = -MathHelper.sin(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        float y = -MathHelper.sin(f * 0.017453292f);
        float z = MathHelper.cos(f1 * 0.017453292f) * MathHelper.cos(f * 0.017453292f);
        shoot(x, y, z, f3, f4);
        Vec3d entityMotion = entity.getMotion();
        setMotion(getMotion().add(entityMotion.x, entity.onGround ? 0 : entityMotion.y, entityMotion.z));
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(DART_EFFECT_TYPE, (byte) 0);
    }

    public void setDartEffectType(DartType type) {
        setDartEffectType(type.typeID);
    }

    public void setDartEffectType(byte i) {
        dataManager.set(DART_EFFECT_TYPE, i);
    }

    public DartType getDartEffectType() {
        return DartType.dartTypes[getDartEffectId()];
    }

    public byte getDartEffectId() {
        byte effectId = dataManager.get(DART_EFFECT_TYPE);
        if (effectId < 0) effectId = 0;
        return effectId;
    }

    public float[] getDartColor() {
        int effectId = getDartEffectId();
        return DART_COLORS[effectId >= DART_COLORS.length ? 0 : effectId];
    }

    @Override
    public void onEntityHit(Entity entity) {
        DamageSource damagesource = WeaponDamageSource.causeProjectileWeaponDamage(this, getDamagingEntity());
        if (entity.attackEntityFrom(damagesource, 1.0f + extraDamage)) {
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).addPotionEffect(new EffectInstance(getDartEffectType().potionEffect));
            }
            applyEntityHitEffects(entity);
            playHitSound();
            remove();
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
        playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0f, 1.2f / (rand.nextFloat() * 0.2f + 0.2f));
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
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putByte("darttype", getDartEffectId());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setDartEffectType(nbttagcompound.getByte("darttype"));
    }

}
