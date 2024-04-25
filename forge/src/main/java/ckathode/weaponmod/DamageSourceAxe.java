package ckathode.weaponmod;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSourceAxe extends DamageSource {
    public DamageSourceAxe() {
        super("battleaxe");
        bypassArmor();
    }
}
