package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem {
    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.hasTag() && itemstack.getTag().getBoolean("halb");
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new NBTTagCompound());
        }
        itemstack.getTag().putBoolean("halb", flag);
    }

    public MeleeCompHalberd(IItemTier itemTier) {
        super(MeleeSpecs.HALBERD, itemTier);
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
    public EnumAction getUseAction(ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        setHalberdState(itemstack, !getHalberdState(itemstack));
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
        return 4.0f;
    }
}
