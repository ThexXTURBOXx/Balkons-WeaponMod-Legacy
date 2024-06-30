package ckathode.weaponmod.forge;

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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilderImpl {

    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType) {
        return new ItemBlowgunDart(dartType) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemCannon createItemCannon() {
        return new ItemCannon() {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemDummy createItemDummy() {
        return new ItemDummy() {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemDynamite createItemDynamite() {
        return new ItemDynamite() {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemFlail createItemFlail(MeleeComponent meleeComponent) {
        return new ItemFlail(meleeComponent) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemJavelin createItemJavelin() {
        return new ItemJavelin() {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent) {
        return new ItemMelee(meleeComponent) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemMelee(meleeComponent, properties) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem) {
        return new ItemMusket(meleeComponent, bayonetItem) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent) {
        return new ItemShooter(rangedComponent, meleeComponent) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                Item.Properties properties) {
        return new ItemShooter(rangedComponent, meleeComponent, properties) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static WMItem createWMItem() {
        return new WMItem() {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

    public static WMItem createWMItem(Item.Properties properties) {
        return new WMItem(properties) {
            @Override
            public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
                if (entity != null && entity.getUseItem() == stack && stack.getItem() instanceof ItemMelee &&
                    entity.isBlocking())
                    return true;
                return super.isShield(stack, entity);
            }
        };
    }

}
