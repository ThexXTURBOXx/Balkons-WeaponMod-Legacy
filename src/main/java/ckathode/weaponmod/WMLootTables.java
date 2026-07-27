package ckathode.weaponmod;

import ckathode.weaponmod.item.DartType;
import java.util.Arrays;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import static ckathode.weaponmod.BalkonsWeaponMod.battleaxeDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.battleaxeGold;
import static ckathode.weaponmod.BalkonsWeaponMod.battleaxeSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.battleaxeStone;
import static ckathode.weaponmod.BalkonsWeaponMod.battleaxeWood;
import static ckathode.weaponmod.BalkonsWeaponMod.bayonetDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.bayonetGold;
import static ckathode.weaponmod.BalkonsWeaponMod.bayonetSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.bayonetStone;
import static ckathode.weaponmod.BalkonsWeaponMod.bayonetWood;
import static ckathode.weaponmod.BalkonsWeaponMod.blowgun;
import static ckathode.weaponmod.BalkonsWeaponMod.blunderShot;
import static ckathode.weaponmod.BalkonsWeaponMod.blunderbuss;
import static ckathode.weaponmod.BalkonsWeaponMod.bolt;
import static ckathode.weaponmod.BalkonsWeaponMod.boomerangDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.boomerangGold;
import static ckathode.weaponmod.BalkonsWeaponMod.boomerangSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.boomerangStone;
import static ckathode.weaponmod.BalkonsWeaponMod.boomerangWood;
import static ckathode.weaponmod.BalkonsWeaponMod.cannon;
import static ckathode.weaponmod.BalkonsWeaponMod.cannonBall;
import static ckathode.weaponmod.BalkonsWeaponMod.crossbow;
import static ckathode.weaponmod.BalkonsWeaponMod.dart;
import static ckathode.weaponmod.BalkonsWeaponMod.dynamite;
import static ckathode.weaponmod.BalkonsWeaponMod.flailDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.flailGold;
import static ckathode.weaponmod.BalkonsWeaponMod.flailSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.flailStone;
import static ckathode.weaponmod.BalkonsWeaponMod.flailWood;
import static ckathode.weaponmod.BalkonsWeaponMod.flintlockPistol;
import static ckathode.weaponmod.BalkonsWeaponMod.halberdDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.halberdGold;
import static ckathode.weaponmod.BalkonsWeaponMod.halberdSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.halberdStone;
import static ckathode.weaponmod.BalkonsWeaponMod.halberdWood;
import static ckathode.weaponmod.BalkonsWeaponMod.instance;
import static ckathode.weaponmod.BalkonsWeaponMod.javelin;
import static ckathode.weaponmod.BalkonsWeaponMod.katanaDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.katanaGold;
import static ckathode.weaponmod.BalkonsWeaponMod.katanaSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.katanaStone;
import static ckathode.weaponmod.BalkonsWeaponMod.katanaWood;
import static ckathode.weaponmod.BalkonsWeaponMod.knifeDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.knifeGold;
import static ckathode.weaponmod.BalkonsWeaponMod.knifeSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.knifeStone;
import static ckathode.weaponmod.BalkonsWeaponMod.knifeWood;
import static ckathode.weaponmod.BalkonsWeaponMod.mortar;
import static ckathode.weaponmod.BalkonsWeaponMod.mortarShell;
import static ckathode.weaponmod.BalkonsWeaponMod.musket;
import static ckathode.weaponmod.BalkonsWeaponMod.musketBullet;
import static ckathode.weaponmod.BalkonsWeaponMod.spearDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.spearGold;
import static ckathode.weaponmod.BalkonsWeaponMod.spearSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.spearStone;
import static ckathode.weaponmod.BalkonsWeaponMod.spearWood;
import static ckathode.weaponmod.BalkonsWeaponMod.warhammerDiamond;
import static ckathode.weaponmod.BalkonsWeaponMod.warhammerGold;
import static ckathode.weaponmod.BalkonsWeaponMod.warhammerSteel;
import static ckathode.weaponmod.BalkonsWeaponMod.warhammerStone;
import static ckathode.weaponmod.BalkonsWeaponMod.warhammerWood;

public class WMLootTables {

    public static void registerLootTableAdditions() {
        if (!instance.modConfig.enableLootTables) return;

        Item[] woodenItems = new Item[]{battleaxeWood, boomerangWood, flailWood, katanaWood,
                halberdWood, knifeWood, bayonetWood, spearWood, warhammerWood};
        Item[] stoneItems = new Item[]{battleaxeStone, boomerangStone, flailStone, katanaStone,
                halberdStone, knifeStone, bayonetStone, spearStone, warhammerStone};
        Item[] ironItems = new Item[]{battleaxeSteel, boomerangSteel, flailSteel, katanaSteel,
                halberdSteel, knifeSteel, bayonetSteel, spearSteel, warhammerSteel};
        Item[] goldItems = new Item[]{battleaxeGold, boomerangGold, flailGold, katanaGold,
                halberdGold, knifeGold, bayonetGold, spearGold, warhammerGold};
        Item[] diamondItems = new Item[]{battleaxeDiamond, boomerangDiamond, flailDiamond, katanaDiamond,
                halberdDiamond, knifeDiamond, bayonetDiamond, spearDiamond, warhammerDiamond};
        String[][] meleeConfigs = new String[][]{{"battleaxe"}, {"boomerang"}, {"flail"}, {"katana"},
                {"halberd"}, {"knife"}, {"musket", "knife"}, {"spear"}, {"warhammer"}};

        Item[] shooters = new Item[]{blowgun, crossbow, javelin, musket,
                blunderbuss, flintlockPistol, mortar, cannon};
        String[][] shooterConfigs = new String[][]{{"blowgun"}, {"crossbow"}, {"javelin"}, {"musket"},
                {"blunderbuss"}, {"flintlock"}, {"mortar"}, {"cannon"}};
        // TODO: JAVELIN: STACK SIZE BETWEEN 4 AND 9

        Item[] projectiles = new Item[]{bolt, musketBullet, blunderShot, dart};
        String[][] projectileConfigs = new String[][]{
                {"crossbow"}, {"musket", "flintlock"}, {"blunderbuss"}, {"blowgun"}};

        Item[] explosives = new Item[]{dynamite, cannonBall, mortarShell};
        String[][] explosiveConfigs = new String[][]{{"dynamite"}, {"cannon"}, {"mortar"}};

        for (int i = 0; i < ironItems.length; i++) {
            if (Arrays.stream(meleeConfigs[i]).anyMatch(cfg -> !instance.modConfig.isEnabled(cfg))) continue;
            ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR,
                    new WeightedRandomChestContent(ironItems[i], 0, 1, 1, 5));
            ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH,
                    new WeightedRandomChestContent(ironItems[i], 0, 1, 1, 5));
        }

        for (int i = 0; i < goldItems.length; i++) {
            if (Arrays.stream(meleeConfigs[i]).anyMatch(cfg -> !instance.modConfig.isEnabled(cfg))) continue;
            ChestGenHooks.addItem(ChestGenHooks.NETHER_FORTRESS,
                    new WeightedRandomChestContent(goldItems[i], 0, 1, 1, 5));
        }

        for (int i = 0; i < explosives.length; i++) {
            if (Arrays.stream(explosiveConfigs[i]).anyMatch(cfg -> !instance.modConfig.isEnabled(cfg))) continue;
            ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST,
                    new WeightedRandomChestContent(explosives[i], 0, 4, 9, 5));
        }

        for (int i = 0; i < projectiles.length; i++) {
            if (Arrays.stream(projectileConfigs[i]).anyMatch(cfg -> !instance.modConfig.isEnabled(cfg))) continue;
            if (projectiles[i] == dart) {
                // Handle darts extra
                int weight = MathHelper.ceiling_float_int(30f / DartType.DART_TYPES.size());
                for (DartType type : DartType.DART_TYPES.valueCollection()) {
                    ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER,
                            new WeightedRandomChestContent(projectiles[i], type.typeID, 2, 7, weight));
                }
                continue;
            }
            ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER,
                    new WeightedRandomChestContent(projectiles[i], 0, 2, 7, 30));
        }
    }

}
