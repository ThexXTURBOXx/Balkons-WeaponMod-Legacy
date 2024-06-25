package ckathode.weaponmod.network;

import ckathode.weaponmod.AdvancedExplosion;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

public class MsgExplosion implements WMMessage<MsgExplosion> {

    private double x;
    private double y;
    private double z;
    private float size;
    private List<BlockPos> blocks;
    private boolean smallParticles;
    private boolean bigParticles;

    public MsgExplosion() {
        // Needed for WMMessagePipeline instantiation
    }

    public MsgExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts) {
        x = explosion.explosionX;
        y = explosion.explosionY;
        z = explosion.explosionZ;
        size = explosion.explosionSize;
        blocks = explosion.getToBlow();
        smallParticles = smallparts;
        bigParticles = bigparts;
    }

    @Override
    public void decode(ByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        size = buf.readFloat();
        smallParticles = buf.readBoolean();
        bigParticles = buf.readBoolean();
        int size = buf.readInt();
        blocks = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            int ix = buf.readByte() + (int) x;
            int iy = buf.readByte() + (int) y;
            int iz = buf.readByte() + (int) z;
            blocks.add(new BlockPos(ix, iy, iz));
        }
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeFloat(size);
        buf.writeBoolean(smallParticles);
        buf.writeBoolean(bigParticles);
        buf.writeInt(blocks.size());
        for (BlockPos pos : blocks) {
            int dx = pos.getX() - (int) x;
            int dy = pos.getY() - (int) y;
            int dz = pos.getZ() - (int) z;
            buf.writeByte(dx);
            buf.writeByte(dy);
            buf.writeByte(dz);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handleClientSide(MsgExplosion msg, Supplier<NetworkManager.PacketContext> ctx) {
        Level world = ctx.get().getPlayer().getLevel();
        AdvancedExplosion expl = new AdvancedExplosion(world, null, x, y, z, size,
                false, Explosion.BlockInteraction.DESTROY);
        expl.setAffectedBlockPositions(blocks);
        expl.doParticleExplosion(smallParticles, bigParticles);
    }

    @Override
    public void handleServerSide(MsgExplosion msg, Supplier<NetworkManager.PacketContext> ctx) {
    }

}
