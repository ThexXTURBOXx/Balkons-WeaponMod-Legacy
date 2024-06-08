package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public record WMConfigConditionForge(String weapon) implements ICondition {

    public static final MapCodec<WMConfigConditionForge> CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder
                    .group(Codec.STRING.fieldOf("weapon").forGetter(WMConfigConditionForge::weapon))
                    .apply(builder, WMConfigConditionForge::new));
    public static final ResourceLocation CONDITION_ID =
            new ResourceLocation(BalkonsWeaponMod.MOD_ID, "config_conditional");

    @Override
    public boolean test(@NotNull IContext context) {
        return WeaponModConfig.get().isEnabled(weapon);
    }

    @NotNull
    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }

    @Override
    public String toString() {
        return "config_conditional(\"" + weapon + "\")";
    }

}
