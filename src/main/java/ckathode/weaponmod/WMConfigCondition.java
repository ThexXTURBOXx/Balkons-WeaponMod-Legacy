package ckathode.weaponmod;

import com.google.gson.JsonObject;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class WMConfigCondition implements ICondition {
    private static final ResourceLocation CONDITION_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "config_conditional");
    private final WeaponModConfig config;
    private final String weapon;

    public WMConfigCondition(WeaponModConfig config, String weapon) {
        this.config = config;
        this.weapon = weapon;
    }

    @Override
    public boolean test() {
        return config.isEnabled(weapon);
    }

    @Override
    public ResourceLocation getID() {
        return CONDITION_ID;
    }

    public static class Serializer implements IConditionSerializer<WMConfigCondition> {
        private final WeaponModConfig config;

        public Serializer(WeaponModConfig config) {
            this.config = config;
        }

        @Override
        public void write(JsonObject json, WMConfigCondition value) {
            json.addProperty("weapon", value.weapon);
        }

        @Override
        public WMConfigCondition read(JsonObject json) {
            return new WMConfigCondition(config, JSONUtils.getString(json, "weapon"));
        }

        @Override
        public ResourceLocation getID() {
            return CONDITION_ID;
        }
    }

}
