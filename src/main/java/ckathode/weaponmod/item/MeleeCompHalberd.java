package ckathode.weaponmod.item;

import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem
{
    public static boolean getHalberdState(final ItemStack itemstack) {
        return itemstack.hasTagCompound() && itemstack.getTagCompound().getBoolean("halb");
    }
    
    public static void setHalberdState(final ItemStack itemstack, final boolean flag) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
        itemstack.getTagCompound().setBoolean("halb", flag);
    }
    
    public MeleeCompHalberd(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.HALBERD, toolmaterial);
    }
    
    @Override
    public float getAttackDelay(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        final float ad = super.getAttackDelay(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? 0.0f : ad;
    }
    
    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        final float kb = super.getKnockBack(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? (kb / 2.0f) : kb;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.NONE;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        setHalberdState(itemstack, !getHalberdState(itemstack));
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public float getExtendedReach(final World world, final EntityLivingBase living, final ItemStack itemstack) {
        return 4.0f;
    }
}
