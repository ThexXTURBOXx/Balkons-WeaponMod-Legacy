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
import ckathode.weaponmod.item.DartType;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

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

    public BalkonsWeaponMod() {
        instance = this;
        messagePipeline = new WMMessagePipeline();
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
        registerItems();
        proxy.registerRenderersEntity(modConfig);
        proxy.registerRenderersItem(modConfig);
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

    private void registerWeapons() {
        EntityRegistry.registerModEntity(EntitySpear.class, "spear",
                1, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityKnife.class, "knife",
                2, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityJavelin.class, "javelin",
                3, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityMusketBullet.class, "bullet",
                4, this, 16, 20, true);
        EntityRegistry.registerModEntity(EntityCrossbowBolt.class, "bolt",
                5, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityBlowgunDart.class, "dart",
                6, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityDynamite.class, "dynamite",
                7, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityFlail.class, "flail",
                8, this, 32, 20, true);
        EntityRegistry.registerModEntity(EntityCannon.class, "cannon",
                9, this, 64, 128, false);
        EntityRegistry.registerModEntity(EntityCannonBall.class,
                "cannonball", 10, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityBlunderShot.class, "shot",
                11, this, 16, 20, true);
        EntityRegistry.registerModEntity(EntityDummy.class, "dummy",
                12, this, 64, 20, false);
        EntityRegistry.registerModEntity(EntityBoomerang.class,
                "boomerang", 13, this, 64, 20, true);
        EntityRegistry.registerModEntity(EntityMortarShell.class, "shell",
                14, this, 64, 20, true);

        if (modConfig.isEnabled("spear")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(spearWood, "  #", " X ", "X  ",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(spearStone, "  #", " X ", "X  ",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(spearSteel, "  #", " X ", "X  ",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(spearDiamond, "  #", " X ", "X  ",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(spearGold, "  #", " X ", "X  ",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(spearGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("halberd")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(halberdWood, " ##", " X#", "X  ",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(halberdStone, " ##", " X#", "X  ",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(halberdSteel, " ##", " X#", "X  ",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(halberdDiamond, " ##", " X#", "X  ",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(halberdGold, " ##", " X#", "X  ",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(halberdGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("knife")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeWood, "#X",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeStone, "#X",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeSteel, "#X",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeDiamond, "#X",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeGold, "#X",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeWood, "#", "X",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeStone, "#", "X",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeSteel, "#", "X",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeDiamond, "#", "X",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(knifeGold, "#", "X",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(knifeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("javelin")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(javelin, 4), "  #", " X ", "X  ",
                    'X', "stickWood", '#', Items.FLINT));
        }
        if (modConfig.isEnabled("musket")) {
            if (modConfig.isEnabled("knife")) {
                GameRegistry.addShapelessRecipe(new ItemStack(bayonetWood), knifeWood, musket);
                GameRegistry.addShapelessRecipe(new ItemStack(bayonetStone), knifeStone, musket);
                GameRegistry.addShapelessRecipe(new ItemStack(bayonetSteel), knifeSteel, musket);
                GameRegistry.addShapelessRecipe(new ItemStack(bayonetDiamond), knifeDiamond, musket);
                GameRegistry.addShapelessRecipe(new ItemStack(bayonetGold), knifeGold, musket);
            }
            GameRegistry.addRecipe(new ItemStack(musket), "#", "X",
                    'X', gunStock, '#', musketIronPart);
            GameRegistry.addRecipe(new ShapedOreRecipe(musketIronPart, "XX#", "  X",
                    'X', "ingotIron", '#', Items.FLINT_AND_STEEL));
        }
        if (modConfig.isEnabled("musket") || modConfig.isEnabled("flintlock")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(musketBullet, 8), "X", "#", "O",
                    'X', "ingotIron", '#', "gunpowder", 'O', "paper"));
        }
        if (modConfig.isEnabled("battleaxe")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeWood, "###", "#X#", " X ",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeStone, "###", "#X#", " X ",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeSteel, "###", "#X#", " X ",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeDiamond, "###", "#X#", " X ",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(battleaxeGold, "###", "#X#", " X ",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(battleaxeGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("warhammer")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(warhammerWood, "#X#", "#X#", " X ",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(warhammerStone, "#X#", "#X#", " X ",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(warhammerSteel, "#X#", "#X#", " X ",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(warhammerDiamond, "#X#", "#X#", " X ",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(warhammerGold, "#X#", "#X#", " X ",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(warhammerGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("crossbow")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(crossbow, "O##", "#X ", "# X",
                    'X', "plankWood", '#', "ingotIron", 'O', Items.BOW));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(bolt, 4), "#", "X",
                    'X', "feather", '#', "ingotIron"));
        }
        if (modConfig.isEnabled("blowgun")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blowgun), "X  ", " X ", "  X",
                    'X', "sugarcane"));
            for (DartType type : DartType.dartTypes) {
                if (type != null) {
                    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dart, 4, type.typeID), "#", "X", "O",
                            'X', type.craftItem, '#', "stickWood", 'O', "feather"));
                }
            }
        }
        if (modConfig.isEnabled("dynamite")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dynamite, 2), "#", "X", "X",
                    'X', "gunpowder", '#', "string"));
        }
        if (modConfig.isEnabled("flail")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(flailWood, "  O", " XO", "X #",
                    'X', "stickWood", 'O', "string", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(flailStone, "  O", " XO", "X #",
                    'X', "stickWood", 'O', "string", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(flailSteel, "  O", " XO", "X #",
                    'X', "stickWood", 'O', "string", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(flailDiamond, "  O", " XO", "X #",
                    'X', "stickWood", 'O', "string", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(flailGold, "  O", " XO", "X #",
                    'X', "stickWood", 'O', "string", '#', "ingotGold"));
            GameRegistry.addSmelting(flailGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("firerod")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(fireRod, "#  ", " X ", "  X",
                    'X', "stickWood", '#', "torch"));
        }
        if (modConfig.isEnabled("cannon")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(cannon, "XX#", "  X", "XXO",
                    'X', "ingotIron", '#', Items.FLINT_AND_STEEL, 'O', "logWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cannonBall, 4), " X ", "XXX", " X ",
                    'X', "stone"));
        }
        if (modConfig.isEnabled("blunderbuss")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blunderShot, 8), "X", "#", "O",
                    'X', "gravel", '#', "gunpowder", 'O', "paper"));
            GameRegistry.addRecipe(new ItemStack(blunderbuss), "#", "X",
                    'X', gunStock, '#', blunderIronPart);
            GameRegistry.addRecipe(new ShapedOreRecipe(blunderIronPart, "X  ", " X#", "X X",
                    'X', "ingotIron", '#', Items.FLINT_AND_STEEL));
        }
        if (modConfig.isEnabled("musket") || modConfig.isEnabled("blunderbuss") || modConfig.isEnabled("mortar")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(gunStock, "XX#",
                    'X', "stickWood", '#', "plankWood"));
        }
        if (modConfig.isEnabled("dummy")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(dummy, " U ", "XOX", " # ",
                    '#', "stickWood", 'X', "cropWheat", 'O', Items.LEATHER_CHESTPLATE, 'U', Blocks.WOOL));
        }
        if (modConfig.isEnabled("boomerang")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(boomerangWood, "XX#", "  X", "  X",
                    'X', "plankWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(boomerangStone, "XX#", "  X", "  X",
                    'X', "plankWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(boomerangSteel, "XX#", "  X", "  X",
                    'X', "plankWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(boomerangDiamond, "XX#", "  X", "  X",
                    'X', "plankWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(boomerangGold, "XX#", "  X", "  X",
                    'X', "plankWood", '#', "ingotGold"));
            GameRegistry.addSmelting(boomerangGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("katana")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(katanaWood, "  #", " # ", "X  ",
                    'X', "stickWood", '#', "plankWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(katanaStone, "  #", " # ", "X  ",
                    'X', "stickWood", '#', "cobblestone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(katanaSteel, "  #", " # ", "X  ",
                    'X', "stickWood", '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(katanaDiamond, "  #", " # ", "X  ",
                    'X', "stickWood", '#', "gemDiamond"));
            GameRegistry.addRecipe(new ShapedOreRecipe(katanaGold, "  #", " # ", "X  ",
                    'X', "stickWood", '#', "ingotGold"));
            GameRegistry.addSmelting(katanaGold, new ItemStack(Items.GOLD_NUGGET), 0.1f);
        }
        if (modConfig.isEnabled("flintlock")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(flintlockPistol, "XX#", " -O",
                    'X', "ingotIron", '#', Items.FLINT_AND_STEEL, '-', "stickWood", 'O', "plankWood"));
        }
        if (modConfig.isEnabled("mortar")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(mortarIronPart, "## ", "##X", "  #",
                    'X', Items.FLINT_AND_STEEL, '#', "ingotIron"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mortarShell, 2), "#", "X", "X",
                    'X', "gunpowder", '#', "ingotIron"));
            GameRegistry.addRecipe(new ItemStack(mortar), "X", "#",
                    'X', mortarIronPart, '#', gunStock);
            if (modConfig.isEnabled("dynamite")) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mortarShell, 2), "#", "X",
                        'X', dynamite, '#', "ingotIron"));
            }
        }
    }

    public void registerItems() {
        GameRegistry.register(spearWood = WMItemBuilder.createStandardSpear("spear.wood", ToolMaterial.WOOD));
        GameRegistry.register(spearStone = WMItemBuilder.createStandardSpear("spear.stone", ToolMaterial.STONE));
        GameRegistry.register(spearSteel = WMItemBuilder.createStandardSpear("spear.iron", ToolMaterial.IRON));
        GameRegistry.register(spearGold = WMItemBuilder.createStandardSpear("spear.gold", ToolMaterial.GOLD));
        GameRegistry.register(spearDiamond = WMItemBuilder.createStandardSpear("spear.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(halberdWood = WMItemBuilder.createStandardHalberd("halberd.wood", ToolMaterial.WOOD));
        GameRegistry.register(halberdStone = WMItemBuilder.createStandardHalberd("halberd.stone", ToolMaterial.STONE));
        GameRegistry.register(halberdSteel = WMItemBuilder.createStandardHalberd("halberd.iron", ToolMaterial.IRON));
        GameRegistry.register(halberdGold = WMItemBuilder.createStandardHalberd("halberd.gold", ToolMaterial.GOLD));
        GameRegistry.register(halberdDiamond =
                WMItemBuilder.createStandardHalberd("halberd.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(battleaxeWood =
                WMItemBuilder.createStandardBattleaxe("battleaxe.wood", ToolMaterial.WOOD));
        GameRegistry.register(battleaxeStone =
                WMItemBuilder.createStandardBattleaxe("battleaxe.stone", ToolMaterial.STONE));
        GameRegistry.register(battleaxeSteel =
                WMItemBuilder.createStandardBattleaxe("battleaxe.iron", ToolMaterial.IRON));
        GameRegistry.register(battleaxeGold =
                WMItemBuilder.createStandardBattleaxe("battleaxe.gold", ToolMaterial.GOLD));
        GameRegistry.register(battleaxeDiamond =
                WMItemBuilder.createStandardBattleaxe("battleaxe.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(knifeWood = WMItemBuilder.createStandardKnife("knife.wood", ToolMaterial.WOOD));
        GameRegistry.register(knifeStone = WMItemBuilder.createStandardKnife("knife.stone", ToolMaterial.STONE));
        GameRegistry.register(knifeSteel = WMItemBuilder.createStandardKnife("knife.iron", ToolMaterial.IRON));
        GameRegistry.register(knifeGold = WMItemBuilder.createStandardKnife("knife.gold", ToolMaterial.GOLD));
        GameRegistry.register(knifeDiamond = WMItemBuilder.createStandardKnife("knife.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(warhammerWood =
                WMItemBuilder.createStandardWarhammer("warhammer.wood", ToolMaterial.WOOD));
        GameRegistry.register(warhammerStone =
                WMItemBuilder.createStandardWarhammer("warhammer.stone", ToolMaterial.STONE));
        GameRegistry.register(warhammerSteel =
                WMItemBuilder.createStandardWarhammer("warhammer.iron", ToolMaterial.IRON));
        GameRegistry.register(warhammerGold =
                WMItemBuilder.createStandardWarhammer("warhammer.gold", ToolMaterial.GOLD));
        GameRegistry.register(warhammerDiamond =
                WMItemBuilder.createStandardWarhammer("warhammer.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(flailWood = WMItemBuilder.createStandardFlail("flail.wood", ToolMaterial.WOOD));
        GameRegistry.register(flailStone = WMItemBuilder.createStandardFlail("flail.stone", ToolMaterial.STONE));
        GameRegistry.register(flailSteel = WMItemBuilder.createStandardFlail("flail.iron", ToolMaterial.IRON));
        GameRegistry.register(flailGold = WMItemBuilder.createStandardFlail("flail.gold", ToolMaterial.GOLD));
        GameRegistry.register(flailDiamond = WMItemBuilder.createStandardFlail("flail.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(katanaWood = WMItemBuilder.createStandardKatana("katana.wood", ToolMaterial.WOOD));
        GameRegistry.register(katanaStone = WMItemBuilder.createStandardKatana("katana.stone", ToolMaterial.STONE));
        GameRegistry.register(katanaSteel = WMItemBuilder.createStandardKatana("katana.iron", ToolMaterial.IRON));
        GameRegistry.register(katanaGold = WMItemBuilder.createStandardKatana("katana.gold", ToolMaterial.GOLD));
        GameRegistry.register(katanaDiamond =
                WMItemBuilder.createStandardKatana("katana.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(boomerangWood =
                WMItemBuilder.createStandardBoomerang("boomerang.wood", ToolMaterial.WOOD));
        GameRegistry.register(boomerangStone =
                WMItemBuilder.createStandardBoomerang("boomerang.stone", ToolMaterial.STONE));
        GameRegistry.register(boomerangSteel =
                WMItemBuilder.createStandardBoomerang("boomerang.iron", ToolMaterial.IRON));
        GameRegistry.register(boomerangGold =
                WMItemBuilder.createStandardBoomerang("boomerang.gold", ToolMaterial.GOLD));
        GameRegistry.register(boomerangDiamond =
                WMItemBuilder.createStandardBoomerang("boomerang.diamond", ToolMaterial.DIAMOND));

        GameRegistry.register(fireRod = WMItemBuilder.createStandardFirerod("firerod"));

        GameRegistry.register(javelin = WMItemBuilder.createStandardJavelin("javelin"));

        GameRegistry.register(crossbow = WMItemBuilder.createStandardCrossbow("crossbow"));
        GameRegistry.register(bolt = WMItemBuilder.createWMItem("bolt"));

        GameRegistry.register(blowgun = WMItemBuilder.createStandardBlowgun("blowgun"));
        GameRegistry.register(dart = WMItemBuilder.createStandardBlowgunDart("dart"));

        GameRegistry.register(bayonetWood = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.wood",
                ToolMaterial.WOOD, knifeWood));
        GameRegistry.register(bayonetStone = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.stone",
                ToolMaterial.STONE, knifeStone));
        GameRegistry.register(bayonetSteel = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.iron",
                ToolMaterial.IRON, knifeSteel));
        GameRegistry.register(bayonetGold = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.gold",
                ToolMaterial.GOLD, knifeGold));
        GameRegistry.register(bayonetDiamond = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.diamond",
                ToolMaterial.DIAMOND, knifeDiamond));
        GameRegistry.register(musket = WMItemBuilder.createStandardMusket("musket"));
        GameRegistry.register(musketIronPart = WMItemBuilder.createWMItem("musket-ironpart"));

        GameRegistry.register(blunderbuss = WMItemBuilder.createStandardBlunderbuss("blunderbuss"));
        GameRegistry.register(blunderIronPart = WMItemBuilder.createWMItem("blunder-ironpart"));
        GameRegistry.register(blunderShot = WMItemBuilder.createWMItem("shot"));

        GameRegistry.register(flintlockPistol = WMItemBuilder.createStandardFlintlock("flintlock"));

        GameRegistry.register(dynamite = WMItemBuilder.createStandardDynamite("dynamite"));

        GameRegistry.register(cannon = WMItemBuilder.createStandardCannon("cannon"));
        GameRegistry.register(cannonBall = WMItemBuilder.createWMItem("cannonball"));

        GameRegistry.register(dummy = WMItemBuilder.createStandardDummy("dummy"));

        GameRegistry.register(gunStock = WMItemBuilder.createWMItem("gun-stock"));

        GameRegistry.register(musketBullet = WMItemBuilder.createWMItem("bullet"));

        GameRegistry.register(mortar = WMItemBuilder.createStandardMortar("mortar"));
        GameRegistry.register(mortarIronPart = WMItemBuilder.createWMItem("mortar-ironpart"));
        GameRegistry.register(mortarShell = WMItemBuilder.createWMItem("shell"));

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
