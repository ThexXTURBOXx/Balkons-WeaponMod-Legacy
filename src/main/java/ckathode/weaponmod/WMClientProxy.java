package ckathode.weaponmod;

import ckathode.weaponmod.render.GuiOverlayReloaded;
import ckathode.weaponmod.render.RenderBlowgunDart;
import ckathode.weaponmod.render.RenderBlunderShot;
import ckathode.weaponmod.render.RenderBoomerang;
import ckathode.weaponmod.render.RenderCannon;
import ckathode.weaponmod.render.RenderCannonBall;
import ckathode.weaponmod.render.RenderCrossbowBolt;
import ckathode.weaponmod.render.RenderDummy;
import ckathode.weaponmod.render.RenderDynamite;
import ckathode.weaponmod.render.RenderFlail;
import ckathode.weaponmod.render.RenderJavelin;
import ckathode.weaponmod.render.RenderKnife;
import ckathode.weaponmod.render.RenderMortarShell;
import ckathode.weaponmod.render.RenderMusketBullet;
import ckathode.weaponmod.render.RenderSpear;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new WMClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded());
    }

    @Override
    public void registerRenderersEntity() {
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityKnife, RenderKnife::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entitySpear, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityJavelin, RenderJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityMusketBullet, RenderMusketBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCrossbowBolt, RenderCrossbowBolt::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBlowgunDart, RenderBlowgunDart::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityDynamite, RenderDynamite::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityFlail, RenderFlail::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCannon, RenderCannon::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityCannonBall, RenderCannonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBlunderShot, RenderBlunderShot::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityDummy, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityBoomerang, RenderBoomerang::new);
        RenderingRegistry.registerEntityRenderingHandler(BalkonsWeaponMod.entityMortarShell, RenderMortarShell::new);
    }
}
