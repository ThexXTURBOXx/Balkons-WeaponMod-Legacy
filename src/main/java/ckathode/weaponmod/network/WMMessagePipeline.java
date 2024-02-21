package ckathode.weaponmod.network;

import io.netty.handler.codec.*;
import net.minecraftforge.fml.common.network.internal.*;
import ckathode.weaponmod.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraft.entity.player.*;

@ChannelHandler.Sharable
public class WMMessagePipeline extends MessageToMessageCodec<FMLProxyPacket, WMMessage>
{
    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends WMMessage>> packets;
    private boolean isPostInitialized;

    public WMMessagePipeline() {
        this.packets = new LinkedList<Class<? extends WMMessage>>();
        this.isPostInitialized = false;
    }

    public boolean registerPacket(final Class<? extends WMMessage> class0) {
        if (this.packets.size() > 256) {
            BalkonsWeaponMod.modLog.error("More than 256 packets registered");
            return false;
        }
        if (this.packets.contains(class0)) {
            BalkonsWeaponMod.modLog.warn("Packet already registered");
            return false;
        }
        if (this.isPostInitialized) {
            BalkonsWeaponMod.modLog.error("Already post-initialized");
            return false;
        }
        this.packets.add(class0);
        return true;
    }

    protected void encode(final ChannelHandlerContext ctx, final WMMessage msg, final List<Object> out) throws Exception {
        final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        if (!this.packets.contains(msg.getClass())) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }
        final byte discriminator = (byte)this.packets.indexOf(msg.getClass());
        buffer.writeByte(discriminator);
        msg.encodeInto(ctx, buffer);
        final FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer, ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    protected void decode(final ChannelHandlerContext ctx, final FMLProxyPacket msg, final List<Object> out) throws Exception {
        final ByteBuf payload = msg.payload().duplicate();
        if (payload.readableBytes() < 1) {
            FMLLog.log.log(Level.ERROR, "The FMLIndexedCodec has received an empty buffer on channel %s, likely a result of a LAN server issue. Pipeline parts : %s", new Object[] { ctx.channel().attr(NetworkRegistry.FML_CHANNEL), ctx.pipeline().toString() });
        }
        final byte discriminator = payload.readByte();
        final Class<? extends WMMessage> clazz = this.packets.get(discriminator);
        if (clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }
        final WMMessage pkt = clazz.newInstance();
        pkt.decodeInto(ctx, payload.slice());
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
            case CLIENT: {
                final EntityPlayer player = this.getClientPlayer();
                pkt.handleClientSide(player);
                break;
            }
            case SERVER: {
                final INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
                final EntityPlayer player = ((NetHandlerPlayServer)netHandler).player;
                pkt.handleServerSide(player);
                break;
            }
        }
        out.add(pkt);
    }

    public void initalize() {
        this.channels = NetworkRegistry.INSTANCE.newChannel("WeaponMod", new ChannelHandler[] {this});
    }

    public void postInitialize() {
        if (this.isPostInitialized) {
            return;
        }
        this.isPostInitialized = true;
        Collections.sort(this.packets, (clazz1, clazz2) -> {
            int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
            if (com == 0) {
                com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
            }
            return com;
        });
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

    public void sendToAll(final WMMessage message) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendTo(final WMMessage message, final EntityPlayerMP player) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToAllAround(final WMMessage message, final NetworkRegistry.TargetPoint point) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToDimension(final WMMessage message, final int dimensionId) {
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        this.channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToServer(final WMMessage message) {
        this.channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channels.get(Side.CLIENT).writeAndFlush(message);
    }
}
