package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity, SynchedEntityData.Builder builder) {
        if (entity instanceof Player) {
            PlayerWeaponData.initPlayerWeaponData((Player) entity, builder);
        }
    }

    public static EventResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source, float amount) {
        if (!(entity instanceof Player player)) return EventResult.pass();
        ItemStack stack = player.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (!(item instanceof IItemWeapon)) return EventResult.pass();

        player.stopUsingItem();
        return EventResult.pass();
    }

    public static void init() {
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
    }

}
