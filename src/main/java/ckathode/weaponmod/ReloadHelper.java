package ckathode.weaponmod;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ReloadHelper {
    public static int STATE_NONE;
    public static int STATE_RELOADED;
    public static int STATE_READY;

    private static void initTagCompound(final ItemStack itemstack) {
        if (itemstack.getTagCompound() == null) {
            itemstack.setTagCompound(new NBTTagCompound());
        }
    }

    public static int getReloadState(final ItemStack itemstack) {
        if (itemstack.hasTagCompound()) {
            return itemstack.getTagCompound().getByte("rld");
        }
        return 0;
    }

    public static void setReloadState(final ItemStack itemstack, final int state) {
        initTagCompound(itemstack);
        itemstack.getTagCompound().setByte("rld", (byte) state);
    }

    static {
        ReloadHelper.STATE_NONE = 0;
        ReloadHelper.STATE_RELOADED = 1;
        ReloadHelper.STATE_READY = 2;
    }
}
