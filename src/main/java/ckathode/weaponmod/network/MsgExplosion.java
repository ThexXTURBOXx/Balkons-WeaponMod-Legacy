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

    public MsgExplosion(final AdvancedExplosion explosion, final boolean smallparts, final boolean bigparts) {
        this.x = explosion.explosionX;
        this.y = explosion.explosionY;
        this.z = explosion.explosionZ;
        this.size = explosion.explosionSize;
        this.blocks = explosion.getAffectedBlockPositions();
        this.smallParticles = smallparts;
        this.bigParticles = bigparts;
    }

    public MsgExplosion() {
    }

    @Override
    public void decode(final ByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.size = buf.readFloat();
        this.smallParticles = buf.readBoolean();
        this.bigParticles = buf.readBoolean();
        final int size = buf.readInt();
        this.blocks = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            final int ix = buf.readByte() + (int) this.x;
            final int iy = buf.readByte() + (int) this.y;
            final int iz = buf.readByte() + (int) this.z;
            this.blocks.add(new BlockPos(ix, iy, iz));
        }
    }

    @Override
    public void encode(final ByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.writeFloat(this.size);
        buf.writeBoolean(this.smallParticles);
        buf.writeBoolean(this.bigParticles);
        final int n = this.blocks.size();
        buf.writeInt(n);
        for (int i = 0; i < n; ++i) {
            final BlockPos pos = this.blocks.get(i);
            final int dx = pos.getX() - (int) this.x;
            final int dy = pos.getY() - (int) this.y;
            final int dz = pos.getZ() - (int) this.z;
            buf.writeByte(dx);
            buf.writeByte(dy);
            buf.writeByte(dz);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleClientSide(final MsgExplosion msg, final Supplier<NetworkEvent.Context> ctx) {
        final World world = Minecraft.getInstance().world;
        final AdvancedExplosion expl = new AdvancedExplosion(world, null, this.x, this.y, this.z, this.size,
                false, true);
        expl.setAffectedBlockPositions(this.blocks);
        expl.doParticleExplosion(this.smallParticles, this.bigParticles);
    }

    @Override
    public void handleServerSide(final MsgExplosion msg, final Supplier<NetworkEvent.Context> ctx) {
    }
}
