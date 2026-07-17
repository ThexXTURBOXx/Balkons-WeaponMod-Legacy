package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerWeaponData.initPlayerWeaponData((PlayerEntity) entity);
        }
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        ItemStack stack = entity.getActiveItemStack();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return;
        if (!(item instanceof IItemWeapon)) return;
        if (entity.isInvulnerableTo(source)) return;
        if (entity.getHealth() <= 0) return;
        if (source.isFireDamage() && entity.isPotionActive(Effects.FIRE_RESISTANCE)) return;
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.abilities.disableDamage && !source.canHarmInCreative()) return;
        }

        entity.resetActiveHand();
    }

    public static LootPool.Builder getIronWeaponsLootPool() {
        return addIronWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addIronWeapons(LootPool.Builder lootPool,
                                                  Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerSteel)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getGoldWeaponsLootPool() {
        return addGoldWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addGoldWeapons(LootPool.Builder lootPool,
                                                  Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerGold)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    public static LootPool.Builder getDiamondWeaponsLootPool() {
        return addDiamondWeapons(LootPool.builder(), b -> b);
    }

    public static LootPool.Builder addDiamondWeapons(LootPool.Builder lootPool,
                                                     Function<ItemLootEntry.Builder<?>, ItemLootEntry.Builder<?>> mod) {
        return lootPool
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.battleaxeDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.boomerangDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.flailDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.katanaDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.halberdDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.knifeDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.bayonetDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.spearDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))))
                .addEntry(mod.apply(ItemLootEntry.builder(BalkonsWeaponMod.warhammerDiamond)
                        .acceptFunction(SetCount.builder(ConstantRange.of(1)))));
    }

    @SubscribeEvent
    public void registerLootTableAdditions(LootTableLoadEvent event) {
        ResourceLocation id = event.getName();
        LootTable lootTable = event.getTable();

        if (LootTables.CHESTS_BURIED_TREASURE.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
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
        if (LootTables.CHESTS_NETHER_BRIDGE.equals(id)) {
            lootTable.addPool(getGoldWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_STRONGHOLD_CORRIDOR.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
        if (LootTables.CHESTS_VILLAGE_VILLAGE_WEAPONSMITH.equals(id)) {
            lootTable.addPool(getIronWeaponsLootPool().rolls(RandomValueRange.of(0, 1)).build());
        }
    }
}
