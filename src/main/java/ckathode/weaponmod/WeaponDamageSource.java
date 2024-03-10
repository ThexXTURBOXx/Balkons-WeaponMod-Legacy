package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class WeaponDamageSource extends EntityDamageSourceIndirect {
    private final EntityProjectile projectileEntity;
    private final Entity shooterEntity;

    public WeaponDamageSource(String s, EntityProjectile projectile, Entity entity) {
        super(s, projectile, entity);
        projectileEntity = projectile;
        shooterEntity = entity;
    }

    public Entity getProjectile() {
        return projectileEntity;
    }

    @Override
    public Entity getTrueSource() {
        return shooterEntity;
    }

    public static DamageSource causeProjectileWeaponDamage(EntityProjectile projectile, Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }
}
