package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WMItemProperties {

    public static final ResourceLocation RELOAD_GETTER_ID = BalkonsWeaponMod.id("reload");

    public record Reload() implements ConditionalItemModelProperty {
        public static final MapCodec<Reload> MAP_CODEC = MapCodec.unit(new Reload());

        public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity,
                           int i, ItemDisplayContext itemDisplayContext) {
            return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack &&
                   !RangedComponent.isReloaded(itemStack);
        }

        @NotNull
        public MapCodec<Reload> type() {
            return MAP_CODEC;
        }
    }

    public static final ResourceLocation RELOADED_GETTER_ID = BalkonsWeaponMod.id("reloaded");

    public record Reloaded() implements ConditionalItemModelProperty {
        public static final MapCodec<Reloaded> MAP_CODEC = MapCodec.unit(new Reloaded());

        public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity,
                           int i, ItemDisplayContext itemDisplayContext) {
            return RangedComponent.isReloaded(itemStack);
        }

        @NotNull
        public MapCodec<Reloaded> type() {
            return MAP_CODEC;
        }
    }

    public static final ResourceLocation FLAIL_THROWN_GETTER_ID = BalkonsWeaponMod.id("flail-thrown");

    public record FlailThrown() implements ConditionalItemModelProperty {
        public static final MapCodec<FlailThrown> MAP_CODEC = MapCodec.unit(new FlailThrown());

        public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity,
                           int i, ItemDisplayContext itemDisplayContext) {
            return livingEntity instanceof Player player && livingEntity.getMainHandItem() == itemStack &&
                   PlayerWeaponData.isFlailThrown(player);
        }

        @NotNull
        public MapCodec<FlailThrown> type() {
            return MAP_CODEC;
        }
    }

    public static final ResourceLocation HALBERD_STATE_GETTER_ID = BalkonsWeaponMod.id("halberd-state");

    public record HalberdState() implements ConditionalItemModelProperty {
        public static final MapCodec<HalberdState> MAP_CODEC = MapCodec.unit(new HalberdState());

        public boolean get(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity,
                           int i, ItemDisplayContext itemDisplayContext) {
            return MeleeCompHalberd.getHalberdState(itemStack);
        }

        @NotNull
        public MapCodec<HalberdState> type() {
            return MAP_CODEC;
        }
    }

    private WMItemProperties() {
        throw new UnsupportedOperationException();
    }

    public static void init() {
        ConditionalItemModelProperties.ID_MAPPER.put(RELOAD_GETTER_ID, Reload.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(RELOADED_GETTER_ID, Reloaded.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(FLAIL_THROWN_GETTER_ID, FlailThrown.MAP_CODEC);
        ConditionalItemModelProperties.ID_MAPPER.put(HALBERD_STATE_GETTER_ID, HalberdState.MAP_CODEC);
    }

}
