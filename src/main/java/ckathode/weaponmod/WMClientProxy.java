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
import ckathode.weaponmod.item.DartType;
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
import ckathode.weaponmod.render.RenderLongItem;
import ckathode.weaponmod.render.RenderMortarShell;
import ckathode.weaponmod.render.RenderMusketBullet;
import ckathode.weaponmod.render.RenderSpear;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        WMClientEventHandler clientEventHandler = new WMClientEventHandler();
        FMLCommonHandler.instance().bus().register(clientEventHandler);
        MinecraftForge.EVENT_BUS.register(clientEventHandler);
        MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded());
    }

    @Override
    public void registerPackets(final WMMessagePipeline pipeline) {
        super.registerPackets(pipeline);
    }

    @Override
    public void registerRenderersItem(final WeaponModConfig config) {
        WMItemVariants.expectItemVariants(BalkonsWeaponMod.crossbow, "-loaded");
        WMItemVariants.expectItemVariants("weaponmod:flail", "-thrown");
        for (DartType type : DartType.dartTypes) {
            if (type != null) {
                String variant = type.getIconVariantName();
                WMItemVariants.expectItemVariants(BalkonsWeaponMod.dart, variant);
            }
        }

        RenderLongItem longRender = new RenderLongItem();
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdWood, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdStone, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdSteel, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdDiamond, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.halberdGold, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearWood, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearStone, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearSteel, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearDiamond, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.spearGold, longRender);
        MinecraftForgeClient.registerItemRenderer(BalkonsWeaponMod.javelin, longRender);
    }

    @Override
    public void registerRenderersEntity(final WeaponModConfig config) {
        RenderingRegistry.registerEntityRenderingHandler(EntityKnife.class, new RenderKnife());
        RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSpear());
        RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RenderJavelin());
        RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, new RenderMusketBullet());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossbowBolt.class, new RenderCrossbowBolt());
        RenderingRegistry.registerEntityRenderingHandler(EntityBlowgunDart.class, new RenderBlowgunDart());
        RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderDynamite());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlail.class, new RenderFlail());
        RenderingRegistry.registerEntityRenderingHandler(EntityCannon.class, new RenderCannon());
        RenderingRegistry.registerEntityRenderingHandler(EntityCannonBall.class, new RenderCannonBall());
        RenderingRegistry.registerEntityRenderingHandler(EntityBlunderShot.class, new RenderBlunderShot());
        RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, new RenderDummy());
        RenderingRegistry.registerEntityRenderingHandler(EntityBoomerang.class, new RenderBoomerang());
        RenderingRegistry.registerEntityRenderingHandler(EntityMusketBullet.class, new RenderMusketBullet());
        RenderingRegistry.registerEntityRenderingHandler(EntityMortarShell.class, new RenderMortarShell());
    }
}
