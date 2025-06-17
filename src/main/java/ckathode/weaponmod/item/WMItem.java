package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class WMItem extends Item {
    public WMItem(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public WMItem(String modId, String id) {
        setRegistryName(new ResourceLocation(modId, id));
        setTranslationKey(id);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.ATTACK_DAMAGE_MODIFIER;
    }

    public static UUID getAttackSpeedModifierUUID() {
        return Item.ATTACK_SPEED_MODIFIER;
    }

    public static void decrStackSize(ItemStack stack, int amount, EntityLivingBase entity) {
        stack.splitStack(amount);
        if (stack.isEmpty() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, stack, player.swingingHand));
        }
    }

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    @Nullable
    public static Tuple<EnumHand, Integer> findAnyItemSlot(EntityPlayer player, Collection<Item> item) {
        if (isItemInList(player.getHeldItemMainhand(), item))
            return new Tuple<>(EnumHand.MAIN_HAND, player.inventory.currentItem);
        if (isItemInList(player.getHeldItemOffhand(), item))
            return new Tuple<>(EnumHand.OFF_HAND, 0);
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (isItemInList(itemstack, item))
                return new Tuple<>(EnumHand.MAIN_HAND, i);
        }
        return null;
    }

    public static boolean consumeInventoryItem(EntityPlayer player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }

    public static boolean consumeAnyInventoryItem(EntityPlayer player, Collection<Item> item) {
        Tuple<EnumHand, Integer> slot = findAnyItemSlot(player, item);
        if (slot == null) return false;
        NonNullList<ItemStack> inv = slot.getFirst() == EnumHand.OFF_HAND ? player.inventory.offHandInventory :
                player.inventory.mainInventory;
        inv.get(slot.getSecond()).splitStack(1);
        return true;
    }

    @Override
    public boolean isShield(@Nonnull ItemStack stack, @Nullable EntityLivingBase entity) {
        if (entity != null && entity.getActiveItemStack() == stack && stack.getItem() instanceof WMItem &&
            entity.isActiveItemStackBlocking())
            return true;
        return super.isShield(stack, entity);
    }

}
