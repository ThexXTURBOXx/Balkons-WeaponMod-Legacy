package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WMCommonEventHandler {

    public static EventResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source, float amount) {
        ItemStack stack = entity.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (!(item instanceof IItemWeapon)) return EventResult.pass();
        if (entity instanceof Player player && player.isCreative() &&
            !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return EventResult.pass();

        entity.stopUsingItem();
        return EventResult.pass();
    }

    public static void init() {
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
    }

}
