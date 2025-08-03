package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.WMItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void damageBlockingWeapons(LivingAttackEvent event) {
        if (event.getAmount() < 3.0f) return;
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack = entity.getActiveItemStack();
        if (stack == null) return;
        if (!entity.isActiveItemStackBlocking()) return;
        if (!(stack.getItem() instanceof ItemMelee) && !(stack.getItem() instanceof ItemShooter) &&
            !(stack.getItem() instanceof WMItem)) return;

        int i = 1 + MathHelper.floor_float(event.getAmount());
        stack.damageItem(i, entity);
        if (stack.stackSize <= 0 && entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).inventory.deleteStack(stack);
        }
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack = entity.getActiveItemStack();
        Item item = stack == null ? null : stack.getItem();
        if (!(item instanceof IItemWeapon)) return;

        entity.resetActiveHand();
    }
}
