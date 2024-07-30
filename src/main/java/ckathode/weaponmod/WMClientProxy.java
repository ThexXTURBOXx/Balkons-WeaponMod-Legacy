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
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
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
                MOD_ID + ":" + BalkonsWeaponMod.knifeWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.knifeStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.knifeSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeDiamond, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.knifeDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.knifeGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.knifeGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.spearWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.spearStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.spearSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearDiamond, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.spearDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.spearGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.spearGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.javelin, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.javelin.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.fireRod, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.fireRod.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musket, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.musket.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bayonetWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bayonetStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bayonetSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetDiamond, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bayonetDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bayonetGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bayonetGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketBullet, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.musketBullet.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.gunStock, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.gunStock.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.musketIronPart, 0,
                new ModelResourceLocation(MOD_ID + ":" + BalkonsWeaponMod.musketIronPart.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.crossbow, "", "-loaded");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.bolt, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.bolt.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blowgun, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.blowgun.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 1, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".hunger", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 2, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".slow", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dart, 3, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dart.getUnlocalizedName().substring(5) + ".damage", "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dynamite, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dynamite.getUnlocalizedName().substring(5), "inventory"));
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailWood, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailStone, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailSteel, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailDiamond, "", "-thrown");
        WMItemVariants.registerItemVariants(BalkonsWeaponMod.flailGold, "", "-thrown");
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannon, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.cannon.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.cannonBall, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.cannonBall.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderShot, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.blunderShot.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderbuss, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.blunderbuss.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.blunderIronPart, 0,
                new ModelResourceLocation(MOD_ID + ":" + BalkonsWeaponMod.blunderIronPart.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.dummy, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.dummy.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.boomerangWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.boomerangStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.boomerangSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangDiamond, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.boomerangDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.boomerangGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.boomerangGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.katanaWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.katanaStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.katanaSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaDiamond, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.katanaDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.katanaGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.katanaGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.flintlockPistol, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.flintlockPistol.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.warhammerWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.warhammerStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.warhammerSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerDiamond, 0,
                new ModelResourceLocation(MOD_ID + ":" + BalkonsWeaponMod.warhammerDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.warhammerGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.warhammerGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeWood, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.battleaxeWood.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeStone, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.battleaxeStone.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeSteel, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.battleaxeSteel.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeDiamond, 0,
                new ModelResourceLocation(MOD_ID + ":" + BalkonsWeaponMod.battleaxeDiamond.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.battleaxeGold, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.battleaxeGold.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarShell, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.mortarShell.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortar, 0, new ModelResourceLocation(
                MOD_ID + ":" + BalkonsWeaponMod.mortar.getUnlocalizedName().substring(5), "inventory"));
        ModelLoader.setCustomModelResourceLocation(BalkonsWeaponMod.mortarIronPart, 0,
                new ModelResourceLocation(MOD_ID + ":" + BalkonsWeaponMod.mortarIronPart.getUnlocalizedName().substring(5), "inventory"));
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

    @Override
    public void registerSounds(WeaponModConfig config) {
        super.registerSounds(config);
        applySoundFix();
        IResourceManager resourceManager = Minecraft.getMinecraft().getResourceManager();
        if (resourceManager instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) resourceManager).registerReloadListener(a -> applySoundFix());
        }
    }

    private void applySoundFix() {
        // Add random.breath sound to game (can also be accessed via /playsound then!)
        SoundList list = new SoundList();
        SoundList.SoundEntry entry = new SoundList.SoundEntry();
        entry.setSoundEntryName("random/breath");
        list.setSoundCategory(SoundCategory.PLAYERS);
        list.getSoundList().add(entry);
        Minecraft.getMinecraft().getSoundHandler().loadSoundResource(
                new ResourceLocation("minecraft", "random.breath"), list);
    }
}
