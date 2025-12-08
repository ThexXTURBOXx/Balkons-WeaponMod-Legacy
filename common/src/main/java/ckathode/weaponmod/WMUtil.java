package ckathode.weaponmod;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WMUtil {

    public enum EffectiveSide {
        CLIENT, SERVER;

        @ExpectPlatform
        public static EffectiveSide get() {
            throw new AssertionError();
        }
    }

    public static class Client {

        public static Player getLocalPlayer() {
            return Minecraft.getInstance().player;
        }

        public static Level getLocalLevel() {
            return Minecraft.getInstance().level;
        }

    }

}
