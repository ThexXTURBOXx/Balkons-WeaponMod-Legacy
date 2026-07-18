package ckathode.weaponmod;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Arrays;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class WMLootConfigCondition implements LootItemCondition {

    public static final String ID = "loot_config_condition";
    public static final LootItemConditionType TYPE = new LootItemConditionType(new Serializer());

    private final String[] weapons;

    public WMLootConfigCondition(String... weapons) {
        this.weapons = weapons;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return Arrays.stream(weapons).allMatch(cfg -> WeaponModConfig.get().isEnabled(cfg));
    }

    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<WMLootConfigCondition> {

        @Override
        public void serialize(JsonObject json, WMLootConfigCondition value,
                              JsonSerializationContext serializationContext) {
            json.add("weapons", serializationContext.serialize(value.weapons));
        }

        @Override
        public WMLootConfigCondition deserialize(JsonObject json, JsonDeserializationContext serializationContext) {
            return new WMLootConfigCondition(
                    GsonHelper.getAsObject(json, "weapons", serializationContext, String[].class));
        }
    }

}
