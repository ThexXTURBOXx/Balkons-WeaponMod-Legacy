package ckathode.weaponmod;

import net.minecraftforge.event.entity.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class WMCommonEventHandler
{
    @SubscribeEvent
    public void onEntityConstructed(final EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer)event.getEntity());
        }
    }
}
