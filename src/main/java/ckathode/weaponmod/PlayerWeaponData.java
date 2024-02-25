package ckathode.weaponmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public abstract class PlayerWeaponData {
    private static final DataParameter<Integer> BOOLEANS;
    private static final DataParameter<Integer> WARHAMMER_LAST_SMASH_TICKS;
    private static final DataParameter<Integer> FLAIL_ENTITY_ID;
    public static final int WARHAMMER_CHARGED = 1;
    public static final int FLAIL_THROWN = 2;

    public static void initPlayerWeaponData(final EntityPlayer player) {
        final String playername = getPlayerName(player);
        BalkonsWeaponMod.modLog.trace("Initializing DataManager values for " + playername);
        final EntityDataManager dataManager = player.getDataManager();
        try {
            dataManager.get(PlayerWeaponData.BOOLEANS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + PlayerWeaponData.BOOLEANS.getId());
        } catch (final NullPointerException ignored) {
        } finally {
            dataManager.register(PlayerWeaponData.BOOLEANS, 0);
        }
        try {
            dataManager.get(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
        } catch (final NullPointerException ignored) {
        } finally {
            dataManager.register(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS, player.ticksExisted);
        }
        try {
            dataManager.get(PlayerWeaponData.FLAIL_ENTITY_ID);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + PlayerWeaponData.FLAIL_ENTITY_ID.getId());
        } catch (final NullPointerException ignored) {
        } finally {
            dataManager.register(PlayerWeaponData.FLAIL_ENTITY_ID, 0);
        }
    }

    private static String getPlayerName(final EntityPlayer player) {
        String playername;
        if (player.getGameProfile() != null) {
            playername = player.getGameProfile().getName();
        } else {
            playername = "[unknown]";
        }
        return "player:" + playername;
    }

    private static void unavailableError(final EntityPlayer player, final int id) {
        BalkonsWeaponMod.modLog.error("DataManager ID " + id + " for " + getPlayerName(player) + " unavailable, "
                                      + "trying to reinitialize");
        initPlayerWeaponData(player);
    }

    public static void setFlailEntityId(final EntityPlayer player, final int id) {
        try {
            player.getDataManager().set(PlayerWeaponData.FLAIL_ENTITY_ID, id);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.FLAIL_ENTITY_ID.getId());
        }
    }

    public static int getFlailEntityId(final EntityPlayer player) {
        try {
            return player.getDataManager().get(PlayerWeaponData.FLAIL_ENTITY_ID);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.FLAIL_ENTITY_ID.getId());
            return 0;
        }
    }

    public static int getLastWarhammerSmashTicks(final EntityPlayer player) {
        try {
            return player.getDataManager().get(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(final EntityPlayer player, final int age) {
        try {
            player.getDataManager().set(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS, age);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
        }
    }

    public static void setFlailThrown(final EntityPlayer player, final boolean flag) {
        putBoolean(player, FLAIL_THROWN, flag);
    }

    public static boolean isFlailThrown(final EntityPlayer player) {
        return getBoolean(player.getDataManager().get(PlayerWeaponData.BOOLEANS), FLAIL_THROWN);
    }

    public static void putBoolean(final EntityPlayer player, final int state, final boolean flag) {
        try {
            int i = player.getDataManager().get(PlayerWeaponData.BOOLEANS);
            i = putBoolean(i, state, flag);
            player.getDataManager().set(PlayerWeaponData.BOOLEANS, i);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.BOOLEANS.getId());
        }
    }

    public static boolean getBoolean(final EntityPlayer player, final int state) {
        try {
            return getBoolean(player.getDataManager().get(PlayerWeaponData.BOOLEANS), state);
        } catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.BOOLEANS.getId());
            return false;
        }
    }

    private static boolean getBoolean(final int i, final int pos) {
        return (i & 1 << pos) != 0x0;
    }

    private static int putBoolean(int i, final int pos, final boolean flag) {
        final int mask = 1 << pos;
        i &= ~mask;
        if (flag) {
            i |= mask;
        }
        return i;
    }

    static {
        BOOLEANS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
        WARHAMMER_LAST_SMASH_TICKS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
        FLAIL_ENTITY_ID = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
    }
}
