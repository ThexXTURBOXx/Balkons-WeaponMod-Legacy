package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityMaterialProjectile<T extends EntityMaterialProjectile<T>> extends EntityProjectile<T> {

    private static final EntityDataAccessor<Integer> WEAPON_MATERIAL =
            SynchedEntityData.defineId(EntityMaterialProjectile.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> WEAPON_ITEM =
            SynchedEntityData.defineId(EntityMaterialProjectile.class, EntityDataSerializers.ITEM_STACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}, {0.3f, 0.3f, 0.3f}};

    public EntityMaterialProjectile(EntityType<T> type, Level world) {
        super(type, world);
    }

    public EntityMaterialProjectile(EntityType<T> type, Level world, @Nullable ItemStack firedFromWeapon) {
        super(type, world, firedFromWeapon);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(WEAPON_MATERIAL, 0);
        builder.define(WEAPON_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        super.saveAdditionalSpawnData(buf);
        buf.writeInt(getWeaponMaterialId());
        ItemStack.STREAM_CODEC.encode(new RegistryFriendlyByteBuf(buf, registryAccess()), getWeapon());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        super.loadAdditionalSpawnData(buf);
        entityData.set(WEAPON_MATERIAL, buf.readInt());
        entityData.set(WEAPON_ITEM, ItemStack.STREAM_CODEC.decode(new RegistryFriendlyByteBuf(buf, registryAccess())));
    }

    public float getMeleeHitDamage(Entity entity, float baseDamage) {
        Entity shooter = getOwner();
        ItemStack weaponItem = getWeaponItem();
        if (weaponItem != null && shooter instanceof LivingEntity && entity instanceof LivingEntity &&
            level() instanceof ServerLevel serverLevel) {
            baseDamage = EnchantmentHelper.modifyDamage(serverLevel, weaponItem, entity, getDamageSource(), baseDamage);
        }
        return baseDamage;
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        Entity shooter = getOwner();
        if (shooter instanceof LivingEntity livingShooter && entity instanceof LivingEntity livingEntity) {
            Registry<Enchantment> enchRegistry = registryAccess().registryOrThrow(Registries.ENCHANTMENT);
            Holder<Enchantment> knockBack = enchRegistry.getHolderOrThrow(Enchantments.KNOCKBACK);
            Holder<Enchantment> fireAspect = enchRegistry.getHolderOrThrow(Enchantments.FIRE_ASPECT);
            int i = EnchantmentHelper.getEnchantmentLevel(knockBack, livingShooter);
            if (i != 0) {
                livingEntity.knockback(i * 0.4f,
                        -Mth.sin(getYRot() * 0.017453292f),
                        -Mth.cos(getYRot() * 0.017453292f));
            }
            i = EnchantmentHelper.getEnchantmentLevel(fireAspect, livingShooter);
            if (i > 0 && !livingEntity.isOnFire()) {
                livingEntity.igniteForSeconds(1);
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
            nbttagcompound.put("thrI", thrownItem.save(registryAccess()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setThrownItemStack(ItemStack.parseOptional(registryAccess(), nbttagcompound.getCompound("thrI")));
    }

}
