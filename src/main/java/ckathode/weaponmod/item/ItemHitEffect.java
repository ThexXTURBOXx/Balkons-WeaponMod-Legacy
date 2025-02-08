package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.util.math.RayTraceResult;

public interface ItemHitEffect {

    void onHitEntity(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity,
                     RayTraceResult rayTraceResult);

    void onHitBlock(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity,
                    RayTraceResult rayTraceResult);

}
