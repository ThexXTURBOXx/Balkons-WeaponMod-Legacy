package ckathode.weaponmod;

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
import ckathode.weaponmod.item.RangedComponent;
import ckathode.weaponmod.item.WMItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilder {

    // ==================================================================== //
    // ==================================================================== //
    // =========================== Item builders ========================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemMelee createStandardBattleaxe(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompBattleaxe(tier));
    }

    public static ItemShooter createStandardBlowgun(String id) {
        return createItemShooter(id, new RangedCompBlowgun(), new MeleeCompNone(null));
    }

    public static ItemBlowgunDart createStandardBlowgunDart(String id, @NotNull DartType dartType) {
        return createItemBlowgunDart(id, dartType);
    }

    public static ItemShooter createStandardBlunderbuss(String id) {
        return createItemShooter(id, new RangedCompBlunderbuss(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardBoomerang(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompBoomerang(tier));
    }

    public static ItemCannon createStandardCannon(String id) {
        return createItemCannon(id);
    }

    public static ItemShooter createStandardCrossbow(String id) {
        return createItemShooter(id, new RangedCompCrossbow(), new MeleeCompNone(null));
    }

    public static ItemDummy createStandardDummy(String id) {
        return createItemDummy(id);
    }

    public static ItemDynamite createStandardDynamite(String id) {
        return createItemDynamite(id);
    }

    public static ItemMelee createStandardFirerod(String id) {
        return createItemMelee(id, new MeleeCompFirerod());
    }

    public static ItemFlail createStandardFlail(String id, @NotNull IItemTier tier) {
        return createItemFlail(id, new MeleeCompNone(tier));
    }

    public static ItemShooter createStandardFlintlock(String id) {
        return createItemShooter(id, new RangedCompFlintlock(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardHalberd(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompHalberd(tier));
    }

    public static ItemJavelin createStandardJavelin(String id) {
        return createItemJavelin(id);
    }

    public static ItemMelee createStandardKatana(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, tier));
    }

    public static ItemMelee createStandardKnife(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompKnife(tier));
    }

    public static ItemShooter createStandardMortar(String id) {
        return createItemShooter(id, new RangedCompMortar(), new MeleeCompNone(null));
    }

    public static ItemMusket createStandardMusket(String id) {
        return createItemMusket(id);
    }

    public static ItemMusket createStandardMusketWithBayonet(String id, @NotNull IItemTier tier,
                                                             @NotNull Item bayonetItem) {
        return createItemMusket(id, new MeleeCompKnife(tier), bayonetItem);
    }

    public static ItemMelee createStandardSpear(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompSpear(tier));
    }

    public static ItemMelee createStandardWarhammer(String id, @NotNull IItemTier tier) {
        return createItemMelee(id, new MeleeCompWarhammer(tier));
    }

    // ==================================================================== //
    // ==================================================================== //
    // ========================= Mod Item builders ======================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemMelee createStandardBattleaxe(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompBattleaxe(tier));
    }

    public static ItemShooter createStandardBlowgun(String modId, String id) {
        return createItemShooter(modId, id, new RangedCompBlowgun(), new MeleeCompNone(null));
    }

    public static ItemBlowgunDart createStandardBlowgunDart(String modId, String id, @NotNull DartType dartType) {
        return createItemBlowgunDart(modId, id, dartType);
    }

    public static ItemShooter createStandardBlunderbuss(String modId, String id) {
        return createItemShooter(modId, id, new RangedCompBlunderbuss(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardBoomerang(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompBoomerang(tier));
    }

    public static ItemCannon createStandardCannon(String modId, String id) {
        return createItemCannon(modId, id);
    }

    public static ItemShooter createStandardCrossbow(String modId, String id) {
        return createItemShooter(modId, id, new RangedCompCrossbow(), new MeleeCompNone(null));
    }

    public static ItemDummy createStandardDummy(String modId, String id) {
        return createItemDummy(modId, id);
    }

    public static ItemDynamite createStandardDynamite(String modId, String id) {
        return createItemDynamite(modId, id);
    }

    public static ItemMelee createStandardFirerod(String modId, String id) {
        return createItemMelee(modId, id, new MeleeCompFirerod());
    }

    public static ItemFlail createStandardFlail(String modId, String id, @NotNull IItemTier tier) {
        return createItemFlail(modId, id, new MeleeCompNone(tier));
    }

    public static ItemShooter createStandardFlintlock(String modId, String id) {
        return createItemShooter(modId, id, new RangedCompFlintlock(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardHalberd(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompHalberd(tier));
    }

    public static ItemJavelin createStandardJavelin(String modId, String id) {
        return createItemJavelin(modId, id);
    }

    public static ItemMelee createStandardKatana(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, tier));
    }

    public static ItemMelee createStandardKnife(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompKnife(tier));
    }

    public static ItemShooter createStandardMortar(String modId, String id) {
        return createItemShooter(modId, id, new RangedCompMortar(), new MeleeCompNone(null));
    }

    public static ItemMusket createStandardMusket(String modId, String id) {
        return createItemMusket(modId, id);
    }

    public static ItemMusket createStandardMusketWithBayonet(String modId, String id, @NotNull IItemTier tier,
                                                             @NotNull Item bayonetItem) {
        return createItemMusket(modId, id, new MeleeCompKnife(tier), bayonetItem);
    }

    public static ItemMelee createStandardSpear(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompSpear(tier));
    }

    public static ItemMelee createStandardWarhammer(String modId, String id, @NotNull IItemTier tier) {
        return createItemMelee(modId, id, new MeleeCompWarhammer(tier));
    }

    // ==================================================================== //
    // ==================================================================== //
    // ======================== ItemClass builders ======================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemBlowgunDart createItemBlowgunDart(String id, @NotNull DartType dartType) {
        return new ItemBlowgunDart(id, dartType);
    }

    public static ItemCannon createItemCannon(String id) {
        return new ItemCannon(id);
    }

    public static ItemDummy createItemDummy(String id) {
        return new ItemDummy(id);
    }

    public static ItemDynamite createItemDynamite(String id) {
        return new ItemDynamite(id);
    }

    public static ItemFlail createItemFlail(String id, MeleeComponent meleeComponent) {
        return new ItemFlail(id, meleeComponent);
    }

    public static ItemJavelin createItemJavelin(String id) {
        return new ItemJavelin(id);
    }

    public static ItemMelee createItemMelee(String id, MeleeComponent meleeComponent) {
        return new ItemMelee(id, meleeComponent);
    }

    public static ItemMelee createItemMelee(String id, MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemMelee(id, meleeComponent, properties);
    }

    public static ItemMusket createItemMusket(String id) {
        return createItemMusket(id, new MeleeCompNone(null), null);
    }

    public static ItemMusket createItemMusket(String id, MeleeComponent meleeComponent, @Nullable Item bayonetItem) {
        return new ItemMusket(id, meleeComponent, bayonetItem);
    }

    public static ItemShooter createItemShooter(String id, RangedComponent rangedComponent,
                                                MeleeComponent meleeComponent) {
        return new ItemShooter(id, rangedComponent, meleeComponent);
    }

    public static ItemShooter createItemShooter(String id, RangedComponent rangedComponent,
                                                MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemShooter(id, rangedComponent, meleeComponent, properties);
    }

    public static WMItem createWMItem(String id) {
        return new WMItem(id);
    }

    public static WMItem createWMItem(String id, Item.Properties properties) {
        return new WMItem(id, properties);
    }

    // ==================================================================== //
    // ==================================================================== //
    // ====================== Mod ItemClass builders ====================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemBlowgunDart createItemBlowgunDart(String modId, String id, @NotNull DartType dartType) {
        return new ItemBlowgunDart(modId, id, dartType);
    }

    public static ItemCannon createItemCannon(String modId, String id) {
        return new ItemCannon(modId, id);
    }

    public static ItemDummy createItemDummy(String modId, String id) {
        return new ItemDummy(modId, id);
    }

    public static ItemDynamite createItemDynamite(String modId, String id) {
        return new ItemDynamite(modId, id);
    }

    public static ItemFlail createItemFlail(String modId, String id, MeleeComponent meleeComponent) {
        return new ItemFlail(modId, id, meleeComponent);
    }

    public static ItemJavelin createItemJavelin(String modId, String id) {
        return new ItemJavelin(modId, id);
    }

    public static ItemMelee createItemMelee(String modId, String id, MeleeComponent meleeComponent) {
        return new ItemMelee(modId, id, meleeComponent);
    }

    public static ItemMelee createItemMelee(String modId, String id, MeleeComponent meleeComponent,
                                            Item.Properties properties) {
        return new ItemMelee(modId, id, meleeComponent, properties);
    }

    public static ItemMusket createItemMusket(String modId, String id) {
        return createItemMusket(modId, id, new MeleeCompNone(null), null);
    }

    public static ItemMusket createItemMusket(String modId, String id, MeleeComponent meleeComponent,
                                              @Nullable Item bayonetItem) {
        return new ItemMusket(modId, id, meleeComponent, bayonetItem);
    }

    public static ItemShooter createItemShooter(String modId, String id, RangedComponent rangedComponent,
                                                MeleeComponent meleeComponent) {
        return new ItemShooter(modId, id, rangedComponent, meleeComponent);
    }

    public static ItemShooter createItemShooter(String modId, String id, RangedComponent rangedComponent,
                                                MeleeComponent meleeComponent, Item.Properties properties) {
        return new ItemShooter(modId, id, rangedComponent, meleeComponent, properties);
    }

    public static WMItem createWMItem(String modId, String id) {
        return new WMItem(modId, id);
    }

    public static WMItem createWMItem(String modId, String id, Item.Properties properties) {
        return new WMItem(modId, id, properties);
    }

}
