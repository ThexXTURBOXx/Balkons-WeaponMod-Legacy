package ckathode.weaponmod;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity, SynchedEntityData.Builder builder) {
        if (entity instanceof Player) {
            PlayerWeaponData.initPlayerWeaponData((Player) entity, builder);
        }
    }

    public static void init() {
    }

}
