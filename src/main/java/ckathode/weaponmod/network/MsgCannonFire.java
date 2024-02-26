package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class MsgCannonFire implements WMMessage<MsgCannonFire> {
    private int cannonEntityID;

    public MsgCannonFire() {
        // Needed for WMMessagePipeline instantiation
    }

    public MsgCannonFire(EntityCannon entity) {
        cannonEntityID = 0;
        cannonEntityID = entity.getEntityId();
    }

    @Override
    public void decode(ByteBuf buf) {
        cannonEntityID = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(cannonEntityID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClientSide(MsgCannonFire msg, Supplier<NetworkEvent.Context> ctx) {
    }

    @Override
    public void handleServerSide(MsgCannonFire msg, Supplier<NetworkEvent.Context> ctx) {
        Entity entity = ctx.get().getSender().world.getEntityByID(cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }
}
