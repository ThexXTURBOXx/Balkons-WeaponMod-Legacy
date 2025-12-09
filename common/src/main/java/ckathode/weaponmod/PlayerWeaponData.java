package ckathode.weaponmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

public final class PlayerWeaponData {

    @ExpectPlatform
    public static int getLastWarhammerSmashTicks(Player player) {
        // Will get replaced at run time
        return 0;
    }

    @ExpectPlatform
    public static void setLastWarhammerSmashTicks(Player player, int age) {
        // Will get replaced at run time
    }

    @ExpectPlatform
    public static boolean isFlailThrown(Player player) {
        // Will get replaced at run time
        return false;
    }

    @ExpectPlatform
    public static void setFlailThrown(Player player, boolean flag) {
        // Will get replaced at run time
    }

    @ExpectPlatform
    public static int getFlailEntityId(Player player) {
        // Will get replaced at run time
        return 0;
    }

    @ExpectPlatform
    public static void setFlailEntityId(Player player, int id) {
        // Will get replaced at run time
    }

}
