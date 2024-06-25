package ckathode.weaponmod.forge;

import ckathode.weaponmod.WeaponModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fmlclient.ConfigGuiHandler;

public class BalkonsWeaponModConfigIntegration {

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                () -> new ConfigGuiHandler.ConfigGuiFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(WeaponModConfig.class, parent).get()));
    }

}
