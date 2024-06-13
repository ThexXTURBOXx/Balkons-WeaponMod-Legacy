package ckathode.weaponmod.network;

import ckathode.weaponmod.entity.EntityCannon;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class MsgCannonFire implements CustomPacketPayload {

    public static final Type<MsgCannonFire> CANNON_FIRE_PACKET_TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(MOD_ID, "cannon_fire"));
    public static final StreamCodec<RegistryFriendlyByteBuf, MsgCannonFire> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, p -> p.cannonEntityID,
            MsgCannonFire::new);

    private final int cannonEntityID;

    public MsgCannonFire(EntityCannon entity) {
        this(entity.getId());
    }

    public MsgCannonFire(int id) {
        cannonEntityID = id;
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
