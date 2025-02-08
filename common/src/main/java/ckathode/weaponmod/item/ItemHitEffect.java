package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityMaterialProjectile;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public interface ItemHitEffect {

    void onEntityHit(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity, EntityHitResult result);

    void onGroundHit(EntityMaterialProjectile<? extends EntityMaterialProjectile<?>> entity, BlockHitResult result);

}
