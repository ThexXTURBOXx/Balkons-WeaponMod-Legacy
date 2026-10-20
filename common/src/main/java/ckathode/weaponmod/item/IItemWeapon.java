package ckathode.weaponmod.item;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IItemWeapon {

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

    boolean leftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player, @NotNull Entity entity);

    void inventoryTickClient(@NotNull ItemStack itemStack, @NotNull Level level,
                             @NotNull Player player, @Nullable EquipmentSlot equipmentSlot);

    default void modifyTooltip(ItemStack stack, List<Component> lines,
                               TooltipContext tooltipContext, TooltipFlag flag) {
    }

}
