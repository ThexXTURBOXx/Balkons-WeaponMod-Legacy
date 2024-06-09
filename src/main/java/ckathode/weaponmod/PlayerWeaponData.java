package ckathode.weaponmod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

public final class PlayerWeaponData {
    private static final DataParameter<Integer> WARHAMMER_LAST_SMASH_TICKS =
            EntityDataManager.createKey(PlayerEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> FLAIL_THROWN = EntityDataManager.createKey(PlayerEntity.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> FLAIL_ENTITY_ID = EntityDataManager.createKey(PlayerEntity.class,
            DataSerializers.VARINT);

    public static void initPlayerWeaponData(PlayerEntity player) {
        String playername = getPlayerName(player);
        BalkonsWeaponMod.modLog.trace("Initializing DataManager values for {}", playername);
        EntityDataManager dataManager = player.getDataManager();
        try {
            dataManager.get(WARHAMMER_LAST_SMASH_TICKS);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, WARHAMMER_LAST_SMASH_TICKS.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(WARHAMMER_LAST_SMASH_TICKS, player.ticksExisted);
        }
        try {
            dataManager.get(FLAIL_THROWN);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_THROWN.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(FLAIL_THROWN, false);
        }
        try {
            dataManager.get(FLAIL_ENTITY_ID);
            BalkonsWeaponMod.modLog.warn("DataManager ID conflict for {} @ {}",
                    playername, FLAIL_ENTITY_ID.getId());
        } catch (NullPointerException ignored) {
        } finally {
            dataManager.register(FLAIL_ENTITY_ID, 0);
        }
    }

    private static String getPlayerName(PlayerEntity player) {
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

    private static void unavailableError(PlayerEntity player, int id) {
        BalkonsWeaponMod.modLog.error("DataManager ID {} for {} unavailable, trying to reinitialize",
                id, getPlayerName(player));
        initPlayerWeaponData(player);
    }

    public static int getLastWarhammerSmashTicks(PlayerEntity player) {
        try {
            return player.getDataManager().get(WARHAMMER_LAST_SMASH_TICKS);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.getId());
            return 0;
        }
    }

    public static void setLastWarhammerSmashTicks(PlayerEntity player, int age) {
        try {
            player.getDataManager().set(WARHAMMER_LAST_SMASH_TICKS, age);
        } catch (NullPointerException e) {
            unavailableError(player, WARHAMMER_LAST_SMASH_TICKS.getId());
        }
    }

    public static boolean isFlailThrown(PlayerEntity player) {
        try {
            return player.getDataManager().get(FLAIL_THROWN);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN.getId());
            return false;
        }
    }

    public static void setFlailThrown(PlayerEntity player, boolean flag) {
        try {
            player.getDataManager().set(FLAIL_THROWN, flag);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_THROWN.getId());
        }
    }

    public static int getFlailEntityId(PlayerEntity player) {
        try {
            return player.getDataManager().get(FLAIL_ENTITY_ID);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.getId());
            return 0;
        }
    }

    public static void setFlailEntityId(PlayerEntity player, int id) {
        try {
            player.getDataManager().set(FLAIL_ENTITY_ID, id);
        } catch (NullPointerException e) {
            unavailableError(player, FLAIL_ENTITY_ID.getId());
        }
    }

}
