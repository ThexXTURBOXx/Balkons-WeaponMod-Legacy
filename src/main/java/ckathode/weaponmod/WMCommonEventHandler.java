package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import java.util.Arrays;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        ItemStack stack = entity.getActiveItemStack();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return;
        if (!(item instanceof IItemWeapon)) return;
        if (entity.isInvulnerableTo(source)) return;
        if (entity.getHealth() <= 0) return;
        if (source.isFireDamage() && entity.isPotionActive(MobEffects.FIRE_RESISTANCE)) return;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.abilities.disableDamage && !source.canHarmInCreative()) return;
        }

        entity.resetActiveHand();
    }

    private static LootCondition cfgCondition(String... configs) {
        return (rand, context) -> Arrays.stream(configs).allMatch(cfg ->
                BalkonsWeaponMod.instance.modConfig.isEnabled(cfg));
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

    @SubscribeEvent
    public void registerLootTableAdditions(LootTableLoadEvent event) {
        if (!BalkonsWeaponMod.instance.modConfig.enableLootTables.get()) return;

        ResourceLocation id = event.getName();
        LootTable lootTable = event.getTable();

        if (LootTableList.CHESTS_BURIED_TREASURE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#bt");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);
        }
        if (LootTableList.CHESTS_END_CITY_TREASURE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#ect");
            addDiamondWeapons(lootPool,
                    new EnchantWithLevels(new LootCondition[0], new RandomValueRange(20, 39), true)
            );
            addIronWeapons(lootPool,
                    new EnchantWithLevels(new LootCondition[0], new RandomValueRange(20, 39), true)
            );
            lootTable.addPool(lootPool);
        }
        if (LootTableList.CHESTS_NETHER_BRIDGE.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#nb");
            addGoldWeapons(lootPool);
            lootTable.addPool(lootPool);
        }
        if (LootTableList.CHESTS_STRONGHOLD_CORRIDOR.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#sc");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);
        }
        if (LootTableList.CHESTS_VILLAGE_BLACKSMITH.equals(id)) {
            LootPool lootPool = new LootPool(new LootEntry[0], new LootCondition[0],
                    new RandomValueRange(0, 1), new RandomValueRange(0, 0),
                    "custom#weaponmod#vb");
            addIronWeapons(lootPool);
            lootTable.addPool(lootPool);
        }
    }
}
