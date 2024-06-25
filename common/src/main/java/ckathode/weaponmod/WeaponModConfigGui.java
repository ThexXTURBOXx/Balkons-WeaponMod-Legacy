package ckathode.weaponmod;

import java.util.Map;
import java.util.stream.Collectors;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.util.Utils;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.TranslatableComponent;

public class WeaponModConfigGui {

    @SuppressWarnings({"rawtypes"})
    public static void registerStringBooleanMapProvider(GuiRegistry registry) {
        registry.registerAnnotationProvider((i, f, c, d, r) -> {
            Map<String, Boolean> map = Utils.getUnsafely(f, c);
            Map<String, Boolean> defaults = Utils.getUnsafely(f, d);
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(e -> ConfigEntryBuilder.create()
                            .startBooleanToggle(new TranslatableComponent(i + "." + e.getKey()), e.getValue())
                            .setDefaultValue(() -> defaults.get(e.getKey()))
                            .setSaveConsumer(s -> map.put(e.getKey(), s))
                            .build())
                    .map(e -> (AbstractConfigListEntry) e)
                    .collect(Collectors.toList());
        }, WeaponModConfig.StringBooleanMap.class);
    }

    @SuppressWarnings({"rawtypes"})
    public static void registerStringIntMapProvider(GuiRegistry registry) {
        registry.registerAnnotationProvider((i, f, c, d, r) -> {
            Map<String, Integer> map = Utils.getUnsafely(f, c);
            Map<String, Integer> defaults = Utils.getUnsafely(f, d);
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .map(e -> ConfigEntryBuilder.create()
                            .startIntField(new TranslatableComponent(i + "." + e.getKey()), e.getValue())
                            .setDefaultValue(() -> defaults.get(e.getKey()))
                            .setSaveConsumer(s -> map.put(e.getKey(), s))
                            .build())
                    .map(e -> (AbstractConfigListEntry) e)
                    .collect(Collectors.toList());
        }, WeaponModConfig.StringIntMap.class);
    }

    public static void init() {
        GuiRegistry registry = AutoConfig.getGuiRegistry(WeaponModConfig.class);
        registerStringBooleanMapProvider(registry);
        registerStringIntMapProvider(registry);
    }

}
