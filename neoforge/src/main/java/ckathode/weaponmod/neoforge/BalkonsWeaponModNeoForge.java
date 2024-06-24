package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.mojang.serialization.MapCodec;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

@Mod(MOD_ID)
public class BalkonsWeaponModNeoForge {

    public static final DeferredRegister<MapCodec<? extends ICondition>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<WMConfigConditionForge>> CONFIG_CONDITION =
            DATA_COMPONENT_TYPES.register(WMConfigConditionForge.CONDITION_ID.getPath(),
                    () -> WMConfigConditionForge.CODEC);

    public BalkonsWeaponModNeoForge(IEventBus modEventBus) {
        EnvExecutor.runInEnv(Env.CLIENT,
                () -> () -> NeoForge.EVENT_BUS.register(new WMClientEventHandlerForge.MainEvents()));
        EnvExecutor.runInEnv(Env.CLIENT,
                () -> () -> modEventBus.register(new WMClientEventHandlerForge.ModEvents()));

        DATA_COMPONENT_TYPES.register(modEventBus);

        BalkonsWeaponMod.init();
        BalkonsWeaponModConfigIntegration.registerConfigScreen();
    }

}
