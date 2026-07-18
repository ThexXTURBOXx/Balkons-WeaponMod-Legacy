package ckathode.weaponmod;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.jetbrains.annotations.NotNull;

public record WMLootConfigCondition(List<String> weapons) implements LootItemCondition {

    public static final MapCodec<WMLootConfigCondition> CODEC =
            RecordCodecBuilder.mapCodec((instance) ->
                    instance.group(Codec.STRING.listOf().fieldOf("weapons").forGetter(WMLootConfigCondition::weapons)).apply(instance, WMLootConfigCondition::new));

    public static final String ID = "loot_config_condition";
    public static final LootItemConditionType TYPE = new LootItemConditionType(CODEC);

    @Override
    public boolean test(LootContext lootContext) {
        return weapons.stream().allMatch(cfg -> WeaponModConfig.get().isEnabled(cfg));
    }

    @NotNull
    @Override
    public LootItemConditionType getType() {
        return TYPE;
    }

    public static LootItemCondition.Builder of(String... weapons) {
        List<String> list = new ArrayList<>(weapons.length);
        Collections.addAll(list, weapons);
        return () -> new WMLootConfigCondition(list);
    }

}
