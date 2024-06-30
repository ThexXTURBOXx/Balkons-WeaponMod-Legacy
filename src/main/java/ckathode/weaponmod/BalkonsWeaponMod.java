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
import ckathode.weaponmod.entity.projectile.dispense.DispenseBlowgunDart;
import ckathode.weaponmod.entity.projectile.dispense.DispenseBlunderShot;
import ckathode.weaponmod.entity.projectile.dispense.DispenseCannonBall;
import ckathode.weaponmod.entity.projectile.dispense.DispenseCrossbowBolt;
import ckathode.weaponmod.entity.projectile.dispense.DispenseDynamite;
import ckathode.weaponmod.entity.projectile.dispense.DispenseJavelin;
import ckathode.weaponmod.entity.projectile.dispense.DispenseMortarShell;
import ckathode.weaponmod.entity.projectile.dispense.DispenseMusketBullet;
import ckathode.weaponmod.item.ItemBlowgunDart;
import ckathode.weaponmod.item.ItemCannon;
import ckathode.weaponmod.item.ItemDummy;
import ckathode.weaponmod.item.ItemDynamite;
import ckathode.weaponmod.item.ItemFlail;
import ckathode.weaponmod.item.ItemJavelin;
import ckathode.weaponmod.item.ItemMelee;
import ckathode.weaponmod.item.ItemMusket;
import ckathode.weaponmod.item.ItemShooter;
import ckathode.weaponmod.item.WMItem;
import ckathode.weaponmod.network.WMMessagePipeline;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber
@Mod(modid = BalkonsWeaponMod.MOD_ID, name = BalkonsWeaponMod.MOD_NAME, version = BalkonsWeaponMod.MOD_VERSION,
        updateJSON = BalkonsWeaponMod.UPDATE_JSON, acceptedMinecraftVersions = "@MC_RANGE@")
public class BalkonsWeaponMod {
    public static final String MOD_ID = "@MOD_ID@";
    public static final String MOD_NAME = "@MOD_NAME@";
    public static final String MOD_VERSION = "@MOD_VERSION@";
    public static final String UPDATE_JSON = "@MOD_UPDATE_JSON@";
    @Mod.Instance(MOD_ID)
    public static BalkonsWeaponMod instance;
    public static Logger modLog;
    @SidedProxy(clientSide = "ckathode.weaponmod.WMClientProxy", serverSide = "ckathode.weaponmod.WMCommonProxy")
    public static WMCommonProxy proxy;
    public static ItemJavelin javelin;
    public static ItemMelee spearWood;
    public static ItemMelee spearStone;
    public static ItemMelee spearSteel;
    public static ItemMelee spearDiamond;
    public static ItemMelee spearGold;
    public static ItemMelee halberdWood;
    public static ItemMelee halberdStone;
    public static ItemMelee halberdSteel;
    public static ItemMelee halberdDiamond;
    public static ItemMelee halberdGold;
    public static ItemMelee knifeWood;
    public static ItemMelee knifeStone;
    public static ItemMelee knifeSteel;
    public static ItemMelee knifeDiamond;
    public static ItemMelee knifeGold;
    public static ItemMusket bayonetWood;
    public static ItemMusket bayonetStone;
    public static ItemMusket bayonetSteel;
    public static ItemMusket bayonetDiamond;
    public static ItemMusket bayonetGold;
    public static ItemMusket musket;
    public static WMItem musketBullet;
    public static WMItem gunStock;
    public static WMItem musketIronPart;
    public static ItemMelee battleaxeWood;
    public static ItemMelee battleaxeStone;
    public static ItemMelee battleaxeSteel;
    public static ItemMelee battleaxeDiamond;
    public static ItemMelee battleaxeGold;
    public static ItemMelee warhammerWood;
    public static ItemMelee warhammerStone;
    public static ItemMelee warhammerSteel;
    public static ItemMelee warhammerDiamond;
    public static ItemMelee warhammerGold;
    public static ItemShooter crossbow;
    public static WMItem bolt;
    public static ItemShooter blowgun;
    public static ItemBlowgunDart dart;
    public static ItemDynamite dynamite;
    public static ItemFlail flailWood;
    public static ItemFlail flailStone;
    public static ItemFlail flailSteel;
    public static ItemFlail flailDiamond;
    public static ItemFlail flailGold;
    public static ItemMelee fireRod;
    public static ItemCannon cannon;
    public static WMItem cannonBall;
    public static WMItem blunderShot;
    public static ItemShooter blunderbuss;
    public static WMItem blunderIronPart;
    public static ItemDummy dummy;
    public static ItemMelee boomerangWood;
    public static ItemMelee boomerangStone;
    public static ItemMelee boomerangSteel;
    public static ItemMelee boomerangDiamond;
    public static ItemMelee boomerangGold;
    public static ItemMelee katanaWood;
    public static ItemMelee katanaStone;
    public static ItemMelee katanaSteel;
    public static ItemMelee katanaDiamond;
    public static ItemMelee katanaGold;
    public static ItemShooter flintlockPistol;
    public static WMItem mortarShell;
    public static ItemShooter mortar;
    public static WMItem mortarIronPart;
    public WeaponModConfig modConfig;
    public WMMessagePipeline messagePipeline;
    public final IConditionFactory configConditional;

    public BalkonsWeaponMod() {
        instance = this;
        messagePipeline = new WMMessagePipeline();
        MinecraftForge.EVENT_BUS.register(this);

        configConditional = new WMConfigCondition();
        CraftingHelper.register(new ResourceLocation(MOD_ID, "config_conditional"), configConditional);
    }

    @Mod.EventHandler
    public void preInitMod(final FMLPreInitializationEvent event) {
        modLog = event.getModLog();

        modConfig = new WeaponModConfig(new Configuration(event.getSuggestedConfigurationFile()));
        modConfig.addEnableSetting("spear");
        modConfig.addEnableSetting("halberd");
        modConfig.addEnableSetting("battleaxe");
        modConfig.addEnableSetting("knife");
        modConfig.addEnableSetting("warhammer");
        modConfig.addEnableSetting("flail");
        modConfig.addEnableSetting("katana");
        modConfig.addEnableSetting("boomerang");
        modConfig.addEnableSetting("firerod");
        modConfig.addEnableSetting("javelin");
        modConfig.addEnableSetting("crossbow");
        modConfig.addEnableSetting("blowgun");
        modConfig.addEnableSetting("musket");
        modConfig.addEnableSetting("blunderbuss");
        modConfig.addEnableSetting("flintlock");
        modConfig.addEnableSetting("dynamite");
        modConfig.addEnableSetting("cannon");
        modConfig.addEnableSetting("dummy");
        modConfig.addEnableSetting("mortar");
        modConfig.addReloadTimeSetting("musket", 30);
        modConfig.addReloadTimeSetting("crossbow", 15);
        modConfig.addReloadTimeSetting("blowgun", 10);
        modConfig.addReloadTimeSetting("blunderbuss", 20);
        modConfig.addReloadTimeSetting("flintlock", 15);
        modConfig.addReloadTimeSetting("mortar", 50);
        modConfig.loadConfig();
        proxy.registerRenderersEntity(modConfig);
    }

    @Mod.EventHandler
    public void initMod(final FMLInitializationEvent event) {
        messagePipeline.initalize();
        proxy.registerPackets(messagePipeline);
        proxy.registerEventHandlers();
        registerWeapons();
        registerDispenseBehavior();
    }

    @Mod.EventHandler
    public void postInitMod(FMLPostInitializationEvent event) {
        messagePipeline.postInitialize();
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        proxy.registerRenderersItem(instance.modConfig);
    }

    private void registerWeapons() {
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:spear"), EntitySpear.class, "spear", 1,
                this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:knife"), EntityKnife.class, "knife", 2,
                this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:javelin"), EntityJavelin.class, "javelin"
                , 3, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:bullet"), EntityMusketBullet.class,
                "bullet", 4, this, 16, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:bolt"), EntityCrossbowBolt.class, "bolt"
                , 5, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dart"), EntityBlowgunDart.class, "dart",
                6, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dynamite"), EntityDynamite.class,
                "dynamite", 7, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:flail"), EntityFlail.class, "flail", 8,
                this, 32, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:cannon"), EntityCannon.class, "cannon",
                9, this, 64, 128, false);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:cannonball"), EntityCannonBall.class,
                "cannonball", 10, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:shot"), EntityBlunderShot.class, "shot",
                11, this, 16, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dummy"), EntityDummy.class, "dummy", 12,
                this, 64, 20, false);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:boomerang"), EntityBoomerang.class,
                "boomerang", 13, this, 64, 20, true);
        EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:shell"), EntityMortarShell.class, "shell"
                , 14, this, 64, 20, true);

        if (modConfig.isEnabled("spear")) {
            GameRegistry.addSmelting(spearSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(spearGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("halberd")) {
            GameRegistry.addSmelting(halberdSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(halberdGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("knife")) {
            GameRegistry.addSmelting(knifeSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(knifeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("battleaxe")) {
            GameRegistry.addSmelting(battleaxeSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(battleaxeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("warhammer")) {
            GameRegistry.addSmelting(warhammerSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(warhammerGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("flail")) {
            GameRegistry.addSmelting(flailSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(flailGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("boomerang")) {
            GameRegistry.addSmelting(boomerangSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(boomerangGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("katana")) {
            GameRegistry.addSmelting(katanaSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(katanaGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(spearWood = WMItemBuilder.createStandardSpear("spear.wood", ToolMaterial.WOOD));
        registry.register(spearStone = WMItemBuilder.createStandardSpear("spear.stone", ToolMaterial.STONE));
        registry.register(spearSteel = WMItemBuilder.createStandardSpear("spear.iron", ToolMaterial.IRON));
        registry.register(spearGold = WMItemBuilder.createStandardSpear("spear.gold", ToolMaterial.GOLD));
        registry.register(spearDiamond = WMItemBuilder.createStandardSpear("spear.diamond", ToolMaterial.DIAMOND));

        registry.register(halberdWood = WMItemBuilder.createStandardHalberd("halberd.wood", ToolMaterial.WOOD));
        registry.register(halberdStone = WMItemBuilder.createStandardHalberd("halberd.stone", ToolMaterial.STONE));
        registry.register(halberdSteel = WMItemBuilder.createStandardHalberd("halberd.iron", ToolMaterial.IRON));
        registry.register(halberdGold = WMItemBuilder.createStandardHalberd("halberd.gold", ToolMaterial.GOLD));
        registry.register(halberdDiamond =
                WMItemBuilder.createStandardHalberd("halberd.diamond", ToolMaterial.DIAMOND));

        registry.register(battleaxeWood = WMItemBuilder.createStandardBattleaxe("battleaxe.wood", ToolMaterial.WOOD));
        registry.register(battleaxeStone =
                WMItemBuilder.createStandardBattleaxe("battleaxe.stone", ToolMaterial.STONE));
        registry.register(battleaxeSteel = WMItemBuilder.createStandardBattleaxe("battleaxe.iron", ToolMaterial.IRON));
        registry.register(battleaxeGold = WMItemBuilder.createStandardBattleaxe("battleaxe.gold", ToolMaterial.GOLD));
        registry.register(battleaxeDiamond =
                WMItemBuilder.createStandardBattleaxe("battleaxe.diamond", ToolMaterial.DIAMOND));

        registry.register(knifeWood = WMItemBuilder.createStandardKnife("knife.wood", ToolMaterial.WOOD));
        registry.register(knifeStone = WMItemBuilder.createStandardKnife("knife.stone", ToolMaterial.STONE));
        registry.register(knifeSteel = WMItemBuilder.createStandardKnife("knife.iron", ToolMaterial.IRON));
        registry.register(knifeGold = WMItemBuilder.createStandardKnife("knife.gold", ToolMaterial.GOLD));
        registry.register(knifeDiamond = WMItemBuilder.createStandardKnife("knife.diamond", ToolMaterial.DIAMOND));

        registry.register(warhammerWood = WMItemBuilder.createStandardWarhammer("warhammer.wood", ToolMaterial.WOOD));
        registry.register(warhammerStone =
                WMItemBuilder.createStandardWarhammer("warhammer.stone", ToolMaterial.STONE));
        registry.register(warhammerSteel = WMItemBuilder.createStandardWarhammer("warhammer.iron", ToolMaterial.IRON));
        registry.register(warhammerGold = WMItemBuilder.createStandardWarhammer("warhammer.gold", ToolMaterial.GOLD));
        registry.register(warhammerDiamond =
                WMItemBuilder.createStandardWarhammer("warhammer.diamond", ToolMaterial.DIAMOND));

        registry.register(flailWood = WMItemBuilder.createStandardFlail("flail.wood", ToolMaterial.WOOD));
        registry.register(flailStone = WMItemBuilder.createStandardFlail("flail.stone", ToolMaterial.STONE));
        registry.register(flailSteel = WMItemBuilder.createStandardFlail("flail.iron", ToolMaterial.IRON));
        registry.register(flailGold = WMItemBuilder.createStandardFlail("flail.gold", ToolMaterial.GOLD));
        registry.register(flailDiamond = WMItemBuilder.createStandardFlail("flail.diamond", ToolMaterial.DIAMOND));

        registry.register(katanaWood = WMItemBuilder.createStandardKatana("katana.wood", ToolMaterial.WOOD));
        registry.register(katanaStone = WMItemBuilder.createStandardKatana("katana.stone", ToolMaterial.STONE));
        registry.register(katanaSteel = WMItemBuilder.createStandardKatana("katana.iron", ToolMaterial.IRON));
        registry.register(katanaGold = WMItemBuilder.createStandardKatana("katana.gold", ToolMaterial.GOLD));
        registry.register(katanaDiamond = WMItemBuilder.createStandardKatana("katana.diamond", ToolMaterial.DIAMOND));

        registry.register(boomerangWood = WMItemBuilder.createStandardBoomerang("boomerang.wood", ToolMaterial.WOOD));
        registry.register(boomerangStone =
                WMItemBuilder.createStandardBoomerang("boomerang.stone", ToolMaterial.STONE));
        registry.register(boomerangSteel = WMItemBuilder.createStandardBoomerang("boomerang.iron", ToolMaterial.IRON));
        registry.register(boomerangGold = WMItemBuilder.createStandardBoomerang("boomerang.gold", ToolMaterial.GOLD));
        registry.register(boomerangDiamond =
                WMItemBuilder.createStandardBoomerang("boomerang.diamond", ToolMaterial.DIAMOND));

        registry.register(fireRod = WMItemBuilder.createStandardFirerod("firerod"));

        registry.register(javelin = WMItemBuilder.createStandardJavelin("javelin"));

        registry.register(crossbow = WMItemBuilder.createStandardCrossbow("crossbow"));
        registry.register(bolt = WMItemBuilder.createWMItem("bolt"));

        registry.register(blowgun = WMItemBuilder.createStandardBlowgun("blowgun"));
        registry.register(dart = WMItemBuilder.createStandardBlowgunDart("dart"));

        registry.register(bayonetWood = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.wood",
                ToolMaterial.WOOD, knifeWood));
        registry.register(bayonetStone = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.stone",
                ToolMaterial.STONE, knifeStone));
        registry.register(bayonetSteel = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.iron",
                ToolMaterial.IRON, knifeSteel));
        registry.register(bayonetGold = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.gold",
                ToolMaterial.GOLD, knifeGold));
        registry.register(bayonetDiamond = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.diamond",
                ToolMaterial.DIAMOND, knifeDiamond));
        registry.register(musket = WMItemBuilder.createStandardMusket("musket"));
        registry.register(musketIronPart = WMItemBuilder.createWMItem("musket-ironpart"));

        registry.register(blunderbuss = WMItemBuilder.createStandardBlunderbuss("blunderbuss"));
        registry.register(blunderIronPart = WMItemBuilder.createWMItem("blunder-ironpart"));
        registry.register(blunderShot = WMItemBuilder.createWMItem("shot"));

        registry.register(flintlockPistol = WMItemBuilder.createStandardFlintlock("flintlock"));

        registry.register(dynamite = WMItemBuilder.createStandardDynamite("dynamite"));

        registry.register(cannon = WMItemBuilder.createStandardCannon("cannon"));
        registry.register(cannonBall = WMItemBuilder.createWMItem("cannonball"));

        registry.register(dummy = WMItemBuilder.createStandardDummy("dummy"));

        registry.register(gunStock = WMItemBuilder.createWMItem("gun-stock"));

        registry.register(musketBullet = WMItemBuilder.createWMItem("bullet"));

        registry.register(mortar = WMItemBuilder.createStandardMortar("mortar"));
        registry.register(mortarIronPart = WMItemBuilder.createWMItem("mortar-ironpart"));
        registry.register(mortarShell = WMItemBuilder.createWMItem("shell"));

        registerDispenseBehavior();
    }

    private void registerDispenseBehavior() {
        if (musketBullet != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(musketBullet, new DispenseMusketBullet());
        }
        if (javelin != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(javelin, new DispenseJavelin());
        }
        if (bolt != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(bolt, new DispenseCrossbowBolt());
        }
        if (dart != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(dart, new DispenseBlowgunDart());
        }
        if (dynamite != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(dynamite, new DispenseDynamite());
        }
        if (blunderShot != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(blunderShot, new DispenseBlunderShot());
        }
        if (cannonBall != null) {
            DispenseCannonBall behavior = new DispenseCannonBall();
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(cannonBall, behavior);
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.GUNPOWDER, behavior);
        }
        if (mortarShell != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(mortarShell, new DispenseMortarShell());
        }
    }

}
