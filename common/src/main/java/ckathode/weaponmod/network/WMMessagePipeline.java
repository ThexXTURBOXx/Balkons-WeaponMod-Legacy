package ckathode.weaponmod.network;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMUtil.EffectiveSide;
import io.netty.buffer.ByteBuf;
import java.util.LinkedList;
import java.util.function.Supplier;
import me.shedaniel.architectury.networking.NetworkChannel;
import me.shedaniel.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public final class WMMessagePipeline {

    public static final NetworkChannel CHANNEL = NetworkChannel.create(
            new ResourceLocation(MOD_ID, "main"));
    private static final LinkedList<Class<? extends WMMessage<?>>> ID_TO_PACKET = new LinkedList<>();

    public static <T extends WMMessage<T>> void registerPacket(Class<T> messageType) {
        ID_TO_PACKET.add(messageType);
        CHANNEL.register(messageType, WMMessagePipeline::encode, WMMessagePipeline::decode, WMMessagePipeline::handle);
    }

    private static <T extends WMMessage<T>> void encode(T msg, FriendlyByteBuf buf) {
        if (!ID_TO_PACKET.contains(msg.getClass())) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }
        int discriminator = ID_TO_PACKET.indexOf(msg.getClass());
        buf.writeInt(discriminator);
        msg.encode(buf);
    }

    @SuppressWarnings("unchecked")
    private static <T extends WMMessage<T>> T decode(FriendlyByteBuf buf) {
        ByteBuf payload = buf.duplicate();
        if (payload.readableBytes() < 1) {
            BalkonsWeaponMod.LOGGER.error("Balkon's Weapon Mod has received an empty buffer, likely a "
                                          + "result of a LAN server issue.");
        }
        int discriminator = payload.readInt();
        Class<T> clazz = (Class<T>) ID_TO_PACKET.get(discriminator);
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

    private static <T extends WMMessage<T>> void handle(T msg, Supplier<NetworkManager.PacketContext> ctx) {
        switch (EffectiveSide.get()) {
        case CLIENT:
            msg.handleClientSide(msg, ctx);
            break;
        case SERVER:
            msg.handleServerSide(msg, ctx);
            break;
        }
    }

    public static <T extends WMMessage<T>> void sendTo(@NotNull WMMessage<T> message, @NotNull ServerPlayer player) {
        if (CHANNEL.canPlayerReceive(player, message.getClass())) {
            CHANNEL.sendToPlayer(player, message);
        }
    }

    public static <T extends WMMessage<T>> void sendToAround(@NotNull WMMessage<T> message,
                                                             @NotNull ServerLevel level, double x, double y, double z,
                                                             double radius, ResourceKey<Level> dimension) {
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            if (player.level.dimension() == dimension) {
                double d = x - player.getX();
                double e = y - player.getY();
                double f = z - player.getZ();
                if (d * d + e * e + f * f < radius * radius) sendTo(message, player);
            }
        }
    }

    public static <T extends WMMessage<T>> void sendToServer(@NotNull WMMessage<T> message) {
        if (CHANNEL.canServerReceive(message.getClass())) {
            CHANNEL.sendToServer(message);
        }
    }

    public static void init() {
        registerPacket(MsgCannonFire.class);
        registerPacket(MsgExplosion.class);
    }

}
