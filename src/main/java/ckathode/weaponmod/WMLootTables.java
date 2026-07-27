package ckathode.weaponmod;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;

public class WMLootTables {

    private static LootCondition cfgCondition(String... configs) {
        return (rand, context) ->
                BalkonsWeaponMod.instance.modConfig.enableLootTables.get() &&
                Arrays.stream(configs).allMatch(cfg ->
                        BalkonsWeaponMod.instance.modConfig.isEnabled(cfg));
    }

    private static LootCondition orCondition(LootCondition... conditions) {
        return (rand, context) ->
                Arrays.stream(conditions).anyMatch(c -> c.testCondition(rand, context));
    }

    public static LootPool addWoodenWeapons(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.battleaxeWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("battleaxe")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.boomerangWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("boomerang")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flailWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flail")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.katanaWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("katana")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.halberdWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("halberd")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.knifeWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bayonetWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket", "knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.spearWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("spear")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w7"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.warhammerWood,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("warhammer")},
                "custom#weaponmod#" + lootPool.hashCode() + "#w8"));
        return lootPool;
    }

    public static LootPool addStoneWeapons(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.battleaxeStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("battleaxe")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.boomerangStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("boomerang")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flailStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flail")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.katanaStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("katana")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.halberdStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("halberd")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.knifeStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bayonetStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket", "knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.spearStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("spear")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s7"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.warhammerStone,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("warhammer")},
                "custom#weaponmod#" + lootPool.hashCode() + "#s8"));
        return lootPool;
    }

    public static LootPool addIronWeapons(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.battleaxeSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("battleaxe")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.boomerangSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("boomerang")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flailSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flail")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.katanaSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("katana")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.halberdSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("halberd")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.knifeSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bayonetSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket", "knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.spearSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("spear")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i7"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.warhammerSteel,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("warhammer")},
                "custom#weaponmod#" + lootPool.hashCode() + "#i8"));
        return lootPool;
    }

    public static LootPool addGoldWeapons(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.battleaxeGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("battleaxe")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.boomerangGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("boomerang")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flailGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flail")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.katanaGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("katana")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.halberdGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("halberd")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.knifeGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bayonetGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket", "knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.spearGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("spear")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g7"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.warhammerGold,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("warhammer")},
                "custom#weaponmod#" + lootPool.hashCode() + "#g8"));
        return lootPool;
    }

    public static LootPool addDiamondWeapons(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.battleaxeDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("battleaxe")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.boomerangDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("boomerang")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flailDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flail")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.katanaDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("katana")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.halberdDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("halberd")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.knifeDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bayonetDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket", "knife")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.spearDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("spear")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d7"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.warhammerDiamond,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("warhammer")},
                "custom#weaponmod#" + lootPool.hashCode() + "#d8"));
        return lootPool;
    }

    public static LootPool addShooters(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.blowgun,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("blowgun")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.crossbow,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("crossbow")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.javelin,
                1, 0,
                Stream.of(lootFunctions,
                                new LootFunction[]{new SetCount(new LootCondition[0], new RandomValueRange(4, 9))})
                        .flatMap(Stream::of).toArray(LootFunction[]::new),
                new LootCondition[]{cfgCondition("javelin")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h2"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.musket,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("musket")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h3"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.blunderbuss,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("blunderbuss")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h4"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.flintlockPistol,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("flintlock")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h5"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.mortar,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("mortar")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h6"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.cannon,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("cannon")},
                "custom#weaponmod#" + lootPool.hashCode() + "#h7"));
        return lootPool;
    }

    public static LootPool addProjectiles(LootPool lootPool, LootFunction... lootFunctions) {
        AtomicInteger index = new AtomicInteger(3);
        BalkonsWeaponMod.darts.values().forEach(d -> lootPool
                .addEntry(new LootEntryItem(d,
                        1, 0, lootFunctions, new LootCondition[]{cfgCondition("blowgun")},
                        "custom#weaponmod#" + lootPool.hashCode() + "#p" + index.getAndIncrement())));

        int weight = BalkonsWeaponMod.darts.size(); // Generate as many darts as other weapons

        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.bolt,
                weight, 0, lootFunctions, new LootCondition[]{cfgCondition("crossbow")},
                "custom#weaponmod#" + lootPool.hashCode() + "#p0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.musketBullet,
                weight, 0, lootFunctions,
                new LootCondition[]{orCondition(cfgCondition("musket"), cfgCondition("flintlock"))},
                "custom#weaponmod#" + lootPool.hashCode() + "#p1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.blunderShot,
                weight, 0, lootFunctions, new LootCondition[]{cfgCondition("blunderbuss")},
                "custom#weaponmod#" + lootPool.hashCode() + "#p2"));
        return lootPool;
    }

    public static LootPool addExplosives(LootPool lootPool, LootFunction... lootFunctions) {
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.dynamite,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("dynamite")},
                "custom#weaponmod#" + lootPool.hashCode() + "#e0"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.cannonBall,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("cannon")},
                "custom#weaponmod#" + lootPool.hashCode() + "#e1"));
        lootPool.addEntry(new LootEntryItem(BalkonsWeaponMod.mortarShell,
                1, 0, lootFunctions, new LootCondition[]{cfgCondition("mortar")},
                "custom#weaponmod#" + lootPool.hashCode() + "#e2"));
        return lootPool;
    }

    public static void registerLootTableAdditions(ResourceLocation id, LootTable lootTable) {
        if (LootTableList.CHESTS_BURIED_TREASURE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cbt0");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);

            lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cbt1");
            addExplosives(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_DESERT_PYRAMID.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cdp0");
            addExplosives(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_END_CITY_TREASURE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cect0");
            addDiamondWeapons(lootPool,
                    new EnchantWithLevels(new LootCondition[0], new RandomValueRange(20, 39), true)
            );
            addIronWeapons(lootPool,
                    new EnchantWithLevels(new LootCondition[0], new RandomValueRange(20, 39), true)
            );
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_JUNGLE_TEMPLE_DISPENSER.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cjtd0");
            addProjectiles(lootPool,
                    new SetCount(new LootCondition[0], new RandomValueRange(2, 7))
            );
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_NETHER_BRIDGE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cnb0");
            addGoldWeapons(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_SHIPWRECK_SUPPLY.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#css0");
            addExplosives(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_STRONGHOLD_CORRIDOR.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#csc0");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_UNDERWATER_RUIN_BIG.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#curb0");
            addStoneWeapons(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_UNDERWATER_RUIN_SMALL.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#curs0");
            addStoneWeapons(lootPool);
            lootTable.addPool(lootPool);
        }

        if (LootTableList.CHESTS_VILLAGE_BLACKSMITH.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#cvb0");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);
        }
    }

}
