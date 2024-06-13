package ckathode.weaponmod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

public class WeaponModAttributes {

    private static final String PREFIX = "attribute." + MOD_ID;

    public static final ResourceLocation IGNORE_ARMOUR_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID,
            PREFIX + ".ignore-armour");
    public static final ResourceLocation WEAPON_KNOCKBACK_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID,
            PREFIX + ".knockback");
    public static final ResourceLocation RELOAD_TIME_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID,
            PREFIX + ".reload-time");
    public static final ResourceLocation WEAPON_REACH_ID = ResourceLocation.fromNamespaceAndPath(MOD_ID,
            PREFIX + ".reach");

    public static final Attribute IGNORE_ARMOUR_DAMAGE =
            new RangedAttribute(IGNORE_ARMOUR_DAMAGE_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_KNOCKBACK =
            new RangedAttribute(WEAPON_KNOCKBACK_ID.getPath(), 0.4, 0.0, 2048.0).setSyncable(true);
    public static final Attribute RELOAD_TIME =
            new RangedAttribute(RELOAD_TIME_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);
    public static final Attribute WEAPON_REACH =
            new RangedAttribute(WEAPON_REACH_ID.getPath(), 0.0, 0.0, 2048.0).setSyncable(true);

}
