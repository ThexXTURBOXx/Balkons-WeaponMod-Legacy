package ckathode.weaponmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public abstract class PlayerWeaponData {
    private static final DataParameter<Integer> BOOLEANS = EntityDataManager.createKey(EntityPlayer.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> WARHAMMER_LAST_SMASH_TICKS =
            EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FLAIL_ENTITY_ID = EntityDataManager.createKey(EntityPlayer.class,
            DataSerializers.VARINT);
    public static final int WARHAMMER_CHARGED = 1;
    public static final int FLAIL_THROWN = 2;

    public static void initPlayerWeaponData(EntityPlayer player) {
        String playername = getPlayerName(player);
        BalkonsWeaponMod.modLog.trace("Initializing DataManager values for " + playername);
        EntityDataManager dataManager = player.getDataManager();
        try {
            dataManager.get(BOOLEANS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + BOOLEANS.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(BOOLEANS, 0);
        }
        try {
            dataManager.get(WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + WARHAMMER_LAST_SMASH_TICKS.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(WARHAMMER_LAST_SMASH_TICKS, player.ticksExisted);
        }
        try {
            dataManager.get(FLAIL_ENTITY_ID);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + FLAIL_ENTITY_ID.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(FLAIL_ENTITY_ID, 0);
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
        BalkonsWeaponMod.modLog.error("DataManager ID " + id + " for " + getPlayerName(player) + " unavailable, " +
                                      "trying to reinitialize");
        initPlayerWeaponData(player);
    }

    public static void setFlailEntityId(EntityPlayer player, int id) {
        try {
            player.getDataManager().set(FLAIL_ENTITY_ID, id);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.getId());
        }
    }

    public static int getFlailEntityId(EntityPlayer player) {
        try {
            return player.getDataManager().get(FLAIL_ENTITY_ID);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.getId());
            return 0;
        }
    }

    public static int getLastWarhammerSmashTicks(EntityPlayer player) {
        try {
            return player.getDataManager().get(WARHAMMER_LAST_SMASH_TICKS);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.getId());
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(EntityPlayer player, int age) {
        try {
            player.getDataManager().set(WARHAMMER_LAST_SMASH_TICKS, age);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.getId());
        }
    }

    public static void setFlailThrown(EntityPlayer player, boolean flag) {
        putBoolean(player, FLAIL_THROWN, flag);
    }

    public static boolean isFlailThrown(EntityPlayer player) {
        return getBoolean(player.getDataManager().get(BOOLEANS), FLAIL_THROWN);
    }

    public static void putBoolean(EntityPlayer player, int state, boolean flag) {
        try {
            int i = player.getDataManager().get(BOOLEANS);
            i = putBoolean(i, state, flag);
            player.getDataManager().set(BOOLEANS, i);
        } catch (NullPointerException e) {
            unavailableError(player, BOOLEANS.getId());
        }
    }

    public static boolean getBoolean(EntityPlayer player, int state) {
        try {
            return getBoolean(player.getDataManager().get(BOOLEANS), state);
        } catch (NullPointerException e) {
            unavailableError(player, BOOLEANS.getId());
            return false;
        }
    }

    private static boolean getBoolean(int i, int pos) {
        return (i & 1 << pos) != 0x0;
    }

    private static int putBoolean(int i, int pos, boolean flag) {
        int mask = 1 << pos;
        i &= ~mask;
        if (flag) {
            i |= mask;
        }
        return i;
    }

}
