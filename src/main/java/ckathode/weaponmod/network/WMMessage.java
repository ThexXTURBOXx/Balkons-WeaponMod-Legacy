package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import java.util.function.Supplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public interface WMMessage<T extends WMMessage<T>> {
    void encode(final ByteBuf buf);

    void decode(final ByteBuf buf);

    @OnlyIn(Dist.CLIENT)
    void handleClientSide(final T msg, final Supplier<NetworkEvent.Context> ctx);

    void handleServerSide(final T msg, final Supplier<NetworkEvent.Context> ctx);
}
