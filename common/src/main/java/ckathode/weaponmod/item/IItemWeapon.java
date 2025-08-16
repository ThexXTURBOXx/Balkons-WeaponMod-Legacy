package ckathode.weaponmod.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface IItemWeapon {

    MeleeComponent getMeleeComponent();

    RangedComponent getRangedComponent();

    boolean leftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player, @NotNull Entity entity);
}
