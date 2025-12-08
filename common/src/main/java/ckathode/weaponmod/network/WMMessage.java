package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import me.shedaniel.architectury.networking.NetworkManager;

public interface WMMessage<T extends WMMessage<T>> {
    void encode(ByteBuf buf);

    void decode(ByteBuf buf);

    void handleClientSide(T msg, Supplier<NetworkManager.PacketContext> ctx);

    void handleServerSide(T msg, Supplier<NetworkManager.PacketContext> ctx);
}
