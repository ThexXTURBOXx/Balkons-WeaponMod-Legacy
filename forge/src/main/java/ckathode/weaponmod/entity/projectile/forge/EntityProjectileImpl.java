package ckathode.weaponmod.entity.projectile.forge;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityProjectileImpl {

    public static boolean onProjectileImpact(EntityProjectile<?> projectile, HitResult hitResult) {
        return ForgeEventFactory.onProjectileImpact(projectile, hitResult);
    }

}
