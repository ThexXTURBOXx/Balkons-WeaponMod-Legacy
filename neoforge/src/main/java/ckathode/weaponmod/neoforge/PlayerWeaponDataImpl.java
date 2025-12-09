package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PlayerWeaponDataImpl {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, BalkonsWeaponMod.MOD_ID);

    private static final Supplier<AttachmentType<Integer>> WARHAMMER_SMASH_TICKS = ATTACHMENT_TYPES.register(
            "warhammer_last_smash_ticks", () -> AttachmentType.builder(() -> 0)
                    .sync((holder, to) -> holder == to, ByteBufCodecs.INT)
                    .build()
    );

    private static final Supplier<AttachmentType<Boolean>> FLAIL_THROWN = ATTACHMENT_TYPES.register(
            "flail_thrown", () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL.fieldOf("flail_thrown"))
                    .sync(ByteBufCodecs.BOOL)
                    .build()
    );

    private static final Supplier<AttachmentType<Integer>> FLAIL_ENTITY_ID = ATTACHMENT_TYPES.register(
            "flail_entity_id", () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT.fieldOf("flail_entity_id"))
                    .sync(ByteBufCodecs.INT)
                    .build()
    );

    public static int getLastWarhammerSmashTicks(Player player) {
        return player.getData(WARHAMMER_SMASH_TICKS);
    }

    public static void setLastWarhammerSmashTicks(Player player, int age) {
        player.setData(WARHAMMER_SMASH_TICKS, age);
    }

    public static boolean isFlailThrown(Player player) {
        return player.getData(FLAIL_THROWN);
    }

    public static void setFlailThrown(Player player, boolean flag) {
        player.setData(FLAIL_THROWN, flag);
    }

    public static int getFlailEntityId(Player player) {
        return player.getData(FLAIL_ENTITY_ID);
    }

    public static void setFlailEntityId(Player player, int id) {
        player.setData(FLAIL_ENTITY_ID, id);
    }

    public static void init(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
    }

}
