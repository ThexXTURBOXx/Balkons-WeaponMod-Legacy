package ckathode.weaponmod;

import java.util.Arrays;
import java.util.function.Function;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.Alternative;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.SetCount;

public class WMLootTables {

    private static ILootCondition cfgCondition(String... configs) {
        return context ->
                BalkonsWeaponMod.instance.modConfig.enableLootTables.get() &&
                Arrays.stream(configs).allMatch(cfg ->
                        BalkonsWeaponMod.instance.modConfig.isEnabled(cfg));
    }

    public static LootPool.Builder getWoodenWeaponsLootPool() {
        return addWoodenWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addWoodenWeapons(LootPool.Builder lootPool,
                                                    Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeWood)
                        .acceptCondition(() -> cfgCondition("battleaxe"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangWood)
                        .acceptCondition(() -> cfgCondition("boomerang"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailWood)
                        .acceptCondition(() -> cfgCondition("flail"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaWood)
                        .acceptCondition(() -> cfgCondition("katana"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdWood)
                        .acceptCondition(() -> cfgCondition("halberd"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeWood)
                        .acceptCondition(() -> cfgCondition("knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetWood)
                        .acceptCondition(() -> cfgCondition("musket", "knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearWood)
                        .acceptCondition(() -> cfgCondition("spear"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerWood)
                        .acceptCondition(() -> cfgCondition("warhammer"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getStoneWeaponsLootPool() {
        return addStoneWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addStoneWeapons(LootPool.Builder lootPool,
                                                   Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeStone)
                        .acceptCondition(() -> cfgCondition("battleaxe"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangStone)
                        .acceptCondition(() -> cfgCondition("boomerang"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailStone)
                        .acceptCondition(() -> cfgCondition("flail"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaStone)
                        .acceptCondition(() -> cfgCondition("katana"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdStone)
                        .acceptCondition(() -> cfgCondition("halberd"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeStone)
                        .acceptCondition(() -> cfgCondition("knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetStone)
                        .acceptCondition(() -> cfgCondition("musket", "knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearStone)
                        .acceptCondition(() -> cfgCondition("spear"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerStone)
                        .acceptCondition(() -> cfgCondition("warhammer"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getIronWeaponsLootPool() {
        return addIronWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addIronWeapons(LootPool.Builder lootPool,
                                                  Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeSteel)
                        .acceptCondition(() -> cfgCondition("battleaxe"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangSteel)
                        .acceptCondition(() -> cfgCondition("boomerang"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailSteel)
                        .acceptCondition(() -> cfgCondition("flail"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaSteel)
                        .acceptCondition(() -> cfgCondition("katana"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdSteel)
                        .acceptCondition(() -> cfgCondition("halberd"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeSteel)
                        .acceptCondition(() -> cfgCondition("knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetSteel)
                        .acceptCondition(() -> cfgCondition("musket", "knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearSteel)
                        .acceptCondition(() -> cfgCondition("spear"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerSteel)
                        .acceptCondition(() -> cfgCondition("warhammer"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getGoldWeaponsLootPool() {
        return addGoldWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addGoldWeapons(LootPool.Builder lootPool,
                                                  Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeGold)
                        .acceptCondition(() -> cfgCondition("battleaxe"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangGold)
                        .acceptCondition(() -> cfgCondition("boomerang"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailGold)
                        .acceptCondition(() -> cfgCondition("flail"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaGold)
                        .acceptCondition(() -> cfgCondition("katana"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdGold)
                        .acceptCondition(() -> cfgCondition("halberd"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeGold)
                        .acceptCondition(() -> cfgCondition("knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetGold)
                        .acceptCondition(() -> cfgCondition("musket", "knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearGold)
                        .acceptCondition(() -> cfgCondition("spear"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerGold)
                        .acceptCondition(() -> cfgCondition("warhammer"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getDiamondWeaponsLootPool() {
        return addDiamondWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addDiamondWeapons(LootPool.Builder lootPool,
                                                     Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeDiamond)
                        .acceptCondition(() -> cfgCondition("battleaxe"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangDiamond)
                        .acceptCondition(() -> cfgCondition("boomerang"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailDiamond)
                        .acceptCondition(() -> cfgCondition("flail"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaDiamond)
                        .acceptCondition(() -> cfgCondition("katana"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdDiamond)
                        .acceptCondition(() -> cfgCondition("halberd"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeDiamond)
                        .acceptCondition(() -> cfgCondition("knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetDiamond)
                        .acceptCondition(() -> cfgCondition("musket", "knife"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearDiamond)
                        .acceptCondition(() -> cfgCondition("spear"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerDiamond)
                        .acceptCondition(() -> cfgCondition("warhammer"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getShooterLootPool() {
        return addShooters(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addShooters(LootPool.Builder lootPool,
                                               Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.blowgun)
                        .acceptCondition(() -> cfgCondition("blowgun"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.crossbow)
                        .acceptCondition(() -> cfgCondition("crossbow"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.javelin)
                        .acceptCondition(() -> cfgCondition("javelin"))
                        .acceptFunction(SetCount.builder(RandomValueRange.of(4, 9)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.musket)
                        .acceptCondition(() -> cfgCondition("musket"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.blunderbuss)
                        .acceptCondition(() -> cfgCondition("blunderbuss"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flintlockPistol)
                        .acceptCondition(() -> cfgCondition("flintlock"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.mortar)
                        .acceptCondition(() -> cfgCondition("mortar"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.cannon)
                        .acceptCondition(() -> cfgCondition("cannon"))
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getProjectileLootPool() {
        return addProjectiles(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addProjectiles(LootPool.Builder lootPool,
                                                  Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        BalkonsWeaponMod.darts.values().forEach(d -> lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(d)
                        .acceptCondition(() -> cfgCondition("blowgun")))));

        int weight = BalkonsWeaponMod.darts.size(); // Generate as many darts as other weapons
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bolt)
                        .acceptCondition(() -> cfgCondition("crossbow"))
                        .weight(weight)))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.musketBullet)
                        .acceptCondition(Alternative.builder(
                                () -> cfgCondition("musket"), () -> cfgCondition("flintlock")))
                        .weight(weight)))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.blunderShot)
                        .acceptCondition(() -> cfgCondition("blunderbuss"))
                        .weight(weight)));
    }

    public static LootPool.Builder getExplosiveLootPool() {
        return addExplosives(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addExplosives(LootPool.Builder lootPool,
                                                 Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.dynamite)
                        .acceptCondition(() -> cfgCondition("dynamite"))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.cannonBall)
                        .acceptCondition(() -> cfgCondition("cannon"))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.mortarShell)
                        .acceptCondition(() -> cfgCondition("mortar"))));
    }

    public static void registerLootTableAdditions(ResourceLocation id, LootTable lootTable) {
        if (LootTables.CHESTS_BURIED_TREASURE.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
            lootTable.addPool(getExplosiveLootPool()
                    .acceptFunction(SetCount.builder(RandomValueRange.of(4, 9)))
                    .rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_DESERT_PYRAMID.equals(id)) {
            lootTable.addPool(getExplosiveLootPool()
                    .acceptFunction(SetCount.builder(RandomValueRange.of(4, 9)))
                    .rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_END_CITY_TREASURE.equals(id)) {
            LootPool.Builder lootPool = LootPool.builder();
            addDiamondWeapons(lootPool, b -> b
                    .acceptFunction(EnchantWithLevels.func_215895_a(RandomValueRange.of(20, 39)))
            );
            addIronWeapons(lootPool, b -> b
                    .acceptFunction(EnchantWithLevels.func_215895_a(RandomValueRange.of(20, 39)))
            );
            lootTable.addPool(lootPool.rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_JUNGLE_TEMPLE_DISPENSER.equals(id)) {
            lootTable.addPool(getProjectileLootPool()
                    .acceptFunction(SetCount.builder(RandomValueRange.of(2, 7)))
                    .rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_NETHER_BRIDGE.equals(id)) {
            lootTable.addPool(getGoldWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_SHIPWRECK_SUPPLY.equals(id)) {
            lootTable.addPool(getExplosiveLootPool()
                    .acceptFunction(SetCount.builder(RandomValueRange.of(4, 9)))
                    .rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_STRONGHOLD_CORRIDOR.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_UNDERWATER_RUIN_BIG.equals(id)) {
            lootTable.addPool(getStoneWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_UNDERWATER_RUIN_SMALL.equals(id)) {
            lootTable.addPool(getStoneWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_VILLAGE_VILLAGE_FLETCHER.equals(id)) {
            lootTable.addPool(getProjectileLootPool()
                    .acceptFunction(SetCount.builder(RandomValueRange.of(1, 3)))
                    .rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
    }

}
