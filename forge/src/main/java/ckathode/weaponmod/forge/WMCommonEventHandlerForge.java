package ckathode.weaponmod.forge;

import ckathode.weaponmod.WMCommonEventHandler;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandlerForge {

    @SubscribeEvent
    public void constructEntity(EntityEvent.EntityConstructing event) {
        WMCommonEventHandler.constructEntity(event.getEntity());
    }

    @SubscribeEvent
    public void onEntitySpawn(MobSpawnEvent.FinalizeSpawn event) {
        WMCommonEventHandler.equipZombies(event.getEntity(), event.getLevel());
    }

}
