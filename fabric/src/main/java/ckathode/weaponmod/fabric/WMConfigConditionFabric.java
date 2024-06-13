package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditionType;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public record WMConfigConditionFabric(String weapon) implements ResourceCondition {

    public static final ResourceLocation CONFIG_CONDITION_ID =
            ResourceLocation.fromNamespaceAndPath(BalkonsWeaponMod.MOD_ID, "config_conditional");
    public static final MapCodec<WMConfigConditionFabric> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                            Codec.STRING.fieldOf("weapon").forGetter(condition -> condition.weapon))
                    .apply(instance, WMConfigConditionFabric::new));
    public static final ResourceConditionType<WMConfigConditionFabric> CONFIG_CONDITION_TYPE =
            ResourceConditionType.create(CONFIG_CONDITION_ID, CODEC);

    @Override
    public ResourceConditionType<?> getType() {
        return CONFIG_CONDITION_TYPE;
    }

    @Override
    public boolean test(@Nullable HolderLookup.Provider registryLookup) {
        return WeaponModConfig.get().isEnabled(weapon);
    }

    public static void init() {
        ResourceConditions.register(CONFIG_CONDITION_TYPE);
    }

}
