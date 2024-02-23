package ckathode.weaponmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void onEntityConstructed(final EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) event.getEntity());
        }
    }
}
