package ckathode.weaponmod;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WeaponModAttributes {

    private static final String PREFIX = "attribute." + MOD_ID;

    public static final Attribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(PREFIX + ".ignoreArmour", 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_KNOCKBACK =
            new RangedAttribute(PREFIX + ".knockback", 0.4, 0.0, 2048.0).setSyncable(true);
    public static final Attribute RELOAD_TIME =
            new RangedAttribute(PREFIX + ".reloadTime", 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_REACH =
            new RangedAttribute(PREFIX + ".reach", 0.0, 0.0, 2048.0).setSyncable(true);

}
