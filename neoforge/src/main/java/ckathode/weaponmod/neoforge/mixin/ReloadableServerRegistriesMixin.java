package ckathode.weaponmod.neoforge.mixin;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.neoforge.WMCommonEventHandlerNeoForge;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ReloadableServerRegistries.class)
public abstract class ReloadableServerRegistriesMixin {

    @Unique
    private static final ThreadLocal<HolderLookup.Provider> CURRENT_PROVIDER = new ThreadLocal<>();

    @Inject(method = "lambda$scheduleRegistryLoad$5",
            at = @At("HEAD")
    )
    private static <T> void captureOps(LootDataType<T> lootDataType, ResourceManager resourceManager,
                                       RegistryOps<JsonElement> ops, CallbackInfoReturnable<WritableRegistry<T>> cir) {
        if (ops.lookupProvider instanceof RegistryOps.HolderLookupAdapter hla) {
            CURRENT_PROVIDER.set(hla.lookupProvider);
        }
    }

    @Inject(method = "lambda$scheduleRegistryLoad$3",
            at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/event/EventHooks;loadLootTable" +
                                                "(Lnet/minecraft/resources/ResourceLocation;" +
                                                "Lnet/minecraft/world/level/storage/loot/LootTable;)" +
                                                "Lnet/minecraft/world/level/storage/loot/LootTable;")
    )
    private static <T> void loadLootTable(LootDataType<T> arg, Map<ResourceLocation, T> map, ResourceLocation rl,
                                          Optional<T> optionalEntry, CallbackInfo ci,
                                          @Local(name = "lootTable") LootTable lootTable) {
        if (lootTable == LootTable.EMPTY)
            return;

        HolderLookup.Provider provider = CURRENT_PROVIDER.get();
        if (provider == null) {
            BalkonsWeaponMod.LOGGER.warn("Cannot modify loot tables because current HolderLookupProvider instance " +
                                         "could not be acquired!");
            return;
        }

        WMCommonEventHandlerNeoForge.onLootTableLoad(provider, rl, lootTable);
    }

    @Inject(method = "lambda$scheduleRegistryLoad$5",
            at = @At("RETURN")
    )
    private static <T> void cleanup(LootDataType<T> lootDataType, ResourceManager resourceManager,
                                    RegistryOps<JsonElement> ops, CallbackInfoReturnable<WritableRegistry<T>> cir) {
        CURRENT_PROVIDER.remove();
    }

}
