package ckathode.weaponmod.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public class WMItem extends Item {

    public static final String CANNON_BALL_ID = "cannonball";
    public static final WMItem CANNON_BALL_ITEM = new WMItem();

    public static final String BLUNDER_SHOT_ID = "shot";
    public static final WMItem BLUNDER_SHOT_ITEM = new WMItem();

    public static final String MUSKET_IRON_PART_ID = "musket-ironpart";
    public static final WMItem MUSKET_IRON_PART_ITEM = new WMItem();

    public static final String BLUNDER_IRON_PART_ID = "blunder-ironpart";
    public static final WMItem BLUNDER_IRON_PART_ITEM = new WMItem();

    public static final String GUN_STOCK_ID = "gun-stock";
    public static final WMItem GUN_STOCK_ITEM = new WMItem();

    public static final String MORTAR_IRON_PART_ID = "mortar-ironpart";
    public static final WMItem MORTAR_IRON_PART_ITEM = new WMItem();

    public WMItem() {
        this(new Properties());
    }

    public WMItem(Properties properties) {
        super(properties.arch$tab(CreativeModeTabs.COMBAT));
    }

}
