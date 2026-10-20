package ckathode.weaponmod.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IItemWeapon {

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

    boolean leftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player, @NotNull Entity entity);

    void inventoryTickClient(@NotNull ItemStack itemStack, @NotNull Level level,
                             @NotNull Player player, @Nullable EquipmentSlot equipmentSlot);
}
