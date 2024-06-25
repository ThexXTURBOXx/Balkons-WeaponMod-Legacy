package ckathode.weaponmod.forge;

import ckathode.weaponmod.WeaponModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

public class BalkonsWeaponModConfigIntegration {

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.CONFIGGUIFACTORY,
                () -> (client, parent) -> AutoConfig.getConfigScreen(WeaponModConfig.class, parent).get());
    }

}
