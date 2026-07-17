package ckathode.weaponmod;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class WeaponModConfig {

    private static final String CATEGORY_SETTINGS = "settings";
    private static final String CATEGORY_ENABLE = "enable";
    private static final String CATEGORY_RELOADTIME = "reloadtime";
    private static final String CATEGORY_DATAWATCHER = "datawatcher";

    private static WeaponModConfig instance;

    private final Configuration config;

    public boolean cannonDoesBlockDamage;
    public boolean dynamiteDoesBlockDamage;
    public boolean mortarDoesBlockDamage;
    public boolean canThrowKnife;
    public boolean canThrowSpear;
    public boolean allCanPickup;
    public boolean guiOverlayReloaded;
    public boolean itemModelForEntity;
    public boolean legacyCannonModel;
    public boolean enableLootTables;
    private final Map<String, EnableSetting> enableSettings;
    private final Map<String, ReloadTimeSetting> reloadTimeSettings;
    private final Map<String, DataWatcherIdSetting> dataWatcherIds;

    public WeaponModConfig(final Configuration configuration) {
        instance = this;
        config = configuration;
        enableSettings = new LinkedHashMap<>();
        reloadTimeSettings = new LinkedHashMap<>();
        dataWatcherIds = new LinkedHashMap<>();
    }

    public void addEnableSetting(final String weapon) {
        enableSettings.put(weapon, new EnableSetting(weapon, true));
    }

    public void addReloadTimeSetting(final String weapon, final int defaulttime) {
        reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
    }

    public void addDataWatcherIdSetting(final String desc, final int defaultId) {
        dataWatcherIds.put(desc, new DataWatcherIdSetting(desc, defaultId));
    }

    public boolean isEnabled(final String weapon) {
        final EnableSetting es = enableSettings.get(weapon);
        return es == null || es.enabled;
    }

    public int getReloadTime(final String weapon) {
        final ReloadTimeSetting rs = reloadTimeSettings.get(weapon);
        return (rs == null) ? 0 : rs.reloadTime;
    }

    public int getDataWatcherId(final String desc) {
        final DataWatcherIdSetting ds = dataWatcherIds.get(desc);
        return (ds == null) ? 0 : ds.id;
    }

    public void init() {
        syncConfig(true);
        FMLCommonHandler.instance().bus().register(this);
    }

    public void syncConfig(boolean load) {
        if (load) config.load();

        config.addCustomCategoryComment(CATEGORY_SETTINGS,
                "Miscellaneous mod settings");
        config.addCustomCategoryComment(CATEGORY_ENABLE,
                "Enable or disable certain weapons " +
                "(only disables their recipes; they are still obtainable " +
                "through Creative mode!)");
        config.addCustomCategoryComment(CATEGORY_RELOADTIME,
                "The reload durations of the reloadable weapons");
        config.addCustomCategoryComment(CATEGORY_DATAWATCHER,
                "Data Watcher IDs - if your game crashes because there " +
                "is a conflict with another mod's Data Watcher " +
                "entries, you can fix it here by changing the ID(s)!");

        config.setCategoryRequiresMcRestart(CATEGORY_ENABLE, true);
        config.setCategoryRequiresWorldRestart(CATEGORY_ENABLE, true);
        config.setCategoryRequiresMcRestart(CATEGORY_RELOADTIME, true);
        config.setCategoryRequiresWorldRestart(CATEGORY_RELOADTIME, true);
        config.setCategoryRequiresMcRestart(CATEGORY_DATAWATCHER, true);
        config.setCategoryRequiresWorldRestart(CATEGORY_DATAWATCHER, true);

        cannonDoesBlockDamage = config.get(CATEGORY_SETTINGS, "cannon-block-damage", true).getBoolean(true);
        dynamiteDoesBlockDamage = config.get(CATEGORY_SETTINGS, "dynamite-block-damage", true).getBoolean(true);
        mortarDoesBlockDamage = config.get(CATEGORY_SETTINGS, "mortar-block-damage", true).getBoolean(true);
        canThrowKnife = config.get(CATEGORY_SETTINGS, "can-throw-knife", true).getBoolean(true);
        canThrowSpear = config.get(CATEGORY_SETTINGS, "can-throw-spear", true).getBoolean(true);
        allCanPickup = config.get(CATEGORY_SETTINGS, "pickup-all", true,
                "Change this to 'false' to allow only the thrower/shooter of the projectile to " +
                "pick the item up. If set to 'true' everyone can pick the item up.").getBoolean(true);
        guiOverlayReloaded = config.get(CATEGORY_SETTINGS, "reload-progress", true,
                "Show reload progress in hotbar.").getBoolean(true);
        itemModelForEntity = config.get(CATEGORY_SETTINGS, "render-entity-model", true,
                "Item model for entity (knife, spear, etc).").getBoolean(true);
        legacyCannonModel = config.get(CATEGORY_SETTINGS, "legacy-cannon-model", false,
                "Changes the cannon to the legacy model from older versions of BWM!").getBoolean(true);
        enableLootTables = config.get(CATEGORY_SETTINGS, "enable-loot-tables", true,
                "Weapons can sometimes be found in loot chests.").getBoolean(true);

        for (final EnableSetting es : enableSettings.values()) {
            es.enabled = config.get(CATEGORY_ENABLE, es.settingName, es.enabled).getBoolean(es.enabled);
        }

        for (final ReloadTimeSetting rs : reloadTimeSettings.values()) {
            rs.reloadTime = config.get(CATEGORY_RELOADTIME, rs.settingName, rs.reloadTime).getInt(rs.reloadTime);
        }

        for (final DataWatcherIdSetting ds : dataWatcherIds.values()) {
            ds.id = config.get(CATEGORY_DATAWATCHER, ds.settingName, ds.id).getInt(ds.id);
        }

        if (config.hasChanged()) config.save();
    }

    private abstract static class Setting {
        final String settingName;

        Setting(final String name) {
            settingName = name;
        }
    }

    private static class ReloadTimeSetting extends Setting {
        int reloadTime;

        ReloadTimeSetting(final String name, final int time) {
            super(name + ".reloadtime");
            reloadTime = time;
        }
    }

    private static class EnableSetting extends Setting {
        boolean enabled;

        EnableSetting(final String name, boolean enabled) {
            super(name + ".enabled");
            this.enabled = enabled;
        }
    }

    private static class DataWatcherIdSetting extends Setting {
        int id;

        DataWatcherIdSetting(final String desc, int id) {
            super(desc);
            this.id = id;
        }
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (BalkonsWeaponMod.MOD_ID.equals(e.modID)) {
            syncConfig(false);
        }
    }

    public static class GuiFactory implements IModGuiFactory {

        @Override
        public void initialize(Minecraft minecraft) {
        }

        @Override
        public Class<? extends GuiScreen> mainConfigGuiClass() {
            return WMGUIConfig.class;
        }

        @Override
        public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
            return null;
        }

        @Override
        public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement runtimeOptionCategoryElement) {
            return null;
        }

    }

    public static class WMGUIConfig extends GuiConfig {

        public WMGUIConfig(GuiScreen parentScreen) {
            this(instance.config, parentScreen);
        }

        public WMGUIConfig(Configuration config, GuiScreen parentScreen) {
            super(parentScreen,
                    config.getCategoryNames().stream().sorted()
                            .map(c -> new ConfigElement<>(config.getCategory(c)))
                            .collect(Collectors.toList()),
                    BalkonsWeaponMod.MOD_ID, false, false,
                    BalkonsWeaponMod.MOD_NAME + " configuration");
        }

    }

}
