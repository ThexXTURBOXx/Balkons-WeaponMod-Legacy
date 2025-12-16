package ckathode.weaponmod.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

public interface IItemWeapon {

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

    boolean leftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player, @NotNull Entity entity);

    default void modifyTooltip(ItemStack stack, List<Component> lines,
                               TooltipContext tooltipContext, TooltipFlag flag) {
    }

}
