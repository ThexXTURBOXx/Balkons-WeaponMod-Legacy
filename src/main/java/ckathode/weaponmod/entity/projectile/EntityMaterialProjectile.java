package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;
import ckathode.weaponmod.*;
import ckathode.weaponmod.item.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.nbt.*;
import net.minecraft.network.datasync.*;

public abstract class EntityMaterialProjectile extends EntityProjectile
{
    private static final DataParameter<Byte> WEAPON_MATERIAL;
    private static final DataParameter<ItemStack> WEAPON_ITEM;
    private static final float[][] MATERIAL_COLORS;
    protected ItemStack thrownItem;

    public EntityMaterialProjectile(final World world) {
        super(world);
    }

    public void entityInit() {
        super.entityInit();
        this.dataManager.register(EntityMaterialProjectile.WEAPON_MATERIAL, (byte) 0);
        this.dataManager.register(EntityMaterialProjectile.WEAPON_ITEM, ItemStack.EMPTY);
    }

    public float getMeleeHitDamage(final Entity entity) {
        if (this.shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            return EnchantmentHelper.getModifierForCreature(((EntityLivingBase)this.shootingEntity).getHeldItemMainhand(), ((EntityLivingBase)entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void applyEntityHitEffects(final Entity entity) {
        super.applyEntityHitEffects(entity);
        if (this.shootingEntity instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this.shootingEntity);
            if (i != 0) {
                ((EntityLivingBase)entity).knockBack(this, i * 0.4f, -MathHelper.sin(this.rotationYaw * 0.017453292f), -MathHelper.cos(this.rotationYaw * 0.017453292f));
            }
            i = EnchantmentHelper.getFireAspectModifier((EntityLivingBase)this.shootingEntity);
            if (i > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }
    }

    public void setThrownItemStack(final ItemStack itemstack) {
        this.thrownItem = itemstack;
        if ((this.thrownItem != null && this.thrownItem.getItem() instanceof ItemFlail) || !BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            this.updateWeaponMaterial();
        }
        else if (this.thrownItem != null && !(this.thrownItem.getItem() instanceof ItemFlail) && BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
            this.dataManager.set(EntityMaterialProjectile.WEAPON_ITEM, itemstack);
            this.dataManager.setDirty(EntityMaterialProjectile.WEAPON_ITEM);
        }
    }

    @Override
    public ItemStack getPickupItem() {
        return this.thrownItem;
    }

    public int getWeaponMaterialId() {
        return this.dataManager.get(EntityMaterialProjectile.WEAPON_MATERIAL);
    }

    public ItemStack getWeapon() {
        return this.dataManager.get(EntityMaterialProjectile.WEAPON_ITEM);
    }

    protected final void updateWeaponMaterial() {
        if (this.thrownItem != null && this.thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon)this.thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(this.thrownItem);
            if (material < 0) {
                material = ((IItemWeapon)this.thrownItem.getItem()).getMeleeComponent().weaponMaterial.ordinal();
            }
            this.dataManager.set(EntityMaterialProjectile.WEAPON_MATERIAL, (byte)(material & 0xFF));
        }
    }

    @SideOnly(Side.CLIENT)
    public final float[] getMaterialColor() {
        final int id = this.getWeaponMaterialId();
        if (id < 5) {
            return EntityMaterialProjectile.MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeEntityToNBT(final NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        if (this.thrownItem != null) {
            nbttagcompound.setTag("thrI", this.thrownItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(final NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.setThrownItemStack(new ItemStack(nbttagcompound.getCompoundTag("thrI")));
    }

    static {
        WEAPON_MATERIAL = EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.BYTE);
        WEAPON_ITEM = EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.ITEM_STACK);
        MATERIAL_COLORS = new float[][] { { 0.6f, 0.4f, 0.1f }, { 0.5f, 0.5f, 0.5f }, { 1.0f, 1.0f, 1.0f }, { 0.0f, 0.8f, 0.7f }, { 1.0f, 0.9f, 0.0f } };
    }
}
