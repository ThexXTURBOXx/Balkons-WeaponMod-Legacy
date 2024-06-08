package ckathode.weaponmod;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public final class PlayerWeaponData {

    private static final EntityDataAccessor<Integer> WARHAMMER_LAST_SMASH_TICKS =
            SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FLAIL_THROWN = SynchedEntityData.defineId(Player.class,
            EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> FLAIL_ENTITY_ID = SynchedEntityData.defineId(Player.class,
            EntityDataSerializers.INT);

    public static void initPlayerWeaponData(Player player, SynchedEntityData.Builder builder) {
        String playername = getPlayerName(player);
        BalkonsWeaponMod.LOGGER.trace("Initializing DataManager values for {}", playername);
        SynchedEntityData dataManager = player.getEntityData();
        try {
            dataManager.get(WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.LOGGER.warn("DataManager ID conflict for {} @ {}",
                    playername, WARHAMMER_LAST_SMASH_TICKS.id());
        } catch (Throwable ignored) {
            if (builder == null)
                dataManager.set(WARHAMMER_LAST_SMASH_TICKS, player.tickCount, true);
            else
                builder.define(WARHAMMER_LAST_SMASH_TICKS, player.tickCount);
        }
        try {
            dataManager.get(FLAIL_THROWN);
            BalkonsWeaponMod.LOGGER.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_THROWN.id());
        } catch (Throwable ignored) {
            if (builder == null)
                dataManager.set(FLAIL_THROWN, false, true);
            else
                builder.define(FLAIL_THROWN, false);
        }
        try {
            dataManager.get(FLAIL_ENTITY_ID);
            BalkonsWeaponMod.LOGGER.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_ENTITY_ID.id());
        } catch (Throwable ignored) {
            if (builder == null)
                dataManager.set(FLAIL_ENTITY_ID, 0, true);
            else
                builder.define(FLAIL_ENTITY_ID, 0);
        }
    }

    private static String getPlayerName(Player player) {
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

    private static void unavailableError(Player player, int id) {
        BalkonsWeaponMod.LOGGER.error("DataManager ID {} for {} unavailable, trying to reinitialize",
                id, getPlayerName(player));
        initPlayerWeaponData(player, null);
    }

    public static int getLastWarhammerSmashTicks(Player player) {
        try {
            return player.getEntityData().get(WARHAMMER_LAST_SMASH_TICKS);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.id());
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(Player player, int age) {
        try {
            player.getEntityData().set(WARHAMMER_LAST_SMASH_TICKS, age);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.id());
        }
    }

    public static boolean isFlailThrown(Player player) {
        try {
            return player.getEntityData().get(FLAIL_THROWN);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN.id());
            return false;
        }
    }

    public static void setFlailThrown(Player player, boolean flag) {
        try {
            player.getEntityData().set(FLAIL_THROWN, flag);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN.id());
        }
    }

    public static int getFlailEntityId(Player player) {
        try {
            return player.getEntityData().get(FLAIL_ENTITY_ID);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.id());
            return 0;
        }
    }

    public static void setFlailEntityId(Player player, int id) {
        try {
            player.getEntityData().set(FLAIL_ENTITY_ID, id);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.id());
        }
    }

    public static void init() {
        // Handled in static constructor
    }

}
