package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemHitEffect;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMaterialProjectile extends EntityProjectile {
    private static final int WEAPON_MATERIAL = 18;
    private static final int WEAPON_ITEM = 19;
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};

    public EntityMaterialProjectile(World world) {
        super(world);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        dataWatcher.addObject(WEAPON_MATERIAL, 0);
        dataWatcher.addObjectByDataType(WEAPON_ITEM, 5); // 5 = ItemStack type
    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        super.writeSpawnData(buf);
        buf.writeInt(getWeaponMaterialId());
        ByteBufUtils.writeItemStack(buf, getWeapon());
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        super.readSpawnData(buf);
        dataWatcher.updateObject(WEAPON_MATERIAL, buf.readInt());
        dataWatcher.updateObject(WEAPON_ITEM, ByteBufUtils.readItemStack(buf));
    }

    public float getEnchantmentDamage(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            return EnchantmentHelper.func_152377_a(getWeapon(), ((EntityLivingBase) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void onHitEntity(MovingObjectPosition mop) {
        super.onHitEntity(mop);
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitEntity(this, mop);
        }
    }

    @Override
    public void onHitBlock(MovingObjectPosition raytraceResult) {
        super.onHitBlock(raytraceResult);
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitBlock(this, raytraceResult);
        }
    }

    @Override
    public void applyEntityHitEffects(Entity entity) {
        super.applyEntityHitEffects(entity);
        ItemStack stack = getWeapon();
        if (entity instanceof EntityLivingBase) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            if (i != 0) {
                ((EntityLivingBase) entity).knockBack(this, i * 0.4f,
                        -MathHelper.sin(rotationYaw * 0.017453292f),
                        -MathHelper.cos(rotationYaw * 0.017453292f));
            }
        }
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
        if (i > 0 && !entity.isBurning()) {
            entity.setFire(1);
        }
    }

    public void setThrownItemStack(@Nullable ItemStack itemstack) {
        dataWatcher.updateObject(WEAPON_ITEM, itemstack);
        updateWeaponMaterial();
    }

    @Nullable
    @Override
    public ItemStack getPickupItem() {
        return getWeapon();
    }

    public int getWeaponMaterialId() {
        return dataWatcher.getWatchableObjectInt(WEAPON_MATERIAL);
    }

    public ItemStack getWeapon() {
        return dataWatcher.getWatchableObjectItemStack(WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null && thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material = ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial.ordinal();
            }
            dataWatcher.updateObject(WEAPON_MATERIAL, material);
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
        ItemStack thrownItem = getWeapon();
        if (thrownItem != null) {
            nbttagcompound.setTag("thrI", thrownItem.writeToNBT(new NBTTagCompound()));
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        if (nbttagcompound.hasKey("thrI")) {
            setThrownItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("thrI")));
        }
    }

}
