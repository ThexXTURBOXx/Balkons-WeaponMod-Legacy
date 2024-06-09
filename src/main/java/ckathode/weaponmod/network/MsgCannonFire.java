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
        // Needed for WMMessagePipeline instantiation
    }

    public MsgCannonFire(EntityCannon entity) {
        cannonEntityID = entity.getEntityId();
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
        cannonEntityID = buf.readInt();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buf) {
        buf.writeInt(cannonEntityID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleClientSide(EntityPlayer player) {
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        Entity entity = player.world.getEntityByID(cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }
}
