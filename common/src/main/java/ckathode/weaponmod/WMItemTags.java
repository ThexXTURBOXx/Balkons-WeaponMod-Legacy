package ckathode.weaponmod;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class WMItemTags {

    // Weapons
    public static final TagKey<Item> BATTLEAXES = bind("battleaxes");
    public static final TagKey<Item> BOOMERANGS = bind("boomerangs");
    public static final TagKey<Item> FLAILS = bind("flails");
    public static final TagKey<Item> HALBERDS = bind("halberds");
    public static final TagKey<Item> KATANAS = bind("katanas");
    public static final TagKey<Item> KNIVES = bind("knives");
    public static final TagKey<Item> MUSKET_BAYONETS = bind("musketbayonets");
    public static final TagKey<Item> MUSKETS = bind("muskets");
    public static final TagKey<Item> SPEARS = bind("spears");
    public static final TagKey<Item> WARHAMMERS = bind("warhammers");

    // Ammo
    public static final TagKey<Item> BOLTS = bind("bolts");
    public static final TagKey<Item> BULLETS = bind("bullets");
    public static final TagKey<Item> DARTS = bind("darts");
    public static final TagKey<Item> SHOTS = bind("shots");
    public static final TagKey<Item> SHELLS = bind("shells");

    private static TagKey<Item> bind(String id) {
        return TagKey.create(Registries.ITEM, BalkonsWeaponMod.id(id));
    }

}
