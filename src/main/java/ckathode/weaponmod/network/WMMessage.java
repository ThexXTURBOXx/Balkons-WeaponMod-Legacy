package ckathode.weaponmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class WMMessage {
    public abstract void encodeInto(final ChannelHandlerContext p0, final ByteBuf p1);

    public abstract void decodeInto(final ChannelHandlerContext p0, final ByteBuf p1);

    @SideOnly(Side.CLIENT)
    public abstract void handleClientSide(final EntityPlayer p0);

    public abstract void handleServerSide(final EntityPlayer p0);
}
