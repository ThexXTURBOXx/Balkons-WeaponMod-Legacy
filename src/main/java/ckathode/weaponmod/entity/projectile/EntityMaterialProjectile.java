package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemHitEffect;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class EntityMaterialProjectile<T extends EntityMaterialProjectile<T>> extends EntityProjectile<T> {
    private static final DataParameter<Integer> WEAPON_MATERIAL =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> WEAPON_ITEM =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.ITEMSTACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};

    public EntityMaterialProjectile(EntityType<T> type, World world) {
        super(type, world);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(WEAPON_MATERIAL, 0);
        dataManager.register(WEAPON_ITEM, ItemStack.EMPTY);
    }

    @Override
    public void writeSpawnData(PacketBuffer buf) {
        super.writeSpawnData(buf);
        buf.writeInt(getWeaponMaterialId());
        buf.writeItemStack(getWeapon());
    }

    @Override
    public void readSpawnData(PacketBuffer buf) {
        super.readSpawnData(buf);
        dataManager.set(WEAPON_MATERIAL, buf.readInt());
        dataManager.set(WEAPON_ITEM, buf.readItemStack());
    }

    public float getEnchantmentDamage(Entity entity) {
        if (entity instanceof LivingEntity) {
            return EnchantmentHelper.getModifierForCreature(getWeapon(),
                    ((LivingEntity) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void onEntityHit(EntityRayTraceResult raytraceResult) {
        super.onEntityHit(raytraceResult);
        ItemStack thrownItem = getWeapon();
        if (!thrownItem.isEmpty() && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitEntity(this, raytraceResult);
        }
    }

    @Override
    public void onGroundHit(BlockRayTraceResult raytraceResult) {
        super.onGroundHit(raytraceResult);
        ItemStack thrownItem = getWeapon();
        if (!thrownItem.isEmpty() && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitBlock(this, raytraceResult);
        }
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        ItemStack stack = getWeapon();
        if (entity instanceof LivingEntity) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, stack);
            if (i != 0) {
                ((LivingEntity) entity).knockBack(this, i * 0.4f,
                        -MathHelper.sin(rotationYaw * 0.017453292f),
                        -MathHelper.cos(rotationYaw * 0.017453292f));
            }
        }
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack);
        if (i > 0 || isBurning()) {
            entity.setFire(i > 0 ? i : 1);
        }
    }

    public void setThrownItemStack(@Nonnull ItemStack itemstack) {
        dataManager.set(WEAPON_ITEM, itemstack);
        updateWeaponMaterial();
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return getWeapon();
    }

    public int getWeaponMaterialId() {
        return dataManager.get(WEAPON_MATERIAL);
    }

    @NotNull
    public ItemStack getWeapon() {
        return dataManager.get(WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        ItemStack thrownItem = getWeapon();
        if (!thrownItem.isEmpty() && thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material =
                        MaterialRegistry.getOrdinal(((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial);
            }
            dataManager.set(WEAPON_MATERIAL, material);
        }
    }

    public float[] getMaterialColor() {
        int id = getWeaponMaterialId();
        if (id >= 0 && id < MATERIAL_COLORS.length) {
            return MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        ItemStack thrownItem = getWeapon();
        nbttagcompound.put("thrI", thrownItem.write(new CompoundNBT()));
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        if (nbttagcompound.contains("thrI")) {
            setThrownItemStack(ItemStack.read(nbttagcompound.getCompound("thrI")));
        }
    }

}
