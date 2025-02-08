package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;

public interface ItemHitEffect {

    void onEntityHit(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity, Entity hitEntity);

    void onGroundHit(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity,
                     RayTraceResult rayTraceResult);

}
