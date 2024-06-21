package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem {
    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("halb");
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        if (!itemstack.hasTagCompound()) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("halb", flag);
    }

    public MeleeCompHalberd(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.HALBERD, toolmaterial);
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, EntityLivingBase entityliving,
                                EntityLivingBase attacker) {
        float ad = super.getAttackDelay(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? 0.0f : ad;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, EntityLivingBase entityliving,
                              EntityLivingBase attacker) {
        float kb = super.getKnockBack(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? (kb / 2.0f) : kb;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public ItemStack onItemRightClick(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        setHalberdState(itemstack, !getHalberdState(itemstack));
        return itemstack;
    }

    @Override
    public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
        return 4.0f;
    }
}
