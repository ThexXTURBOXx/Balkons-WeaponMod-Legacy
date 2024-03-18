package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemFlail;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EntityMaterialProjectile<T extends EntityMaterialProjectile<T>> extends EntityProjectile<T> {
    private static final DataParameter<Byte> WEAPON_MATERIAL =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> WEAPON_ITEM =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.ITEMSTACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};
    protected ItemStack thrownItem;

    public EntityMaterialProjectile(EntityType<T> type, World world) {
        super(type, world);
    }

    @Override
    public void registerData() {
        super.registerData();
        dataManager.register(WEAPON_MATERIAL, (byte) 0);
        dataManager.register(WEAPON_ITEM, ItemStack.EMPTY);
    }

    public float getMeleeHitDamage(Entity entity) {
        Entity shooter = getShooter();
        if (shooter instanceof LivingEntity && entity instanceof LivingEntity) {
            return EnchantmentHelper.getModifierForCreature(((LivingEntity) shooter).getHeldItemMainhand(),
                    ((LivingEntity) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        Entity shooter = getShooter();
        if (shooter instanceof LivingEntity && entity instanceof LivingEntity) {
            int i = EnchantmentHelper.getKnockbackModifier((LivingEntity) shooter);
            if (i != 0) {
                ((LivingEntity) entity).knockBack(this, i * 0.4f,
                        -MathHelper.sin(rotationYaw * 0.017453292f),
                        -MathHelper.cos(rotationYaw * 0.017453292f));
            }
            i = EnchantmentHelper.getFireAspectModifier((LivingEntity) shooter);
            if (i > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }
    }

    public void setThrownItemStack(@Nonnull ItemStack itemstack) {
        thrownItem = itemstack;
        if (thrownItem.getItem() instanceof ItemFlail || !BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            updateWeaponMaterial();
        } else if (thrownItem != null && !(thrownItem.getItem() instanceof ItemFlail) && BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            dataManager.set(WEAPON_ITEM, itemstack);
        }
    }

    @Nonnull
    @Override
    public ItemStack getPickupItem() {
        return thrownItem;
    }

    public int getWeaponMaterialId() {
        return dataManager.get(WEAPON_MATERIAL);
    }

    public ItemStack getWeapon() {
        return dataManager.get(WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        if (thrownItem != null && thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material =
                        MaterialRegistry.getOrdinal(((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial);
            }
            dataManager.set(WEAPON_MATERIAL, (byte) (material & 0xFF));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public float[] getMaterialColor() {
        int id = getWeaponMaterialId();
        if (id < 5) {
            return MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        if (thrownItem != null) {
            nbttagcompound.put("thrI", thrownItem.write(new CompoundNBT()));
        }
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setThrownItemStack(ItemStack.read(nbttagcompound.getCompound("thrI")));
    }

}
