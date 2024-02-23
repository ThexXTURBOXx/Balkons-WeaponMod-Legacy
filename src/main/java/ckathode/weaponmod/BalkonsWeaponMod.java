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
import ckathode.weaponmod.item.MeleeCompBattleaxe;
import ckathode.weaponmod.item.MeleeCompBoomerang;
import ckathode.weaponmod.item.MeleeCompFirerod;
import ckathode.weaponmod.item.MeleeCompHalberd;
import ckathode.weaponmod.item.MeleeCompKnife;
import ckathode.weaponmod.item.MeleeCompNone;
import ckathode.weaponmod.item.MeleeCompSpear;
import ckathode.weaponmod.item.MeleeCompWarhammer;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedCompBlowgun;
import ckathode.weaponmod.item.RangedCompBlunderbuss;
import ckathode.weaponmod.item.RangedCompCrossbow;
import ckathode.weaponmod.item.RangedCompFlintlock;
import ckathode.weaponmod.item.RangedCompMortar;
import ckathode.weaponmod.item.WMItem;
import ckathode.weaponmod.network.WMMessagePipeline;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = "weaponmod", name = "Balkon's WeaponMod", version = "v1.19.2.5", acceptedMinecraftVersions = "[1.12.2]")
public class BalkonsWeaponMod {
    public static final List<Item> MOD_ITEMS;
    public static final String MOD_ID = "weaponmod";
    public static final String MOD_NAME = "Balkon's WeaponMod";
    public static final String MOD_VERSION = "v1.19.2.5";
    @Mod.Instance("weaponmod")
    public static BalkonsWeaponMod instance;
    public static Logger modLog;
    @SidedProxy(clientSide = "ckathode.weaponmod.WMClientProxy", serverSide = "ckathode.weaponmod.WMCommonProxy")
    public static WMCommonProxy proxy;
    public static Item javelin;
    public static Item spearWood;
    public static Item spearStone;
    public static Item spearSteel;
    public static Item spearDiamond;
    public static Item spearGold;
    public static Item halberdWood;
    public static Item halberdStone;
    public static Item halberdSteel;
    public static Item halberdDiamond;
    public static Item halberdGold;
    public static Item knifeWood;
    public static Item knifeStone;
    public static Item knifeSteel;
    public static Item knifeDiamond;
    public static Item knifeGold;
    public static Item bayonetWood;
    public static Item bayonetStone;
    public static Item bayonetSteel;
    public static Item bayonetDiamond;
    public static Item bayonetGold;
    public static Item musketBullet;
    public static Item musket;
    public static Item gunStock;
    public static Item musket_iron_part;
    public static Item battleaxeWood;
    public static Item battleaxeStone;
    public static Item battleaxeSteel;
    public static Item battleaxeDiamond;
    public static Item battleaxeGold;
    public static Item warhammerWood;
    public static Item warhammerStone;
    public static Item warhammerSteel;
    public static Item warhammerDiamond;
    public static Item warhammerGold;
    public static Item crossbow;
    public static Item bolt;
    public static Item blowgun;
    public static Item dart;
    public static Item dynamite;
    public static Item flailWood;
    public static Item flailStone;
    public static Item flailSteel;
    public static Item flailDiamond;
    public static Item flailGold;
    public static Item fireRod;
    public static Item cannon;
    public static Item cannonBall;
    public static Item blunderShot;
    public static Item blunderbuss;
    public static Item blunder_iron_part;
    public static Item dummy;
    public static Item boomerangWood;
    public static Item boomerangStone;
    public static Item boomerangSteel;
    public static Item boomerangDiamond;
    public static Item boomerangGold;
    public static Item katanaWood;
    public static Item katanaStone;
    public static Item katanaSteel;
    public static Item katanaDiamond;
    public static Item katanaGold;
    public static Item flintlockPistol;
    public static Item mortarShell;
    public static Item mortar;
    public static Item mortar_iron_part;
    public WeaponModConfig modConfig;
    public WMMessagePipeline messagePipeline;

    public BalkonsWeaponMod() {
        this.messagePipeline = new WMMessagePipeline();
    }

    @Mod.EventHandler
    public void preInitMod(final FMLPreInitializationEvent event) {
        BalkonsWeaponMod.modLog = event.getModLog();
        (this.modConfig = new WeaponModConfig(new Configuration(event.getSuggestedConfigurationFile()))).addEnableSetting("spear");
        this.modConfig.addEnableSetting("halberd");
        this.modConfig.addEnableSetting("battleaxe");
        this.modConfig.addEnableSetting("knife");
        this.modConfig.addEnableSetting("warhammer");
        this.modConfig.addEnableSetting("flail");
        this.modConfig.addEnableSetting("katana");
        this.modConfig.addEnableSetting("boomerang");
        this.modConfig.addEnableSetting("firerod");
        this.modConfig.addEnableSetting("javelin");
        this.modConfig.addEnableSetting("crossbow");
        this.modConfig.addEnableSetting("blowgun");
        this.modConfig.addEnableSetting("musket");
        this.modConfig.addEnableSetting("blunderbuss");
        this.modConfig.addEnableSetting("flintlock");
        this.modConfig.addEnableSetting("dynamite");
        this.modConfig.addEnableSetting("cannon");
        this.modConfig.addEnableSetting("dummy");
        this.modConfig.addEnableSetting("mortar");
        this.modConfig.addReloadTimeSetting("musket", 30);
        this.modConfig.addReloadTimeSetting("crossbow", 15);
        this.modConfig.addReloadTimeSetting("blowgun", 10);
        this.modConfig.addReloadTimeSetting("blunderbuss", 20);
        this.modConfig.addReloadTimeSetting("flintlock", 15);
        this.modConfig.addReloadTimeSetting("mortar", 50);
        this.modConfig.loadConfig();
        this.addModItems();
        BalkonsWeaponMod.proxy.registerRenderersEntity(this.modConfig);
    }

    @Mod.EventHandler
    public void initMod(final FMLInitializationEvent event) {
        this.messagePipeline.initalize();
        BalkonsWeaponMod.proxy.registerPackets(this.messagePipeline);
        BalkonsWeaponMod.proxy.registerEventHandlers();
        this.registerWeapons();
        this.registerDispenseBehavior();
    }

    @Mod.EventHandler
    public void postInitMod(final FMLPostInitializationEvent event) {
        this.messagePipeline.postInitialize();
    }

    private void addModItems() {
        if (this.modConfig.isEnabled("spear")) {
            BalkonsWeaponMod.spearWood = new ItemMelee("spear.wood", new MeleeCompSpear(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.spearStone = new ItemMelee("spear.stone", new MeleeCompSpear(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.spearSteel = new ItemMelee("spear.iron", new MeleeCompSpear(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.spearGold = new ItemMelee("spear.gold", new MeleeCompSpear(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.spearDiamond = new ItemMelee("spear.diamond",
                    new MeleeCompSpear(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("halberd")) {
            BalkonsWeaponMod.halberdWood = new ItemMelee("halberd.wood", new MeleeCompHalberd(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.halberdStone = new ItemMelee("halberd.stone",
                    new MeleeCompHalberd(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.halberdSteel = new ItemMelee("halberd.iron", new MeleeCompHalberd(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.halberdGold = new ItemMelee("halberd.gold", new MeleeCompHalberd(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.halberdDiamond = new ItemMelee("halberd.diamond",
                    new MeleeCompHalberd(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("battleaxe")) {
            BalkonsWeaponMod.battleaxeWood = new ItemMelee("battleaxe.wood",
                    new MeleeCompBattleaxe(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.battleaxeStone = new ItemMelee("battleaxe.stone",
                    new MeleeCompBattleaxe(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.battleaxeSteel = new ItemMelee("battleaxe.iron",
                    new MeleeCompBattleaxe(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.battleaxeGold = new ItemMelee("battleaxe.gold",
                    new MeleeCompBattleaxe(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.battleaxeDiamond = new ItemMelee("battleaxe.diamond",
                    new MeleeCompBattleaxe(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("knife")) {
            BalkonsWeaponMod.knifeWood = new ItemMelee("knife.wood", new MeleeCompKnife(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.knifeStone = new ItemMelee("knife.stone", new MeleeCompKnife(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.knifeSteel = new ItemMelee("knife.iron", new MeleeCompKnife(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.knifeGold = new ItemMelee("knife.gold", new MeleeCompKnife(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.knifeDiamond = new ItemMelee("knife.diamond",
                    new MeleeCompKnife(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("warhammer")) {
            BalkonsWeaponMod.warhammerWood = new ItemMelee("warhammer.wood",
                    new MeleeCompWarhammer(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.warhammerStone = new ItemMelee("warhammer.stone",
                    new MeleeCompWarhammer(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.warhammerSteel = new ItemMelee("warhammer.iron",
                    new MeleeCompWarhammer(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.warhammerGold = new ItemMelee("warhammer.gold",
                    new MeleeCompWarhammer(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.warhammerDiamond = new ItemMelee("warhammer.diamond",
                    new MeleeCompWarhammer(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("flail")) {
            BalkonsWeaponMod.flailWood = new ItemFlail("flail.wood", new MeleeCompNone(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.flailStone = new ItemFlail("flail.stone", new MeleeCompNone(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.flailSteel = new ItemFlail("flail.iron", new MeleeCompNone(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.flailGold = new ItemFlail("flail.gold", new MeleeCompNone(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.flailDiamond = new ItemFlail("flail.diamond",
                    new MeleeCompNone(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("katana")) {
            BalkonsWeaponMod.katanaWood = new ItemMelee("katana.wood",
                    new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.katanaStone = new ItemMelee("katana.stone",
                    new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Item.ToolMaterial.STONE));
            BalkonsWeaponMod.katanaSteel = new ItemMelee("katana.iron",
                    new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Item.ToolMaterial.IRON));
            BalkonsWeaponMod.katanaGold = new ItemMelee("katana.gold",
                    new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.katanaDiamond = new ItemMelee("katana.diamond",
                    new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("boomerang")) {
            BalkonsWeaponMod.boomerangWood = new ItemMelee("boomerang.wood",
                    new MeleeCompBoomerang(Item.ToolMaterial.WOOD));
            BalkonsWeaponMod.boomerangStone = new ItemMelee("boomerang.stone",
                    new MeleeCompBoomerang(Item.ToolMaterial.STONE));
            BalkonsWeaponMod.boomerangSteel = new ItemMelee("boomerang.iron",
                    new MeleeCompBoomerang(Item.ToolMaterial.IRON));
            BalkonsWeaponMod.boomerangGold = new ItemMelee("boomerang.gold",
                    new MeleeCompBoomerang(Item.ToolMaterial.GOLD));
            BalkonsWeaponMod.boomerangDiamond = new ItemMelee("boomerang.diamond",
                    new MeleeCompBoomerang(Item.ToolMaterial.DIAMOND));
        }
        if (this.modConfig.isEnabled("firerod")) {
            BalkonsWeaponMod.fireRod = new ItemMelee("firerod", new MeleeCompFirerod());
        }
        if (this.modConfig.isEnabled("javelin")) {
            BalkonsWeaponMod.javelin = new ItemJavelin("javelin");
        }
        if (this.modConfig.isEnabled("crossbow")) {
            BalkonsWeaponMod.crossbow = new ItemShooter("crossbow", new RangedCompCrossbow(), new MeleeCompNone(null));
            BalkonsWeaponMod.bolt = new WMItem("bolt");
        }
        if (this.modConfig.isEnabled("blowgun")) {
            BalkonsWeaponMod.blowgun = new ItemShooter("blowgun", new RangedCompBlowgun(), new MeleeCompNone(null));
            BalkonsWeaponMod.dart = new ItemBlowgunDart("dart");
        }
        if (this.modConfig.isEnabled("musket")) {
            if (this.modConfig.isEnabled("knife")) {
                BalkonsWeaponMod.bayonetWood = new ItemMusket("musketbayonet.wood",
                        new MeleeCompKnife(Item.ToolMaterial.WOOD), BalkonsWeaponMod.knifeWood);
                BalkonsWeaponMod.bayonetStone = new ItemMusket("musketbayonet.stone",
                        new MeleeCompKnife(Item.ToolMaterial.STONE), BalkonsWeaponMod.knifeStone);
                BalkonsWeaponMod.bayonetSteel = new ItemMusket("musketbayonet.iron",
                        new MeleeCompKnife(Item.ToolMaterial.IRON), BalkonsWeaponMod.knifeSteel);
                BalkonsWeaponMod.bayonetGold = new ItemMusket("musketbayonet.gold",
                        new MeleeCompKnife(Item.ToolMaterial.GOLD), BalkonsWeaponMod.knifeGold);
                BalkonsWeaponMod.bayonetDiamond = new ItemMusket("musketbayonet.diamond",
                        new MeleeCompKnife(Item.ToolMaterial.DIAMOND), BalkonsWeaponMod.knifeDiamond);
            }
            BalkonsWeaponMod.musket = new ItemMusket("musket", new MeleeCompNone(null), null);
            BalkonsWeaponMod.musket_iron_part = new WMItem("musket-ironpart");
        }
        if (this.modConfig.isEnabled("blunderbuss")) {
            BalkonsWeaponMod.blunderbuss = new ItemShooter("blunderbuss", new RangedCompBlunderbuss(),
                    new MeleeCompNone(null));
            BalkonsWeaponMod.blunder_iron_part = new WMItem("blunder-ironpart");
            BalkonsWeaponMod.blunderShot = new WMItem("shot");
        }
        if (this.modConfig.isEnabled("flintlock")) {
            BalkonsWeaponMod.flintlockPistol = new ItemShooter("flintlock", new RangedCompFlintlock(),
                    new MeleeCompNone(null));
        }
        if (this.modConfig.isEnabled("dynamite")) {
            BalkonsWeaponMod.dynamite = new ItemDynamite("dynamite");
        }
        if (this.modConfig.isEnabled("cannon")) {
            BalkonsWeaponMod.cannon = new ItemCannon("cannon");
            BalkonsWeaponMod.cannonBall = new WMItem("cannonball");
        }
        if (this.modConfig.isEnabled("dummy")) {
            BalkonsWeaponMod.dummy = new ItemDummy("dummy");
        }
        if (this.modConfig.isEnabled("musket") || this.modConfig.isEnabled("blunderbuss")) {
            BalkonsWeaponMod.gunStock = new WMItem("gun-stock");
        }
        if (this.modConfig.isEnabled("musket") || this.modConfig.isEnabled("flintlock")) {
            BalkonsWeaponMod.musketBullet = new WMItem("bullet");
        }
        if (this.modConfig.isEnabled("mortar")) {
            BalkonsWeaponMod.mortar = new ItemShooter("mortar", new RangedCompMortar(), new MeleeCompNone(null));
            BalkonsWeaponMod.mortar_iron_part = new WMItem("mortar-ironpart");
            BalkonsWeaponMod.mortarShell = new WMItem("shell");
        }
    }

    private void registerWeapons() {
        if (this.modConfig.isEnabled("spear")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.spearSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.spearGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:spear"), EntitySpear.class, "spear", 1,
                    this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("halberd")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.halberdSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.halberdGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (this.modConfig.isEnabled("knife")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.knifeSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.knifeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:knife"), EntityKnife.class, "knife", 2,
                    this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("javelin")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:javelin"), EntityJavelin.class, "javelin"
                    , 3, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("musket") || this.modConfig.isEnabled("flintlock")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:bullet"), EntityMusketBullet.class,
                    "bullet", 4, this, 16, 20, true);
        }
        if (this.modConfig.isEnabled("battleaxe")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.battleaxeSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.battleaxeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (this.modConfig.isEnabled("warhammer")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.warhammerSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.warhammerGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (this.modConfig.isEnabled("crossbow")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:bolt"), EntityCrossbowBolt.class, "bolt"
                    , 5, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("blowgun")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dart"), EntityBlowgunDart.class, "dart",
                    6, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("dynamite")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dynamite"), EntityDynamite.class,
                    "dynamite", 7, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("flail")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.flailSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.flailGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:flail"), EntityFlail.class, "flail", 8,
                    this, 32, 20, true);
        }
        if (this.modConfig.isEnabled("cannon")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:cannon"), EntityCannon.class, "cannon",
                    9, this, 64, 128, false);
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:cannonball"), EntityCannonBall.class,
                    "cannonball", 10, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("blunderbuss")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:shot"), EntityBlunderShot.class, "shot",
                    11, this, 16, 20, true);
        }
        if (this.modConfig.isEnabled("dummy")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:dummy"), EntityDummy.class, "dummy", 12,
                    this, 64, 20, false);
        }
        if (this.modConfig.isEnabled("boomerang")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.boomerangSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.boomerangGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:boomerang"), EntityBoomerang.class,
                    "boomerang", 13, this, 64, 20, true);
        }
        if (this.modConfig.isEnabled("katana")) {
            GameRegistry.addSmelting(BalkonsWeaponMod.katanaSteel, new ItemStack(Items.IRON_NUGGET), 0.1f);
            GameRegistry.addSmelting(BalkonsWeaponMod.katanaGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (this.modConfig.isEnabled("mortar")) {
            EntityRegistry.registerModEntity(new ResourceLocation("weaponmod:shell"), EntityMortarShell.class, "shell"
                    , 14, this, 64, 20, true);
        }
    }

    private void registerDispenseBehavior() {
        if (BalkonsWeaponMod.musketBullet != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.musketBullet,
                    new DispenseMusketBullet());
        }
        if (BalkonsWeaponMod.javelin != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.javelin, new DispenseJavelin());
        }
        if (BalkonsWeaponMod.bolt != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.bolt, new DispenseCrossbowBolt());
        }
        if (BalkonsWeaponMod.dart != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.dart, new DispenseBlowgunDart());
        }
        if (BalkonsWeaponMod.dynamite != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.dynamite, new DispenseDynamite());
        }
        if (BalkonsWeaponMod.blunderShot != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.blunderShot,
                    new DispenseBlunderShot());
        }
        if (this.modConfig.isEnabled("cannon")) {
            final DispenseCannonBall behavior = new DispenseCannonBall();
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.cannonBall, behavior);
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.GUNPOWDER, behavior);
        }
        if (BalkonsWeaponMod.mortarShell != null) {
            BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BalkonsWeaponMod.mortarShell,
                    new DispenseMortarShell());
        }
    }

    static {
        MOD_ITEMS = new ArrayList<>();
    }

    @Mod.EventBusSubscriber
    public static class registrationHandler {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(BalkonsWeaponMod.MOD_ITEMS.toArray(new Item[0]));
        }

        @SubscribeEvent
        public static void registerModels(final ModelRegistryEvent event) {
            BalkonsWeaponMod.proxy.registerRenderersItem(BalkonsWeaponMod.instance.modConfig);
        }
    }
}
