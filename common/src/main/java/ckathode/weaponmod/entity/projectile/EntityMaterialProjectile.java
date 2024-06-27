package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class EntityMaterialProjectile<T extends EntityMaterialProjectile<T>> extends EntityProjectile<T> {

    private static final EntityDataAccessor<Integer> WEAPON_MATERIAL =
            SynchedEntityData.defineId(EntityMaterialProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> WEAPON_ITEM =
            SynchedEntityData.defineId(EntityMaterialProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}, {0.3f, 0.3f, 0.3f}};

    public EntityMaterialProjectile(EntityType<T> type, Level world) {
        super(type, world);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(WEAPON_MATERIAL, 0);
        entityData.define(WEAPON_ITEM, ItemStack.EMPTY);
    }

    public float getMeleeHitDamage(Entity entity) {
        Entity shooter = getOwner();
        if (shooter instanceof LivingEntity && entity instanceof LivingEntity) {
            return EnchantmentHelper.getDamageBonus(((LivingEntity) shooter).getMainHandItem(),
                    ((LivingEntity) entity).getMobType());
        }
        return 0.0f;
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        Entity shooter = getOwner();
        if (shooter instanceof LivingEntity && entity instanceof LivingEntity) {
            int i = EnchantmentHelper.getKnockbackBonus((LivingEntity) shooter);
            if (i != 0) {
                ((LivingEntity) entity).knockback(i * 0.4f,
                        -Mth.sin(getYRot() * 0.017453292f),
                        -Mth.cos(getYRot() * 0.017453292f));
            }
            i = EnchantmentHelper.getFireAspect((LivingEntity) shooter);
            if (i > 0 && !entity.isOnFire()) {
                entity.setSecondsOnFire(1);
            }
        }
    }

    public void setThrownItemStack(@NotNull ItemStack itemstack) {
        entityData.set(WEAPON_ITEM, itemstack);
        updateWeaponMaterial();
    }

    @NotNull
    @Override
    public ItemStack getPickupItem() {
        return getWeapon();
    }

    public int getWeaponMaterialId() {
        return entityData.get(WEAPON_MATERIAL);
    }

    public ItemStack getWeapon() {
        return entityData.get(WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null && thrownItem.getItem() instanceof IItemWeapon weapon && weapon.getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material = MaterialRegistry.getOrdinal(weapon.getMeleeComponent().weaponMaterial);
            }
            entityData.set(WEAPON_MATERIAL, material);
        }
    }

    @Environment(EnvType.CLIENT)
    public float[] getMaterialColor() {
        int id = getWeaponMaterialId();
        if (id >= 0 && id < MATERIAL_COLORS.length) {
            return MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null) {
            nbttagcompound.put("thrI", thrownItem.save(new CompoundTag()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setThrownItemStack(ItemStack.of(nbttagcompound.getCompound("thrI")));
    }

}
