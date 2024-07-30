package ckathode.weaponmod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

@SideOnly(Side.CLIENT)
public final class WeaponModResources {
    public static final class Entity {
        public static final ResourceLocation DART = new ResourceLocation(
                MOD_ID, "textures/entity/blowgun_dart.png");
        public static final ResourceLocation BOOMERANG = new ResourceLocation(
                MOD_ID, "textures/entity/boomerang.png");
        public static final ResourceLocation CANNON = new ResourceLocation(
                MOD_ID, "textures/entity/cannon.png");
        public static final ResourceLocation CANNONBALL = new ResourceLocation(
                MOD_ID, "textures/entity/cannon_ball.png");
        public static final ResourceLocation BOLT = new ResourceLocation(
                MOD_ID, "textures/entity/crossbow_bolt.png");
        public static final ResourceLocation DUMMY = new ResourceLocation(
                MOD_ID, "textures/entity/dummy.png");
        public static final ResourceLocation DYNAMITE = new ResourceLocation(
                MOD_ID, "textures/entity/dynamite.png");
        public static final ResourceLocation FLAIL = new ResourceLocation(
                MOD_ID, "textures/entity/flail.png");
        public static final ResourceLocation JAVELIN = new ResourceLocation(
                MOD_ID, "textures/entity/spear.png");
        public static final ResourceLocation KNIFE = new ResourceLocation(
                MOD_ID, "textures/entity/knife.png");
        public static final ResourceLocation BULLET = new ResourceLocation(
                MOD_ID, "textures/entity/musket_bullet.png");
        public static final ResourceLocation SPEAR = new ResourceLocation(
                MOD_ID, "textures/entity/spear.png");
    }

    public static final class Gui {
        public static final ResourceLocation OVERLAY = new ResourceLocation(
                MOD_ID, "textures/gui/overlay.png");
    }
}
