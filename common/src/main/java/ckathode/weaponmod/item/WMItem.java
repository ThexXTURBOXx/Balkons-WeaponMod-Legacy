package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItem extends Item {

    public static final String CANNON_BALL_ID = "cannonball";
    public static final WMItem CANNON_BALL_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(CANNON_BALL_ID));

    public static final String BLUNDER_SHOT_ID = "shot";
    public static final WMItem BLUNDER_SHOT_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(BLUNDER_SHOT_ID));

    public static final String MUSKET_IRON_PART_ID = "musket-ironpart";
    public static final WMItem MUSKET_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(MUSKET_IRON_PART_ID));

    public static final String BLUNDER_IRON_PART_ID = "blunder-ironpart";
    public static final WMItem BLUNDER_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(BLUNDER_IRON_PART_ID));

    public static final String GUN_STOCK_ID = "gun-stock";
    public static final WMItem GUN_STOCK_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(GUN_STOCK_ID));

    public static final String MORTAR_IRON_PART_ID = "mortar-ironpart";
    public static final WMItem MORTAR_IRON_PART_ITEM =
            WMItemBuilder.createWMItem(BalkonsWeaponMod.id(MORTAR_IRON_PART_ID));

    public WMItem(@NotNull ResourceLocation id) {
        this(getBaseProperties(id));
    }

    public WMItem(@NotNull Properties properties) {
        super(properties.arch$tab(CreativeModeTabs.COMBAT));
    }

    public static Properties getBaseProperties(@NotNull ResourceLocation id) {
        return getBaseProperties(null, id);
    }

    public static Properties getBaseProperties(@Nullable ToolMaterial tier, @NotNull ResourceLocation id) {
        Properties properties = new Properties().setId(WMItemBuilder.id(id));
        if (tier == ToolMaterial.NETHERITE) {
            properties = properties.fireResistant();
        }
        return properties;
    }

    @ExpectPlatform
    public static void onPlayerDestroyItem(Player player, ItemStack stack, InteractionHand hand) {
        // Will get replaced at run time
    }

    public static void decrStackSize(ItemStack stack, int amount, LivingEntity entity) {
        stack.shrink(amount);
        if (stack.isEmpty() && entity instanceof Player player) {
            onPlayerDestroyItem(player, stack, player.swingingArm);
        }
    }

    public static boolean isItemInTag(ItemStack stack, TagKey<Item> tag) {
        return stack != null && stack.is(tag);
    }

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    @Nullable
    public static Tuple<EquipmentSlot, Integer> findAnyItemSlot(Player player, TagKey<Item> tag) {
        return findAnyItemSlot(player, is -> isItemInTag(is, tag));
    }

    @Nullable
    public static Tuple<EquipmentSlot, Integer> findAnyItemSlot(Player player, Collection<Item> items) {
        return findAnyItemSlot(player, is -> isItemInList(is, items));
    }

    @Nullable
    public static Tuple<EquipmentSlot, Integer> findAnyItemSlot(Player player, Predicate<ItemStack> matcher) {
        if (matcher.test(player.getMainHandItem()))
            return new Tuple<>(EquipmentSlot.MAINHAND, player.getInventory().getSelectedSlot());
        if (matcher.test(player.getOffhandItem()))
            return new Tuple<>(EquipmentSlot.OFFHAND, 0);
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (matcher.test(itemstack))
                return new Tuple<>(EquipmentSlot.MAINHAND, i);
        }
        return null;
    }

    public static boolean consumeInventoryItem(Player player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }

    public static boolean consumeAnyInventoryItem(Player player, TagKey<Item> item) {
        Tuple<EquipmentSlot, Integer> slot = findAnyItemSlot(player, item);
        if (slot == null) return false;
        ItemStack stack = slot.getA() == EquipmentSlot.MAINHAND ? player.getInventory().getItem(slot.getB()) :
                player.getItemBySlot(slot.getA());
        stack.shrink(1);
        return true;
    }

    public static boolean consumeAnyInventoryItem(Player player, Collection<Item> items) {
        Tuple<EquipmentSlot, Integer> slot = findAnyItemSlot(player, items);
        if (slot == null) return false;
        ItemStack stack = slot.getA() == EquipmentSlot.MAINHAND ? player.getInventory().getItem(slot.getB()) :
                player.getItemBySlot(slot.getA());
        stack.shrink(1);
        return true;
    }

    public static boolean consumeAnyInventoryItem(Player player, Predicate<ItemStack> matcher) {
        Tuple<EquipmentSlot, Integer> slot = findAnyItemSlot(player, matcher);
        if (slot == null) return false;
        ItemStack stack = slot.getA() == EquipmentSlot.MAINHAND ? player.getInventory().getItem(slot.getB()) :
                player.getItemBySlot(slot.getA());
        stack.shrink(1);
        return true;
    }

}
