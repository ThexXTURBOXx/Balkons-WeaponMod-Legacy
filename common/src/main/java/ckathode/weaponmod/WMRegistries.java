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
import ckathode.weaponmod.item.MeleeCompSpear;
import ckathode.weaponmod.item.MeleeCompWarhammer;
import ckathode.weaponmod.item.RangedCompBlowgun;
import ckathode.weaponmod.item.RangedCompBlunderbuss;
import ckathode.weaponmod.item.RangedCompCrossbow;
import ckathode.weaponmod.item.RangedCompFlintlock;
import ckathode.weaponmod.item.RangedCompMortar;
import ckathode.weaponmod.item.WMItem;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import me.shedaniel.architectury.registry.DeferredRegister;
import me.shedaniel.architectury.registry.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WMRegistries {

    // Registries
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(MOD_ID, Registry.ENTITY_TYPE_REGISTRY);
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(MOD_ID, Registry.ATTRIBUTE_REGISTRY);

    // Items
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_WOOD =
            ITEMS.register(MeleeCompSpear.WOOD_ID, () -> MeleeCompSpear.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_STONE =
            ITEMS.register(MeleeCompSpear.STONE_ID, () -> MeleeCompSpear.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_IRON =
            ITEMS.register(MeleeCompSpear.IRON_ID, () -> MeleeCompSpear.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_GOLD =
            ITEMS.register(MeleeCompSpear.GOLD_ID, () -> MeleeCompSpear.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_DIAMOND =
            ITEMS.register(MeleeCompSpear.DIAMOND_ID, () -> MeleeCompSpear.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_SPEAR_NETHERITE =
            ITEMS.register(MeleeCompSpear.NETHERITE_ID, () -> MeleeCompSpear.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_WOOD =
            ITEMS.register(MeleeCompHalberd.WOOD_ID, () -> MeleeCompHalberd.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_STONE =
            ITEMS.register(MeleeCompHalberd.STONE_ID, () -> MeleeCompHalberd.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_IRON =
            ITEMS.register(MeleeCompHalberd.IRON_ID, () -> MeleeCompHalberd.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_GOLD =
            ITEMS.register(MeleeCompHalberd.GOLD_ID, () -> MeleeCompHalberd.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_DIAMOND =
            ITEMS.register(MeleeCompHalberd.DIAMOND_ID, () -> MeleeCompHalberd.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_HALBERD_NETHERITE =
            ITEMS.register(MeleeCompHalberd.NETHERITE_ID, () -> MeleeCompHalberd.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_WOOD =
            ITEMS.register(MeleeCompBattleaxe.WOOD_ID, () -> MeleeCompBattleaxe.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_STONE =
            ITEMS.register(MeleeCompBattleaxe.STONE_ID, () -> MeleeCompBattleaxe.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_IRON =
            ITEMS.register(MeleeCompBattleaxe.IRON_ID, () -> MeleeCompBattleaxe.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_GOLD =
            ITEMS.register(MeleeCompBattleaxe.GOLD_ID, () -> MeleeCompBattleaxe.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_DIAMOND =
            ITEMS.register(MeleeCompBattleaxe.DIAMOND_ID, () -> MeleeCompBattleaxe.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BATTLEAXE_NETHERITE =
            ITEMS.register(MeleeCompBattleaxe.NETHERITE_ID, () -> MeleeCompBattleaxe.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_WOOD =
            ITEMS.register(MeleeCompKnife.WOOD_ID, () -> MeleeCompKnife.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_STONE =
            ITEMS.register(MeleeCompKnife.STONE_ID, () -> MeleeCompKnife.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_IRON =
            ITEMS.register(MeleeCompKnife.IRON_ID, () -> MeleeCompKnife.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_GOLD =
            ITEMS.register(MeleeCompKnife.GOLD_ID, () -> MeleeCompKnife.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_DIAMOND =
            ITEMS.register(MeleeCompKnife.DIAMOND_ID, () -> MeleeCompKnife.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KNIFE_NETHERITE =
            ITEMS.register(MeleeCompKnife.NETHERITE_ID, () -> MeleeCompKnife.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_WOOD =
            ITEMS.register(MeleeCompWarhammer.WOOD_ID, () -> MeleeCompWarhammer.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_STONE =
            ITEMS.register(MeleeCompWarhammer.STONE_ID, () -> MeleeCompWarhammer.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_IRON =
            ITEMS.register(MeleeCompWarhammer.IRON_ID, () -> MeleeCompWarhammer.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_GOLD =
            ITEMS.register(MeleeCompWarhammer.GOLD_ID, () -> MeleeCompWarhammer.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_DIAMOND =
            ITEMS.register(MeleeCompWarhammer.DIAMOND_ID, () -> MeleeCompWarhammer.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_WARHAMMER_NETHERITE =
            ITEMS.register(MeleeCompWarhammer.NETHERITE_ID, () -> MeleeCompWarhammer.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_WOOD =
            ITEMS.register(ItemFlail.WOOD_ID, () -> ItemFlail.WOOD_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_STONE =
            ITEMS.register(ItemFlail.STONE_ID, () -> ItemFlail.STONE_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_IRON =
            ITEMS.register(ItemFlail.IRON_ID, () -> ItemFlail.IRON_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_GOLD =
            ITEMS.register(ItemFlail.GOLD_ID, () -> ItemFlail.GOLD_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_DIAMOND =
            ITEMS.register(ItemFlail.DIAMOND_ID, () -> ItemFlail.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemFlail> ITEM_FLAIL_NETHERITE =
            ITEMS.register(ItemFlail.NETHERITE_ID, () -> ItemFlail.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_WOOD =
            ITEMS.register(ItemMelee.KATANA_WOOD_ID, () -> ItemMelee.KATANA_WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_STONE =
            ITEMS.register(ItemMelee.KATANA_STONE_ID, () -> ItemMelee.KATANA_STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_IRON =
            ITEMS.register(ItemMelee.KATANA_IRON_ID, () -> ItemMelee.KATANA_IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_GOLD =
            ITEMS.register(ItemMelee.KATANA_GOLD_ID, () -> ItemMelee.KATANA_GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_DIAMOND =
            ITEMS.register(ItemMelee.KATANA_DIAMOND_ID, () -> ItemMelee.KATANA_DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_KATANA_NETHERITE =
            ITEMS.register(ItemMelee.KATANA_NETHERITE_ID, () -> ItemMelee.KATANA_NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_WOOD =
            ITEMS.register(MeleeCompBoomerang.WOOD_ID, () -> MeleeCompBoomerang.WOOD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_STONE =
            ITEMS.register(MeleeCompBoomerang.STONE_ID, () -> MeleeCompBoomerang.STONE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_IRON =
            ITEMS.register(MeleeCompBoomerang.IRON_ID, () -> MeleeCompBoomerang.IRON_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_GOLD =
            ITEMS.register(MeleeCompBoomerang.GOLD_ID, () -> MeleeCompBoomerang.GOLD_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_DIAMOND =
            ITEMS.register(MeleeCompBoomerang.DIAMOND_ID, () -> MeleeCompBoomerang.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_BOOMERANG_NETHERITE =
            ITEMS.register(MeleeCompBoomerang.NETHERITE_ID, () -> MeleeCompBoomerang.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMelee> ITEM_FIREROD =
            ITEMS.register(MeleeCompFirerod.ID, () -> MeleeCompFirerod.ITEM);
    public static final RegistrySupplier<ItemJavelin> ITEM_JAVELIN =
            ITEMS.register(ItemJavelin.ID, () -> ItemJavelin.ITEM);
    public static final RegistrySupplier<ItemShooter> ITEM_CROSSBOW =
            ITEMS.register(RangedCompCrossbow.ID, () -> RangedCompCrossbow.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_CROSSBOW_BOLT =
            ITEMS.register(WMItem.CROSSBOW_BOLT_ID, () -> WMItem.CROSSBOW_BOLT_ITEM);
    public static final RegistrySupplier<ItemShooter> ITEM_BLOWGUN =
            ITEMS.register(RangedCompBlowgun.ID, () -> RangedCompBlowgun.ITEM);
    public static final Map<DartType, RegistrySupplier<ItemBlowgunDart>> ITEM_DARTS =
            Arrays.stream(DartType.dartTypes).filter(Objects::nonNull)
                    .map(t -> new Pair<>(t, ItemBlowgunDart.ITEMS.get(t))).collect(Collectors.toMap(Pair::getFirst,
                            p -> ITEMS.register(p.getFirst().typeName, p::getSecond)));
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_WOOD =
            ITEMS.register(ItemMusket.WOOD_ID, () -> ItemMusket.WOOD_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_STONE =
            ITEMS.register(ItemMusket.STONE_ID, () -> ItemMusket.STONE_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_IRON =
            ITEMS.register(ItemMusket.IRON_ID, () -> ItemMusket.IRON_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_GOLD =
            ITEMS.register(ItemMusket.GOLD_ID, () -> ItemMusket.GOLD_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_DIAMOND =
            ITEMS.register(ItemMusket.DIAMOND_ID, () -> ItemMusket.DIAMOND_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET_NETHERITE =
            ITEMS.register(ItemMusket.NETHERITE_ID, () -> ItemMusket.NETHERITE_ITEM);
    public static final RegistrySupplier<ItemMusket> ITEM_MUSKET =
            ITEMS.register(ItemMusket.ID, () -> ItemMusket.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_MUSKET_IRON_PART =
            ITEMS.register(WMItem.MUSKET_IRON_PART_ID, () -> WMItem.MUSKET_IRON_PART_ITEM);
    public static final RegistrySupplier<ItemShooter> ITEM_BLUNDERBUSS =
            ITEMS.register(RangedCompBlunderbuss.ID, () -> RangedCompBlunderbuss.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_BLUNDER_IRON_PART =
            ITEMS.register(WMItem.BLUNDER_IRON_PART_ID, () -> WMItem.BLUNDER_IRON_PART_ITEM);
    public static final RegistrySupplier<WMItem> ITEM_BLUNDER_SHOT =
            ITEMS.register(WMItem.BLUNDER_SHOT_ID, () -> WMItem.BLUNDER_SHOT_ITEM);
    public static final RegistrySupplier<ItemShooter> ITEM_FLINTLOCK_PISTOL =
            ITEMS.register(RangedCompFlintlock.ID, () -> RangedCompFlintlock.ITEM);
    public static final RegistrySupplier<ItemDynamite> ITEM_DYNAMITE =
            ITEMS.register(ItemDynamite.ID, () -> ItemDynamite.ITEM);
    public static final RegistrySupplier<ItemCannon> ITEM_CANNON =
            ITEMS.register(ItemCannon.ID, () -> ItemCannon.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_CANNON_BALL =
            ITEMS.register(WMItem.CANNON_BALL_ID, () -> WMItem.CANNON_BALL_ITEM);
    public static final RegistrySupplier<ItemDummy> ITEM_DUMMY =
            ITEMS.register(ItemDummy.ID, () -> ItemDummy.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_GUN_STOCK =
            ITEMS.register(WMItem.GUN_STOCK_ID, () -> WMItem.GUN_STOCK_ITEM);
    public static final RegistrySupplier<WMItem> ITEM_MUSKET_BULLET =
            ITEMS.register(WMItem.BULLET_MUSKET_ID, () -> WMItem.BULLET_MUSKET_ITEM);
    public static final RegistrySupplier<ItemShooter> ITEM_MORTAR =
            ITEMS.register(RangedCompMortar.ID, () -> RangedCompMortar.ITEM);
    public static final RegistrySupplier<WMItem> ITEM_MORTAR_IRON_PART =
            ITEMS.register(WMItem.MORTAR_IRON_PART_ID, () -> WMItem.MORTAR_IRON_PART_ITEM);
    public static final RegistrySupplier<WMItem> ITEM_MORTAR_SHELL =
            ITEMS.register(WMItem.MORTAR_SHELL_ID, () -> WMItem.MORTAR_SHELL_ITEM);

    // Entity Types
    public static final RegistrySupplier<EntityType<EntitySpear>> ENTITY_SPEAR =
            ENTITY_TYPES.register(EntitySpear.ID, () -> EntitySpear.TYPE);
    public static final RegistrySupplier<EntityType<EntityKnife>> ENTITY_KNIFE =
            ENTITY_TYPES.register(EntityKnife.ID, () -> EntityKnife.TYPE);
    public static final RegistrySupplier<EntityType<EntityFlail>> ENTITY_FLAIL =
            ENTITY_TYPES.register(EntityFlail.ID, () -> EntityFlail.TYPE);
    public static final RegistrySupplier<EntityType<EntityBoomerang>> ENTITY_BOOMERANG =
            ENTITY_TYPES.register(EntityBoomerang.ID, () -> EntityBoomerang.TYPE);
    public static final RegistrySupplier<EntityType<EntityJavelin>> ENTITY_JAVELIN =
            ENTITY_TYPES.register(EntityJavelin.ID, () -> EntityJavelin.TYPE);
    public static final RegistrySupplier<EntityType<EntityCrossbowBolt>> ENTITY_CROSSBOW_BOLT =
            ENTITY_TYPES.register(EntityCrossbowBolt.ID, () -> EntityCrossbowBolt.TYPE);
    public static final RegistrySupplier<EntityType<EntityBlowgunDart>> ENTITY_BLOWGUN_DART =
            ENTITY_TYPES.register(EntityBlowgunDart.ID, () -> EntityBlowgunDart.TYPE);
    public static final RegistrySupplier<EntityType<EntityBlunderShot>> ENTITY_BLUNDER_SHOT =
            ENTITY_TYPES.register(EntityBlunderShot.ID, () -> EntityBlunderShot.TYPE);
    public static final RegistrySupplier<EntityType<EntityDynamite>> ENTITY_DYNAMITE =
            ENTITY_TYPES.register(EntityDynamite.ID, () -> EntityDynamite.TYPE);
    public static final RegistrySupplier<EntityType<EntityCannon>> ENTITY_CANNON =
            ENTITY_TYPES.register(EntityCannon.ID, () -> EntityCannon.TYPE);
    public static final RegistrySupplier<EntityType<EntityCannonBall>> ENTITY_CANNON_BALL =
            ENTITY_TYPES.register(EntityCannonBall.ID, () -> EntityCannonBall.TYPE);
    public static final RegistrySupplier<EntityType<EntityDummy>> ENTITY_DUMMY =
            ENTITY_TYPES.register(EntityDummy.ID, () -> EntityDummy.TYPE);
    public static final RegistrySupplier<EntityType<EntityMusketBullet>> ENTITY_MUSKET_BULLET =
            ENTITY_TYPES.register(EntityMusketBullet.ID, () -> EntityMusketBullet.TYPE);
    public static final RegistrySupplier<EntityType<EntityMortarShell>> ENTITY_MORTAR_SHELL =
            ENTITY_TYPES.register(EntityMortarShell.ID, () -> EntityMortarShell.TYPE);

    // Attributes
    public static final RegistrySupplier<Attribute> IGNORE_ARMOUR_DAMAGE =
            ATTRIBUTES.register(WeaponModAttributes.IGNORE_ARMOUR_DAMAGE.getDescriptionId(),
                    () -> WeaponModAttributes.IGNORE_ARMOUR_DAMAGE);
    public static final RegistrySupplier<Attribute> WEAPON_KNOCKBACK =
            ATTRIBUTES.register(WeaponModAttributes.WEAPON_KNOCKBACK.getDescriptionId(),
                    () -> WeaponModAttributes.WEAPON_KNOCKBACK);
    public static final RegistrySupplier<Attribute> RELOAD_TIME =
            ATTRIBUTES.register(WeaponModAttributes.RELOAD_TIME.getDescriptionId(),
                    () -> WeaponModAttributes.RELOAD_TIME);
    public static final RegistrySupplier<Attribute> WEAPON_REACH =
            ATTRIBUTES.register(WeaponModAttributes.WEAPON_REACH.getDescriptionId(),
                    () -> WeaponModAttributes.WEAPON_REACH);

    private static void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ItemJavelin.ITEM, new DispenseJavelin());
        DispenserBlock.registerBehavior(WMItem.CROSSBOW_BOLT_ITEM, new DispenseCrossbowBolt());
        Arrays.stream(DartType.dartTypes).filter(Objects::nonNull).map(ItemBlowgunDart.ITEMS::get)
                .forEach(item -> DispenserBlock.registerBehavior(item, new DispenseBlowgunDart()));
        DispenserBlock.registerBehavior(WMItem.BLUNDER_SHOT_ITEM, new DispenseBlunderShot());
        DispenserBlock.registerBehavior(ItemDynamite.ITEM, new DispenseDynamite());
        DispenseCannonBall behavior = new DispenseCannonBall();
        DispenserBlock.registerBehavior(WMItem.CANNON_BALL_ITEM, behavior);
        DispenserBlock.registerBehavior(Items.GUNPOWDER, behavior);
        DispenserBlock.registerBehavior(WMItem.BULLET_MUSKET_ITEM, new DispenseMusketBullet());
        DispenserBlock.registerBehavior(WMItem.MORTAR_SHELL_ITEM, new DispenseMortarShell());
    }

    public static void init() {
        ITEMS.register();
        ENTITY_TYPES.register();
        ATTRIBUTES.register();
        registerDispenserBehaviors();
    }

    public static <T extends Entity> EntityType<T> createEntityType(String name, EntityDimensions size,
                                                                    EntityType.EntityFactory<T> factory) {
        return createEntityType(name, size, -1, -1, factory);
    }

    public static <T extends Entity> EntityType<T> createEntityType(String name, EntityDimensions size, int range,
                                                                    int updateFrequency,
                                                                    EntityType.EntityFactory<T> factory) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, MobCategory.MISC);
        if (range >= 0)
            builder.clientTrackingRange(range);
        if (updateFrequency >= 0)
            builder.updateInterval(updateFrequency);
        return builder.sized(size.width, size.height).build(name);
    }
}
