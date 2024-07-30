package ckathode.weaponmod;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WeaponModAttributes {
    public static final BaseAttribute ATTACK_SPEED =
            new RangedAttribute(MOD_ID + ".attackSpeed", 4.0, 0.0, 1024.0).setShouldWatch(true);
    public static final BaseAttribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(MOD_ID + ".ignoreArmour", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute WEAPON_KNOCKBACK =
            new RangedAttribute(MOD_ID + ".knockback", 0.4, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute RELOAD_TIME =
            new RangedAttribute(MOD_ID + ".reloadTime", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute WEAPON_REACH =
            new RangedAttribute(MOD_ID + ".reach", 0.0, 0.0, 2048.0).setShouldWatch(true);

}
