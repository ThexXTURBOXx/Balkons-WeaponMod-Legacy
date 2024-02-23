package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class WMItem extends Item {
    public WMItem(final String id) {
        this.setRegistryName(id);
        this.setTranslationKey(id);
        this.setCreativeTab(CreativeTabs.COMBAT);
        BalkonsWeaponMod.MOD_ITEMS.add(this);
    }
}
