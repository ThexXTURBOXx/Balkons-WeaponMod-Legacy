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
import ckathode.weaponmod.entity.projectile.EntityProjectile;
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
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static BalkonsWeaponMod instance;
    public static final Logger modLog = LogManager.getLogger(MOD_ID);
    public static final WMCommonProxy proxy = DistExecutor.runForDist(() -> WMClientProxy::new,
            () -> WMCommonProxy::new);
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
    public static final Map<DartType, ItemBlowgunDart> darts = new HashMap<>();
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
    public final IConditionSerializer<?> configConditional;
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

        configConditional = CraftingHelper.register(new WMConfigCondition.Serializer(modConfig));
    }

    public void setup(FMLCommonSetupEvent event) {
        messagePipeline = new WMMessagePipeline();
        proxy.registerEventHandlers();
        proxy.registerPackets(messagePipeline);
    }

    public void setupClient(FMLClientSetupEvent event) {
        proxy.registerRenderersEntity();
    }

    private <T extends Entity> EntityType<T> createEntityType(String name, EntitySize size,
                                                              EntityType.IFactory<T> factory) {
        return createEntityType(name, size, -1, -1, true, factory);
    }

    @SuppressWarnings("unchecked")
    // Helper method because Forge is too stupid to handle generics properly...
    private <T extends Entity> EntityType<T> createEntityType(String name, EntitySize size, int range,
                                                              int updateFrequency, boolean velocityUpdates,
                                                              EntityType.IFactory<T> factory) {
        EntityType.Builder<T> builder = EntityType.Builder.create(factory, EntityClassification.MISC);
        if (range >= 0)
            builder.setTrackingRange(range);
        if (updateFrequency >= 0)
            builder.setUpdateInterval(updateFrequency);
        return (EntityType<T>) builder
                .size(size.width, size.height)
                .setShouldReceiveVelocityUpdates(velocityUpdates)
                .build(name).setRegistryName(new ResourceLocation(MOD_ID, name));
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        registry.register(entitySpear =
                createEntityType(EntitySpear.NAME, new EntitySize(0.5f, 0.5f, false), EntitySpear::new));

        registry.register(entityKnife =
                createEntityType(EntityKnife.NAME, new EntitySize(0.5f, 0.5f, false), EntityKnife::new));

        registry.register(entityJavelin =
                createEntityType(EntityJavelin.NAME, new EntitySize(0.5f, 0.5f, false), EntityJavelin::new));

        registry.register(entityMusketBullet =
                createEntityType(EntityMusketBullet.NAME, new EntitySize(0.5f, 0.5f, false), EntityMusketBullet::new));

        registry.register(entityCrossbowBolt =
                createEntityType(EntityCrossbowBolt.NAME, new EntitySize(0.5f, 0.5f, false), EntityCrossbowBolt::new));

        registry.register(entityBlowgunDart =
                createEntityType(EntityBlowgunDart.NAME, new EntitySize(0.5f, 0.5f, false), EntityBlowgunDart::new));

        registry.register(entityDynamite =
                createEntityType(EntityDynamite.NAME, new EntitySize(0.5f, 0.5f, false), EntityDynamite::new));

        registry.register(entityFlail =
                createEntityType(EntityFlail.NAME, new EntitySize(0.5f, 0.5f, false), EntityFlail::new));

        registry.register(entityCannon =
                createEntityType(EntityCannon.NAME, new EntitySize(1.5f, 1.0f, false), EntityCannon::new));
        registry.register(entityCannonBall =
                createEntityType(EntityCannonBall.NAME, new EntitySize(0.5f, 0.5f, false), EntityCannonBall::new));

        registry.register(entityBlunderShot =
                createEntityType(EntityBlunderShot.NAME, new EntitySize(0.5f, 0.5f, false), EntityBlunderShot::new));

        registry.register(entityDummy =
                createEntityType(EntityDummy.NAME, new EntitySize(0.5f, 1.9f, false), 4, 20, true, EntityDummy::new));

        registry.register(entityBoomerang =
                createEntityType(EntityBoomerang.NAME, new EntitySize(0.5f, 0.5f, false), EntityBoomerang::new));

        registry.register(entityMortarShell =
                createEntityType(EntityMortarShell.NAME, new EntitySize(0.5f, 0.5f, false), EntityMortarShell::new));
    }

    @SubscribeEvent
    public void setEyeHeight(EntityEvent.EyeHeight e) {
        if (e.getEntity() instanceof EntityProjectile) {
            e.setNewHeight(0);
        }
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> registry = event.getRegistry();

        registry.register(spearWood = WMItemBuilder.createStandardSpear("spear.wood", ItemTier.WOOD));
        registry.register(spearStone = WMItemBuilder.createStandardSpear("spear.stone", ItemTier.STONE));
        registry.register(spearSteel = WMItemBuilder.createStandardSpear("spear.iron", ItemTier.IRON));
        registry.register(spearGold = WMItemBuilder.createStandardSpear("spear.gold", ItemTier.GOLD));
        registry.register(spearDiamond = WMItemBuilder.createStandardSpear("spear.diamond", ItemTier.DIAMOND));

        registry.register(halberdWood = WMItemBuilder.createStandardHalberd("halberd.wood", ItemTier.WOOD));
        registry.register(halberdStone = WMItemBuilder.createStandardHalberd("halberd.stone", ItemTier.STONE));
        registry.register(halberdSteel = WMItemBuilder.createStandardHalberd("halberd.iron", ItemTier.IRON));
        registry.register(halberdGold = WMItemBuilder.createStandardHalberd("halberd.gold", ItemTier.GOLD));
        registry.register(halberdDiamond = WMItemBuilder.createStandardHalberd("halberd.diamond", ItemTier.DIAMOND));

        registry.register(battleaxeWood = WMItemBuilder.createStandardBattleaxe("battleaxe.wood", ItemTier.WOOD));
        registry.register(battleaxeStone = WMItemBuilder.createStandardBattleaxe("battleaxe.stone", ItemTier.STONE));
        registry.register(battleaxeSteel = WMItemBuilder.createStandardBattleaxe("battleaxe.iron", ItemTier.IRON));
        registry.register(battleaxeGold = WMItemBuilder.createStandardBattleaxe("battleaxe.gold", ItemTier.GOLD));
        registry.register(battleaxeDiamond =
                WMItemBuilder.createStandardBattleaxe("battleaxe.diamond", ItemTier.DIAMOND));

        registry.register(knifeWood = WMItemBuilder.createStandardKnife("knife.wood", ItemTier.WOOD));
        registry.register(knifeStone = WMItemBuilder.createStandardKnife("knife.stone", ItemTier.STONE));
        registry.register(knifeSteel = WMItemBuilder.createStandardKnife("knife.iron", ItemTier.IRON));
        registry.register(knifeGold = WMItemBuilder.createStandardKnife("knife.gold", ItemTier.GOLD));
        registry.register(knifeDiamond = WMItemBuilder.createStandardKnife("knife.diamond", ItemTier.DIAMOND));

        registry.register(warhammerWood = WMItemBuilder.createStandardWarhammer("warhammer.wood", ItemTier.WOOD));
        registry.register(warhammerStone = WMItemBuilder.createStandardWarhammer("warhammer.stone", ItemTier.STONE));
        registry.register(warhammerSteel = WMItemBuilder.createStandardWarhammer("warhammer.iron", ItemTier.IRON));
        registry.register(warhammerGold = WMItemBuilder.createStandardWarhammer("warhammer.gold", ItemTier.GOLD));
        registry.register(warhammerDiamond =
                WMItemBuilder.createStandardWarhammer("warhammer.diamond", ItemTier.DIAMOND));

        registry.register(flailWood = WMItemBuilder.createStandardFlail("flail.wood", ItemTier.WOOD));
        registry.register(flailStone = WMItemBuilder.createStandardFlail("flail.stone", ItemTier.STONE));
        registry.register(flailSteel = WMItemBuilder.createStandardFlail("flail.iron", ItemTier.IRON));
        registry.register(flailGold = WMItemBuilder.createStandardFlail("flail.gold", ItemTier.GOLD));
        registry.register(flailDiamond = WMItemBuilder.createStandardFlail("flail.diamond", ItemTier.DIAMOND));

        registry.register(katanaWood = WMItemBuilder.createStandardKatana("katana.wood", ItemTier.WOOD));
        registry.register(katanaStone = WMItemBuilder.createStandardKatana("katana.stone", ItemTier.STONE));
        registry.register(katanaSteel = WMItemBuilder.createStandardKatana("katana.iron", ItemTier.IRON));
        registry.register(katanaGold = WMItemBuilder.createStandardKatana("katana.gold", ItemTier.GOLD));
        registry.register(katanaDiamond = WMItemBuilder.createStandardKatana("katana.diamond", ItemTier.DIAMOND));

        registry.register(boomerangWood = WMItemBuilder.createStandardBoomerang("boomerang.wood", ItemTier.WOOD));
        registry.register(boomerangStone = WMItemBuilder.createStandardBoomerang("boomerang.stone", ItemTier.STONE));
        registry.register(boomerangSteel = WMItemBuilder.createStandardBoomerang("boomerang.iron", ItemTier.IRON));
        registry.register(boomerangGold = WMItemBuilder.createStandardBoomerang("boomerang.gold", ItemTier.GOLD));
        registry.register(boomerangDiamond = WMItemBuilder.createStandardBoomerang("boomerang.diamond",
                ItemTier.DIAMOND));

        registry.register(fireRod = WMItemBuilder.createStandardFirerod("firerod"));

        registry.register(javelin = WMItemBuilder.createStandardJavelin("javelin"));

        registry.register(crossbow = WMItemBuilder.createStandardCrossbow("crossbow"));
        registry.register(bolt = WMItemBuilder.createWMItem("bolt"));

        registry.register(blowgun = WMItemBuilder.createStandardBlowgun("blowgun"));
        for (DartType type : DartType.dartTypes) {
            if (type == null) continue;
            ItemBlowgunDart dart = WMItemBuilder.createStandardBlowgunDart(type.typeName, type);
            darts.put(type, dart);
            registry.register(dart);
        }

        registry.register(bayonetWood = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.wood",
                ItemTier.WOOD, knifeWood));
        registry.register(bayonetStone = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.stone",
                ItemTier.STONE, knifeStone));
        registry.register(bayonetSteel = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.iron",
                ItemTier.IRON, knifeSteel));
        registry.register(bayonetGold = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.gold",
                ItemTier.GOLD, knifeGold));
        registry.register(bayonetDiamond = WMItemBuilder.createStandardMusketWithBayonet("musketbayonet.diamond",
                ItemTier.DIAMOND, knifeDiamond));
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

    @SubscribeEvent
    public void onModConfig(ModConfig.ModConfigEvent e) {
        modConfig.postLoadConfig();
    }

    private void registerDispenseBehavior() {
        if (musketBullet != null) {
            DispenserBlock.registerDispenseBehavior(musketBullet, new DispenseMusketBullet());
        }
        if (javelin != null) {
            DispenserBlock.registerDispenseBehavior(javelin, new DispenseJavelin());
        }
        if (bolt != null) {
            DispenserBlock.registerDispenseBehavior(bolt, new DispenseCrossbowBolt());
        }
        for (Item dart : darts.values()) {
            DispenserBlock.registerDispenseBehavior(dart, new DispenseBlowgunDart());
        }
        if (dynamite != null) {
            DispenserBlock.registerDispenseBehavior(dynamite, new DispenseDynamite());
        }
        if (blunderShot != null) {
            DispenserBlock.registerDispenseBehavior(blunderShot, new DispenseBlunderShot());
        }
        if (cannonBall != null) {
            DispenseCannonBall behavior = new DispenseCannonBall();
            DispenserBlock.registerDispenseBehavior(cannonBall, behavior);
            DispenserBlock.registerDispenseBehavior(Items.GUNPOWDER, behavior);
        }
        if (mortarShell != null) {
            DispenserBlock.registerDispenseBehavior(mortarShell, new DispenseMortarShell());
        }
    }

}
