package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class WeaponDamageSource extends IndirectEntityDamageSource {
    private final EntityProjectile<?> projectileEntity;
    private final Entity shooterEntity;

    public WeaponDamageSource(String s, EntityProjectile<?> projectile, Entity entity) {
        super(s, projectile, entity);
        projectileEntity = projectile;
        shooterEntity = entity;
    }

    public Entity getProjectile() {
        return projectileEntity;
    }

    @Override
    public Entity getEntity() {
        return shooterEntity;
    }

    public static DamageSource causeProjectileWeaponDamage(EntityProjectile<?> projectile, Entity entity) {
        return new WeaponDamageSource("weapon", projectile, entity).setProjectile();
    }

}
