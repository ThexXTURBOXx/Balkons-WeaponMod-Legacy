package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class WeaponDamageSource extends EntityDamageSourceIndirect {
    private final EntityProjectile projectileEntity;
    private final Entity shooterEntity;

    public WeaponDamageSource(final String s, final EntityProjectile projectile, final Entity entity) {
        super(s, projectile, entity);
        this.projectileEntity = projectile;
        this.shooterEntity = entity;
    }

    public Entity getProjectile() {
        return this.projectileEntity;
    }

    @Override
    public Entity getTrueSource() {
        return this.shooterEntity;
    }

    public static DamageSource causeProjectileWeaponDamage(final EntityProjectile projectile, final Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }
}
