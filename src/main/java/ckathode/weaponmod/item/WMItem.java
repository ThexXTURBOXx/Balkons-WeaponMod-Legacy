package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.creativetab.*;
import ckathode.weaponmod.*;

public class WMItem extends Item
{
    public WMItem(final String id) {
        this.setRegistryName(id);
        this.setTranslationKey(id);
        this.setCreativeTab(CreativeTabs.COMBAT);
        BalkonsWeaponMod.MOD_ITEMS.add(this);
    }
}
