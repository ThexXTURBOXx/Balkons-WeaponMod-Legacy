package ckathode.weaponmod;

import me.shedaniel.architectury.hooks.TagHooks;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class WMItemTags {

    // Weapons
    public static final Tag.Named<Item> BATTLEAXES = bind("battleaxes");
    public static final Tag.Named<Item> BOOMERANGS = bind("boomerangs");
    public static final Tag.Named<Item> FLAILS = bind("flails");
    public static final Tag.Named<Item> HALBERDS = bind("halberds");
    public static final Tag.Named<Item> KATANAS = bind("katanas");
    public static final Tag.Named<Item> KNIVES = bind("knives");
    public static final Tag.Named<Item> MUSKET_BAYONETS = bind("musketbayonets");
    public static final Tag.Named<Item> MUSKETS = bind("muskets");
    public static final Tag.Named<Item> SPEARS = bind("spears");
    public static final Tag.Named<Item> WARHAMMERS = bind("warhammers");

    // Ammo
    public static final Tag.Named<Item> BOLTS = bind("bolts");
    public static final Tag.Named<Item> BULLETS = bind("bullets");
    public static final Tag.Named<Item> DARTS = bind("darts");
    public static final Tag.Named<Item> SHOTS = bind("shots");
    public static final Tag.Named<Item> SHELLS = bind("shells");

    private static Tag.Named<Item> bind(String id) {
        return TagHooks.getItemOptional(BalkonsWeaponMod.id(id));
    }

}
