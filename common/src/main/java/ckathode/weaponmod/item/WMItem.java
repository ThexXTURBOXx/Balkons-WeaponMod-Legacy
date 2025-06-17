package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import dev.architectury.injectables.annotations.ExpectPlatform;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import net.minecraft.core.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import org.jetbrains.annotations.Nullable;

public class WMItem extends Item {

    public static final String BULLET_MUSKET_ID = "bullet";
    public static final WMItem BULLET_MUSKET_ITEM = WMItemBuilder.createWMItem();

    public static final String CANNON_BALL_ID = "cannonball";
    public static final WMItem CANNON_BALL_ITEM = WMItemBuilder.createWMItem();

    public static final String BLUNDER_SHOT_ID = "shot";
    public static final WMItem BLUNDER_SHOT_ITEM = WMItemBuilder.createWMItem();

    public static final String CROSSBOW_BOLT_ID = "bolt";
    public static final WMItem CROSSBOW_BOLT_ITEM = WMItemBuilder.createWMItem();

    public static final String MORTAR_SHELL_ID = "shell";
    public static final WMItem MORTAR_SHELL_ITEM = WMItemBuilder.createWMItem();

    public static final String MUSKET_IRON_PART_ID = "musket-ironpart";
    public static final WMItem MUSKET_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public static final String BLUNDER_IRON_PART_ID = "blunder-ironpart";
    public static final WMItem BLUNDER_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public static final String GUN_STOCK_ID = "gun-stock";
    public static final WMItem GUN_STOCK_ITEM = WMItemBuilder.createWMItem();

    public static final String MORTAR_IRON_PART_ID = "mortar-ironpart";
    public static final WMItem MORTAR_IRON_PART_ITEM = WMItemBuilder.createWMItem();

    public WMItem() {
        this(getBaseProperties(null));
    }

    public WMItem(Properties properties) {
        super(properties.arch$tab(CreativeModeTabs.COMBAT));
    }

    public static Properties getBaseProperties(@Nullable Tier tier) {
        Properties properties = new Properties();
        if (tier == Tiers.NETHERITE) {
            properties = properties.fireResistant();
        }
        return properties;
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.BASE_ATTACK_DAMAGE_UUID;
    }

    public static UUID getAttackSpeedModifierUUID() {
        return Item.BASE_ATTACK_SPEED_UUID;
    }

    @ExpectPlatform
    public static void onPlayerDestroyItem(Player player, ItemStack stack, InteractionHand hand) {
        // Will get replaced at run time
    }

    public static void decrStackSize(ItemStack stack, int amount, LivingEntity entity) {
        stack.shrink(amount);
        if (stack.isEmpty() && entity instanceof Player) {
            Player player = (Player) entity;
            onPlayerDestroyItem(player, stack, player.swingingArm);
        }
    }

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    @Nullable
    public static Tuple<InteractionHand, Integer> findAnyItemSlot(Player player, Collection<Item> item) {
        if (isItemInList(player.getMainHandItem(), item))
            return new Tuple<>(InteractionHand.MAIN_HAND, player.getInventory().selected);
        if (isItemInList(player.getOffhandItem(), item))
            return new Tuple<>(InteractionHand.OFF_HAND, 0);
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack itemstack = player.getInventory().getItem(i);
            if (isItemInList(itemstack, item))
                return new Tuple<>(InteractionHand.MAIN_HAND, i);
        }
        return null;
    }

    public static boolean consumeInventoryItem(Player player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }

    public static boolean consumeAnyInventoryItem(Player player, Collection<Item> item) {
        Tuple<InteractionHand, Integer> slot = findAnyItemSlot(player, item);
        if (slot == null) return false;
        NonNullList<ItemStack> inv = slot.getA() == InteractionHand.OFF_HAND ? player.getInventory().offhand :
                player.getInventory().items;
        inv.get(slot.getB()).shrink(1);
        return true;
    }

}
