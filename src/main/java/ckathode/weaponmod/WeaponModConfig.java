package ckathode.weaponmod;

import net.minecraftforge.common.config.*;
import java.util.*;

public class WeaponModConfig
{
    private final Configuration config;
    public boolean cannonDoesBlockDamage;
    public boolean dynamiteDoesBlockDamage;
    public boolean mortarDoesBlockDamage;
    public boolean canThrowKnife;
    public boolean canThrowSpear;
    public boolean allCanPickup;
    public boolean guiOverlayReloaded;
    public boolean itemModelForEntity;
    private Map<String, EnableSetting> enableSettings;
    private Map<String, ReloadTimeSetting> reloadTimeSettings;
    
    public WeaponModConfig(final Configuration configuration) {
        this.config = configuration;
        this.enableSettings = new LinkedHashMap<String, EnableSetting>();
        this.reloadTimeSettings = new LinkedHashMap<String, ReloadTimeSetting>();
    }
    
    public void addEnableSetting(final String weapon) {
        this.enableSettings.put(weapon, new EnableSetting(weapon));
    }
    
    public void addReloadTimeSetting(final String weapon, final int defaulttime) {
        this.reloadTimeSettings.put(weapon, new ReloadTimeSetting(weapon, defaulttime));
    }
    
    public boolean isEnabled(final String weapon) {
        final EnableSetting es = this.enableSettings.get(weapon);
        return es == null || es.enabled;
    }
    
    public int getReloadTime(final String weapon) {
        final ReloadTimeSetting rs = this.reloadTimeSettings.get(weapon);
        return (rs == null) ? 0 : rs.reloadTime;
    }
    
    public void loadConfig() {
        this.config.load();
        this.config.addCustomCategoryComment("enable", "Enable or disable certain weapons");
        this.config.addCustomCategoryComment("reloadtime", "The reload durations of the reloadable weapons");
        this.config.addCustomCategoryComment("settings", "Miscellaneous mod settings");
        this.cannonDoesBlockDamage = this.config.get("settings", "cannon-block-damage", true).getBoolean(true);
        this.dynamiteDoesBlockDamage = this.config.get("settings", "dynamite-block-damage", true).getBoolean(true);
        this.mortarDoesBlockDamage = this.config.get("settings", "mortar-block-damage", true).getBoolean(true);
        this.canThrowKnife = this.config.get("settings", "can-throw-knife", true).getBoolean(true);
        this.canThrowSpear = this.config.get("settings", "can-throw-spear", true).getBoolean(true);
        this.allCanPickup = this.config.get("settings", "pickup-all", true, "Change this to 'false' to allow only the thrower/shooter of the projectile to pick the item up. If set to 'true' everyone can pick the item up.").getBoolean(true);
        this.guiOverlayReloaded = this.config.get("settings", "reload-progress", true, "Show reload progress in hotbar.").getBoolean(true);
        this.itemModelForEntity = this.config.get("settings", "render-entity-model", true, "Item model for entity (knife, spear, etc).").getBoolean(true);
        for (final EnableSetting es : this.enableSettings.values()) {
            es.enabled = this.config.get("enable", es.settingName, es.enabled).getBoolean(es.enabled);
        }
        for (final ReloadTimeSetting rs : this.reloadTimeSettings.values()) {
            rs.reloadTime = this.config.get("reloadtime", rs.settingName, rs.reloadTime).getInt(rs.reloadTime);
        }
        this.config.save();
    }
    
    private abstract static class Setting
    {
        final String settingName;
        
        Setting(final String name) {
            this.settingName = name;
        }
    }
    
    private static class ReloadTimeSetting extends Setting
    {
        int reloadTime;
        
        ReloadTimeSetting(final String name, final int time) {
            super(name + ".reloadtime");
            this.reloadTime = time;
        }
    }
    
    private static class EnableSetting extends Setting
    {
        boolean enabled;
        
        EnableSetting(final String name) {
            super(name + ".enabled");
            this.enabled = true;
        }
    }
}
