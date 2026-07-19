package ckathode.weaponmod;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;

public class WMItemTags {

    // Weapons
    public static final Tag<Item> BATTLEAXES = makeWrapperTag("battleaxes");
    public static final Tag<Item> BOOMERANGS = makeWrapperTag("boomerangs");
    public static final Tag<Item> FLAILS = makeWrapperTag("flails");
    public static final Tag<Item> HALBERDS = makeWrapperTag("halberds");
    public static final Tag<Item> KATANAS = makeWrapperTag("katanas");
    public static final Tag<Item> KNIVES = makeWrapperTag("knives");
    public static final Tag<Item> MUSKET_BAYONETS = makeWrapperTag("musketbayonets");
    public static final Tag<Item> MUSKETS = makeWrapperTag("muskets");
    public static final Tag<Item> SPEARS = makeWrapperTag("spears");
    public static final Tag<Item> WARHAMMERS = makeWrapperTag("warhammers");

    // Ammo
    public static final Tag<Item> BOLTS = makeWrapperTag("bolts");
    public static final Tag<Item> BULLETS = makeWrapperTag("bullets");
    public static final Tag<Item> DARTS = makeWrapperTag("darts");
    public static final Tag<Item> SHOTS = makeWrapperTag("shots");
    public static final Tag<Item> SHELLS = makeWrapperTag("shells");

    private static Tag<Item> makeWrapperTag(String string) {
        return new ItemTags.Wrapper(BalkonsWeaponMod.id(string));
    }

}
