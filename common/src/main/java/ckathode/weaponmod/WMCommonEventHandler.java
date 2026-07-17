package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import me.shedaniel.architectury.event.events.EntityEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
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
        ItemStack stack = entity.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return InteractionResult.PASS;
        if (!(item instanceof IItemWeapon)) return InteractionResult.PASS;
        if (entity.isInvulnerableTo(source)) return InteractionResult.PASS;
        if (entity.isDeadOrDying()) return InteractionResult.PASS;
        if (source.isFire() && entity.hasEffect(MobEffects.FIRE_RESISTANCE))
            return InteractionResult.PASS;
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.abilities.invulnerable && !source.isBypassInvul())
                return InteractionResult.PASS;
        }

        entity.stopUsingItem();
        return InteractionResult.PASS;
    }

    public static void init() {
        EntityEvent.LIVING_ATTACK.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
    }

}
