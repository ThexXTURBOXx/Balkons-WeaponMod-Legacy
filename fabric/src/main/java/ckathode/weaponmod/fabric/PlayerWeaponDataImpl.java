package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;

public class PlayerWeaponDataImpl {

    private static AttachmentType<Integer> WARHAMMER_SMASH_TICKS;
    private static AttachmentType<Boolean> FLAIL_THROWN;
    private static AttachmentType<Integer> FLAIL_ENTITY_ID;

    public static int getLastWarhammerSmashTicks(Player player) {
        return player.getAttachedOrCreate(WARHAMMER_SMASH_TICKS);
    }

    public static void setLastWarhammerSmashTicks(Player player, int age) {
        player.setAttached(WARHAMMER_SMASH_TICKS, age);
    }

    public static boolean isFlailThrown(Player player) {
        return player.getAttachedOrCreate(FLAIL_THROWN);
    }

    public static void setFlailThrown(Player player, boolean flag) {
        player.setAttached(FLAIL_THROWN, flag);
    }

    public static int getFlailEntityId(Player player) {
        return player.getAttachedOrCreate(FLAIL_ENTITY_ID);
    }

    public static void setFlailEntityId(Player player, int id) {
        player.setAttached(FLAIL_ENTITY_ID, id);
    }

    public static void init() {
        WARHAMMER_SMASH_TICKS = AttachmentRegistry.create(BalkonsWeaponMod.id("warhammer_last_smash_ticks"), b ->
                b.initializer(() -> 0)
                        .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.targetOnly()));

        FLAIL_THROWN = AttachmentRegistry.create(BalkonsWeaponMod.id("flail_thrown"), b ->
                b.initializer(() -> false)
                        .persistent(Codec.BOOL)
                        .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()));

        FLAIL_ENTITY_ID = AttachmentRegistry.create(BalkonsWeaponMod.id("flail_entity_id"), b ->
                b.initializer(() -> 0)
                        .persistent(Codec.INT)
                        .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all()));
    }

}
