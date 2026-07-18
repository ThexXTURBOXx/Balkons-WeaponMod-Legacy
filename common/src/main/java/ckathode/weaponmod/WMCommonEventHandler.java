package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LootEvent;
import java.util.function.Function;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantWithLevelsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity) {
        if (entity instanceof Player player) {
            PlayerWeaponData.initPlayerWeaponData(player);
        }
    }

    public static EventResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source, float amount) {
        ItemStack stack = entity.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return EventResult.pass();
        if (!(item instanceof IItemWeapon)) return EventResult.pass();
        if (entity.isInvulnerableTo(source)) return EventResult.pass();
        if (entity.isDeadOrDying()) return EventResult.pass();
        if (source.is(DamageTypeTags.IS_FIRE) && entity.hasEffect(MobEffects.FIRE_RESISTANCE))
            return EventResult.pass();
        if (entity instanceof Player player) {
            if (player.getAbilities().invulnerable && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
                return EventResult.pass();
        }

        entity.stopUsingItem();
        return EventResult.pass();
    }

    private static LootItemCondition cfgCondition(String... configs) {
        return new WMLootConfigCondition(configs);
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

    public static void registerLootTableAdditions(LootDataManager lootDataManager, ResourceLocation id,
                                                  LootEvent.LootTableModificationContext context, boolean builtIn) {
        if (!builtIn) return;
        if (!WeaponModConfig.get().enableLootTables) return;

        if (BuiltInLootTables.BASTION_BRIDGE.equals(id)) {
            context.addPool(getGoldWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BASTION_OTHER.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addGoldWeapons(lootPool, b -> b);
            addIronWeapons(lootPool, b -> b.setWeight(2)
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.1f, 0.9f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BASTION_TREASURE.equals(id)) {
            LootPool.Builder lootPool = LootPool.lootPool();
            addDiamondWeapons(lootPool, b -> b);
            addDiamondWeapons(lootPool, b -> b
                    .apply(SetItemDamageFunction.setDamage(UniformGenerator.between(0.8f, 1.0f)))
                    .apply(EnchantRandomlyFunction.randomApplicableEnchantment())
            );
            context.addPool(lootPool.setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.BURIED_TREASURE.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
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
        if (BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
        if (BuiltInLootTables.VILLAGE_WEAPONSMITH.equals(id)) {
            context.addPool(getIronWeaponsLootPool().setRolls(UniformGenerator.between(0, 1)));
        }
    }

    public static void init() {
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
        LootEvent.MODIFY_LOOT_TABLE.register(WMCommonEventHandler::registerLootTableAdditions);
    }

}
