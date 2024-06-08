package ckathode.weaponmod.forge;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModConfig;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

public class WMConfigConditionForge implements ICondition {

    private static final ResourceLocation CONDITION_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "config_conditional");
    private final String weapon;

    public WMConfigConditionForge(String weapon) {
        this.weapon = weapon;
    }

    @Override
    @SuppressWarnings("removal")
    public boolean test() {
        return WeaponModConfig.get().isEnabled(weapon);
    }

    @Override
    public ResourceLocation getID() {
        return CONDITION_ID;
    }

    public static class Serializer implements IConditionSerializer<WMConfigConditionForge> {

        @Override
        public void write(JsonObject json, WMConfigConditionForge value) {
            json.addProperty("weapon", value.weapon);
        }

        @Override
        public WMConfigConditionForge read(JsonObject json) {
            return new WMConfigConditionForge(GsonHelper.getAsString(json, "weapon"));
        }

        @Override
        public ResourceLocation getID() {
            return CONDITION_ID;
        }

    }

}
