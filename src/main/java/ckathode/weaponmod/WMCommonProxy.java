package ckathode.weaponmod;

import net.minecraftforge.common.*;
import ckathode.weaponmod.network.*;

public class WMCommonProxy
{
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
    }
    
    public void registerPackets(final WMMessagePipeline pipeline) {
        pipeline.registerPacket(MsgCannonFire.class);
        pipeline.registerPacket(MsgExplosion.class);
    }
    
    public void registerRenderersItem(final WeaponModConfig config) {
    }
    
    public void registerRenderersEntity(final WeaponModConfig config) {
    }
}
