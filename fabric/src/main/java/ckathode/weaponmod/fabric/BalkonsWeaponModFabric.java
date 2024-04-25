package ckathode.weaponmod.fabric;

import ckathode.weaponmod.fabriclike.BalkonsWeaponModFabricLike;
import net.fabricmc.api.ModInitializer;

public class BalkonsWeaponModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BalkonsWeaponModFabricLike.init();
    }

}
