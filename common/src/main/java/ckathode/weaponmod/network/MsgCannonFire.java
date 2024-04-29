package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import me.shedaniel.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.Entity;

public class MsgCannonFire implements WMMessage<MsgCannonFire> {

    private int cannonEntityID;

    public MsgCannonFire() {
        // Needed for WMMessagePipeline instantiation
    }

    public MsgCannonFire(EntityCannon entity) {
        cannonEntityID = entity.getId();
    }

    @Override
    public void decode(ByteBuf buf) {
        cannonEntityID = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(cannonEntityID);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClientSide(MsgCannonFire msg, Supplier<NetworkManager.PacketContext> ctx) {
    }

    @Override
    public void handleServerSide(MsgCannonFire msg, Supplier<NetworkManager.PacketContext> ctx) {
        Entity entity = ctx.get().getPlayer().level.getEntity(cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }

}
