package ckathode.weaponmod;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ReloadHelper {
    public static int STATE_NONE;
    public static int STATE_RELOADED;
    public static int STATE_READY;

    private static void initTagCompound(final ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new NBTTagCompound());
        }
    }

    public static int getReloadState(final ItemStack itemstack) {
        if (itemstack.hasTag()) {
            return itemstack.getTag().getByte("rld");
        }
        return 0;
    }

    public static void setReloadState(final ItemStack itemstack, final int state) {
        initTagCompound(itemstack);
        itemstack.getTag().putByte("rld", (byte) state);
    }

    static {
        ReloadHelper.STATE_NONE = 0;
        ReloadHelper.STATE_RELOADED = 1;
        ReloadHelper.STATE_READY = 2;
    }
}
