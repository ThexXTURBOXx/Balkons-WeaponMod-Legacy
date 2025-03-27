package ckathode.weaponmod.network;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.EntityCannon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public record MsgCannonFire(int cannonEntityID) implements CustomPacketPayload {

    public static final Type<MsgCannonFire> CANNON_FIRE_PACKET_TYPE = new Type<>(BalkonsWeaponMod.id("cannon_fire"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MsgCannonFire> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MsgCannonFire::cannonEntityID,
            MsgCannonFire::new);

    public MsgCannonFire(EntityCannon entity) {
        this(entity.getId());
    }

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return CANNON_FIRE_PACKET_TYPE;
    }

    public static void handleServerSide(MsgCannonFire msg, NetworkManager.PacketContext ctx) {
        Entity entity = ctx.getPlayer().level().getEntity(msg.cannonEntityID);
        if (entity instanceof EntityCannon) {
            ((EntityCannon) entity).fireCannon();
        }
    }

}
