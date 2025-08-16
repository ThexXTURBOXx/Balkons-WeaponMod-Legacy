package ckathode.weaponmod;

import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class WMUtil {

    public static final RandomSource RANDOM = RandomSource.createNewThreadLocalInstance();

    @SuppressWarnings("deprecation")
    public static void hurt(Entity entity, DamageSource damageSource, float amount) {
        entity.hurt(damageSource, amount);
    }

    @SuppressWarnings("deprecation")
    public static boolean hurtOrSimulate(Entity entity, DamageSource damageSource, float amount) {
        return entity.hurtOrSimulate(damageSource, amount);
    }

}
