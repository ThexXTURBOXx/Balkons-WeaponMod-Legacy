package ckathode.weaponmod.network;

import ckathode.weaponmod.AdvancedExplosion;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class MsgExplosion implements CustomPacketPayload {

    public static final Type<MsgExplosion> EXPLOSION_PACKET_TYPE =
            new Type<>(new ResourceLocation(MOD_ID, "explosion"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MsgExplosion> STREAM_CODEC = new StreamCodec<>() {
        @NotNull
        @Override
        public MsgExplosion decode(RegistryFriendlyByteBuf buf) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            float size = buf.readFloat();
            boolean smallParticles = buf.readBoolean();
            boolean bigParticles = buf.readBoolean();
            int len = buf.readInt();
            List<BlockPos> blocks = new ArrayList<>(len);
            for (int i = 0; i < len; ++i) {
                int ix = buf.readByte() + (int) x;
                int iy = buf.readByte() + (int) y;
                int iz = buf.readByte() + (int) z;
                blocks.add(new BlockPos(ix, iy, iz));
            }
            return new MsgExplosion(x, y, z, size, blocks, smallParticles, bigParticles);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, MsgExplosion msg) {
            buf.writeDouble(msg.x);
            buf.writeDouble(msg.y);
            buf.writeDouble(msg.z);
            buf.writeFloat(msg.size);
            buf.writeBoolean(msg.smallParticles);
            buf.writeBoolean(msg.bigParticles);
            buf.writeInt(msg.blocks.size());
            for (BlockPos pos : msg.blocks) {
                int dx = pos.getX() - (int) msg.x;
                int dy = pos.getY() - (int) msg.y;
                int dz = pos.getZ() - (int) msg.z;
                buf.writeByte(dx);
                buf.writeByte(dy);
                buf.writeByte(dz);
            }
        }
    };

    private final double x;
    private final double y;
    private final double z;
    private final float size;
    private final List<BlockPos> blocks;
    private final boolean smallParticles;
    private final boolean bigParticles;

    public MsgExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts) {
        x = explosion.explosionX;
        y = explosion.explosionY;
        z = explosion.explosionZ;
        size = explosion.explosionSize;
        blocks = explosion.getToBlow();
        smallParticles = smallparts;
        bigParticles = bigparts;
    }

    public MsgExplosion(double x, double y, double z, float size, List<BlockPos> blocks,
                        boolean smallparts, boolean bigparts) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.blocks = blocks;
        smallParticles = smallparts;
        bigParticles = bigparts;
    }

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return EXPLOSION_PACKET_TYPE;
    }

    @Environment(EnvType.CLIENT)
    public static void handleClientSide(MsgExplosion msg, NetworkManager.PacketContext ctx) {
        Level level = Minecraft.getInstance().level;
        AdvancedExplosion expl = new AdvancedExplosion(level, null, msg.x, msg.y, msg.z, msg.size,
                false, Explosion.BlockInteraction.DESTROY);
        expl.setAffectedBlockPositions(msg.blocks);
        expl.doParticleExplosion(msg.smallParticles, msg.bigParticles);
    }

}
