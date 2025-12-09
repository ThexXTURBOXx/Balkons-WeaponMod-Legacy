package ckathode.weaponmod;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WeaponModAttributes {

    private static final String PREFIX = "attribute." + MOD_ID;

    public static final Identifier IGNORE_ARMOUR_DAMAGE_ID = BalkonsWeaponMod.id(PREFIX + ".ignore-armour");
    public static final Identifier WEAPON_KNOCKBACK_ID = BalkonsWeaponMod.id(PREFIX + ".knockback");
    public static final Identifier RELOAD_TIME_ID = BalkonsWeaponMod.id(PREFIX + ".reload-time");
    public static final Identifier WEAPON_REACH_ID = BalkonsWeaponMod.id(PREFIX + ".reach");

    public static final Attribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(IGNORE_ARMOUR_DAMAGE_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_KNOCKBACK =
            new RangedAttribute(WEAPON_KNOCKBACK_ID.getPath(), 0.4, 0.0, 2048.0).setSyncable(true);
    public static final Attribute RELOAD_TIME =
            new RangedAttribute(RELOAD_TIME_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_REACH =
            new RangedAttribute(WEAPON_REACH_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);

}
