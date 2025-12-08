package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import net.minecraftforge.fml.network.NetworkEvent;

public interface WMMessage<T extends WMMessage<T>> {
    void encode(ByteBuf buf);

    void decode(ByteBuf buf);

    void handleClientSide(T msg, Supplier<NetworkEvent.Context> ctx);

    void handleServerSide(T msg, Supplier<NetworkEvent.Context> ctx);
}
