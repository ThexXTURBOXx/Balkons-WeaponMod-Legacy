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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IConditionSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BalkonsWeaponMod.MOD_ID)
public class BalkonsWeaponMod {
    public static final String MOD_ID = "weaponmod";
    public static final List<Item> MOD_ITEMS = new ArrayList<>();
    public static BalkonsWeaponMod instance;
    public static final Logger modLog = LogManager.getLogger(MOD_ID);
    public static final WMCommonProxy proxy = DistExecutor.runForDist(() -> WMClientProxy::new,
            () -> WMCommonProxy::new);
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
    public static final Map<DartType, Item> darts = new HashMap<>();
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
    public static EntityType<EntitySpear> entitySpear;
    public static EntityType<EntityKnife> entityKnife;
    public static EntityType<EntityJavelin> entityJavelin;
    public static EntityType<EntityMusketBullet> entityMusketBullet;
    public static EntityType<EntityCrossbowBolt> entityCrossbowBolt;
    public static EntityType<EntityBlowgunDart> entityBlowgunDart;
    public static EntityType<EntityDynamite> entityDynamite;
    public static EntityType<EntityFlail> entityFlail;
    public static EntityType<EntityCannon> entityCannon;
    public static EntityType<EntityCannonBall> entityCannonBall;
    public static EntityType<EntityBlunderShot> entityBlunderShot;
    public static EntityType<EntityDummy> entityDummy;
    public static EntityType<EntityBoomerang> entityBoomerang;
    public static EntityType<EntityMortarShell> entityMortarShell;
    public final WeaponModConfig modConfig;
    public final IConditionSerializer configConditional;
    public WMMessagePipeline messagePipeline;

    public BalkonsWeaponMod() {
        instance = this;

        DistExecutor.runWhenOn(Dist.CLIENT,
                () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

        modConfig = new WeaponModConfig();
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
        modConfig.loadConfig(ModLoadingContext.get());

        configConditional = CraftingHelper.register(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "config_conditional"),
                json -> () -> modConfig.isEnabled(JsonUtils.getString(json, "weapon")));
    }

    @SubscribeEvent
    public void onModConfig(ModConfig.ModConfigEvent e) {
        modConfig.postLoadConfig();
    }

    public void setup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            messagePipeline = new WMMessagePipeline();
            proxy.registerEventHandlers();
            proxy.registerPackets(messagePipeline);
        });
    }

    public void setupClient(FMLClientSetupEvent event) {
        proxy.registerRenderersEntity(modConfig);
    }

    @SuppressWarnings("unchecked")
    private <T extends Entity> EntityType<T> createEntityType(Class<T> entityClass, String name,
                                                              Function<? super World, ? extends T> factory) {
        // Helper method because Forge is too stupid to handle generics properly...
        return (EntityType<T>) EntityType.Builder.create(entityClass, factory)
                .build(name).setRegistryName(new ResourceLocation(MOD_ID, name));
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        if (modConfig.isEnabled("spear")) {
            registry.register(entitySpear =
                    createEntityType(EntitySpear.class, EntitySpear.NAME, EntitySpear::new));
        }
        if (modConfig.isEnabled("knife")) {
            registry.register(entityKnife =
                    createEntityType(EntityKnife.class, EntityKnife.NAME, EntityKnife::new));
        }
        if (modConfig.isEnabled("javelin")) {
            registry.register(entityJavelin =
                    createEntityType(EntityJavelin.class, EntityJavelin.NAME, EntityJavelin::new));
        }
        if (modConfig.isEnabled("musket") || modConfig.isEnabled("flintlock")) {
            registry.register(entityMusketBullet =
                    createEntityType(EntityMusketBullet.class, EntityMusketBullet.NAME, EntityMusketBullet::new));
        }
        if (modConfig.isEnabled("crossbow")) {
            registry.register(entityCrossbowBolt =
                    createEntityType(EntityCrossbowBolt.class, EntityCrossbowBolt.NAME, EntityCrossbowBolt::new));
        }
        if (modConfig.isEnabled("blowgun")) {
            registry.register(entityBlowgunDart =
                    createEntityType(EntityBlowgunDart.class, EntityBlowgunDart.NAME, EntityBlowgunDart::new));
        }
        if (modConfig.isEnabled("dynamite")) {
            registry.register(entityDynamite =
                    createEntityType(EntityDynamite.class, EntityDynamite.NAME, EntityDynamite::new));
        }
        if (modConfig.isEnabled("flail")) {
            registry.register(entityFlail =
                    createEntityType(EntityFlail.class, EntityFlail.NAME, EntityFlail::new));
        }
        if (modConfig.isEnabled("cannon")) {
            registry.register(entityCannon =
                    createEntityType(EntityCannon.class, EntityCannon.NAME, EntityCannon::new));
            registry.register(entityCannonBall =
                    createEntityType(EntityCannonBall.class, EntityCannonBall.NAME, EntityCannonBall::new));
        }
        if (modConfig.isEnabled("blunderbuss")) {
            registry.register(entityBlunderShot =
                    createEntityType(EntityBlunderShot.class, EntityBlunderShot.NAME, EntityBlunderShot::new));
        }
        if (modConfig.isEnabled("dummy")) {
            registry.register(entityDummy =
                    createEntityType(EntityDummy.class, EntityDummy.NAME, EntityDummy::new));
        }
        if (modConfig.isEnabled("boomerang")) {
            registry.register(entityBoomerang =
                    createEntityType(EntityBoomerang.class, EntityBoomerang.NAME, EntityBoomerang::new));
        }
        if (modConfig.isEnabled("mortar")) {
            registry.register(entityMortarShell =
                    createEntityType(EntityMortarShell.class, EntityMortarShell.NAME, EntityMortarShell::new));
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(spearWood = new ItemMelee("spear.wood", new MeleeCompSpear(ItemTier.WOOD)));
        registry.register(spearStone = new ItemMelee("spear.stone", new MeleeCompSpear(ItemTier.STONE)));
        registry.register(spearSteel = new ItemMelee("spear.iron", new MeleeCompSpear(ItemTier.IRON)));
        registry.register(spearGold = new ItemMelee("spear.gold", new MeleeCompSpear(ItemTier.GOLD)));
        registry.register(spearDiamond = new ItemMelee("spear.diamond", new MeleeCompSpear(ItemTier.DIAMOND)));

        registry.register(halberdWood = new ItemMelee("halberd.wood", new MeleeCompHalberd(ItemTier.WOOD)));
        registry.register(halberdStone = new ItemMelee("halberd.stone", new MeleeCompHalberd(ItemTier.STONE)));
        registry.register(halberdSteel = new ItemMelee("halberd.iron", new MeleeCompHalberd(ItemTier.IRON)));
        registry.register(halberdGold = new ItemMelee("halberd.gold", new MeleeCompHalberd(ItemTier.GOLD)));
        registry.register(halberdDiamond = new ItemMelee("halberd.diamond", new MeleeCompHalberd(ItemTier.DIAMOND)));

        registry.register(battleaxeWood = new ItemMelee("battleaxe.wood", new MeleeCompBattleaxe(ItemTier.WOOD)));
        registry.register(battleaxeStone = new ItemMelee("battleaxe.stone", new MeleeCompBattleaxe(ItemTier.STONE)));
        registry.register(battleaxeSteel = new ItemMelee("battleaxe.iron", new MeleeCompBattleaxe(ItemTier.IRON)));
        registry.register(battleaxeGold = new ItemMelee("battleaxe.gold", new MeleeCompBattleaxe(ItemTier.GOLD)));
        registry.register(battleaxeDiamond = new ItemMelee("battleaxe.diamond",
                new MeleeCompBattleaxe(ItemTier.DIAMOND)));

        registry.register(knifeWood = new ItemMelee("knife.wood", new MeleeCompKnife(ItemTier.WOOD)));
        registry.register(knifeStone = new ItemMelee("knife.stone", new MeleeCompKnife(ItemTier.STONE)));
        registry.register(knifeSteel = new ItemMelee("knife.iron", new MeleeCompKnife(ItemTier.IRON)));
        registry.register(knifeGold = new ItemMelee("knife.gold", new MeleeCompKnife(ItemTier.GOLD)));
        registry.register(knifeDiamond = new ItemMelee("knife.diamond", new MeleeCompKnife(ItemTier.DIAMOND)));

        registry.register(warhammerWood = new ItemMelee("warhammer.wood", new MeleeCompWarhammer(ItemTier.WOOD)));
        registry.register(warhammerStone = new ItemMelee("warhammer.stone", new MeleeCompWarhammer(ItemTier.STONE)));
        registry.register(warhammerSteel = new ItemMelee("warhammer.iron", new MeleeCompWarhammer(ItemTier.IRON)));
        registry.register(warhammerGold = new ItemMelee("warhammer.gold", new MeleeCompWarhammer(ItemTier.GOLD)));
        registry.register(warhammerDiamond = new ItemMelee("warhammer.diamond",
                new MeleeCompWarhammer(ItemTier.DIAMOND)));

        registry.register(flailWood = new ItemFlail("flail.wood", new MeleeCompNone(ItemTier.WOOD)));
        registry.register(flailStone = new ItemFlail("flail.stone", new MeleeCompNone(ItemTier.STONE)));
        registry.register(flailSteel = new ItemFlail("flail.iron", new MeleeCompNone(ItemTier.IRON)));
        registry.register(flailGold = new ItemFlail("flail.gold", new MeleeCompNone(ItemTier.GOLD)));
        registry.register(flailDiamond = new ItemFlail("flail.diamond", new MeleeCompNone(ItemTier.DIAMOND)));

        registry.register(katanaWood = new ItemMelee("katana.wood",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ItemTier.WOOD)));
        registry.register(katanaStone = new ItemMelee("katana.stone",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ItemTier.STONE)));
        registry.register(katanaSteel = new ItemMelee("katana.iron",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ItemTier.IRON)));
        registry.register(katanaGold = new ItemMelee("katana.gold",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ItemTier.GOLD)));
        registry.register(katanaDiamond = new ItemMelee("katana.diamond",
                new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, ItemTier.DIAMOND)));

        registry.register(boomerangWood = new ItemMelee("boomerang.wood", new MeleeCompBoomerang(ItemTier.WOOD)));
        registry.register(boomerangStone = new ItemMelee("boomerang.stone", new MeleeCompBoomerang(ItemTier.STONE)));
        registry.register(boomerangSteel = new ItemMelee("boomerang.iron", new MeleeCompBoomerang(ItemTier.IRON)));
        registry.register(boomerangGold = new ItemMelee("boomerang.gold", new MeleeCompBoomerang(ItemTier.GOLD)));
        registry.register(boomerangDiamond = new ItemMelee("boomerang.diamond",
                new MeleeCompBoomerang(ItemTier.DIAMOND)));

        registry.register(fireRod = new ItemMelee("firerod", new MeleeCompFirerod()));

        registry.register(javelin = new ItemJavelin("javelin"));

        registry.register(crossbow = new ItemShooter("crossbow", new RangedCompCrossbow(),
                new MeleeCompNone(null)));
        registry.register(bolt = new WMItem("bolt"));

        registry.register(blowgun = new ItemShooter("blowgun", new RangedCompBlowgun(), new MeleeCompNone(null)));
        for (DartType type : DartType.dartTypes) {
            if (type == null) continue;
            Item dart = new ItemBlowgunDart(type.typeName, type);
            darts.put(type, dart);
            registry.register(dart);
        }

        registry.register(bayonetWood = new ItemMusket("musketbayonet.wood",
                new MeleeCompKnife(ItemTier.WOOD), knifeWood));
        registry.register(bayonetStone = new ItemMusket("musketbayonet.stone",
                new MeleeCompKnife(ItemTier.STONE), knifeStone));
        registry.register(bayonetSteel = new ItemMusket("musketbayonet.iron",
                new MeleeCompKnife(ItemTier.IRON), knifeSteel));
        registry.register(bayonetGold = new ItemMusket("musketbayonet.gold",
                new MeleeCompKnife(ItemTier.GOLD), knifeGold));
        registry.register(bayonetDiamond = new ItemMusket("musketbayonet.diamond",
                new MeleeCompKnife(ItemTier.DIAMOND), knifeDiamond));
        registry.register(musket = new ItemMusket("musket", new MeleeCompNone(null), null));
        registry.register(musket_iron_part = new WMItem("musket-ironpart"));

        registry.register(blunderbuss = new ItemShooter("blunderbuss", new RangedCompBlunderbuss(),
                new MeleeCompNone(null)));
        registry.register(blunder_iron_part = new WMItem("blunder-ironpart"));
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
        registry.register(mortar_iron_part = new WMItem("mortar-ironpart"));
        registry.register(mortarShell = new WMItem("shell"));

        registerDispenseBehavior();
    }

    private void registerDispenseBehavior() {
        if (musketBullet != null) {
            BlockDispenser.registerDispenseBehavior(musketBullet, new DispenseMusketBullet());
        }
        if (javelin != null) {
            BlockDispenser.registerDispenseBehavior(javelin, new DispenseJavelin());
        }
        if (bolt != null) {
            BlockDispenser.registerDispenseBehavior(bolt, new DispenseCrossbowBolt());
        }
        for (Item dart : darts.values()) {
            BlockDispenser.registerDispenseBehavior(dart, new DispenseBlowgunDart());
        }
        if (dynamite != null) {
            BlockDispenser.registerDispenseBehavior(dynamite, new DispenseDynamite());
        }
        if (blunderShot != null) {
            BlockDispenser.registerDispenseBehavior(blunderShot, new DispenseBlunderShot());
        }
        if (modConfig.isEnabled("cannon")) {
            DispenseCannonBall behavior = new DispenseCannonBall();
            BlockDispenser.registerDispenseBehavior(cannonBall, behavior);
            BlockDispenser.registerDispenseBehavior(Items.GUNPOWDER, behavior);
        }
        if (mortarShell != null) {
            BlockDispenser.registerDispenseBehavior(mortarShell, new DispenseMortarShell());
        }
    }

    @Mod.EventBusSubscriber
    public static class registrationHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(MOD_ITEMS.toArray(new Item[0]));
        }
    }
}
