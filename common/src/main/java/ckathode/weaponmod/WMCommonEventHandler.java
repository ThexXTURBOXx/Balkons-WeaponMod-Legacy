package ckathode.weaponmod;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity) {
        if (entity instanceof Player) {
            PlayerWeaponData.initPlayerWeaponData((Player) entity);
        }
    }

    public static void init() {
    }

}
