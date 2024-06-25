package ckathode.weaponmod.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public final class WMMessagePipeline {

    public static void sendTo(@NotNull CustomPacketPayload message, @NotNull ServerPlayer player) {
        if (NetworkManager.canPlayerReceive(player, message.type())) {
            NetworkManager.sendToPlayer(player, message);
        }
    }

    public static void sendToAround(@NotNull CustomPacketPayload message, @NotNull ServerLevel level,
                                    double x, double y, double z, double radius, ResourceKey<Level> dimension) {
        for (ServerPlayer player : level.getServer().getPlayerList().getPlayers()) {
            if (player.level().dimension() == dimension) {
                double d = x - player.getX();
                double e = y - player.getY();
                double f = z - player.getZ();
                if (d * d + e * e + f * f < radius * radius) sendTo(message, player);
            }
        }
    }

    public static void sendToServer(@NotNull CustomPacketPayload message) {
        if (NetworkManager.canServerReceive(message.type())) {
            NetworkManager.sendToServer(message);
        }
    }

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), MsgCannonFire.CANNON_FIRE_PACKET_TYPE,
                MsgCannonFire.STREAM_CODEC, MsgCannonFire::handleServerSide);

        if (Platform.getEnvironment() == Env.CLIENT) {
            NetworkManager.registerReceiver(NetworkManager.s2c(), MsgExplosion.EXPLOSION_PACKET_TYPE,
                    MsgExplosion.STREAM_CODEC, MsgExplosion::handleClientSide);
        } else {
            NetworkManager.registerS2CPayloadType(MsgExplosion.EXPLOSION_PACKET_TYPE, MsgExplosion.STREAM_CODEC);
        }
    }

}
