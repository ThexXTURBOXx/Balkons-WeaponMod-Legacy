package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.WMItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
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
        if (event.getSource().isUnblockable() || !entity.isActiveItemStackBlocking()) return;
        if (!(stack.getItem() instanceof ItemMelee) && !(stack.getItem() instanceof ItemShooter) &&
            !(stack.getItem() instanceof WMItem)) return;
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).isCreative()) return;

        int i = 1 + MathHelper.floor(event.getAmount());
        stack.damageItem(i, entity);
        if (stack.stackSize <= 0 && entity instanceof EntityPlayer) {
            ((EntityPlayer) entity).inventory.deleteStack(stack);
        }
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        ItemStack stack = entity.getActiveItemStack();
        Item item = stack == null ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return;
        if (!(item instanceof IItemWeapon)) return;
        if (entity.isEntityInvulnerable(source)) return;
        if (entity.getHealth() <= 0) return;
        if (source.isFireDamage() && entity.isPotionActive(MobEffects.FIRE_RESISTANCE)) return;
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.capabilities.disableDamage && !source.canHarmInCreative()) return;
        }

        entity.resetActiveHand();
    }
}
