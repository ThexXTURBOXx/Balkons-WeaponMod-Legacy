package ckathode.weaponmod;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;

public final class PlayerWeaponData {
    private static final int WARHAMMER_LAST_SMASH_TICKS =
            BalkonsWeaponMod.instance.modConfig.getDataWatcherId("warhammer_last_smash_ticks");
    private static final int FLAIL_THROWN =
            BalkonsWeaponMod.instance.modConfig.getDataWatcherId("flail_thrown");
    private static final int FLAIL_ENTITY_ID =
            BalkonsWeaponMod.instance.modConfig.getDataWatcherId("flail_entity_id");

    public static void initPlayerWeaponData(EntityPlayer player) {
        String playername = getPlayerName(player);
        BalkonsWeaponMod.modLog.trace("Initializing DataManager values for {}", playername);
        DataWatcher dataWatcher = player.getDataWatcher();
        try {
            dataWatcher.getWatchableObjectInt(WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, WARHAMMER_LAST_SMASH_TICKS);
        } catch (NullPointerException ignored) {
        } finally {
            dataWatcher.addObject(WARHAMMER_LAST_SMASH_TICKS, player.ticksExisted);
        }
        try {
            dataWatcher.getWatchableObjectInt(FLAIL_THROWN);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_THROWN);
        } catch (NullPointerException ignored) {
        } finally {
            dataWatcher.addObject(FLAIL_THROWN, 0);
        }
        try {
            dataWatcher.getWatchableObjectInt(FLAIL_ENTITY_ID);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_ENTITY_ID);
        } catch (NullPointerException ignored) {
        } finally {
            dataWatcher.addObject(FLAIL_ENTITY_ID, 0);
        }
    }

    private static String getPlayerName(EntityPlayer player) {
        // Needed since EntityConstructing event is fired too early in the
        // Entity constructor such that the GameProfile is not yet set
        String playername;
        if (player.getGameProfile() != null) {
            playername = player.getGameProfile().getName();
        } else {
            playername = "[unknown]";
        }
        return "player:" + playername;
    }

    private static void unavailableError(EntityPlayer player, int id) {
        BalkonsWeaponMod.modLog.error("DataManager ID {} for {} unavailable, trying to reinitialize",
                id, getPlayerName(player));
        initPlayerWeaponData(player);
    }

    public static int getLastWarhammerSmashTicks(EntityPlayer player) {
        try {
            return player.getDataWatcher().getWatchableObjectInt(WARHAMMER_LAST_SMASH_TICKS);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS);
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(EntityPlayer player, int age) {
        try {
            player.getDataWatcher().updateObject(WARHAMMER_LAST_SMASH_TICKS, age);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS);
        }
    }

    public static boolean isFlailThrown(EntityPlayer player) {
        try {
            return player.getDataWatcher().getWatchableObjectInt(FLAIL_THROWN) == 1;
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN);
            return false;
        }
    }

    public static void setFlailThrown(EntityPlayer player, boolean flag) {
        try {
            player.getDataWatcher().updateObject(FLAIL_THROWN, flag ? 1 : 0);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN);
        }
    }

    public static int getFlailEntityId(EntityPlayer player) {
        try {
            return player.getDataWatcher().getWatchableObjectInt(FLAIL_ENTITY_ID);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID);
            return 0;
        }
    }

    public static void setFlailEntityId(EntityPlayer player, int id) {
        try {
            player.getDataWatcher().updateObject(FLAIL_ENTITY_ID, id);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID);
        }
    }

}
