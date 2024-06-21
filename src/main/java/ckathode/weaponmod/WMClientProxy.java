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
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class WMClientProxy extends WMCommonProxy {
    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();
        MinecraftForge.EVENT_BUS.register(new WMClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new GuiOverlayReloaded());
    }

    @Override
    public void registerPackets(final WMMessagePipeline pipeline) {
        super.registerPackets(pipeline);
    }

    @Override
    public void registerRenderersItem(final WeaponModConfig config) {
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.halberdWood, "", "_state");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.halberdStone, "", "_state");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.halberdSteel, "", "_state");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.halberdDiamond, "", "_state");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.halberdGold, "", "_state");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeWood, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.knifeWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeStone, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.knifeStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeSteel, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.knifeSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeDiamond, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.knifeDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeGold, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.knifeGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearWood, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.spearWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearStone, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.spearStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearSteel, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.spearSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearDiamond, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.spearDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearGold, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.spearGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.javelin, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.javelin.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.fireRod, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.fireRod.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.musket, "", "_reload");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.bayonetWood, "", "_reload");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.bayonetStone, "", "_reload");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.bayonetSteel, "", "_reload");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.bayonetDiamond, "", "_reload");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.bayonetGold, "", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketBullet, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.musketBullet.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.gunStock, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.gunStock.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketIronPart, 0,
                new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.musketIronPart.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.crossbow, "", "-loaded", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bolt, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.bolt.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.blowgun, "", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 1, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".hunger", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 2, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".slow", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 3, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".damage", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dynamite, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dynamite.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailWood, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailStone, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailSteel, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailDiamond, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailGold, "", "-thrown");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannon, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.cannon.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannonBall, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.cannonBall.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderShot, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.blunderShot.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.blunderbuss, "", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderIronPart, 0,
                new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.blunderIronPart.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dummy, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.dummy.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.boomerangWood, "", "_ready");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.boomerangStone, "", "_ready");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.boomerangSteel, "", "_ready");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.boomerangDiamond, "", "_ready");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.boomerangGold, "", "_ready");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaWood, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.katanaWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaStone, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.katanaStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaSteel, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.katanaSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaDiamond, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.katanaDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaGold, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.katanaGold.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flintlockPistol, "", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerWood, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.warhammerWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerStone, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.warhammerStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerSteel, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.warhammerSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerDiamond, 0,
                new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.warhammerDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerGold, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.warhammerGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeWood, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.battleaxeWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeStone, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.battleaxeStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeSteel, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.battleaxeSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeDiamond, 0,
                new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.battleaxeDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeGold, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.battleaxeGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarShell, 0, new ModelResourceLocation(
                "weaponmod:" + BalkonsWeaponMod.mortarShell.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.mortar, "", "_reload");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarIronPart, 0,
                new ModelResourceLocation("weaponmod:" + BalkonsWeaponMod.mortarIronPart.getUnlocalizedName().substring(5), "inventory"));
    }

    @Override
    public void registerRenderersEntity(final WeaponModConfig config) {
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
