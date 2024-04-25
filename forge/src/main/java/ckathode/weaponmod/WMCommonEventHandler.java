package ckathode.weaponmod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            PlayerWeaponData.initPlayerWeaponData((Player) entity);
        }
    }
}
