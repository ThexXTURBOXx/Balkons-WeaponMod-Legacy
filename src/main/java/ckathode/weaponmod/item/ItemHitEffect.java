package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;

public interface ItemHitEffect {

    void onHitEntity(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity,
                     EntityRayTraceResult rayTraceResult);

    void onHitBlock(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity,
                    BlockRayTraceResult rayTraceResult);

}
