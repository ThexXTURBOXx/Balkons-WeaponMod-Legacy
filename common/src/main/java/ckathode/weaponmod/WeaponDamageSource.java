package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

public class WeaponDamageSource extends IndirectEntityDamageSource {

    public WeaponDamageSource(String s, EntityProjectile<?> projectile, Entity entity) {
        super(s, projectile, entity);
    }

    public EntityProjectile<?> getProjectile() {
        return entity instanceof EntityProjectile<?> ep ? ep : null;
    }

    public static DamageSource causeProjectileWeaponDamage(EntityProjectile<?> projectile, Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }

}
