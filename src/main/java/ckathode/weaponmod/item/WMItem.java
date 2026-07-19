package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import org.jetbrains.annotations.Nullable;

public class WMItem extends Item {

    public WMItem(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public WMItem(String modId, String id) {
        this(modId, id, new Properties());
    }

    public WMItem(String id, Properties properties) {
        this(BalkonsWeaponMod.MOD_ID, id, properties);
    }

    public WMItem(String modId, String id, Properties properties) {
        super(properties.group(ItemGroup.COMBAT));
        setRegistryName(new ResourceLocation(modId, id));
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.ATTACK_DAMAGE_MODIFIER;
    }

    public static UUID getAttackSpeedModifierUUID() {
        return Item.ATTACK_SPEED_MODIFIER;
    }

    public static void decrStackSize(ItemStack stack, int amount, EntityLivingBase entity) {
        stack.shrink(amount);
        if (stack.isEmpty() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, stack, player.swingingHand));
        }
    }

    public static boolean isItemInTag(ItemStack stack, Tag<Item> tag) {
        return stack != null && tag.contains(stack.getItem());
    }

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    @Nullable
    public static Tuple<EnumHand, Integer> findAnyItemSlot(EntityPlayer player, Tag<Item> tag) {
        return findAnyItemSlot(player, is -> isItemInTag(is, tag));
    }

    @Nullable
    public static Tuple<EnumHand, Integer> findAnyItemSlot(EntityPlayer player, Collection<Item> items) {
        return findAnyItemSlot(player, is -> isItemInList(is, items));
    }

    @Nullable
    public static Tuple<EnumHand, Integer> findAnyItemSlot(EntityPlayer player, Predicate<ItemStack> matcher) {
        if (matcher.test(player.getHeldItemMainhand()))
            return new Tuple<>(EnumHand.MAIN_HAND, player.inventory.currentItem);
        if (matcher.test(player.getHeldItemOffhand()))
            return new Tuple<>(EnumHand.OFF_HAND, 0);
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (matcher.test(itemstack))
                return new Tuple<>(EnumHand.MAIN_HAND, i);
        }
        return null;
    }

    public static boolean consumeInventoryItem(EntityPlayer player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }

    public static boolean consumeAnyInventoryItem(EntityPlayer player, Tag<Item> item) {
        Tuple<EnumHand, Integer> slot = findAnyItemSlot(player, item);
        if (slot == null) return false;
        NonNullList<ItemStack> inv = slot.getA() == EnumHand.OFF_HAND ? player.inventory.offHandInventory :
                player.inventory.mainInventory;
        inv.get(slot.getB()).shrink(1);
        return true;
    }

    public static boolean consumeAnyInventoryItem(EntityPlayer player, Collection<Item> items) {
        Tuple<EnumHand, Integer> slot = findAnyItemSlot(player, items);
        if (slot == null) return false;
        NonNullList<ItemStack> inv = slot.getA() == EnumHand.OFF_HAND ? player.inventory.offHandInventory :
                player.inventory.mainInventory;
        inv.get(slot.getB()).shrink(1);
        return true;
    }

    public static boolean consumeAnyInventoryItem(EntityPlayer player, Predicate<ItemStack> matcher) {
        Tuple<EnumHand, Integer> slot = findAnyItemSlot(player, matcher);
        if (slot == null) return false;
        NonNullList<ItemStack> inv = slot.getA() == EnumHand.OFF_HAND ? player.inventory.offHandInventory :
                player.inventory.mainInventory;
        inv.get(slot.getB()).shrink(1);
        return true;
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        if (entity != null && entity.getActiveItemStack() == stack && stack.getItem() instanceof WMItem &&
            entity.isActiveItemStackBlocking())
            return true;
        return super.isShield(stack, entity);
    }

}
