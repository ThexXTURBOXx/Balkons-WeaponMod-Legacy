package ckathode.weaponmod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class WMUtil {

    public static class Client {

        public static EntityPlayer getLocalPlayer() {
            return Minecraft.getMinecraft().player;
        }

        public static World getLocalWorld() {
            return Minecraft.getMinecraft().world;
        }

    }

}
