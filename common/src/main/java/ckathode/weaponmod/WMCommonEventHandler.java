package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public class WMCommonEventHandler {

    public static final Event<ModifyLootTable> MODIFY_LOOT_TABLE = EventFactory.createLoop();

    @FunctionalInterface
    public interface ModifyLootTable {
        /**
         * Modifies a loot table.
         *
         * @param registries the registries provider
         * @param key        the loot table key
         * @param context    the context used to modify the loot table
         * @param builtin    if {@code true}, the loot table is built-in;
         *                   if {@code false}, it is from a user data pack
         */
        void modifyLootTable(HolderLookup.Provider registries, ResourceKey<LootTable> key,
                             LootEvent.LootTableModificationContext context, boolean builtin);
    }

    public static void equipZombies(LivingEntity living, LevelAccessor level) {
        WMMobEquipment.equipZombies(living, level);
    }

    public static EventResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source, float amount) {
        ItemStack stack = entity.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return EventResult.pass();
        if (!(item instanceof IItemWeapon)) return EventResult.pass();
        if (entity.level() instanceof ServerLevel sl && entity.isInvulnerableTo(sl, source))
            return EventResult.pass();
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

    public static void registerLootTableAdditions(HolderLookup.Provider registries, ResourceKey<LootTable> key,
                                                  LootEvent.LootTableModificationContext context, boolean builtIn) {
        WMLootTables.registerLootTableAdditions(registries, key, context, builtIn);
    }

    public static void init() {
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
        MODIFY_LOOT_TABLE.register(WMCommonEventHandler::registerLootTableAdditions);
    }

}
