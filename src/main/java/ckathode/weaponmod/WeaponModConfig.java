package ckathode.weaponmod;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class WeaponModConfig {
    private final ForgeConfigSpec.Builder builder;
    public ForgeConfigSpec.BooleanValue cannonDoesBlockDamage;
    public ForgeConfigSpec.BooleanValue dynamiteDoesBlockDamage;
    public ForgeConfigSpec.BooleanValue mortarDoesBlockDamage;
    public ForgeConfigSpec.BooleanValue canThrowKnife;
    public ForgeConfigSpec.BooleanValue canThrowSpear;
    public ForgeConfigSpec.BooleanValue allCanPickup;
    public ForgeConfigSpec.BooleanValue guiOverlayReloaded;
    public ForgeConfigSpec.BooleanValue itemModelForEntity;
    private final Map<String, EnableSetting> enableSettings;
    private final Map<String, ReloadTimeSetting> reloadTimeSettings;

    public WeaponModConfig() {
        builder = new ForgeConfigSpec.Builder();
        enableSettings = new LinkedHashMap<>();
        reloadTimeSettings = new LinkedHashMap<>();
    }

    public void loadConfig(ModLoadingContext context) {
        builder.comment("Enable or disable certain weapons")
                .push("enable");
        for (final EnableSetting es : enableSettings.values())
            es.configValue = builder.define(es.settingName, es.enabled);
        builder.pop();

        builder.comment("The reload durations of the reloadable weapons")
                .push("reloadtime");
        for (final ReloadTimeSetting rs : reloadTimeSettings.values())
            rs.configValue = builder.define(rs.settingName, rs.reloadTime);
        builder.pop();

        builder.comment("Miscellaneous mod settings")
                .push("settings");
        cannonDoesBlockDamage = builder
                .define("cannon-block-damage", true);
        dynamiteDoesBlockDamage = builder
                .define("dynamite-block-damage", true);
        mortarDoesBlockDamage = builder
                .define("mortar-block-damage", true);
        canThrowKnife = builder
                .define("can-throw-knife", true);
        canThrowSpear = builder
                .define("can-throw-spear", true);
        allCanPickup = builder
                .comment("Change this to 'false' to allow only the thrower/shooter of the projectile to " +
                         "pick the item up. If set to 'true' everyone can pick the item up.")
                .define("pickup-all", true);
        guiOverlayReloaded = builder
                .comment("Show reload progress in hotbar.")
                .define("reload-progress", true);
        itemModelForEntity = builder
                .comment("Item model for entity (knife, spear, etc).")
                .define("render-entity-model", true);
        builder.pop();

        context.registerConfig(ModConfig.Type.COMMON, builder.build());
    }

    public void postLoadConfig() {
        for (EnableSetting es : enableSettings.values())
            es.enabled = es.configValue.get();
        for (ReloadTimeSetting rs : reloadTimeSettings.values())
            rs.reloadTime = rs.configValue.get();
    }

    public void addEnableSetting(String weapon) {
        enableSettings.put(weapon, new EnableSetting(weapon, true));
    }

    public void addReloadTimeSetting(String weapon, int defaulttime) {
        reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
    }

    public boolean isEnabled(String weapon) {
        EnableSetting es = enableSettings.get(weapon);
        return es == null || es.enabled;
    }

    public int getReloadTime(String weapon) {
        ReloadTimeSetting rs = reloadTimeSettings.get(weapon);
        return (rs == null) ? 0 : rs.reloadTime;
    }

    private abstract static class Setting<T> {
        ForgeConfigSpec.ConfigValue<T> configValue;
        final String settingName;

        Setting(String name) {
            settingName = name;
        }
    }

    private static class ReloadTimeSetting extends Setting<Integer> {
        int reloadTime;

        ReloadTimeSetting(String name, int reloadTime) {
            super(name + ".reloadtime");
            this.reloadTime = reloadTime;
        }
    }

    private static class EnableSetting extends Setting<Boolean> {
        boolean enabled;

        EnableSetting(String name, boolean enabled) {
            super(name + ".enabled");
            this.enabled = enabled;
        }
    }
}
