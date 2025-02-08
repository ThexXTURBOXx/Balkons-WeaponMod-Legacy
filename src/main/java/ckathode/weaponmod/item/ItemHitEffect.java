package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.util.math.RayTraceResult;

public interface ItemHitEffect {

    void onHitEntity(EntityMaterialProjectile entity, RayTraceResult rayTraceResult);

    void onHitBlock(EntityMaterialProjectile entity, RayTraceResult rayTraceResult);

}
