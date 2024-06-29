package ckathode.weaponmod;

import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.WMItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (event.getAmount() < 3.0f) return;
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;
        ItemStack stack = player.getActiveItemStack();
        if (stack == null) return;
        if (!player.isActiveItemStackBlocking()) return;
        if (!(stack.getItem() instanceof ItemMelee) && !(stack.getItem() instanceof ItemShooter) &&
            !(stack.getItem() instanceof WMItem)) return;
        int i = 1 + MathHelper.floor(event.getAmount());
        stack.damageItem(i, player);
        if (stack.stackSize <= 0) {
            player.inventory.deleteStack(stack);
        }
    }
}
