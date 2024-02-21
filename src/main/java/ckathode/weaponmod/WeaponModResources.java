package ckathode.weaponmod;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public abstract class WeaponModResources
{
    public abstract static class Textures
    {
        public static final ResourceLocation DART;
        public static final ResourceLocation BOOMERANG;
        public static final ResourceLocation CANNON;
        public static final ResourceLocation CANNONBALL;
        public static final ResourceLocation BOLT;
        public static final ResourceLocation DUMMY;
        public static final ResourceLocation DYNAMITE;
        public static final ResourceLocation FLAIL;
        public static final ResourceLocation JAVELIN;
        public static final ResourceLocation KNIFE;
        public static final ResourceLocation BULLET;
        public static final ResourceLocation SPEAR;
        public static final ResourceLocation ENCHANTMENT_GLINT;
        public static final ResourceLocation ICONS;
        
        static {
            DART = new ResourceLocation("weaponmod", "textures/entity/blowgun_dart.png");
            BOOMERANG = new ResourceLocation("weaponmod", "textures/entity/boomerang.png");
            CANNON = new ResourceLocation("weaponmod", "textures/entity/cannon.png");
            CANNONBALL = new ResourceLocation("weaponmod", "textures/entity/cannon_ball.png");
            BOLT = new ResourceLocation("weaponmod", "textures/entity/crossbow_bolt.png");
            DUMMY = new ResourceLocation("weaponmod", "textures/entity/dummy.png");
            DYNAMITE = new ResourceLocation("weaponmod", "textures/entity/dynamite.png");
            FLAIL = new ResourceLocation("weaponmod", "textures/entity/flail.png");
            JAVELIN = new ResourceLocation("weaponmod", "textures/entity/spear.png");
            KNIFE = new ResourceLocation("weaponmod", "textures/entity/knife.png");
            BULLET = new ResourceLocation("weaponmod", "textures/entity/musket_bullet.png");
            SPEAR = new ResourceLocation("weaponmod", "textures/entity/spear.png");
            ENCHANTMENT_GLINT = new ResourceLocation("minecraft", "%blur%/misc/enchanted_item_glint.png");
            ICONS = new ResourceLocation("weaponmod", "textures/gui/icons.png");
        }
    }
}
