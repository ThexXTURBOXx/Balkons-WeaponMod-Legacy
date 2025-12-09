package ckathode.weaponmod;

import net.minecraft.resources.Identifier;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public final class WeaponModResources {
    public static final class Entity {
        public static final Identifier DART = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/blowgun_dart.png");
        public static final Identifier BOOMERANG = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/boomerang.png");
        public static final Identifier CANNON = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/cannon.png");
        public static final Identifier CANNON_LEGACY = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/cannon_legacy.png");
        public static final Identifier CANNONBALL = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/cannon_ball.png");
        public static final Identifier BOLT = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/crossbow_bolt.png");
        public static final Identifier DUMMY = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/dummy.png");
        public static final Identifier DYNAMITE = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/dynamite.png");
        public static final Identifier FLAIL = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/flail.png");
        public static final Identifier JAVELIN = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/spear.png");
        public static final Identifier KNIFE = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/knife.png");
        public static final Identifier BULLET = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/musket_bullet.png");
        public static final Identifier SPEAR = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/entity/spear.png");
    }

    public static final class Gui {
        public static final Identifier OVERLAY = Identifier.fromNamespaceAndPath(
                MOD_ID, "textures/gui/overlay.png");
    }
}
