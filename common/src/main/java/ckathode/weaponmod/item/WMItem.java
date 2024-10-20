package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.Nullable;

public class WMItem extends Item {

    public static final String CANNON_BALL_ID = "cannonball";
    public static final WMItem CANNON_BALL_ITEM = WMItemBuilder.createWMItem();

    public static final String BLUNDER_SHOT_ID = "shot";
    public static final WMItem BLUNDER_SHOT_ITEM = WMItemBuilder.createWMItem();

    public static final String MUSKET_IRON_PART_ID = "musket-ironpart";
    public static final WMItem MUSKET_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public static final String BLUNDER_IRON_PART_ID = "blunder-ironpart";
    public static final WMItem BLUNDER_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public static final String GUN_STOCK_ID = "gun-stock";
    public static final WMItem GUN_STOCK_ITEM = WMItemBuilder.createWMItem();

    public static final String MORTAR_IRON_PART_ID = "mortar-ironpart";
    public static final WMItem MORTAR_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public WMItem() {
        this(getBaseProperties(null));
    }

    public WMItem(Properties properties) {
        super(properties.arch$tab(CreativeModeTabs.COMBAT));
    }

    public static Properties getBaseProperties(@Nullable Tier tier) {
        Properties properties = new Properties();
        if (tier == Tiers.NETHERITE) {
            properties = properties.fireResistant();
        }
        return properties;
    }

}
