package ckathode.weaponmod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WMUtil {

    public static class Client {

        public static PlayerEntity getLocalPlayer() {
            return Minecraft.getInstance().player;
        }

        public static World getLocalWorld() {
            return Minecraft.getInstance().world;
        }

    }

}
