package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModConfig;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class WMConfigConditionFabric implements ConditionJsonProvider {

    private static final ResourceLocation CONFIG_CONDITION =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "config_conditional");
    private final String weapon;

    public WMConfigConditionFabric(String weapon) {
        this.weapon = weapon;
    }

    @Override
    public ResourceLocation getConditionId() {
        return CONFIG_CONDITION;
    }

    @Override
    public void writeParameters(JsonObject object) {
        object.addProperty("weapon", weapon);
    }

    public static boolean test(JsonObject object) {
        String weapon = GsonHelper.getAsString(object, "weapon");
        return WeaponModConfig.get().isEnabled(weapon);
    }

    public static void init() {
        ResourceConditions.register(CONFIG_CONDITION, WMConfigConditionFabric::test);
    }

}
