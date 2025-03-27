package ckathode.weaponmod.network;

import ckathode.weaponmod.AdvancedExplosion;
import dev.architectury.networking.NetworkManager;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public record MsgExplosion(Vec3 center, float size, List<BlockPos> blocks, boolean smallParticles,
                           boolean bigParticles) implements CustomPacketPayload {

    public static final Type<MsgExplosion> EXPLOSION_PACKET_TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "explosion"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MsgExplosion> STREAM_CODEC = StreamCodec.composite(
            Vec3.STREAM_CODEC, MsgExplosion::center,
            ByteBufCodecs.FLOAT, MsgExplosion::size,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs.list()), MsgExplosion::blocks,
            ByteBufCodecs.BOOL, MsgExplosion::smallParticles,
            ByteBufCodecs.BOOL, MsgExplosion::bigParticles,
            MsgExplosion::new);

    public MsgExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts) {
        this(explosion.center, explosion.explosionSize, explosion.toBlow, smallparts, bigparts);
    }

    public MsgExplosion(double x, double y, double z, float size, List<BlockPos> blocks,
                        boolean smallparts, boolean bigparts) {
        this(new Vec3(x, y, z), size, blocks, smallparts, bigparts);
    }

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return EXPLOSION_PACKET_TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClientSide(MsgExplosion msg, NetworkManager.PacketContext ctx) {
        Level level = ctx.getPlayer().level();
        AdvancedExplosion.doParticleExplosion(level, msg.center, msg.blocks, msg.size, msg.smallParticles,
                msg.bigParticles);
    }

}
