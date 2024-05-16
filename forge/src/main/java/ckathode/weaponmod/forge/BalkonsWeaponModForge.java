package ckathode.weaponmod.forge;

import ckathode.weaponmod.BalkonsWeaponMod;
import dev.architectury.platform.forge.EventBuses;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

@Mod(MOD_ID)
public class BalkonsWeaponModForge {

    public final IConditionSerializer<?> configConditional;

    public BalkonsWeaponModForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(MOD_ID, modBus);

        EnvExecutor.runInEnv(Env.CLIENT,
                () -> () -> MinecraftForge.EVENT_BUS.register(new WMClientEventHandlerForge.MainEvents()));
        EnvExecutor.runInEnv(Env.CLIENT,
                () -> () -> modBus.register(new WMClientEventHandlerForge.ModEvents()));
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandlerForge());

        configConditional = CraftingHelper.register(new WMConfigConditionForge.Serializer());

        BalkonsWeaponMod.init();
    }

}
