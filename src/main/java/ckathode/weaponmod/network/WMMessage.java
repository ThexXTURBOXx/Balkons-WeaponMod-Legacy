package ckathode.weaponmod.network;

import io.netty.channel.*;
import io.netty.buffer.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.relauncher.*;

public abstract class WMMessage
{
    public abstract void encodeInto(final ChannelHandlerContext p0, final ByteBuf p1);
    
    public abstract void decodeInto(final ChannelHandlerContext p0, final ByteBuf p1);
    
    @SideOnly(Side.CLIENT)
    public abstract void handleClientSide(final EntityPlayer p0);
    
    public abstract void handleServerSide(final EntityPlayer p0);
}
