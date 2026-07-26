package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMCommonEventHandler;
import dev.architectury.event.events.common.LootEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public class WMCommonEventHandlerNeoForge {

    private static MethodHandle newContext;

    public static void onLootTableLoad(HolderLookup.Provider registries, ResourceLocation name, LootTable table) {
        try {
            if (newContext == null) {
                Class<?> clazz = Class.forName("dev.architectury.event.forge.LootTableModificationContextImpl");
                Constructor<?> constructor = clazz.getDeclaredConstructor(LootTable.class);
                constructor.setAccessible(true);
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                newContext = lookup.unreflectConstructor(constructor);
            }

            WMCommonEventHandler.MODIFY_LOOT_TABLE.invoker().modifyLootTable(
                    registries, ResourceKey.create(Registries.LOOT_TABLE, name),
                    (LootEvent.LootTableModificationContext) newContext.invoke(table), true);
        } catch (Throwable t) {
            BalkonsWeaponMod.LOGGER.error("Loot tables could not be modified!", t);
        }
    }

}
