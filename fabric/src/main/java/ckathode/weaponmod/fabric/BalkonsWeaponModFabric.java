package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMCommonEventHandler;
import dev.architectury.event.events.common.LootEvent;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootTable;

public class BalkonsWeaponModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BalkonsWeaponMod.init();
        WMConfigConditionFabric.init();
        PlayerWeaponDataImpl.init();

        try {
            Class<?> clazz = Class.forName("dev.architectury.event.fabric.LootTableModificationContextImpl");
            Constructor<?> constructor = clazz.getDeclaredConstructor(LootTable.Builder.class);
            constructor.setAccessible(true);
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandle handle = lookup.unreflectConstructor(constructor);

            LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
                try {
                    WMCommonEventHandler.MODIFY_LOOT_TABLE.invoker().modifyLootTable(
                            registries, key,
                            (LootEvent.LootTableModificationContext) handle.invoke(tableBuilder),
                            source.isBuiltin());
                } catch (Throwable t) {
                    BalkonsWeaponMod.LOGGER.error("Loot tables could not be modified!", t);
                }
            });
        } catch (Throwable t) {
            BalkonsWeaponMod.LOGGER.error("Loot tables could not be modified!", t);
        }
    }

}
