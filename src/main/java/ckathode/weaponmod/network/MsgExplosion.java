package ckathode.weaponmod.network;

import ckathode.weaponmod.AdvancedExplosion;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MsgExplosion extends WMMessage {
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
        blocks = explosion.getAffectedBlockPositions();
        smallParticles = smallparts;
        bigParticles = bigparts;
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
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
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
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

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player) {
        World world = Minecraft.getMinecraft().theWorld;
        AdvancedExplosion expl = new AdvancedExplosion(world, null, x, y, z, size,
                false, true);
        expl.setAffectedBlockPositions(blocks);
        expl.doParticleExplosion(smallParticles, bigParticles);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
    }
}
