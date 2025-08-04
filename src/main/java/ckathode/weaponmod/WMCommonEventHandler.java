package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {
    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        ItemStack stack = entity.getActiveItemStack();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (!(item instanceof IItemWeapon)) return;

        entity.resetActiveHand();
    }
}
