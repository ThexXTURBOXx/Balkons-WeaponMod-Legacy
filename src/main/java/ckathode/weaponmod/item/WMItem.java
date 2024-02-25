package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(final String id) {
        this(id, new Properties());
    }

    public WMItem(final String id, final Properties properties) {
        super(properties.group(ItemGroup.COMBAT));
        this.setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        // TODO: NEEDED? this.setTranslationKey(id);
        BalkonsWeaponMod.MOD_ITEMS.add(this);
    }
}
