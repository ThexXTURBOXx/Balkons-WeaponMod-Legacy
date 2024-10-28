package ckathode.weaponmod.fabric;

import ckathode.weaponmod.WMItemBuilder;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilderImpl extends WMItemBuilder {

    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType, @NotNull ResourceLocation id) {
        return new ItemBlowgunDart(dartType, id);
    }

    public static ItemCannon createItemCannon(@NotNull ResourceLocation id) {
        return new ItemCannon(id);
    }

    public static ItemDummy createItemDummy(@NotNull ResourceLocation id) {
        return new ItemDummy(id);
    }

    public static ItemDynamite createItemDynamite(@NotNull ResourceLocation id) {
        return new ItemDynamite(id);
    }

    public static ItemFlail createItemFlail(MeleeComponent meleeComponent, @NotNull ResourceLocation id) {
        return new ItemFlail(meleeComponent, id);
    }

    public static ItemJavelin createItemJavelin(@NotNull ResourceLocation id) {
        return new ItemJavelin(id);
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull ResourceLocation id) {
        return new ItemMelee(meleeComponent, id);
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull Item.Properties properties) {
        return new ItemMelee(meleeComponent, properties);
    }

    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem,
                                              @NotNull ResourceLocation id) {
        return new ItemMusket(meleeComponent, bayonetItem, id);
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                @NotNull ResourceLocation id) {
        return new ItemShooter(rangedComponent, meleeComponent, id);
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                @NotNull Item.Properties properties) {
        return new ItemShooter(rangedComponent, meleeComponent, properties);
    }

    public static WMItem createWMItem(@NotNull ResourceLocation id) {
        return new WMItem(id);
    }

    public static WMItem createWMItem(@NotNull Item.Properties properties) {
        return new WMItem(properties);
    }

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull ResourceLocation id) {
        return new WMItemProjectile(id) {

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

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull Item.Properties properties) {
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
