package ckathode.weaponmod.item;

import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMRegistries;
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

    private static final ResourceLocation BLOCK_GETTER_ID = new ResourceLocation(MOD_ID, "block");
    private static final ClampedItemPropertyFunction BLOCK_GETTER =
            (@NotNull ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int i) ->
                    entity != null && entity.getUseItem() == stack ? 1.0f : 0.0f;

    private WMItemProperties() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        WMRegistries.ITEM_BLOWGUN.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_BLUNDERBUSS.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_CROSSBOW.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MORTAR.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_WOOD.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_STONE.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_IRON.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_GOLD.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_DIAMOND.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));
        WMRegistries.ITEM_MUSKET_NETHERITE.listen(item -> ItemPropertiesRegistry.register(
                item, RELOAD_GETTER_ID, RELOAD_GETTER));

        WMRegistries.ITEM_CROSSBOW.listen(item -> ItemPropertiesRegistry.register(
                item, RELOADED_GETTER_ID, RELOADED_GETTER));

        WMRegistries.ITEM_BOOMERANG_WOOD.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));
        WMRegistries.ITEM_BOOMERANG_STONE.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));
        WMRegistries.ITEM_BOOMERANG_IRON.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));
        WMRegistries.ITEM_BOOMERANG_GOLD.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));
        WMRegistries.ITEM_BOOMERANG_DIAMOND.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));
        WMRegistries.ITEM_BOOMERANG_NETHERITE.listen(item -> ItemPropertiesRegistry.register(
                item, BOOMERANG_READY_GETTER_ID, BOOMERANG_READY_GETTER));

        WMRegistries.ITEM_FLAIL_WOOD.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));
        WMRegistries.ITEM_FLAIL_STONE.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));
        WMRegistries.ITEM_FLAIL_IRON.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));
        WMRegistries.ITEM_FLAIL_GOLD.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));
        WMRegistries.ITEM_FLAIL_DIAMOND.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));
        WMRegistries.ITEM_FLAIL_NETHERITE.listen(item -> ItemPropertiesRegistry.register(
                item, FLAIL_THROWN_GETTER_ID, FLAIL_THROWN_GETTER));

        WMRegistries.ITEM_HALBERD_WOOD.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));
        WMRegistries.ITEM_HALBERD_STONE.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));
        WMRegistries.ITEM_HALBERD_IRON.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));
        WMRegistries.ITEM_HALBERD_GOLD.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));
        WMRegistries.ITEM_HALBERD_DIAMOND.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));
        WMRegistries.ITEM_HALBERD_NETHERITE.listen(item -> ItemPropertiesRegistry.register(
                item, HALBERD_STATE_GETTER_ID, HALBERD_STATE_GETTER));

        WMRegistries.ITEM_BATTLEAXE_WOOD.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
        WMRegistries.ITEM_BATTLEAXE_STONE.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
        WMRegistries.ITEM_BATTLEAXE_IRON.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
        WMRegistries.ITEM_BATTLEAXE_GOLD.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
        WMRegistries.ITEM_BATTLEAXE_DIAMOND.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
        WMRegistries.ITEM_BATTLEAXE_NETHERITE.listen(item -> ItemPropertiesRegistry.register(
                item, BLOCK_GETTER_ID, BLOCK_GETTER));
    }

}
