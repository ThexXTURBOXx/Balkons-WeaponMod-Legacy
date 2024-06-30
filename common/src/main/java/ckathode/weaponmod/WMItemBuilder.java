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
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilder {

    // ==================================================================== //
    // ==================================================================== //
    // =========================== Item builders ========================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemMelee createStandardBattleaxe(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompBattleaxe(tier));
    }

    public static ItemShooter createStandardBlowgun() {
        return createItemShooter(new RangedCompBlowgun(), new MeleeCompNone(null));
    }

    public static ItemBlowgunDart createStandardBlowgunDart(@NotNull DartType dartType) {
        return createItemBlowgunDart(dartType);
    }

    public static ItemShooter createStandardBlunderbuss() {
        return createItemShooter(new RangedCompBlunderbuss(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardBoomerang(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompBoomerang(tier));
    }

    public static ItemCannon createStandardCannon() {
        return createItemCannon();
    }

    public static ItemShooter createStandardCrossbow() {
        return createItemShooter(new RangedCompCrossbow(), new MeleeCompNone(null));
    }

    public static ItemDummy createStandardDummy() {
        return createItemDummy();
    }

    public static ItemDynamite createStandardDynamite() {
        return createItemDynamite();
    }

    public static ItemMelee createStandardFirerod() {
        return createItemMelee(new MeleeCompFirerod());
    }

    public static ItemFlail createStandardFlail(@NotNull Tier tier) {
        return createItemFlail(new MeleeCompNone(tier));
    }

    public static ItemShooter createStandardFlintlock() {
        return createItemShooter(new RangedCompFlintlock(), new MeleeCompNone(null));
    }

    public static ItemMelee createStandardHalberd(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompHalberd(tier));
    }

    public static ItemJavelin createStandardJavelin() {
        return createItemJavelin();
    }

    public static ItemMelee createStandardKatana(@NotNull Tier tier) {
        return createItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, tier));
    }

    public static ItemMelee createStandardKnife(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompKnife(tier));
    }

    public static ItemShooter createStandardMortar() {
        return createItemShooter(new RangedCompMortar(), new MeleeCompNone(null));
    }

    public static ItemMusket createStandardMusket() {
        return createItemMusket();
    }

    public static ItemMusket createStandardMusketWithBayonet(@NotNull Tier tier, @NotNull Item bayonetItem) {
        return createItemMusket(new MeleeCompKnife(tier), bayonetItem);
    }

    public static ItemMelee createStandardSpear(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompSpear(tier));
    }

    public static ItemMelee createStandardWarhammer(@NotNull Tier tier) {
        return createItemMelee(new MeleeCompWarhammer(tier));
    }

    // ==================================================================== //
    // ==================================================================== //
    // ======================== ItemClass builders ======================== //
    // ==================================================================== //
    // ==================================================================== //

    @ExpectPlatform
    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemCannon createItemCannon() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemDummy createItemDummy() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemDynamite createItemDynamite() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemFlail createItemFlail(MeleeComponent meleeComponent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemJavelin createItemJavelin() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemMelee createItemMelee(MeleeComponent meleeComponent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, Item.Properties properties) {
        throw new AssertionError();
    }

    public static ItemMusket createItemMusket() {
        return createItemMusket(new MeleeCompNone(null), null);
    }

    @ExpectPlatform
    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItem createWMItem() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItem createWMItem(Item.Properties properties) {
        throw new AssertionError();
    }

}
