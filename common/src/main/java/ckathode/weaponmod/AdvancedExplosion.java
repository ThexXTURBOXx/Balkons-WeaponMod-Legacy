package ckathode.weaponmod;

import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AdvancedExplosion extends ServerExplosion {

    protected static final Random rand = new Random();
    public final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList<>();
    public final ServerLevel serverLevel;
    public final DamageSource damageSource;
    public final Vec3 center;
    public final Entity exploder;
    public final float explosionSize;
    protected boolean blocksCalculated;

    public AdvancedExplosion(ServerLevel world, Entity entity, Vec3 position,
                             float size, boolean flame, BlockInteraction mode) {
        this(world, entity, null, null, position, size, flame, mode);
    }

    public AdvancedExplosion(ServerLevel world, Entity entity, @Nullable DamageSource source,
                             @Nullable ExplosionDamageCalculator calculator, Vec3 position,
                             float size, boolean flame, BlockInteraction mode) {
        super(world, entity, source, calculator, position, size, flame, mode);
        serverLevel = world;
        damageSource = world.damageSources().explosion(this);
        exploder = entity;
        center = position;
        explosionSize = size;
    }

    public void setAffectedBlockPositions(List<BlockPos> list) {
        toBlow.addAll(list);
        blocksCalculated = true;
    }

    public void doEntityExplosion() {
        doEntityExplosion(damageSource);
    }

    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = Mth.floor(center.x - size - 1.0);
        int l1 = Mth.floor(center.x + size + 1.0);
        int i2 = Mth.floor(center.y - size - 1.0);
        int i3 = Mth.floor(center.y + size + 1.0);
        int j2 = Mth.floor(center.z - size - 1.0);
        int j3 = Mth.floor(center.z + size + 1.0);
        List<Entity> list = serverLevel.getEntities(exploder, new AABB(k1, i2, j2, l1, i3, j3));
        for (Entity entity : list) {
            if (!entity.ignoreExplosion(this)) {
                double dr = Math.sqrt(entity.distanceToSqr(center)) / size;
                if (dr <= 1.0) {
                    Vec3 d = entity.position().subtract(center);
                    if (d.lengthSqr() != 0.0) {
                        d = d.normalize();
                        double dens = getSeenPercent(center, entity);
                        double var36 = (1.0 - dr) * dens;
                        int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                        entity.hurtServer(serverLevel, damagesource, (float) damage);
                        entity.setDeltaMovement(entity.getDeltaMovement().add(d.x * var36, d.y * var36, d.z * var36));
                    }
                }
            }
        }
    }

    public void doBlockExplosion() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }

        ObjectArrayList<BlockPos> positions = new ObjectArrayList<>(toBlow);
        List<StackCollector> list = new ArrayList<>();
        Util.shuffle(positions, WMUtil.RANDOM);
        for (BlockPos blockPos2 : positions) {
            serverLevel.getBlockState(blockPos2).onExplosionHit(serverLevel, blockPos2, this,
                    (itemStack, blockPos) -> ServerExplosion.addOrAppendStack(list, itemStack, blockPos));
        }
        for (StackCollector collector : list) {
            Block.popResource(serverLevel, collector.pos, collector.stack);
        }
    }

    public void doFlaming() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : toBlow) {
            if (rand.nextInt(3) != 0 || !serverLevel.getBlockState(blockpos).isAir() ||
                !serverLevel.getBlockState(blockpos.below()).isSolidRender()) continue;
            serverLevel.setBlockAndUpdate(blockpos, BaseFireBlock.getState(serverLevel, blockpos));
        }
    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles) {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        doParticleExplosion(serverLevel, center, toBlow, explosionSize, smallparticles, bigparticles);
    }

    public static void doParticleExplosion(Level level, Vec3 center, Collection<BlockPos> toBlow, float explosionSize,
                                           boolean smallparticles, boolean bigparticles) {
        level.playSound(null, center.x, center.y, center.z,
                SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 4.0f,
                (1.0f + (WMUtil.RANDOM.nextFloat() - WMUtil.RANDOM.nextFloat()) * 0.2f) * 0.7f);
        if (bigparticles && level.isClientSide()) {
            level.addParticle(ParticleTypes.EXPLOSION, center.x, center.y, center.z, 0.0, 0.0, 0.0);
        }
        if (!smallparticles) {
            return;
        }
        for (BlockPos blockpos : toBlow) {
            double px = blockpos.getX() + WMUtil.RANDOM.nextFloat();
            double py = blockpos.getY() + WMUtil.RANDOM.nextFloat();
            double pz = blockpos.getZ() + WMUtil.RANDOM.nextFloat();
            double dx = px - center.x;
            double dy = py - center.y;
            double dz = pz - center.z;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5 / (distance / explosionSize + 0.1);
            d7 *= WMUtil.RANDOM.nextFloat() * WMUtil.RANDOM.nextFloat() + 0.3f;
            dx *= d7;
            dy *= d7;
            dz *= d7;
            if (level.isClientSide()) {
                level.addParticle(ParticleTypes.POOF, (px + center.x) / 2.0,
                        (py + center.y) / 2.0, (pz + center.z) / 2.0, dx, dy, dz);
                level.addParticle(ParticleTypes.SMOKE, px, py, pz, dx, dy, dz);
            }
        }
    }

    protected void calculateBlockExplosion() {
        byte maxsize = 16;
        Set<BlockPos> set = Sets.newHashSet();
        for (int j = 0; j < maxsize; ++j) {
            for (int k = 0; k < maxsize; ++k) {
                for (int l = 0; l < maxsize; ++l) {
                    if (j == 0 || j == maxsize - 1 || k == 0 || k == maxsize - 1 || l == 0 || l == maxsize - 1) {
                        double rx = j / 15.0f * 2.0f - 1.0f;
                        double ry = k / 15.0f * 2.0f - 1.0f;
                        double rz = l / 15.0f * 2.0f - 1.0f;
                        double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
                        rx /= rd;
                        ry /= rd;
                        rz /= rd;
                        float strength = explosionSize * (0.7f + WMUtil.RANDOM.nextFloat() * 0.6f);
                        double dx = center.x;
                        double dy = center.y;
                        double dz = center.z;
                        float f = 0.3f;
                        while (strength > 0.0f) {
                            BlockPos blockpos = BlockPos.containing(dx, dy, dz);
                            BlockState iblockstate = serverLevel.getBlockState(blockpos);
                            if (!iblockstate.isAir()) {
                                strength -= (iblockstate.getBlock().getExplosionResistance() + 0.3f) * f;
                            }
                            if (strength > 0.0f) {
                                set.add(blockpos);
                            }
                            dx += rx * 0.3;
                            dy += ry * 0.3;
                            dz += rz * 0.3;
                            strength -= 0.22500001f;
                        }
                    }
                }
            }
        }
        toBlow.addAll(set);
        blocksCalculated = true;
    }

}
