package ckathode.weaponmod.entity.projectile.dispense;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import org.jetbrains.annotations.NotNull;

public interface WMDispenserExtension extends ProjectileItem {

    default void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
        origFn.accept(blockSource);
    }

    default void playAnimation(@NotNull BiConsumer<BlockSource, Direction> origFn, @NotNull BlockSource blockSource,
                               @NotNull Direction direction) {
        origFn.accept(blockSource, direction);
    }

    default double getYVel(@NotNull BiFunction<BlockSource, ItemStack, Double> origFn, @NotNull BlockSource blockSource,
                           @NotNull ItemStack itemStack) {
        return origFn.apply(blockSource, itemStack);
    }

}
