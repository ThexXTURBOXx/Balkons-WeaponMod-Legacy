package ckathode.weaponmod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

@Config(name = MOD_ID)
@SuppressWarnings("FieldMayBeFinal")
public class WeaponModConfig implements ConfigData {

    @Category("settings")
    @Comment("Whether the cannon should do block damage.")
    public volatile boolean cannonDoesBlockDamage = true;
    @Category("settings")
    @Comment("Whether dynamite should do block damage.")
    public volatile boolean dynamiteDoesBlockDamage = true;
    @Category("settings")
    @Comment("Whether the mortar should do block damage.")
    public volatile boolean mortarDoesBlockDamage = true;
    @Category("settings")
    @Comment("Whether the knife can be thrown.")
    public volatile boolean canThrowKnife = true;
    @Category("settings")
    @Comment("Whether the spear can be thrown.")
    public volatile boolean canThrowSpear = true;
    @Category("settings")
    @Comment("Change this to 'false' to allow only the thrower/shooter of the projectile to " +
             "pick the item up. If set to 'true' everyone can pick the item up.")
    public volatile boolean allCanPickup = true;
    @Category("settings")
    @Comment("Show reload progress in hotbar.")
    public volatile boolean guiOverlayReloaded = true;
    @Category("settings")
    @Comment("Item model for entity (knife, spear, etc).")
    public volatile boolean itemModelForEntity = true;

    @Category("enable")
    @StringBooleanMap
    @Comment("Enable/disable recipes for the different weapons (only works in Forge).")
    private volatile Map<String, Boolean> enableSettings = new HashMap<>();
    @Category("reloadTimes")
    @StringIntMap
    @Comment("Change the reload times of the different weapons.")
    private volatile Map<String, Integer> reloadTimeSettings = new HashMap<>();

    private WeaponModConfig() {
        addEnableSetting("spear");
        addEnableSetting("halberd");
        addEnableSetting("battleaxe");
        addEnableSetting("knife");
        addEnableSetting("warhammer");
        addEnableSetting("flail");
        addEnableSetting("katana");
        addEnableSetting("boomerang");
        addEnableSetting("firerod");
        addEnableSetting("javelin");
        addEnableSetting("crossbow");
        addEnableSetting("blowgun");
        addEnableSetting("musket");
        addEnableSetting("blunderbuss");
        addEnableSetting("flintlock");
        addEnableSetting("dynamite");
        addEnableSetting("cannon");
        addEnableSetting("dummy");
        addEnableSetting("mortar");

        addReloadTimeSetting("musket", 30);
        addReloadTimeSetting("crossbow", 15);
        addReloadTimeSetting("blowgun", 10);
        addReloadTimeSetting("blunderbuss", 20);
        addReloadTimeSetting("flintlock", 15);
        addReloadTimeSetting("mortar", 50);
    }

    private void addEnableSetting(String weapon) {
        enableSettings.put(weapon, true);
    }

    public boolean isEnabled(String weapon) {
        Boolean enabled = enableSettings.get(weapon);
        return enabled == null || enabled;
    }

    private void addReloadTimeSetting(String weapon, int defaultTime) {
        reloadTimeSettings.put(weapon, defaultTime);
    }

    public int getReloadTime(String weapon) {
        Integer time = reloadTimeSettings.get(weapon);
        return (time == null) ? 0 : time;
    }

    public static WeaponModConfig get() {
        return AutoConfig.getConfigHolder(WeaponModConfig.class).getConfig();
    }

    @SuppressWarnings({"rawtypes"})
    public static void init() {
        AutoConfig.register(WeaponModConfig.class, JanksonConfigSerializer::new);

        GuiRegistry registry = AutoConfig.getGuiRegistry(WeaponModConfig.class);
        registry.registerAnnotationProvider((i, f, c, d, r) -> {
            Map<String, Boolean> map = Utils.getUnsafely(f, c);
            Map<String, Boolean> defaults = Utils.getUnsafely(f, d);
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(e -> ConfigEntryBuilder.create()
                            .startBooleanToggle(Component.translatable(i + "." + e.getKey()), e.getValue())
                            .setDefaultValue(() -> defaults.get(e.getKey()))
                            .setSaveConsumer(s -> map.put(e.getKey(), s))
                            .build())
                    .map(e -> (AbstractConfigListEntry) e)
                    .toList();
        }, StringBooleanMap.class);
        registry.registerAnnotationProvider((i, f, c, d, r) -> {
            Map<String, Integer> map = Utils.getUnsafely(f, c);
            Map<String, Integer> defaults = Utils.getUnsafely(f, d);
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(e -> ConfigEntryBuilder.create()
                            .startIntField(Component.translatable(i + "." + e.getKey()), e.getValue())
                            .setDefaultValue(() -> defaults.get(e.getKey()))
                            .setSaveConsumer(s -> map.put(e.getKey(), s))
                            .build())
                    .map(e -> (AbstractConfigListEntry) e)
                    .toList();
        }, StringIntMap.class);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface StringBooleanMap {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface StringIntMap {
    }

}
