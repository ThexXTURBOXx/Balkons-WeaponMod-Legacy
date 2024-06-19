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
import net.minecraft.block.BlockDispenser;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
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
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
    public static Item musketIronPart;
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
    public static Item blunderIronPart;
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
    public static Item mortarIronPart;
    public WeaponModConfig modConfig;
    public WMMessagePipeline messagePipeline;

    public BalkonsWeaponMod() {
        instance = this;
        messagePipeline = new WMMessagePipeline();
        MinecraftForge.EVENT_BUS.register(this);
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

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(spearWood = new ItemMelee("spear.wood", new MeleeCompSpear(ToolMaterial.WOOD)));
        registry.register(spearStone = new ItemMelee("spear.stone", new MeleeCompSpear(ToolMaterial.STONE)));
        registry.register(spearSteel = new ItemMelee("spear.iron", new MeleeCompSpear(ToolMaterial.IRON)));
        registry.register(spearGold = new ItemMelee("spear.gold", new MeleeCompSpear(ToolMaterial.GOLD)));
        registry.register(spearDiamond = new ItemMelee("spear.diamond", new MeleeCompSpear(ToolMaterial.DIAMOND)));

        registry.register(halberdWood = new ItemMelee("halberd.wood", new MeleeCompHalberd(ToolMaterial.WOOD)));
        registry.register(halberdStone = new ItemMelee("halberd.stone", new MeleeCompHalberd(ToolMaterial.STONE)));
        registry.register(halberdSteel = new ItemMelee("halberd.iron", new MeleeCompHalberd(ToolMaterial.IRON)));
        registry.register(halberdGold = new ItemMelee("halberd.gold", new MeleeCompHalberd(ToolMaterial.GOLD)));
        registry.register(halberdDiamond = new ItemMelee("halberd.diamond",
                new MeleeCompHalberd(ToolMaterial.DIAMOND)));

        registry.register(battleaxeWood = new ItemMelee("battleaxe.wood", new MeleeCompBattleaxe(ToolMaterial.WOOD)));
        registry.register(battleaxeStone = new ItemMelee("battleaxe.stone",
                new MeleeCompBattleaxe(ToolMaterial.STONE)));
        registry.register(battleaxeSteel = new ItemMelee("battleaxe.iron", new MeleeCompBattleaxe(ToolMaterial.IRON)));
        registry.register(battleaxeGold = new ItemMelee("battleaxe.gold", new MeleeCompBattleaxe(ToolMaterial.GOLD)));
        registry.register(battleaxeDiamond = new ItemMelee("battleaxe.diamond",
                new MeleeCompBattleaxe(ToolMaterial.DIAMOND)));

        registry.register(knifeWood = new ItemMelee("knife.wood", new MeleeCompKnife(ToolMaterial.WOOD)));
        registry.register(knifeStone = new ItemMelee("knife.stone", new MeleeCompKnife(ToolMaterial.STONE)));
        registry.register(knifeSteel = new ItemMelee("knife.iron", new MeleeCompKnife(ToolMaterial.IRON)));
        registry.register(knifeGold = new ItemMelee("knife.gold", new MeleeCompKnife(ToolMaterial.GOLD)));
        registry.register(knifeDiamond = new ItemMelee("knife.diamond", new MeleeCompKnife(ToolMaterial.DIAMOND)));

        registry.register(warhammerWood = new ItemMelee("warhammer.wood", new MeleeCompWarhammer(ToolMaterial.WOOD)));
        registry.register(warhammerStone = new ItemMelee("warhammer.stone",
                new MeleeCompWarhammer(ToolMaterial.STONE)));
        registry.register(warhammerSteel = new ItemMelee("warhammer.iron", new MeleeCompWarhammer(ToolMaterial.IRON)));
        registry.register(warhammerGold = new ItemMelee("warhammer.gold", new MeleeCompWarhammer(ToolMaterial.GOLD)));
        registry.register(warhammerDiamond = new ItemMelee("warhammer.diamond",
                new MeleeCompWarhammer(ToolMaterial.DIAMOND)));

        registry.register(flailWood = new ItemFlail("flail.wood", new MeleeCompNone(ToolMaterial.WOOD)));
        registry.register(flailStone = new ItemFlail("flail.stone", new MeleeCompNone(ToolMaterial.STONE)));
        registry.register(flailSteel = new ItemFlail("flail.iron", new MeleeCompNone(ToolMaterial.IRON)));
        registry.register(flailGold = new ItemFlail("flail.gold", new MeleeCompNone(ToolMaterial.GOLD)));
        registry.register(flailDiamond = new ItemFlail("flail.diamond", new MeleeCompNone(ToolMaterial.DIAMOND)));

        registry.register(katanaWood = new ItemMelee("katana.wood",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ToolMaterial.WOOD)));
        registry.register(katanaStone = new ItemMelee("katana.stone",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ToolMaterial.STONE)));
        registry.register(katanaSteel = new ItemMelee("katana.iron",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ToolMaterial.IRON)));
        registry.register(katanaGold = new ItemMelee("katana.gold",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ToolMaterial.GOLD)));
        registry.register(katanaDiamond = new ItemMelee("katana.diamond",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ToolMaterial.DIAMOND)));

        registry.register(boomerangWood = new ItemMelee("boomerang.wood", new MeleeCompBoomerang(ToolMaterial.WOOD)));
        registry.register(boomerangStone = new ItemMelee("boomerang.stone",
                new MeleeCompBoomerang(ToolMaterial.STONE)));
        registry.register(boomerangSteel = new ItemMelee("boomerang.iron", new MeleeCompBoomerang(ToolMaterial.IRON)));
        registry.register(boomerangGold = new ItemMelee("boomerang.gold", new MeleeCompBoomerang(ToolMaterial.GOLD)));
        registry.register(boomerangDiamond = new ItemMelee("boomerang.diamond",
                new MeleeCompBoomerang(ToolMaterial.DIAMOND)));

        registry.register(fireRod = new ItemMelee("firerod", new MeleeCompFirerod()));

        registry.register(javelin = new ItemJavelin("javelin"));

        registry.register(crossbow = new ItemShooter("crossbow", new RangedCompCrossbow(),
                new MeleeCompNone(null)));
        registry.register(bolt = new WMItem("bolt"));

        registry.register(blowgun = new ItemShooter("blowgun", new RangedCompBlowgun(), new MeleeCompNone(null)));
        registry.register(dart = new ItemBlowgunDart("dart"));

        registry.register(bayonetWood = new ItemMusket("musketbayonet.wood",
                new MeleeCompKnife(ToolMaterial.WOOD), knifeWood));
        registry.register(bayonetStone = new ItemMusket("musketbayonet.stone",
                new MeleeCompKnife(ToolMaterial.STONE), knifeStone));
        registry.register(bayonetSteel = new ItemMusket("musketbayonet.iron",
                new MeleeCompKnife(ToolMaterial.IRON), knifeSteel));
        registry.register(bayonetGold = new ItemMusket("musketbayonet.gold",
                new MeleeCompKnife(ToolMaterial.GOLD), knifeGold));
        registry.register(bayonetDiamond = new ItemMusket("musketbayonet.diamond",
                new MeleeCompKnife(ToolMaterial.DIAMOND), knifeDiamond));
        registry.register(musket = new ItemMusket("musket", new MeleeCompNone(null), null));
        registry.register(musketIronPart = new WMItem("musket-ironpart"));

        registry.register(blunderbuss = new ItemShooter("blunderbuss", new RangedCompBlunderbuss(),
                new MeleeCompNone(null)));
        registry.register(blunderIronPart = new WMItem("blunder-ironpart"));
        registry.register(blunderShot = new WMItem("shot"));

        registry.register(flintlockPistol = new ItemShooter("flintlock", new RangedCompFlintlock(),
                new MeleeCompNone(null)));

        registry.register(dynamite = new ItemDynamite("dynamite"));

        registry.register(cannon = new ItemCannon("cannon"));
        registry.register(cannonBall = new WMItem("cannonball"));

        registry.register(dummy = new ItemDummy("dummy"));

        registry.register(gunStock = new WMItem("gun-stock"));

        registry.register(musketBullet = new WMItem("bullet"));

        registry.register(mortar = new ItemShooter("mortar", new RangedCompMortar(), new MeleeCompNone(null)));
        registry.register(mortarIronPart = new WMItem("mortar-ironpart"));
        registry.register(mortarShell = new WMItem("shell"));

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
