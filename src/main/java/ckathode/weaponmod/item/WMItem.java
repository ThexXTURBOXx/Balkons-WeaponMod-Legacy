package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import cpw.mods.fml.common.registry.GameRegistry;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class WMItem extends Item {

    public WMItem(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public WMItem(String modId, String id) {
        setTextureName(modId + ":" + id);
        GameRegistry.registerItem(this, id, modId);
        setUnlocalizedName(id);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.field_111210_e;
    }

    public static void decrStackSize(ItemStack stack, int amount, EntityLivingBase entity) {
        stack.stackSize -= amount;
        if (stack.stackSize <= 0 && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.inventory.mainInventory[player.inventory.currentItem] = null;
            MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, stack));
        }
    }

    public static void damageItem(ItemStack stack, int dmg, EntityLivingBase entity) {
        stack.damageItem(dmg, entity);
        if (stack.stackSize <= 0 && entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).destroyCurrentEquippedItem();
        }
    }

    public static boolean isItemInList(ItemStack stack, Collection<Item> items) {
        return stack != null && items.contains(stack.getItem());
    }

    public static int findAnyItemSlot(EntityPlayer player, Collection<Item> item) {
        if (isItemInList(player.getHeldItem(), item))
            return player.inventory.currentItem;
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
            ItemStack itemstack = player.inventory.getStackInSlot(i);
            if (isItemInList(itemstack, item)) return i;
        }
        return -1;
    }

    public static boolean consumeInventoryItem(EntityPlayer player, Item item) {
        return consumeAnyInventoryItem(player, Collections.singletonList(item));
    }

    public static boolean consumeAnyInventoryItem(EntityPlayer player, Collection<Item> item) {
        int slot = findAnyItemSlot(player, item);
        if (slot < 0) return false;
        ItemStack itemStack = player.inventory.mainInventory[slot];
        if (--itemStack.stackSize <= 0)
            player.inventory.mainInventory[slot] = null;
        return true;
    }

}
