package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class WeaponDamageSource extends EntityDamageSourceIndirect
{
    private EntityProjectile projectileEntity;
    private Entity shooterEntity;
    
    public WeaponDamageSource(final String s, final EntityProjectile projectile, final Entity entity) {
        super(s, projectile, entity);
        this.projectileEntity = projectile;
        this.shooterEntity = entity;
    }
    
    public Entity getProjectile() {
        return this.projectileEntity;
    }
    
    public Entity getTrueSource() {
        return this.shooterEntity;
    }
    
    public static DamageSource causeProjectileWeaponDamage(final EntityProjectile projectile, final Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }
}
