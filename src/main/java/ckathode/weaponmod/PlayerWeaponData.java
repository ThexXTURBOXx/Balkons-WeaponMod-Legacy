package ckathode.weaponmod;

import net.minecraft.entity.player.*;
import net.minecraft.network.datasync.*;

public abstract class PlayerWeaponData
{
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
        }
        catch (final NullPointerException ex) {}
        finally {
            dataManager.register(PlayerWeaponData.BOOLEANS, 0);
        }
        try {
            dataManager.get(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
        }
        catch (final NullPointerException ex2) {}
        finally {
            dataManager.register(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS, player.ticksExisted);
        }
        try {
            dataManager.get(PlayerWeaponData.FLAIL_ENTITY_ID);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for " + playername + " @ " + PlayerWeaponData.FLAIL_ENTITY_ID.getId());
        }
        catch (final NullPointerException ex3) {}
        finally {
            dataManager.register(PlayerWeaponData.FLAIL_ENTITY_ID, 0);
        }
    }

    private static String getPlayerName(final EntityPlayer player) {
        String playername;
        if (player.getGameProfile() != null) {
            playername = player.getName();
        }
        else {
            playername = "[unknown]";
        }
        playername = "player:" + playername;
        return playername;
    }

    private static void unavailableError(final EntityPlayer player, final int id) {
        BalkonsWeaponMod.modLog.error("DataManager ID " + id + " for " + getPlayerName(player) + " unavailable, trying to reinitialize");
        initPlayerWeaponData(player);
    }

    public static void setFlailEntityId(final EntityPlayer player, final int id) {
        try {
            player.getDataManager().set(PlayerWeaponData.FLAIL_ENTITY_ID, id);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.FLAIL_ENTITY_ID.getId());
        }
    }

    public static int getFlailEntityId(final EntityPlayer player) {
        try {
            return player.getDataManager().get(PlayerWeaponData.FLAIL_ENTITY_ID);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.FLAIL_ENTITY_ID.getId());
            return 0;
        }
    }

    public static int getLastWarhammerSmashTicks(final EntityPlayer player) {
        try {
            return player.getDataManager().get(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(final EntityPlayer player, final int age) {
        try {
            player.getDataManager().set(PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS, age);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.WARHAMMER_LAST_SMASH_TICKS.getId());
        }
    }

    public static void setFlailThrown(final EntityPlayer player, final boolean flag) {
        setBoolean(player, 2, flag);
    }

    public static boolean isFlailThrown(final EntityPlayer player) {
        return getBoolean(player.getDataManager().get(PlayerWeaponData.BOOLEANS), 2);
    }

    public static void setBoolean(final EntityPlayer player, final int state, final boolean flag) {
        try {
            int i = player.getDataManager().get(PlayerWeaponData.BOOLEANS);
            i = setBoolean(i, state, flag);
            player.getDataManager().set(PlayerWeaponData.BOOLEANS, i);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.BOOLEANS.getId());
        }
    }

    public static boolean getBoolean(final EntityPlayer player, final int state) {
        try {
            return getBoolean(player.getDataManager().get(PlayerWeaponData.BOOLEANS), state);
        }
        catch (final NullPointerException e) {
            unavailableError(player, PlayerWeaponData.BOOLEANS.getId());
            return false;
        }
    }

    private static boolean getBoolean(final int i, final int pos) {
        return (i & 1 << pos) != 0x0;
    }

    private static int setBoolean(int i, final int pos, final boolean flag) {
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
