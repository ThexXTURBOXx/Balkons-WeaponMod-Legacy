package ckathode.weaponmod;

import javax.annotation.Nonnull;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public final class ReloadHelper {

    private static void initTagCompound(ItemStack itemstack) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundTag());
        }
    }

    @Nonnull
    public static ReloadState getReloadState(ItemStack itemstack) {
        try {
            if (itemstack.hasTag()) {
                return ReloadState.values()[itemstack.getTag().getByte("rld")];
            }
        } catch (Throwable ignored) {
        }
        return ReloadState.STATE_NONE;
    }

    public static void setReloadState(ItemStack itemstack, ReloadState state) {
        initTagCompound(itemstack);
        itemstack.getTag().putByte("rld", (byte) state.ordinal());
    }

    public enum ReloadState {
        STATE_NONE,
        STATE_RELOADED,
        STATE_READY;

        public boolean isReloaded() {
            return this == STATE_RELOADED || this == STATE_READY;
        }
    }

}
