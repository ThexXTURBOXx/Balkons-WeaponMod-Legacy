package ckathode.weaponmod;

import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class WMUtil {

    public static final RandomSource RANDOM = RandomSource.createNewThreadLocalInstance();

    public static class Client {

        public static Player getLocalPlayer() {
            return Minecraft.getInstance().player;
        }

        public static Level getLocalLevel() {
            return Minecraft.getInstance().level;
        }

    }

}
