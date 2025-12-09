package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.dispense.WMDispenserExtension;
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
import ckathode.weaponmod.item.WMItemProjectile;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WMItemBuilder {

    // ==================================================================== //
    // ==================================================================== //
    // =========================== Item builders ========================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ItemMelee createStandardBattleaxe(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompBattleaxe(tier), id);
    }

    public static ItemShooter createStandardBlowgun(@NotNull Identifier id) {
        return createItemShooter(new RangedCompBlowgun(), new MeleeCompNone(null), id);
    }

    public static ItemBlowgunDart createStandardBlowgunDart(@NotNull DartType dartType, @NotNull Identifier id) {
        return createItemBlowgunDart(dartType, id);
    }

    public static ItemShooter createStandardBlunderbuss(@NotNull Identifier id) {
        return createItemShooter(new RangedCompBlunderbuss(), new MeleeCompNone(null), id);
    }

    public static ItemMelee createStandardBoomerang(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompBoomerang(tier), id);
    }

    public static ItemCannon createStandardCannon(@NotNull Identifier id) {
        return createItemCannon(id);
    }

    public static ItemShooter createStandardCrossbow(@NotNull Identifier id) {
        return createItemShooter(new RangedCompCrossbow(), new MeleeCompNone(null), id);
    }

    public static ItemDummy createStandardDummy(@NotNull Identifier id) {
        return createItemDummy(id);
    }

    public static ItemDynamite createStandardDynamite(@NotNull Identifier id) {
        return createItemDynamite(id);
    }

    public static ItemMelee createStandardFirerod(@NotNull Identifier id) {
        return createItemMelee(new MeleeCompFirerod(), id);
    }

    public static ItemFlail createStandardFlail(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemFlail(new MeleeCompNone(tier), id);
    }

    public static ItemShooter createStandardFlintlock(@NotNull Identifier id) {
        return createItemShooter(new RangedCompFlintlock(), new MeleeCompNone(null), id);
    }

    public static ItemMelee createStandardHalberd(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompHalberd(tier), id);
    }

    public static ItemJavelin createStandardJavelin(@NotNull Identifier id) {
        return createItemJavelin(id);
    }

    public static ItemMelee createStandardKatana(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, tier), id);
    }

    public static ItemMelee createStandardKnife(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompKnife(tier), id);
    }

    public static ItemShooter createStandardMortar(@NotNull Identifier id) {
        return createItemShooter(new RangedCompMortar(), new MeleeCompNone(null), id);
    }

    public static ItemMusket createStandardMusket(@NotNull Identifier id) {
        return createItemMusket(id);
    }

    public static ItemMusket createStandardMusketWithBayonet(@NotNull ToolMaterial tier, @NotNull Item bayonetItem,
                                                             @NotNull Identifier id) {
        return createItemMusket(new MeleeCompKnife(tier), bayonetItem, id);
    }

    public static ItemMelee createStandardSpear(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompSpear(tier), id);
    }

    public static ItemMelee createStandardWarhammer(@NotNull ToolMaterial tier, @NotNull Identifier id) {
        return createItemMelee(new MeleeCompWarhammer(tier), id);
    }

    // ==================================================================== //
    // ==================================================================== //
    // ======================== ItemClass builders ======================== //
    // ==================================================================== //
    // ==================================================================== //

    @ExpectPlatform
    public static ItemBlowgunDart createItemBlowgunDart(@NotNull DartType dartType, @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemCannon createItemCannon(@NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemDummy createItemDummy(@NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemDynamite createItemDynamite(@NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemFlail createItemFlail(MeleeComponent meleeComponent, @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemJavelin createItemJavelin(@NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemMelee createItemMelee(MeleeComponent meleeComponent, @NotNull Item.Properties properties) {
        throw new AssertionError();
    }

    public static ItemMusket createItemMusket(@NotNull Identifier id) {
        return createItemMusket(new MeleeCompNone(null), null, id);
    }

    @ExpectPlatform
    public static ItemMusket createItemMusket(MeleeComponent meleeComponent, @Nullable Item bayonetItem,
                                              @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ItemShooter createItemShooter(RangedComponent rangedComponent, MeleeComponent meleeComponent,
                                                @NotNull Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItem createWMItem(@NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItem createWMItem(@NotNull Item.Properties properties) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull Identifier id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static WMItemProjectile createWMItemProjectile(WMDispenserExtension extension,
                                                          @NotNull Item.Properties properties) {
        throw new AssertionError();
    }

    // ==================================================================== //
    // ==================================================================== //
    // ========================== Helper methods ========================== //
    // ==================================================================== //
    // ==================================================================== //

    public static ResourceKey<Item> id(Identifier id) {
        return ResourceKey.create(Registries.ITEM, id);
    }

}
