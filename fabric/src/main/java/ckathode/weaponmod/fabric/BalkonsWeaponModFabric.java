package ckathode.weaponmod.fabric;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMCommonEventHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

public class BalkonsWeaponModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BalkonsWeaponMod.init();

        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) ->
                WMCommonEventHandler.MODIFY_LOOT_TABLE.invoker().modifyLootTable(lootManager, id, supplier::withPool));
    }

}
