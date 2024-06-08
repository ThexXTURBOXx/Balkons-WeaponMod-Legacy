package ckathode.weaponmod.entity.projectile.neoforge;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;

public class EntityProjectileImpl {

    public static boolean onProjectileImpact(EntityProjectile<?> projectile, HitResult hitResult) {
        return EventHooks.onProjectileImpact(projectile, hitResult);
    }

}
