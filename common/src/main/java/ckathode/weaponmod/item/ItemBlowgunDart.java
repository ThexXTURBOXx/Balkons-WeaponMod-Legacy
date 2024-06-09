package ckathode.weaponmod.item;

import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemBlowgunDart extends WMItem {

    public static final String ID_PREFIX = "dart";
    public static final Map<DartType, ItemBlowgunDart> ITEMS =
            Arrays.stream(DartType.dartTypes).filter(Objects::nonNull)
                    .map(t -> new Pair<>(t, new ItemBlowgunDart(t)))
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
        PotionUtils.addPotionTooltip(Collections.singletonList(dartType.potionEffect), list, 1.0f);
    }

    @NotNull
    public DartType getDartType() {
        return dartType;
    }

}
