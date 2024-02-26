package ckathode.weaponmod;

import ckathode.weaponmod.network.MsgCannonFire;
import ckathode.weaponmod.network.MsgExplosion;
import ckathode.weaponmod.network.WMMessagePipeline;
import net.minecraftforge.common.MinecraftForge;

public class WMCommonProxy {
    public void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandler());
    }

    public void registerPackets(WMMessagePipeline pipeline) {
        pipeline.registerPacket(MsgCannonFire.class);
        pipeline.registerPacket(MsgExplosion.class);
    }

    public void registerRenderersEntity() {
    }
}
