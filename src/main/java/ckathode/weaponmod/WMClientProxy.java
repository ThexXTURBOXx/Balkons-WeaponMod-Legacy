package ckathode.weaponmod;

import ckathode.weaponmod.entity.EntityCannon;
import ckathode.weaponmod.entity.EntityDummy;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import ckathode.weaponmod.entity.projectile.EntitySpear;
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
        RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, RenderKnife::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, RenderSpear::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, RenderJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, RenderMusketBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, RenderCrossbowBolt::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlowgunDart.class, RenderBlowgunDart::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, RenderDynamite::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFlail.class, RenderFlail::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, RenderCannon::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, RenderCannonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlunderShot.class, RenderBlunderShot::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, RenderDummy::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, RenderBoomerang::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, RenderMusketBullet::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMortarShell.class, RenderMortarShell::new);
    }
}
