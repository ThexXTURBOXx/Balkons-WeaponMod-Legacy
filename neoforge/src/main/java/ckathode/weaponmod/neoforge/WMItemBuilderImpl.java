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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilderImpl {

    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType, @NotNull ResourceLocation id) {
        return new ItemBlowgunDart(dartType, id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemBlowgunDart) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemCannon createItemCannon(@NotNull ResourceLocation id) {
        return new ItemCannon(id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemCannon) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemDummy createItemDummy(@NotNull ResourceLocation id) {
        return new ItemDummy(id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemDummy) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemDynamite createItemDynamite(@NotNull ResourceLocation id) {
        return new ItemDynamite(id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemDynamite) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemFlail createItemFlail(MeleeComponent meleeComponent, @NotNull ResourceLocation id) {
        return new ItemFlail(meleeComponent, id) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemFlail) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemJavelin createItemJavelin(@NotNull ResourceLocation id) {
        return new ItemJavelin(id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemJavelin) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull ResourceLocation id) {
        return new ItemMelee(meleeComponent, id) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMelee) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull Item.Properties properties) {
        return new ItemMelee(meleeComponent, properties) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMelee) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem,
                                              @NotNull ResourceLocation id) {
        return new ItemMusket(meleeComponent, bayonetItem, id) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemMusket) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                @NotNull ResourceLocation id) {
        return new ItemShooter(rangedComponent, meleeComponent, id) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemShooter) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                Item.Properties properties) {
        return new ItemShooter(rangedComponent, meleeComponent, properties) {
            @Override
            public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                             @NotNull Entity entity) {
                return leftClickEntity(itemstack, player, entity);
            }

            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof ItemShooter) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static WMItem createWMItem(@NotNull ResourceLocation id) {
        return new WMItem(id) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItem) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static WMItem createWMItem(Item.Properties properties) {
        return new WMItem(properties) {
            @Override
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItem) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }
        };
    }

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull ResourceLocation id) {
        return new WMItemProjectile(id) {

            @NotNull
            @Override
            public Projectile asProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack,
                                           @NotNull Direction direction) {
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
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItemProjectile) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }

        };
    }

    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull Item.Properties properties) {
        return new WMItemProjectile(properties) {

            @NotNull
            @Override
            public Projectile asProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack,
                                           @NotNull Direction direction) {
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
            public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ItemAbility itemAbility) {
                if (itemAbility == ItemAbilities.SHIELD_BLOCK) {
                    if (stack.getItem() instanceof WMItemProjectile) return true;
                }
                return super.canPerformAction(stack, itemAbility);
            }

        };
    }

}
