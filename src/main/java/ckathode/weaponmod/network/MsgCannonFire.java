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
        this.cannonEntityID = 0;
    }

    public MsgCannonFire(final EntityCannon entity) {
        this.cannonEntityID = 0;
        this.cannonEntityID = entity.getEntityId();
    }

    @Override
    public void decode(final ByteBuf buf) {
        this.cannonEntityID = buf.readInt();
    }

    @Override
    public void encode(final ByteBuf buf) {
        buf.writeInt(this.cannonEntityID);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClientSide(final MsgCannonFire msg, final Supplier<NetworkEvent.Context> ctx) {
    }

    @Override
    public void handleServerSide(final MsgCannonFire msg, final Supplier<NetworkEvent.Context> ctx) {
        final Entity entity = ctx.get().getSender().world.getEntityByID(this.cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }
}
