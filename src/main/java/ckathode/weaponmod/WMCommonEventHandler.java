package ckathode.weaponmod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.entity;
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }
}
