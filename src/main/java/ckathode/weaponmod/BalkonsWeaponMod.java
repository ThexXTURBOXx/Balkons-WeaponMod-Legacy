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
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import makamys.mclib.core.MCLib;
import makamys.mclib.core.MCLibModules;
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Logger;

@Mod(modid = BalkonsWeaponMod.MOD_ID, name = BalkonsWeaponMod.MOD_NAME, version = BalkonsWeaponMod.MOD_VERSION,
        acceptedMinecraftVersions = "@MC_RANGE@")
public class BalkonsWeaponMod {
    public static final String MOD_ID = "@MOD_ID@";
    public static final String MOD_NAME = "@MOD_NAME@";
    public static final String MOD_VERSION = "@MOD_VERSION@";
    public static final String UPDATE_JSON = "@MOD_UPDATE_JSON@";
    @Mod.Instance(MOD_ID)
    public static BalkonsWeaponMod instance;
    public static Logger modLog;
    @SidedProxy(clientSide = "ckathode.weaponmod.WMClientProxy", serverSide = "ckathode.weaponmod.WMCommonProxy",
            modId = MOD_ID)
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
    public void constructMod(final FMLConstructionEvent event) {
        MCLib.init();
    }

    @Mod.EventHandler
    public void preInitMod(final FMLPreInitializationEvent event) {
        modLog = event.getModLog();

        MCLibModules.updateCheckAPI.submitModTask(MOD_ID, MOD_VERSION, UPDATE_JSON);

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
        modConfig.addDataWatcherIdSetting("warhammer_last_smash_ticks", 26);
        modConfig.addDataWatcherIdSetting("flail_thrown", 27);
        modConfig.addDataWatcherIdSetting("flail_entity_id", 28);
        modConfig.loadConfig();

        proxy.registerEventHandlers();
        registerItems();
        proxy.registerPackets(messagePipeline);
        proxy.registerRenderersItem(modConfig);
        proxy.registerRenderersEntity(modConfig);
        registerEntities();
    }

    @Mod.EventHandler
    public void initMod(final FMLInitializationEvent event) {
        messagePipeline.initalize();
        registerRecipes();
        registerDispenseBehavior();
    }

    @Mod.EventHandler
    public void postInitMod(FMLPostInitializationEvent event) {
        messagePipeline.postInitialize();
        proxy.registerSounds(modConfig);
    }

    private void registerEntities() {
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
    }

    public void registerRecipes() {
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
            GameRegistry.addSmelting(spearGold, new ItemStack(Items.gold_nugget), 0.1f);
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
            GameRegistry.addSmelting(halberdGold, new ItemStack(Items.gold_nugget), 0.1f);
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
            GameRegistry.addSmelting(knifeGold, new ItemStack(Items.gold_nugget), 0.1f);
        }
        if (modConfig.isEnabled("javelin")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(javelin, 4), "  #", " X ", "X  ",
                    'X', "stickWood", '#', Items.flint));
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
                    'X', "ingotIron", '#', Items.flint_and_steel));
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
            GameRegistry.addSmelting(battleaxeGold, new ItemStack(Items.gold_nugget), 0.1f);
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
            GameRegistry.addSmelting(warhammerGold, new ItemStack(Items.gold_nugget), 0.1f);
        }
        if (modConfig.isEnabled("crossbow")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(crossbow, "O##", "#X ", "# X",
                    'X', "plankWood", '#', "ingotIron", 'O', Items.bow));
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
            GameRegistry.addSmelting(flailGold, new ItemStack(Items.gold_nugget), 0.1f);
        }
        if (modConfig.isEnabled("firerod")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(fireRod, "#  ", " X ", "  X",
                    'X', "stickWood", '#', "torch"));
        }
        if (modConfig.isEnabled("cannon")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(cannon, "XX#", "  X", "XXO",
                    'X', "ingotIron", '#', Items.flint_and_steel, 'O', "logWood"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cannonBall, 4), " X ", "XXX", " X ",
                    'X', "stone"));
        }
        if (modConfig.isEnabled("blunderbuss")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blunderShot, 8), "X", "#", "O",
                    'X', "gravel", '#', "gunpowder", 'O', "paper"));
            GameRegistry.addRecipe(new ItemStack(blunderbuss), "#", "X",
                    'X', gunStock, '#', blunderIronPart);
            GameRegistry.addRecipe(new ShapedOreRecipe(blunderIronPart, "X  ", " X#", "X X",
                    'X', "ingotIron", '#', Items.flint_and_steel));
        }
        if (modConfig.isEnabled("musket") || modConfig.isEnabled("blunderbuss") || modConfig.isEnabled("mortar")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(gunStock, "XX#",
                    'X', "stickWood", '#', "plankWood"));
        }
        if (modConfig.isEnabled("dummy")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(dummy, " U ", "XOX", " # ",
                    '#', "stickWood", 'X', "cropWheat", 'O', Items.leather_chestplate, 'U', Blocks.wool));
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
            GameRegistry.addSmelting(boomerangGold, new ItemStack(Items.gold_nugget), 0.1f);
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
            GameRegistry.addSmelting(katanaGold, new ItemStack(Items.gold_nugget), 0.1f);
        }
        if (modConfig.isEnabled("flintlock")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(flintlockPistol, "XX#", " -O",
                    'X', "ingotIron", '#', Items.flint_and_steel, '-', "stickWood", 'O', "plankWood"));
        }
        if (modConfig.isEnabled("mortar")) {
            GameRegistry.addRecipe(new ShapedOreRecipe(mortarIronPart, "## ", "##X", "  #",
                    'X', Items.flint_and_steel, '#', "ingotIron"));
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
        spearWood = WMItemBuilder.createStandardSpear("spear.wood", ToolMaterial.WOOD);
        spearStone = WMItemBuilder.createStandardSpear("spear.stone", ToolMaterial.STONE);
        spearSteel = WMItemBuilder.createStandardSpear("spear.iron", ToolMaterial.IRON);
        spearGold = WMItemBuilder.createStandardSpear("spear.gold", ToolMaterial.GOLD);
        spearDiamond = WMItemBuilder.createStandardSpear("spear.diamond", ToolMaterial.EMERALD);

        halberdWood = WMItemBuilder.createStandardHalberd("halberd.wood", ToolMaterial.WOOD);
        halberdStone = WMItemBuilder.createStandardHalberd("halberd.stone", ToolMaterial.STONE);
        halberdSteel = WMItemBuilder.createStandardHalberd("halberd.iron", ToolMaterial.IRON);
        halberdGold = WMItemBuilder.createStandardHalberd("halberd.gold", ToolMaterial.GOLD);
        halberdDiamond = WMItemBuilder.createStandardHalberd("halberd.diamond", ToolMaterial.EMERALD);

        battleaxeWood = WMItemBuilder.createStandardBattleaxe("battleaxe.wood", ToolMaterial.WOOD);
        battleaxeStone = WMItemBuilder.createStandardBattleaxe("battleaxe.stone", ToolMaterial.STONE);
        battleaxeSteel = WMItemBuilder.createStandardBattleaxe("battleaxe.iron", ToolMaterial.IRON);
        battleaxeGold = WMItemBuilder.createStandardBattleaxe("battleaxe.gold", ToolMaterial.GOLD);
        battleaxeDiamond = WMItemBuilder.createStandardBattleaxe("battleaxe.diamond", ToolMaterial.EMERALD);

        knifeWood = WMItemBuilder.createStandardKnife("knife.wood", ToolMaterial.WOOD);
        knifeStone = WMItemBuilder.createStandardKnife("knife.stone", ToolMaterial.STONE);
        knifeSteel = WMItemBuilder.createStandardKnife("knife.iron", ToolMaterial.IRON);
        knifeGold = WMItemBuilder.createStandardKnife("knife.gold", ToolMaterial.GOLD);
        knifeDiamond = WMItemBuilder.createStandardKnife("knife.diamond", ToolMaterial.EMERALD);

        warhammerWood = WMItemBuilder.createStandardWarhammer("warhammer.wood", ToolMaterial.WOOD);
        warhammerStone = WMItemBuilder.createStandardWarhammer("warhammer.stone", ToolMaterial.STONE);
        warhammerSteel = WMItemBuilder.createStandardWarhammer("warhammer.iron", ToolMaterial.IRON);
        warhammerGold = WMItemBuilder.createStandardWarhammer("warhammer.gold", ToolMaterial.GOLD);
        warhammerDiamond = WMItemBuilder.createStandardWarhammer("warhammer.diamond", ToolMaterial.EMERALD);

        flailWood = WMItemBuilder.createStandardFlail("flail.wood", ToolMaterial.WOOD);
        flailStone = WMItemBuilder.createStandardFlail("flail.stone", ToolMaterial.STONE);
        flailSteel = WMItemBuilder.createStandardFlail("flail.iron", ToolMaterial.IRON);
        flailGold = WMItemBuilder.createStandardFlail("flail.gold", ToolMaterial.GOLD);
        flailDiamond = WMItemBuilder.createStandardFlail("flail.diamond", ToolMaterial.EMERALD);

        katanaWood = WMItemBuilder.createStandardKatana("katana.wood", ToolMaterial.WOOD);
        katanaStone = WMItemBuilder.createStandardKatana("katana.stone", ToolMaterial.STONE);
        katanaSteel = WMItemBuilder.createStandardKatana("katana.iron", ToolMaterial.IRON);
        katanaGold = WMItemBuilder.createStandardKatana("katana.gold", ToolMaterial.GOLD);
        katanaDiamond = WMItemBuilder.createStandardKatana("katana.diamond", ToolMaterial.EMERALD);

        boomerangWood = WMItemBuilder.createStandardBoomerang("boomerang.wood", ToolMaterial.WOOD);
        boomerangStone = WMItemBuilder.createStandardBoomerang("boomerang.stone", ToolMaterial.STONE);
        boomerangSteel = WMItemBuilder.createStandardBoomerang("boomerang.iron", ToolMaterial.IRON);
        boomerangGold = WMItemBuilder.createStandardBoomerang("boomerang.gold", ToolMaterial.GOLD);
        boomerangDiamond = WMItemBuilder.createStandardBoomerang("boomerang.diamond", ToolMaterial.EMERALD);

        fireRod = WMItemBuilder.createStandardFirerod("firerod");

        javelin = WMItemBuilder.createStandardJavelin("javelin");

        crossbow = WMItemBuilder.createStandardCrossbow("crossbow");
        bolt = WMItemBuilder.createWMItem("bolt");

        blowgun = WMItemBuilder.createStandardBlowgun("blowgun");
        dart = WMItemBuilder.createStandardBlowgunDart("dart");

        bayonetWood = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.wood",
                ToolMaterial.WOOD, knifeWood);
        bayonetStone = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.stone",
                ToolMaterial.STONE, knifeStone);
        bayonetSteel = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.iron",
                ToolMaterial.IRON, knifeSteel);
        bayonetGold = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.gold",
                ToolMaterial.GOLD, knifeGold);
        bayonetDiamond = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.diamond",
                ToolMaterial.EMERALD, knifeDiamond);
        musket = WMItemBuilder.createStandardMusket("musket");
        musketIronPart = WMItemBuilder.createWMItem("musket-ironpart");

        blunderbuss = WMItemBuilder.createStandardBlunderbuss("blunderbuss");
        blunderIronPart = WMItemBuilder.createWMItem("blunder-ironpart");
        blunderShot = WMItemBuilder.createWMItem("shot");

        flintlockPistol = WMItemBuilder.createStandardFlintlock("flintlock");

        dynamite = WMItemBuilder.createStandardDynamite("dynamite");

        cannon = WMItemBuilder.createStandardCannon("cannon");
        cannonBall = WMItemBuilder.createWMItem("cannonball");

        dummy = WMItemBuilder.createStandardDummy("dummy");

        gunStock = WMItemBuilder.createWMItem("gun-stock");

        musketBullet = WMItemBuilder.createWMItem("bullet");

        mortar = WMItemBuilder.createStandardMortar("mortar");
        mortarIronPart = WMItemBuilder.createWMItem("mortar-ironpart");
        mortarShell = WMItemBuilder.createWMItem("shell");

        registerDispenseBehavior();
    }

    private void registerDispenseBehavior() {
        if (musketBullet != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(musketBullet, new DispenseMusketBullet());
        }
        if (javelin != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(javelin, new DispenseJavelin());
        }
        if (bolt != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(bolt, new DispenseCrossbowBolt());
        }
        if (dart != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(dart, new DispenseBlowgunDart());
        }
        if (dynamite != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(dynamite, new DispenseDynamite());
        }
        if (blunderShot != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(blunderShot, new DispenseBlunderShot());
        }
        if (cannonBall != null) {
            DispenseCannonBall behavior = new DispenseCannonBall();
            BlockDispenser.dispenseBehaviorRegistry.putObject(cannonBall, behavior);
            BlockDispenser.dispenseBehaviorRegistry.putObject(Items.gunpowder, behavior);
        }
        if (mortarShell != null) {
            BlockDispenser.dispenseBehaviorRegistry.putObject(mortarShell, new DispenseMortarShell());
        }
    }

}
