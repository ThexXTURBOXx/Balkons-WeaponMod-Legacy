package ckathode.weaponmod;

import com.google.gson.JsonObject;
import java.util.function.BooleanSupplier;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class WMConfigCondition implements IConditionFactory {

    @Override
    public BooleanSupplier parse(JsonContext ctx, JsonObject json) {
        return () -> BalkonsWeaponMod.instance.modConfig.isEnabled(JsonUtils.getString(json, "weapon"));
    }

}
