package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.WeaponModConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class BalkonsWeaponModConfigIntegration {

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (client, parent) -> AutoConfigClient.getConfigScreen(WeaponModConfig.class, parent).get());
    }

}
