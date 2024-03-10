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
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        WMClientEventHandler eventhandler = new WMClientEventHandler();
        MinecraftForge.EVENT_BUS.register(eventhandler);
        if (BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded) {
            MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded(Minecraft.getMinecraft()));
        }
    }

    @Override
    public void registerPackets(final WMMessagePipeline pipeline) {
        super.registerPackets(pipeline);
    }

    @Override
    public void registerRenderersItem(final WeaponModConfig config) {
        if (config.isEnabled("halberd")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.halberdWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.halberdWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.halberdStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.halberdStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.halberdSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.halberdSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.halberdDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.halberdDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.halberdGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.halberdGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("knife")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.knifeWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.knifeStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.knifeSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.knifeDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.knifeGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("spear")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.spearWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.spearStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.spearSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.spearDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.spearGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("javelin")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.javelin, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.javelin.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("firerod")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.fireRod, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.fireRod.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("musket")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musket, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.musket.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.bayonetWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.bayonetStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.bayonetSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.bayonetDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.bayonetGold.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketBullet, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.musketBullet.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.gunStock, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.gunStock.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketIronPart, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.musketIronPart.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("crossbow")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.crossbow, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.crossbow.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bolt, 0, new ModelResourceLocation("weaponmod"
                                                                                                           + ":" + BalkonsWeaponMod.bolt.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("blowgun")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blowgun, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.blowgun.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 0, new ModelResourceLocation("weaponmod"
                                                                                                           + ":" + BalkonsWeaponMod.dart.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 1, new ModelResourceLocation("weaponmod"
                                                                                                           + ":" + BalkonsWeaponMod.dart.getTranslationKey().substring(5) + ".hunger", "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 2, new ModelResourceLocation("weaponmod"
                                                                                                           + ":" + BalkonsWeaponMod.dart.getTranslationKey().substring(5) + ".slow", "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 3, new ModelResourceLocation("weaponmod"
                                                                                                           + ":" + BalkonsWeaponMod.dart.getTranslationKey().substring(5) + ".damage", "inventory"));
        }
        if (config.isEnabled("dynamite")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dynamite, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.dynamite.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("flail")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flailWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.flailWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flailStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.flailStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flailSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.flailSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flailDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.flailDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flailGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.flailGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("cannon")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannon, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.cannon.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannonBall, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.cannonBall.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("blunderbuss")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderShot, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.blunderShot.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderbuss, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.blunderbuss.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderIronPart, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.blunderIronPart.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("dummy")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dummy, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.dummy.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("boomerang")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.boomerangWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.boomerangStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.boomerangSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangDiamond, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.boomerangDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.boomerangGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("katana")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.katanaWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.katanaStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.katanaSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaDiamond, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.katanaDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.katanaGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("flintlock")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flintlockPistol, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.flintlockPistol.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("warhammer")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.warhammerWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.warhammerStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.warhammerSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerDiamond, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.warhammerDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.warhammerGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("battleaxe")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeWood, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.battleaxeWood.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeStone, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.battleaxeStone.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeSteel, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.battleaxeSteel.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeDiamond, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.battleaxeDiamond.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeGold, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.battleaxeGold.getTranslationKey().substring(5), "inventory"));
        }
        if (config.isEnabled("mortar")) {
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarShell, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.mortarShell.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortar, 0, new ModelResourceLocation(
                    "weaponmod:" + BalkonsWeaponMod.mortar.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarIronPart, 0,
                    new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.mortarIronPart.getTranslationKey().substring(5), "inventory"));
        }
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
