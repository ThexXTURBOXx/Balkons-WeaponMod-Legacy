package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(String id) {
        this(id, new Properties());
    }

    public WMItem(String id, Properties properties) {
        super(properties.tab(ItemGroup.TAB_COMBAT));
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
    }
}
