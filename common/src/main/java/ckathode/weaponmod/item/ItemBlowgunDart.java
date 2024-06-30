package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBlowgunDart extends WMItem {

    public static final String ID_PREFIX = "dart";
    public static final Map<DartType, ItemBlowgunDart> ITEMS =
            Arrays.stream(DartType.dartTypes).filter(Objects::nonNull)
                    .map(t -> new Pair<>(t, WMItemBuilder.createStandardBlowgunDart(t)))
                    .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

    @NotNull
    private final DartType dartType;

    public ItemBlowgunDart(@NotNull DartType dartType) {
        super();
        this.dartType = dartType;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @Nullable Level worldIn,
                                @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        MobEffectInstance potioneffect = dartType.potionEffect;
        MobEffect potion = potioneffect.getEffect();
        MutableComponent s = Component.translatable(potioneffect.getDescriptionId());
        if (potioneffect.getAmplifier() > 0) {
            s = Component.translatable("potion.withAmplifier",
                    s, Component.translatable("potion.potency." + potioneffect.getAmplifier()));
        }
        if (potioneffect.getDuration() > 20) {
            s = Component.translatable("potion.withDuration", s, MobEffectUtil.formatDuration(potioneffect, 1.0f));
        }
        s = s.withStyle(potion.getCategory().getTooltipFormatting());
        list.add(s);
    }

    @NotNull
    public DartType getDartType() {
        return dartType;
    }

}
