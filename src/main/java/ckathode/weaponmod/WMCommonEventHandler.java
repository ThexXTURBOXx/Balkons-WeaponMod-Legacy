package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeCompFirerod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WMCommonEventHandler {

    @SubscribeEvent
    public void initPlayerWeaponData(EntityEvent.EntityConstructing event) {
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerWeaponData.initPlayerWeaponData((PlayerEntity) entity);
        }
    }

    @SubscribeEvent
    public void onEntityAttack(LivingAttackEvent event) {
        Entity source = event.getSource().getTrueSource();
        if (!(source instanceof LivingEntity)) return;
        if (source instanceof PlayerEntity) return; // Already handled in MeleeCompFirerod

        LivingEntity living = (LivingEntity) source;
        ItemStack stack = living.getHeldItemMainhand();
        Item item = stack.getItem();
        if (item == BalkonsWeaponMod.fireRod) {
            ((MeleeCompFirerod) BalkonsWeaponMod.fireRod.meleeComponent).applyFire(event.getEntityLiving(), stack);
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(LivingSpawnEvent.SpecialSpawn event) {
        WMMobEquipment.equipZombies(event.getEntityLiving(), event.getWorld());
    }

    @SubscribeEvent
    public void cancelBlockingOfRangedWeapons(LivingAttackEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        float amount = event.getAmount();

        ItemStack stack = entity.getActiveItemStack();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return;
        if (!(item instanceof IItemWeapon)) return;
        if (entity.isInvulnerableTo(source)) return;
        if (entity.getHealth() <= 0) return;
        if (source.isFireDamage() && entity.isPotionActive(Effects.FIRE_RESISTANCE)) return;
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (player.abilities.disableDamage && !source.canHarmInCreative()) return;
        }

        entity.resetActiveHand();
    }

    @SubscribeEvent
    public void registerLootTableAdditions(LootTableLoadEvent event) {
        WMLootTables.registerLootTableAdditions(event.getName(), event.getTable());
    }

}
