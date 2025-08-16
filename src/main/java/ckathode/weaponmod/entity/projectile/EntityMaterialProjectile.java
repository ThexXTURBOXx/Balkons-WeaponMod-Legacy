package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemHitEffect;
import com.google.common.base.Optional;
import io.netty.buffer.ByteBuf;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaterialProjectile extends EntityProjectile {
    private static final DataParameter<Integer> WEAPON_MATERIAL =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<ItemStack>> WEAPON_ITEM =
            EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.OPTIONAL_ITEM_STACK);
    private static final float[][] MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f},
            {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f, 0.7f}, {1.0f, 0.9f, 0.0f}};

    public EntityMaterialProjectile(World world) {
        super(world);
    }

    @Override
    public void entityInit() {
        super.entityInit();
        dataManager.register(WEAPON_MATERIAL, 0);
        dataManager.register(WEAPON_ITEM, Optional.absent());
    }

    @Override
    public void writeSpawnData(ByteBuf buf) {
        super.writeSpawnData(buf);
        buf.writeInt(getWeaponMaterialId());
        ByteBufUtils.writeItemStack(buf, getWeapon().orNull());
    }

    @Override
    public void readSpawnData(ByteBuf buf) {
        super.readSpawnData(buf);
        dataManager.set(WEAPON_MATERIAL, buf.readInt());
        dataManager.set(WEAPON_ITEM, Optional.fromNullable(ByteBufUtils.readItemStack(buf)));
    }

    public float getEnchantmentDamage(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            return EnchantmentHelper.getModifierForCreature(getWeapon().orNull(),
                    ((EntityLivingBase) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void onHitEntity(RayTraceResult raytraceResult) {
        super.onHitEntity(raytraceResult);
        ItemStack thrownItem = getWeapon().orNull();
        if (thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitEntity(this, raytraceResult);
        }
    }

    @Override
    public void onHitBlock(RayTraceResult raytraceResult) {
        super.onHitBlock(raytraceResult);
        ItemStack thrownItem = getWeapon().orNull();
        if (thrownItem != null && thrownItem.getItem() instanceof ItemHitEffect) {
            ((ItemHitEffect) thrownItem.getItem()).onHitBlock(this, raytraceResult);
        }
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

    public void setThrownItemStack(@Nullable ItemStack itemstack) {
        dataManager.set(WEAPON_ITEM, Optional.fromNullable(itemstack));
        updateWeaponMaterial();
    }

    @Nullable
    @Override
    public ItemStack getPickupItem() {
        return getWeapon().orNull();
    }

    @Nonnull
    @Override
    protected ItemStack getArrowStack() {
        return getWeapon().or(super.getArrowStack());
    }

    public int getWeaponMaterialId() {
        return dataManager.get(WEAPON_MATERIAL);
    }

    public Optional<ItemStack> getWeapon() {
        return dataManager.get(WEAPON_ITEM);
    }

    protected void updateWeaponMaterial() {
        ItemStack thrownItem = getWeapon().orNull();
        if (thrownItem != null && thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(thrownItem);
            if (material < 0) {
                material = ((IItemWeapon) thrownItem.getItem()).getMeleeComponent().weaponMaterial.ordinal();
            }
            dataManager.set(WEAPON_MATERIAL, material);
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
        ItemStack thrownItem = getWeapon().orNull();
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
