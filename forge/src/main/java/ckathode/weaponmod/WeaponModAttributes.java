package ckathode.weaponmod;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class WeaponModAttributes {
    public static final Attribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".ignoreArmour", 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_KNOCKBACK =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".knockback", 0.4, 0.0, 2048.0).setSyncable(true);
    public static final Attribute RELOAD_TIME =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".reloadTime", 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_REACH =
            new RangedAttribute(BalkonsWeaponMod.MOD_ID + ".reach", 0.0, 0.0, 2048.0).setSyncable(true);

}
