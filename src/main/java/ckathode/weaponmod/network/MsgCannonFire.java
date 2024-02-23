package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MsgCannonFire extends WMMessage {
    private int cannonEntityID;

    public MsgCannonFire() {
        this.cannonEntityID = 0;
    }

    public MsgCannonFire(final EntityCannon entity) {
        this.cannonEntityID = 0;
        this.cannonEntityID = entity.getEntityId();
    }

    @Override
    public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf buf) {
        this.cannonEntityID = buf.readInt();
    }

    @Override
    public void encodeInto(final ChannelHandlerContext ctx, final ByteBuf buf) {
        buf.writeInt(this.cannonEntityID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(final EntityPlayer player) {
    }

    @Override
    public void handleServerSide(final EntityPlayer player) {
        final Entity entity = player.world.getEntityByID(this.cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }
}
