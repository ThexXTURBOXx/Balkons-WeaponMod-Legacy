package ckathode.weaponmod.forge;

import ckathode.weaponmod.WMCommonEventHandler;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandlerForge {

    @SubscribeEvent
    public void constructEntity(EntityEvent.EntityConstructing event) {
        WMCommonEventHandler.constructEntity(event.getEntity());
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        WMCommonEventHandler.equipZombies(event.getEntityLiving(), event.getWorld());
    }

    @SubscribeEvent
    public void registerLootTableAdditions(LootTableLoadEvent event) {
        WMCommonEventHandler.MODIFY_LOOT_TABLE.invoker().modifyLootTable(
                event.getLootTableManager(), event.getName(), (pool) -> event.getTable().addPool(pool));
    }

}
