package ckathode.weaponmod.network;

import ckathode.weaponmod.BalkonsWeaponMod;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ChannelHandler.Sharable
public class WMMessagePipeline extends MessageToMessageCodec<FMLProxyPacket, WMMessage> {
    private EnumMap<Side, FMLEmbeddedChannel> channels;
    private final LinkedList<Class<? extends WMMessage>> packets;
    private boolean isPostInitialized;

    public WMMessagePipeline() {
        packets = new LinkedList<>();
        isPostInitialized = false;
    }

    public boolean registerPacket(final Class<? extends WMMessage> class0) {
        if (packets.size() > 256) {
            BalkonsWeaponMod.modLog.error("More than 256 packets registered");
            return false;
        }
        if (packets.contains(class0)) {
            BalkonsWeaponMod.modLog.warn("Packet already registered");
            return false;
        }
        if (isPostInitialized) {
            BalkonsWeaponMod.modLog.error("Already post-initialized");
            return false;
        }
        packets.add(class0);
        return true;
    }

    @Override
    protected void encode(final ChannelHandlerContext ctx, final WMMessage msg, final List<Object> out) {
        final PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        if (!packets.contains(msg.getClass())) {
            throw new NullPointerException("No Packet Registered for: " + msg.getClass().getCanonicalName());
        }
        final byte discriminator = (byte) packets.indexOf(msg.getClass());
        buffer.writeByte(discriminator);
        msg.encodeInto(ctx, buffer);
        final FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer,
                ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    @Override
    protected void decode(final ChannelHandlerContext ctx, final FMLProxyPacket msg, final List<Object> out) throws Exception {
        final ByteBuf payload = msg.payload().duplicate();
        if (payload.readableBytes() < 1) {
            FMLLog.log.error("The FMLIndexedCodec has received an empty buffer on channel %s, likely a "
                             + "result of a LAN server issue. Pipeline parts : %s",
                    ctx.channel().attr(NetworkRegistry.FML_CHANNEL), ctx.pipeline().toString());
        }
        final byte discriminator = payload.readByte();
        final Class<? extends WMMessage> clazz = packets.get(discriminator);
        if (clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }
        final WMMessage pkt = clazz.getDeclaredConstructor().newInstance();
        pkt.decodeInto(ctx, payload.slice());
        switch (FMLCommonHandler.instance().getEffectiveSide()) {
        case CLIENT: {
            final EntityPlayer player = getClientPlayer();
            pkt.handleClientSide(player);
            break;
        }
        case SERVER: {
            final INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
            final EntityPlayer player = ((NetHandlerPlayServer) netHandler).player;
            pkt.handleServerSide(player);
            break;
        }
        }
        out.add(pkt);
    }

    public void initalize() {
        channels = NetworkRegistry.INSTANCE.newChannel("WeaponMod", this);
    }

    public void postInitialize() {
        if (isPostInitialized) {
            return;
        }
        isPostInitialized = true;
        packets.sort((clazz1, clazz2) -> {
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
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendTo(final WMMessage message, final EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToAllAround(final WMMessage message, final NetworkRegistry.TargetPoint point) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToDimension(final WMMessage message, final int dimensionId) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToServer(final WMMessage message) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message);
    }
}
