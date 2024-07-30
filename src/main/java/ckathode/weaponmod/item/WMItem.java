package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public WMItem(String modId, String id) {
        setRegistryName(new ResourceLocation(modId, id));
        setUnlocalizedName(id);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.itemModifierUUID;
    }

    public static void deleteStack(IInventory inv, ItemStack stack) {
        int size = inv.getSizeInventory();
        for (int i = 0; i < size; ++i) {
            if (inv.getStackInSlot(i) == stack)
                inv.setInventorySlotContents(i, null);
        }
    }
}
