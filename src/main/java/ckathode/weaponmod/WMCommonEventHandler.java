package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeCompFirerod;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.EnchantWithLevels;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {

    private static final Map<ItemStack, Collection<String>> ZOMBIE_WEAPONS = new HashMap<>();

    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerWeaponData.initPlayerWeaponData((PlayerEntity) entity);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource();
        if (!(source instanceof LivingEntity)) return;
        if (source instanceof PlayerEntity) return; // Already handled in MeleeCompFirerod

        LivingEntity living = (LivingEntity) source;
        ItemStack stack = living.getHeldItemMainhand();
        if (stack.isEmpty()) return;
        Item item = stack.getItem();
        if (item == BalkonsWeaponMod.fireRod) {
            ((MeleeCompFirerod) BalkonsWeaponMod.fireRod.meleeComponent).applyFire(event.getEntityLiving(), stack);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        if (!BalkonsWeaponMod.instance.modConfig.zombiesSpawnWithWeapons.get()) return;
        if (event.getWorld().isRemote()) return;
        if (!(event.getEntityLiving() instanceof ZombieEntity)) return;

        ZombieEntity entity = (ZombieEntity) event.getEntityLiving();
        if (entity.rand.nextFloat() < (event.getWorld().getDifficulty() == Difficulty.HARD ? 0.05F : 0.01F)) {
            ZOMBIE_WEAPONS.entrySet()
                    .stream()
                    .skip(entity.rand.nextInt(ZOMBIE_WEAPONS.size()))
                    .findFirst()
                    .ifPresent(e -> {
                        if (e.getValue().stream().anyMatch(cfg ->
                                !BalkonsWeaponMod.instance.modConfig.isEnabled(cfg))) return;
                        entity.setItemStackToSlot(EquipmentSlotType.MAINHAND, e.getKey());
                    });
        }
    }

    public static void registerZombieWeapon(ItemStack stack, String... configs) {
        ZOMBIE_WEAPONS.put(stack, new HashSet<>(Arrays.asList(configs)));
    }

    public static void registerZombieWeapon(Item item, String... configs) {
        registerZombieWeapon(new ItemStack(item), configs);
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

    private static ILootCondition cfgCondition(String... configs) {
        return context -> Arrays.stream(configs).allMatch(cfg ->
                BalkonsWeaponMod.instance.modConfig.isEnabled(cfg));
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

    @SubscribeEvent
    public void registerLootTableAdditions(LootTableLoadEvent event) {
        if (!BalkonsWeaponMod.instance.modConfig.enableLootTables.get()) return;

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
