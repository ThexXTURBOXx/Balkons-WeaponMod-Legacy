package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import dev.architectury.networking.NetworkManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface WMMessage<T extends WMMessage<T>> {
    void encode(ByteBuf buf);

    void decode(ByteBuf buf);

    @Environment(EnvType.CLIENT)
    void handleClientSide(T msg, Supplier<NetworkManager.PacketContext> ctx);

    void handleServerSide(T msg, Supplier<NetworkManager.PacketContext> ctx);
}
