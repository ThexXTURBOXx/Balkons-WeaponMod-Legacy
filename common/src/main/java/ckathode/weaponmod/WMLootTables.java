package ckathode.weaponmod;

import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.predicates.AlternativeLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class WMLootTables {

    private static LootItemCondition cfgCondition(String... configs) {
        return new WMLootConfigCondition(configs);
    }

    public static LootPool.Builder getWoodenWeaponsLootPool() {
        return addWoodenWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addWoodenWeapons(LootPool.Builder lootPool,
                                                    Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_WOOD.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_WOOD.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_WOOD.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_WOOD.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_WOOD.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_WOOD.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_WOOD.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_WOOD.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_WOOD.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getStoneWeaponsLootPool() {
        return addStoneWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addStoneWeapons(LootPool.Builder lootPool,
                                                   Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_STONE.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_STONE.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_STONE.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_STONE.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_STONE.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_STONE.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_STONE.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_STONE.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_STONE.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getIronWeaponsLootPool() {
        return addIronWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addIronWeapons(LootPool.Builder lootPool,
                                                  Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_IRON.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_IRON.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_IRON.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_IRON.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_IRON.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_IRON.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_IRON.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_IRON.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_IRON.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getGoldWeaponsLootPool() {
        return addGoldWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addGoldWeapons(LootPool.Builder lootPool,
                                                  Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_GOLD.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_GOLD.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_GOLD.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_GOLD.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_GOLD.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_GOLD.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_GOLD.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_GOLD.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_GOLD.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getDiamondWeaponsLootPool() {
        return addDiamondWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addDiamondWeapons(LootPool.Builder lootPool,
                                                     Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_DIAMOND.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_DIAMOND.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_DIAMOND.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_DIAMOND.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_DIAMOND.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_DIAMOND.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_DIAMOND.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_DIAMOND.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_DIAMOND.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getNetheriteWeaponsLootPool() {
        return addNetheriteWeapons(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addNetheriteWeapons(LootPool.Builder lootPool,
                                                       Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BATTLEAXE_NETHERITE.get())
                        .when(() -> cfgCondition("battleaxe"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BOOMERANG_NETHERITE.get())
                        .when(() -> cfgCondition("boomerang"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLAIL_NETHERITE.get())
                        .when(() -> cfgCondition("flail"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KATANA_NETHERITE.get())
                        .when(() -> cfgCondition("katana"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_HALBERD_NETHERITE.get())
                        .when(() -> cfgCondition("halberd"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_KNIFE_NETHERITE.get())
                        .when(() -> cfgCondition("knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_NETHERITE.get())
                        .when(() -> cfgCondition("musket", "knife"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_SPEAR_NETHERITE.get())
                        .when(() -> cfgCondition("spear"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_WARHAMMER_NETHERITE.get())
                        .when(() -> cfgCondition("warhammer"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getShooterLootPool() {
        return addShooters(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addShooters(LootPool.Builder lootPool,
                                               Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BLOWGUN.get())
                        .when(() -> cfgCondition("blowgun"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_CROSSBOW.get())
                        .when(() -> cfgCondition("crossbow"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_JAVELIN.get())
                        .when(() -> cfgCondition("javelin"))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET.get())
                        .when(() -> cfgCondition("musket"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BLUNDERBUSS.get())
                        .when(() -> cfgCondition("blunderbuss"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_FLINTLOCK_PISTOL.get())
                        .when(() -> cfgCondition("flintlock"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MORTAR.get())
                        .when(() -> cfgCondition("mortar"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_CANNON.get())
                        .when(() -> cfgCondition("cannon"))
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))));
    }

    public static LootPool.Builder getProjectileLootPool() {
        return addProjectiles(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addProjectiles(LootPool.Builder lootPool,
                                                  Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        WMRegistries.ITEM_DARTS.values().forEach(d -> lootPool
                .add(mod.apply(LootItem.lootTableItem(d.get())
                        .when(() -> cfgCondition("blowgun")))));

        int weight = WMRegistries.ITEM_DARTS.size(); // Generate as many darts as other weapons
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_CROSSBOW_BOLT.get())
                        .when(() -> cfgCondition("crossbow"))
                        .setWeight(weight)))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MUSKET_BULLET.get())
                        .when(AlternativeLootItemCondition.alternative(
                                () -> cfgCondition("musket"), () -> cfgCondition("flintlock")))
                        .setWeight(weight)))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_BLUNDER_SHOT.get())
                        .when(() -> cfgCondition("blunderbuss"))
                        .setWeight(weight)));
    }

    public static LootPool.Builder getExplosiveLootPool() {
        return addExplosives(LootPool.lootPool(), b -> b);
    }

    public static LootPool.Builder addExplosives(LootPool.Builder lootPool,
                                                 Function<LootItem.Builder<?>, LootItem.Builder<?>> mod) {
        return lootPool
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_DYNAMITE.get())
                        .when(() -> cfgCondition("dynamite"))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_CANNON_BALL.get())
                        .when(() -> cfgCondition("cannon"))))
                .add(mod.apply(LootItem.lootTableItem(WMRegistries.ITEM_MORTAR_SHELL.get())
                        .when(() -> cfgCondition("mortar"))));
    }

    public static void registerLootTableAdditions(ResourceLocation id,
                                                  WMCommonEventHandler.LootTableModificationContext context) {
        if (BuiltInLootTables.BASTION_BRIDGE.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addGoldWeapons(lootPool, b -> b);
            addShooters(lootPool, b -> b
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.5f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 17)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BASTION_HOGLIN_STABLE.equals(id)) {
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 17)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BASTION_OTHER.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addGoldWeapons(lootPool, b -> b);
            addIronWeapons(lootPool, b -> b.setWeight(2)
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            addShooters(lootPool, b -> b);
            addShooters(lootPool, b -> b
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(5, 17)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BASTION_TREASURE.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addDiamondWeapons(lootPool, b -> b);
            addDiamondWeapons(lootPool, b -> b
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8f, 1.0f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(12, 25)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BURIED_TREASURE.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
            context.addPool(getExplosiveLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.DESERT_PYRAMID.equals(id)) {
            context.addPool(getExplosiveLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.END_CITY_TREASURE.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addDiamondWeapons(lootPool, b -> b
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20, 39)))
            );
            addIronWeapons(lootPool, b -> b
                    .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(20, 39)))
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER.equals(id)) {
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 7)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.NETHER_BRIDGE.equals(id)) {
            context.addPool(getGoldWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.RUINED_PORTAL.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addGoldWeapons(lootPool, b -> b
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.SHIPWRECK_SUPPLY.equals(id)) {
            context.addPool(getExplosiveLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 9)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.UNDERWATER_RUIN_BIG.equals(id)) {
            context.addPool(getStoneWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.UNDERWATER_RUIN_SMALL.equals(id)) {
            context.addPool(getStoneWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.VILLAGE_FLETCHER.equals(id)) {
            context.addPool(getProjectileLootPool()
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 3)))
                    .setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
    }

}
