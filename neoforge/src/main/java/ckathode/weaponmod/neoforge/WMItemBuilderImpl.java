package ckathode.weaponmod.neoforge;

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
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilderImpl {

    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType) {
        return new ItemBlowgunDart(dartType) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemBlowgunDart) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemCannon createItemCannon() {
        return new ItemCannon() {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemCannon) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemDummy createItemDummy() {
        return new ItemDummy() {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemDummy) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemDynamite createItemDynamite() {
        return new ItemDynamite() {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemDynamite) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemFlail createItemFlail(MeleeComponent meleeComponent) {
        return new ItemFlail(meleeComponent) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemFlail) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemJavelin createItemJavelin() {
        return new ItemJavelin() {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemJavelin) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent) {
        return new ItemMelee(meleeComponent) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMelee) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemMelee(meleeComponent, properties) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMelee) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem) {
        return new ItemMusket(meleeComponent, bayonetItem) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMusket) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent) {
        return new ItemShooter(rangedComponent, meleeComponent) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemShooter) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                Item.Properties properties) {
        return new ItemShooter(rangedComponent, meleeComponent, properties) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemShooter) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static WMItem createWMItem() {
        return new WMItem() {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItem) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
    }

    public static WMItem createWMItem(Item.Properties properties) {
        return new WMItem(properties) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItem) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }
        };
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

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItemProjectile) return true;
                }
                return super.canPerformAction(stack, toolAction);
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

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction) {
                if (toolAction == ToolActions.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItemProjectile) return true;
                }
                return super.canPerformAction(stack, toolAction);
            }

        };
    }

}
