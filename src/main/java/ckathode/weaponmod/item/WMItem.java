package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(String id) {
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        setTranslationKey(id);
        setCreativeTab(CreativeTabs.COMBAT);
    }
}
