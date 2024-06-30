package ckathode.weaponmod.fabric;

import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
import ckathode.weaponmod.item.DartType;
import ckathode.weaponmod.item.ItemBlowgunDart;
import ckathode.weaponmod.item.ItemCannon;
import ckathode.weaponmod.item.ItemDummy;
import ckathode.weaponmod.item.ItemDynamite;
import ckathode.weaponmod.item.ItemFlail;
import ckathode.weaponmod.item.ItemJavelin;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemMusket;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.item.WMItem;
import ckathode.weaponmod.item.WMItemProjectile;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilderImpl {

    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType) {
        return new ItemBlowgunDart(dartType);
    }

    public static ItemCannon createItemCannon() {
        return new ItemCannon();
    }

    public static ItemDummy createItemDummy() {
        return new ItemDummy();
    }

    public static ItemDynamite createItemDynamite() {
        return new ItemDynamite();
    }

    public static ItemFlail createItemFlail(MeleeComponent meleeComponent) {
        return new ItemFlail(meleeComponent);
    }

    public static ItemJavelin createItemJavelin() {
        return new ItemJavelin();
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent) {
        return new ItemMelee(meleeComponent);
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemMelee(meleeComponent, properties);
    }

    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem) {
        return new ItemMusket(meleeComponent, bayonetItem);
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent) {
        return new ItemShooter(rangedComponent, meleeComponent);
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                Item.Properties properties) {
        return new ItemShooter(rangedComponent, meleeComponent, properties);
    }

    public static WMItem createWMItem() {
        return new WMItem();
    }

    public static WMItem createWMItem(Item.Properties properties) {
        return new WMItem(properties);
    }

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension) {
        return new WMItemProjectile() {

            @NotNull
            @Override
            public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
                return extension.asProjectile(level, pos, stack, direction);
            }

            @NotNull
            @Override
            public ProjectileItem.DispenseConfig createDispenseConfig() {
                return extension.createDispenseConfig();
            }

            @Override
            public double getYVel(@NotNull BiFunction<BlockSource, ItemStack, Double> origFn,
                                  @NotNull BlockSource blockSource,
                                  @NotNull ItemStack itemStack) {
                return extension.getYVel(origFn, blockSource, itemStack);
            }

            @Override
            public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
                extension.playSound(origFn, blockSource);
            }

            @Override
            public void playAnimation(@NotNull BiConsumer<BlockSource, Direction> origFn,
                                      @NotNull BlockSource blockSource,
                                      @NotNull Direction direction) {
                extension.playAnimation(origFn, blockSource, direction);
            }

        };
    }

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension, Item.Properties properties) {
        return new WMItemProjectile(properties) {

            @NotNull
            @Override
            public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
                return extension.asProjectile(level, pos, stack, direction);
            }

            @NotNull
            @Override
            public ProjectileItem.DispenseConfig createDispenseConfig() {
                return extension.createDispenseConfig();
            }

            @Override
            public double getYVel(@NotNull BiFunction<BlockSource, ItemStack, Double> origFn,
                                  @NotNull BlockSource blockSource,
                                  @NotNull ItemStack itemStack) {
                return extension.getYVel(origFn, blockSource, itemStack);
            }

            @Override
            public void playSound(@NotNull Consumer<BlockSource> origFn, @NotNull BlockSource blockSource) {
                extension.playSound(origFn, blockSource);
            }

            @Override
            public void playAnimation(@NotNull BiConsumer<BlockSource, Direction> origFn,
                                      @NotNull BlockSource blockSource,
                                      @NotNull Direction direction) {
                extension.playAnimation(origFn, blockSource, direction);
            }

        };
    }

}
