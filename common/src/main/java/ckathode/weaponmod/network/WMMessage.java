package ckathode.weaponmod.network;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;

public interface WMMessage<T extends WMMessage<T>> {
    void encode(ByteBuf buf);

    void decode(ByteBuf buf);

    void handleClientSide(T msg, Supplier<NetworkManager.PacketContext> ctx);

    void handleServerSide(T msg, Supplier<NetworkManager.PacketContext> ctx);
}
