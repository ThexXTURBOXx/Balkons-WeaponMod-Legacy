package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import me.shedaniel.architectury.event.events.EntityEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity) {
        if (entity instanceof Player) {
            PlayerWeaponData.initPlayerWeaponData((Player) entity);
        }
    }

    public static InteractionResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source,
                                                                  float amount) {
        if (!(entity instanceof Player)) return InteractionResult.PASS;
        Player player = (Player) entity;
        ItemStack stack = player.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (!(item instanceof IItemWeapon)) return InteractionResult.PASS;

        player.stopUsingItem();
        return InteractionResult.PASS;
    }

    public static void init() {
        EntityEvent.LIVING_ATTACK.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
    }

}
