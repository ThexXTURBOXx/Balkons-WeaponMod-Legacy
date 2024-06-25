package ckathode.weaponmod.forge;

import ckathode.weaponmod.BalkonsWeaponMod;
import me.shedaniel.architectury.platform.forge.EventBuses;
import me.shedaniel.architectury.utils.Env;
import me.shedaniel.architectury.utils.EnvExecutor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

@Mod(MOD_ID)
public class BalkonsWeaponModForge {

    public final IConditionSerializer<?> configConditional;

    public BalkonsWeaponModForge() {
        EventBuses.registerModEventBus(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        EnvExecutor.runInEnv(Env.CLIENT,
                () -> () -> MinecraftForge.EVENT_BUS.register(new WMClientEventHandlerForge()));
        MinecraftForge.EVENT_BUS.register(new WMCommonEventHandlerForge());

        configConditional = CraftingHelper.register(new WMConfigConditionForge.Serializer());

        BalkonsWeaponMod.init();
        BalkonsWeaponModConfigIntegration.registerConfigScreen();
    }

}
