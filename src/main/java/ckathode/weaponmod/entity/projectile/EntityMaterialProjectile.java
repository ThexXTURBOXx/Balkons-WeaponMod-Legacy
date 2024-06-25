package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityMaterialProjectile extends EntityProjectile {
    private static final DataParameter<Byte> WEAPON_MATERIAL =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.BYTE);
    private static final DataParameter<ItemStack> WEAPON_ITEM =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.ITEM_STACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};
    protected ItemStack thrownItem;

    public EntityMaterialProjectile(World world) {
        super(world);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        dataManager.register(WEAPON_MATERIAL, (byte) 0);
        dataManager.register(WEAPON_ITEM, ItemStack.EMPTY);
    }

    public float getMeleeHitDamage(Entity entity) {
        if (shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            return EnchantmentHelper.getModifierForCreature(((EntityLivingBase) shootingEntity).getHeldItemMainhand(),
                    ((EntityLivingBase) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        if (shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase) shootingEntity);
            if (i != 0) {
                ((EntityLivingBase) entity).knockBack(this, i * 0.4f,
                        -MathHelper.sin(rotationYaw * 0.017453292f),
                        -MathHelper.cos(rotationYaw * 0.017453292f));
            }
            i = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) shootingEntity);
            if (i > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }
    }

    public void setThrownItemStack(@Nonnull ItemStack itemstack) {
        thrownItem = itemstack;
        dataManager.set(WEAPON_ITEM, itemstack);
        updateWeaponMaterial();
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
                material = ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial.ordinal();
            }
            dataManager.set(WEAPON_MATERIAL, (byte) (material & 0xFF));
        }
    }

    @SideOnly(Side.CLIENT)
    public float[] getMaterialColor() {
        int id = getWeaponMaterialId();
        if (id >= 0 && id < MATERIAL_COLORS.length) {
            return MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        if (thrownItem != null) {
            nbttagcompound.setTag("thrI", thrownItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setThrownItemStack(new ItemStack(nbttagcompound.getCompoundTag("thrI")));
    }

}
