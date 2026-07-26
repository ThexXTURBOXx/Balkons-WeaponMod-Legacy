package ckathode.weaponmod;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeCompFirerod;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LootEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.loot.LootDataManager;

public class WMCommonEventHandler {

    public static void constructEntity(Entity entity) {
        if (entity instanceof Player player) {
            PlayerWeaponData.initPlayerWeaponData(player);
        }
    }

    public static EventResult onEntityAttack(LivingEntity entity, DamageSource damageSource, float amount) {
        Entity source = damageSource.getEntity();
        if (!(source instanceof LivingEntity living)) return EventResult.pass();

        ItemStack stack = living.getMainHandItem();
        if (stack.is(MeleeCompFirerod.ITEM)) {
            ((MeleeCompFirerod) MeleeCompFirerod.ITEM.meleeComponent).applyFire(entity, stack);
        }

        return EventResult.pass();
    }

    public static void equipZombies(LivingEntity living, LevelAccessor level) {
        WMMobEquipment.equipZombies(living, level);
    }

    public static EventResult cancelBlockingOfRangedWeapons(LivingEntity entity, DamageSource source, float amount) {
        ItemStack stack = entity.getUseItem();
        Item item = stack.isEmpty() ? null : stack.getItem();
        if (Float.isFinite(amount) && amount <= 0) return EventResult.pass();
        if (!(item instanceof IItemWeapon)) return EventResult.pass();
        if (entity.isInvulnerableTo(source)) return EventResult.pass();
        if (entity.isDeadOrDying()) return EventResult.pass();
        if (source.is(DamageTypeTags.IS_FIRE) && entity.hasEffect(MobEffects.FIRE_RESISTANCE))
            return EventResult.pass();
        if (entity instanceof Player player) {
            if (player.getAbilities().invulnerable && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY))
                return EventResult.pass();
        }

        entity.stopUsingItem();
        return EventResult.pass();
    }

    public static void registerLootTableAdditions(LootDataManager lootDataManager, ResourceLocation id,
                                                  LootEvent.LootTableModificationContext context, boolean builtIn) {
        WMLootTables.registerLootTableAdditions(id, context, builtIn);
    }

    public static void init() {
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::cancelBlockingOfRangedWeapons);
        EntityEvent.LIVING_HURT.register(WMCommonEventHandler::onEntityAttack);
        LootEvent.MODIFY_LOOT_TABLE.register(WMCommonEventHandler::registerLootTableAdditions);
    }

}
