package ckathode.weaponmod;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class WeaponModAttributes extends SharedMonsterAttributes
{
    public static final BaseAttribute IGNORE_ARMOUR_DAMAGE;
    public static final BaseAttribute WEAPON_KNOCKBACK;
    public static final BaseAttribute RELOAD_TIME;
    public static final BaseAttribute WEAPON_REACH;
    
    static {
        IGNORE_ARMOUR_DAMAGE = new RangedAttribute(null, "weaponmod.ignoreArmour", 0.0, 0.0, 2048.0).setShouldWatch(true);
        WEAPON_KNOCKBACK = new RangedAttribute(null, "weaponmod.knockback", 0.4, 0.0, 2048.0).setShouldWatch(true);
        RELOAD_TIME = new RangedAttribute(null, "weaponmod.reloadTime", 0.0, 0.0, 2048.0).setShouldWatch(true);
        WEAPON_REACH = new RangedAttribute(null, "weaponmod.reach", 0.0, 0.0, 2048.0).setShouldWatch(true);
    }
}
