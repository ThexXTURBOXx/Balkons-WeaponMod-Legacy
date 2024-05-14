package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public final class WMItemProperties {

    private static final ResourceLocation RELOAD_GETTER_ID = new ResourceLocation(MOD_ID, "reload");
    private static final ClampedItemPropertyFunction RELOAD_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !RangedComponent.isReloaded(stack)) ? 1.0f : 0.0f;

    private static final ResourceLocation RELOADED_GETTER_ID = new ResourceLocation(MOD_ID, "reloaded");
    private static final ClampedItemPropertyFunction RELOADED_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    RangedComponent.isReloaded(stack) ? 1.0f : 0.0f;

    private static final ResourceLocation BOOMERANG_READY_GETTER_ID = new ResourceLocation(MOD_ID, "boomerang-ready");
    private static final ClampedItemPropertyFunction BOOMERANG_READY_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    (entity != null && entity.isUsingItem() && entity.getUseItem() == stack) ? 1.0f : 0.0f;

    private static final ResourceLocation FLAIL_THROWN_GETTER_ID = new ResourceLocation(MOD_ID, "flail-thrown");
    private static final ClampedItemPropertyFunction FLAIL_THROWN_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    entity instanceof Player && entity.getMainHandItem() == stack && PlayerWeaponData.isFlailThrown((Player) entity) ? 1.0f : 0.0f;

    private static final ResourceLocation HALBERD_STATE_GETTER_ID = new ResourceLocation(MOD_ID, "halberd-state");
    private static final ClampedItemPropertyFunction HALBERD_STATE_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;

    private WMItemProperties() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        ItemPropertiesRegistry.register(RangedCompBlowgun.ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(RangedCompBlunderbuss.ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(RangedCompCrossbow.ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(RangedCompMortar.ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.WOOD_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.STONE_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.IRON_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.GOLD_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.DIAMOND_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);
        ItemPropertiesRegistry.register(ItemMusket.NETHERITE_ITEM,
                RELOAD_GETTER_ID, RELOAD_GETTER);

        ItemPropertiesRegistry.register(RangedCompCrossbow.ITEM,
                RELOADED_GETTER_ID, RELOADED_GETTER);

        ItemPropertiesRegistry.register(MeleeCompBoomerang.WOOD_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(MeleeCompBoomerang.STONE_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(MeleeCompBoomerang.IRON_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(MeleeCompBoomerang.GOLD_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(MeleeCompBoomerang.DIAMOND_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);
        ItemPropertiesRegistry.register(MeleeCompBoomerang.NETHERITE_ITEM,
                BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER);

        ItemPropertiesRegistry.register(ItemFlail.WOOD_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(ItemFlail.STONE_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(ItemFlail.IRON_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(ItemFlail.GOLD_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(ItemFlail.DIAMOND_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);
        ItemPropertiesRegistry.register(ItemFlail.NETHERITE_ITEM,
                FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER);

        ItemPropertiesRegistry.register(MeleeCompHalberd.WOOD_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(MeleeCompHalberd.STONE_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(MeleeCompHalberd.IRON_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(MeleeCompHalberd.GOLD_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(MeleeCompHalberd.DIAMOND_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
        ItemPropertiesRegistry.register(MeleeCompHalberd.NETHERITE_ITEM,
                HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER);
    }

}
