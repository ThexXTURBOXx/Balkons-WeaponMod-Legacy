package ckathode.weaponmod.network;

import ckathode.weaponmod.AdvancedExplosion;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class MsgExplosion implements WMMessage<MsgExplosion> {
    private double x;
    private double y;
    private double z;
    private float size;
    private List<BlockPos> blocks;
    private boolean smallParticles;
    private boolean bigParticles;

    public MsgExplosion(AdvancedExplosion explosion, boolean smallparts, boolean bigparts) {
        x = explosion.explosionX;
        y = explosion.explosionY;
        z = explosion.explosionZ;
        size = explosion.explosionSize;
        blocks = explosion.getAffectedBlockPositions();
        smallParticles = smallparts;
        bigParticles = bigparts;
    }

    public MsgExplosion() {
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
        int n = blocks.size();
        buf.writeInt(n);
        for (int i = 0; i < n; ++i) {
            BlockPos pos = blocks.get(i);
            int dx = pos.getX() - (int) x;
            int dy = pos.getY() - (int) y;
            int dz = pos.getZ() - (int) z;
            buf.writeByte(dx);
            buf.writeByte(dy);
            buf.writeByte(dz);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClientSide(MsgExplosion msg, Supplier<NetworkEvent.Context> ctx) {
        World world = Minecraft.getInstance().world;
        AdvancedExplosion expl = new AdvancedExplosion(world, null, x, y, z, size,
                false, true);
        expl.setAffectedBlockPositions(blocks);
        expl.doParticleExplosion(smallParticles, bigParticles);
    }

    @Override
    public void handleServerSide(MsgExplosion msg, Supplier<NetworkEvent.Context> ctx) {
    }
}
