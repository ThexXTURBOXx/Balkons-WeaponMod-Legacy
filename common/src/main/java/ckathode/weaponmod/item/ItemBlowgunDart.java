package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBlowgunDart extends WMItem {

    public static final String ID_PREFIX = "dart";
    public static final Map<DartType, ItemBlowgunDart> ITEMS = DartType.DART_TYPES.stream()
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
        for (MobEffectInstance mei : getEffects(itemstack)) {
            MobEffect effect = mei.getEffect();
            MutableComponent s = new TranslatableComponent(mei.getDescriptionId());
            if (mei.getAmplifier() > 0)
                s = new TranslatableComponent("potion.withAmplifier",
                        s, new TranslatableComponent("potion.potency." + mei.getAmplifier()));
            if (mei.getDuration() > 20)
                s = new TranslatableComponent("potion.withDuration", s, MobEffectUtil.formatDuration(mei, 1.0f));
            s = s.withStyle(effect.getCategory().getTooltipFormatting());
            list.add(s);
        }
    }

    @NotNull
    public DartType getDartType() {
        return dartType;
    }

    @NotNull
    private static DartType getDartType(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ItemBlowgunDart
                ? ((ItemBlowgunDart) stack.getItem()).getDartType()
                : DartType.DAMAGE;
    }

    public static List<MobEffectInstance> getEffects(@NotNull ItemStack stack) {
        if (stack.isEmpty()) return Collections.emptyList();

        List<MobEffectInstance> effects = new ArrayList<>(getDartType(stack).potionEffects);
        PotionUtils.getCustomEffects(stack.getTag(), effects);
        return effects;
    }

    public static float[] getColor(ItemStack stack) {
        if (stack.isEmpty()) return DartType.DAMAGE.color;
        return getDartType(stack).color;
    }

}
