package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;

public interface ItemHitEffect {

    void onEntityHit(EntityMaterialProjectile entity, Entity hitEntity);

    void onGroundHit(EntityMaterialProjectile entity, MovingObjectPosition mop);

}
