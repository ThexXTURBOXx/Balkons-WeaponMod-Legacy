package ckathode.weaponmod;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class WeaponModAttributes extends SharedMonsterAttributes {
    public static final Attribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(null, BalkonsWeaponMod.MOD_ID + ".ignore-armour", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final Attribute WEAPON_KNOCKBACK =
            new RangedAttribute(null, BalkonsWeaponMod.MOD_ID + ".knockback", 0.4, 0.0, 2048.0).setShouldWatch(true);
    public static final Attribute RELOAD_TIME =
            new RangedAttribute(null, BalkonsWeaponMod.MOD_ID + ".reload-time", 0.0, 0.0, 2048.0).setShouldWatch(true);
    public static final Attribute WEAPON_REACH =
            new RangedAttribute(null, BalkonsWeaponMod.MOD_ID + ".reach", 0.0, 0.0, 2048.0).setShouldWatch(true);

}
