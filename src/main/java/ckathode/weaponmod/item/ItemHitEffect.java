package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.util.MovingObjectPosition;

public interface ItemHitEffect {

    void onHitEntity(EntityMaterialProjectile entity, MovingObjectPosition mop);

    void onHitBlock(EntityMaterialProjectile entity, MovingObjectPosition mop);

}
