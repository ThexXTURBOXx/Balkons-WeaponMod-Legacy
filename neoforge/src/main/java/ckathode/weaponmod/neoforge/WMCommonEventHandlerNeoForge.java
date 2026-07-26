package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMCommonEventHandler;
import dev.architectury.event.events.common.LootEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public class WMCommonEventHandlerNeoForge {

    public static class MainEvents {

        private MethodHandle newContext;

        @SubscribeEvent(priority = EventPriority.HIGH)
        public void onLootTableLoad(LootTableLoadEvent event) {
            try {
                if (newContext == null) {
                    Class<?> clazz = Class.forName("dev.architectury.event.forge.LootTableModificationContextImpl");
                    Constructor<?> constructor = clazz.getDeclaredConstructor(LootTable.class);
                    constructor.setAccessible(true);
                    MethodHandles.Lookup lookup = MethodHandles.lookup();
                    newContext = lookup.unreflectConstructor(constructor);
                }

                WMCommonEventHandler.MODIFY_LOOT_TABLE.invoker().modifyLootTable(
                        event.getRegistries(), ResourceKey.create(Registries.LOOT_TABLE, event.getName()),
                        (LootEvent.LootTableModificationContext) newContext.invoke(event.getTable()), true);
            } catch (Throwable t) {
                BalkonsWeaponMod.LOGGER.error("Loot tables could not be modified!", t);
            }
        }

    }

}
