package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class WMItem extends Item {
    public WMItem(String id) {
        this(id, new Properties());
    }

    public WMItem(String id, Properties properties) {
        super(properties.tab(CreativeModeTab.TAB_COMBAT));
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
    }
}
