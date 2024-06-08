package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import net.fabricmc.api.ModInitializer;

public class BalkonsWeaponModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BalkonsWeaponMod.init();
        WMConfigConditionFabric.init();
    }

}
