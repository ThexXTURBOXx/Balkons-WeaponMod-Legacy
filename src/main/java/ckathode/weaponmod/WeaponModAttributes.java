package ckathode.weaponmod;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class WeaponModAttributes {
    public static final BaseAttribute ATTACK_SPEED =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".attackSpeed", 4.0, 0.0, 1024.0).setShouldWatch(true);
    public static final BaseAttribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".ignoreArmour", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute WEAPON_KNOCKBACK =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".knockback", 0.4, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute RELOAD_TIME =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".reloadTime", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final BaseAttribute WEAPON_REACH =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".reach", 0.0, 0.0, 2048.0).setShouldWatch(true);

}
