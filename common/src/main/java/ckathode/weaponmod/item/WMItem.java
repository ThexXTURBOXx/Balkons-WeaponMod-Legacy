package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItem extends Item {

    public static final String CANNON_BALL_ID = "cannonball";
    public static final WMItem CANNON_BALL_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(CANNON_BALL_ID));

    public static final String BLUNDER_SHOT_ID = "shot";
    public static final WMItem BLUNDER_SHOT_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(BLUNDER_SHOT_ID));

    public static final String MUSKET_IRON_PART_ID = "musket-ironpart";
    public static final WMItem MUSKET_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(MUSKET_IRON_PART_ID));

    public static final String BLUNDER_IRON_PART_ID = "blunder-ironpart";
    public static final WMItem BLUNDER_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(BLUNDER_IRON_PART_ID));

    public static final String GUN_STOCK_ID = "gun-stock";
    public static final WMItem GUN_STOCK_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(GUN_STOCK_ID));

    public static final String MORTAR_IRON_PART_ID = "mortar-ironpart";
    public static final WMItem MORTAR_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(MORTAR_IRON_PART_ID));

    public WMItem(@NotNull ResourceLocation id) {
        this(getBaseProperties(id));
    }

    public WMItem(@NotNull Properties properties) {
        super(properties.arch$tab(CreativeModeTabs.COMBAT));
    }

    public static Properties getBaseProperties(@NotNull ResourceLocation id) {
        return getBaseProperties(null, id);
    }

    public static Properties getBaseProperties(@Nullable ToolMaterial tier, @NotNull ResourceLocation id) {
        Properties properties = new Properties().setId(WMItemBuilder.id(id));
        if (tier == ToolMaterial.NETHERITE) {
            properties = properties.fireResistant();
        }
        return properties;
    }

}
