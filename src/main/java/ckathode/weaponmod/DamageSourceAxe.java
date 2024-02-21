package ckathode.weaponmod;

import net.minecraft.util.*;

public class DamageSourceAxe extends DamageSource
{
    public DamageSourceAxe() {
        super("battleaxe");
        this.setDamageBypassesArmor();
    }
}
