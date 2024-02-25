package ckathode.weaponmod;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ReloadHelper {
    public static int STATE_NONE = 0;
    public static int STATE_RELOADED = 1;
    public static int STATE_READY = 2;

    private static void initTagCompound(ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new NBTTagCompound());
        }
    }

    public static int getReloadState(ItemStack itemstack) {
        if (itemstack.hasTag()) {
            return itemstack.getTag().getByte("rld");
        }
        return 0;
    }

    public static void setReloadState(ItemStack itemstack, int state) {
        initTagCompound(itemstack);
        itemstack.getTag().putByte("rld", (byte) state);
    }

}
