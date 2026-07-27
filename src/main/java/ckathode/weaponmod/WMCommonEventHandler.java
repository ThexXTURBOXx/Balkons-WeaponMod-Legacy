package ckathode.weaponmod;

import ckathode.weaponmod.item.MeleeCompFirerod;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class WMCommonEventHandler {

    @SubscribeEvent
    public void onEntityConstructed(EntityEvent.EntityConstructing event) {
        Entity entity = event.entity;
        if (entity instanceof EntityPlayer) {
            PlayerWeaponData.initPlayerWeaponData((EntityPlayer) entity);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        Entity source = event.source.getEntity();
        if (!(source instanceof EntityLivingBase)) return;
        if (source instanceof EntityPlayer) return; // Already handled in MeleeCompFirerod

        EntityLivingBase living = (EntityLivingBase) source;
        ItemStack stack = living.getHeldItem();
        if (stack == null) return;
        Item item = stack.getItem();
        if (item == BalkonsWeaponMod.fireRod) {
            ((MeleeCompFirerod) BalkonsWeaponMod.fireRod.meleeComponent).applyFire(event.entityLiving, stack);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        WMMobEquipment.equipZombies(event.entityLiving, event.world);
    }

}
