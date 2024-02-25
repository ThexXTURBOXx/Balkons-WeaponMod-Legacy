package ckathode.weaponmod.entity.projectile;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemFlail;
import javax.annotation.Nonnull;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EntityMaterialProjectile<T extends EntityMaterialProjectile<T>> extends EntityProjectile<T> {
    private static final DataParameter<Byte> WEAPON_MATERIAL;
    private static final DataParameter<ItemStack> WEAPON_ITEM;
    private static final float[][] MATERIAL_COLORS;
    protected ItemStack thrownItem;

    public EntityMaterialProjectile(final EntityType<T> type, final World world) {
        super(type, world);
    }

    @Override
    public void registerData() {
        super.registerData();
        this.dataManager.register(EntityMaterialProjectile.WEAPON_MATERIAL, (byte) 0);
        this.dataManager.register(EntityMaterialProjectile.WEAPON_ITEM, ItemStack.EMPTY);
    }

    public float getMeleeHitDamage(final Entity entity) {
        Entity shooter = getShooter();
        if (shooter instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            return EnchantmentHelper.getModifierForCreature(((EntityLivingBase) shooter).getHeldItemMainhand(),
                    ((EntityLivingBase) entity).getCreatureAttribute());
        }
        return 0.0f;
    }

    @Override
    public void applyEntityHitEffects(final Entity entity) {
        super.applyEntityHitEffects(entity);
        Entity shooter = getShooter();
        if (shooter instanceof EntityLivingBase && entity instanceof EntityLivingBase) {
            int i = EnchantmentHelper.getKnockbackModifier((EntityLivingBase) shooter);
            if (i != 0) {
                ((EntityLivingBase) entity).knockBack(this, i * 0.4f,
                        -MathHelper.sin(this.rotationYaw * 0.017453292f),
                        -MathHelper.cos(this.rotationYaw * 0.017453292f));
            }
            i = EnchantmentHelper.getFireAspectModifier((EntityLivingBase) shooter);
            if (i > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }
    }

    public void setThrownItemStack(@Nonnull final ItemStack itemstack) {
        this.thrownItem = itemstack;
        if (this.thrownItem.getItem() instanceof ItemFlail || !BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            this.updateWeaponMaterial();
        } else if (this.thrownItem != null && !(this.thrownItem.getItem() instanceof ItemFlail) && BalkonsWeaponMod.instance.modConfig.itemModelForEntity.get()) {
            this.dataManager.set(EntityMaterialProjectile.WEAPON_ITEM, itemstack);
        }
    }

    @Nonnull
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
        if (this.thrownItem != null && this.thrownItem.getItem() instanceof IItemWeapon && ((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent() != null) {
            int material = MaterialRegistry.getMaterialID(this.thrownItem);
            if (material < 0) {
                material =
                        MaterialRegistry.getOrdinal(((IItemWeapon) this.thrownItem.getItem()).getMeleeComponent().weaponMaterial);
            }
            this.dataManager.set(EntityMaterialProjectile.WEAPON_MATERIAL, (byte) (material & 0xFF));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public final float[] getMaterialColor() {
        final int id = this.getWeaponMaterialId();
        if (id < 5) {
            return EntityMaterialProjectile.MATERIAL_COLORS[id];
        }
        return MaterialRegistry.getColorFromMaterialID(id);
    }

    @Override
    public void writeAdditional(final NBTTagCompound nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        if (this.thrownItem != null) {
            nbttagcompound.put("thrI", this.thrownItem.write(new NBTTagCompound()));
        }
    }

    @Override
    public void readAdditional(final NBTTagCompound nbttagcompound) {
        super.readAdditional(nbttagcompound);
        this.setThrownItemStack(ItemStack.read(nbttagcompound.getCompound("thrI")));
    }

    static {
        WEAPON_MATERIAL = EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.BYTE);
        WEAPON_ITEM = EntityDataManager.createKey(EntityMaterialProjectile.class, DataSerializers.ITEM_STACK);
        MATERIAL_COLORS = new float[][]{{0.6f, 0.4f, 0.1f}, {0.5f, 0.5f, 0.5f}, {1.0f, 1.0f, 1.0f}, {0.0f, 0.8f,
                0.7f}, {1.0f, 0.9f, 0.0f}};
    }
}
