package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemVariants;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemFlail extends ItemMelee {
    private final float flailDamage;
    private IIcon thrownIcon;
    private Boolean thrownIconExists;

    public ItemFlail(String id, MeleeComponent meleecomponent) {
        super(id, meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getDamageVsEntity();
        thrownIcon = null;
        thrownIconExists = null;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (!isThrown(player)) {
            return;
        }
        ItemStack itemstack2 = player.getCurrentEquippedItem();
        if (itemstack2 == null || !((itemstack2.getItem()) instanceof ItemFlail)) {
            setThrown(player, false);
        } else if (itemstack2.getItem() == this) {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setThrower(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        removePreviousFlail(world, entityplayer);
        if (itemstack.stackSize > 0) {
            entityplayer.swingItem();
            if (!entityplayer.capabilities.isCreativeMode) {
                itemstack.damageItem(1, entityplayer);
                if (itemstack.stackSize <= 0) {
                    WMItem.deleteStack(entityplayer.inventory, itemstack);
                }
            }
            if (itemstack.stackSize > 0) {
                throwFlail(itemstack, world, entityplayer);
            } else {
                WMItem.deleteStack(entityplayer.inventory, itemstack);
            }
        } else {
            WMItem.deleteStack(entityplayer.inventory, itemstack);
        }
        return itemstack;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        onItemRightClick(itemstack, attacker.worldObj, (EntityPlayer) attacker);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            world.spawnEntityInWorld(entityflail);
            setThrown(entityplayer, true);
        }
    }

    public void setThrown(EntityPlayer entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(World world, EntityPlayer entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.setDead();
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (thrownIconExists == null)
            thrownIconExists = WMItemVariants.itemVariantExists(thrownIcon);
        if (thrownIconExists && isThrown(player))
            return thrownIcon;

        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        thrownIcon = WMItemVariants.registerItemVariants(register, "weaponmod:flail", "-thrown").get(0);
    }
}
