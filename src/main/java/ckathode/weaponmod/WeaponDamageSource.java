package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;

public class WeaponDamageSource extends IndirectEntityDamageSource {

    public WeaponDamageSource(String s, EntityProjectile<?> projectile, Entity entity) {
        super(s, projectile, entity);
    }

    public EntityProjectile<?> getProjectile() {
        return damageSourceEntity instanceof EntityProjectile ? (EntityProjectile<?>) damageSourceEntity : null;
    }

    public static DamageSource causeProjectileWeaponDamage(EntityProjectile<?> projectile, Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }

}
