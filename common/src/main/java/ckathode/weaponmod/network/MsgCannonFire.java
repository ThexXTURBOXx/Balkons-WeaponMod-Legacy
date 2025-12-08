package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

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

    @Override
    public void handleClientSide(MsgCannonFire msg, Supplier<NetworkManager.PacketContext> ctx) {
    }

    @Override
    public void handleServerSide(MsgCannonFire msg, Supplier<NetworkManager.PacketContext> ctx) {
        Player player = ctx.get().getPlayer();
        if (player == null) return;

        Entity entity = player.level.getEntity(cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }

}
