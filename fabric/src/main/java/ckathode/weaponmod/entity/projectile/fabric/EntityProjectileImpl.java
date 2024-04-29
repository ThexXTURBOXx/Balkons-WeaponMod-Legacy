package ckathode.weaponmod.entity.projectile.fabric;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.world.phys.HitResult;

public class EntityProjectileImpl {

    public static boolean onProjectileImpact(EntityProjectile<?> projectile, HitResult hitResult) {
        // There is no equivalent on Fabric - hence, do not cancel
        return false;
    }

}
