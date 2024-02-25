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
import ckathode.weaponmod.network.WMMessagePipeline;
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
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        final WMClientEventHandler eventhandler = new WMClientEventHandler();
        MinecraftForge.EVENT_BUS.register(eventhandler);
        if (BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded.get()) {
            MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded(Minecraft.getInstance()));
        }
    }

    @Override
    public void registerPackets(final WMMessagePipeline pipeline) {
        super.registerPackets(pipeline);
    }

    @Override
    public void registerRenderersEntity(final WeaponModConfig config) {
        if (config.isEnabled("knife")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, RenderKnife::new);
        }
        if (config.isEnabled("spear")) {
            RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, RenderSpear::new);
        }
        if (config.isEnabled("javelin")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, RenderJavelin::new);
        }
        if (config.isEnabled("musket")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, RenderMusketBullet::new);
        }
        if (config.isEnabled("crossbow")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, RenderCrossbowBolt::new);
        }
        if (config.isEnabled("blowgun")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBlowgunDart.class, RenderBlowgunDart::new);
        }
        if (config.isEnabled("dynamite")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, RenderDynamite::new);
        }
        if (config.isEnabled("flail")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityFlail.class, RenderFlail::new);
        }
        if (config.isEnabled("cannon")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, RenderCannon::new);
            RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, RenderCannonBall::new);
        }
        if (config.isEnabled("blunderbuss")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBlunderShot.class, RenderBlunderShot::new);
        }
        if (config.isEnabled("dummy")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, RenderDummy::new);
        }
        if (config.isEnabled("boomerang")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, RenderBoomerang::new);
        }
        if (config.isEnabled("flintlock")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, RenderMusketBullet::new);
        }
        if (config.isEnabled("mortar")) {
            RenderingRegistry.registerEntityRenderingHandler(EntityMortarShell.class, RenderMortarShell::new);
        }
    }
}
