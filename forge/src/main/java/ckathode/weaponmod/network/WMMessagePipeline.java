package ckathode.weaponmod.network;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import java.util.LinkedList;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

@ChannelHandler.Sharable
public class WMMessagePipeline {

    private final String protocolVersion = Integer.toString(1);
    private final SimpleChannel handler = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "main"))
            .clientAcceptedVersions(protocolVersion::equals)
            .serverAcceptedVersions(protocolVersion::equals)
            .networkProtocolVersion(() -> protocolVersion)
            .simpleChannel();
    private final LinkedList<Class<? extends WMMessage<?>>> idToPacket = new LinkedList<>();

    private int registerIndex = 0;

    public <T extends WMMessage<T>> void registerPacket(Class<T> messageType) {
        idToPacket.add(messageType);
        handler.registerMessage(registerIndex++, messageType, this::encode, this::decode, this::handle);
    }

    protected <T extends WMMessage<T>> void encode(T msg, FriendlyByteBuf buf) {
        if (!idToPacket.contains(msg.getClass())) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }
        int discriminator = idToPacket.indexOf(msg.getClass());
        buf.writeInt(discriminator);
        msg.encode(buf);
    }

    @SuppressWarnings("unchecked")
    protected <T extends WMMessage<T>> T decode(FriendlyByteBuf buf) {
        ByteBuf payload = buf.duplicate();
        if (payload.readableBytes() < 1) {
            BalkonsWeaponModForge.modLog.error("The FMLIndexedCodec has received an empty buffer, likely a "
                                               + "result of a LAN server issue.");
        }
        int discriminator = payload.readInt();
        Class<T> clazz = (Class<T>) idToPacket.get(discriminator);
        if (clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }
        try {
            T pkt = clazz.newInstance();
            pkt.decode(payload.slice());
            return pkt;
        } catch (Throwable t) {
            NullPointerException e = new NullPointerException("Could not instantiate packet: " + discriminator);
            e.addSuppressed(t);
            throw e;
        }
    }

    protected <T extends WMMessage<T>> void handle(T msg, Supplier<NetworkEvent.Context> ctx) {
        switch (EffectiveSide.get()) {
        case CLIENT: {
            msg.handleClientSide(msg, ctx);
            break;
        }
        case SERVER: {
            msg.handleServerSide(msg, ctx);
            break;
        }
        }
        ctx.get().setPacketHandled(true);
    }

    public <T extends WMMessage<T>> void sendToAll(WMMessage<T> message) {
        handler.send(PacketDistributor.ALL.noArg(), message);
    }

    public <T extends WMMessage<T>> void sendTo(WMMessage<T> message, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            handler.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    public <T extends WMMessage<T>> void sendToAllAround(WMMessage<T> message,
                                                         PacketDistributor.TargetPoint point) {
        handler.send(PacketDistributor.NEAR.with(() -> point), message);
    }

    public <T extends WMMessage<T>> void sendToDimension(WMMessage<T> message,
                                                         ResourceKey<Level> dimension) {
        handler.send(PacketDistributor.DIMENSION.with(() -> dimension), message);
    }

    public <T extends WMMessage<T>> void sendToServer(WMMessage<T> message) {
        handler.sendToServer(message);
    }

}
