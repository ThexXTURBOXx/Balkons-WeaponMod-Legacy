package ckathode.weaponmod;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AdvancedExplosion extends Explosion {

    protected static final Random rand = new Random();
    public final Level worldObj;
    public final DamageSource damageSource;
    public final double explosionX;
    public final double explosionY;
    public final double explosionZ;
    public final Entity exploder;
    public final float explosionSize;
    protected boolean blocksCalculated;

    public AdvancedExplosion(Level world, Entity entity, double x, double y, double z,
                             float size, boolean flame, BlockInteraction mode) {
        super(world, entity, null, null, x, y, z, size, flame, mode);
        worldObj = world;
        damageSource = world.damageSources().explosion(this);
        exploder = entity;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        explosionSize = size;
    }

    public void setAffectedBlockPositions(List<BlockPos> list) {
        getToBlow().addAll(list);
        blocksCalculated = true;
    }

    public void doEntityExplosion() {
        doEntityExplosion(damageSource);
    }

    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = Mth.floor(explosionX - size - 1.0);
        int l1 = Mth.floor(explosionX + size + 1.0);
        int i2 = Mth.floor(explosionY - size - 1.0);
        int i3 = Mth.floor(explosionY + size + 1.0);
        int j2 = Mth.floor(explosionZ - size - 1.0);
        int j3 = Mth.floor(explosionZ + size + 1.0);
        List<Entity> list = worldObj.getEntities(exploder, new AABB(k1, i2, j2, l1, i3, j3));
        Vec3 vec31 = new Vec3(explosionX, explosionY, explosionZ);
        for (Entity entity : list) {
            if (!entity.ignoreExplosion()) {
                double dr = Math.sqrt(entity.distanceToSqr(explosionX, explosionY, explosionZ)) / size;
                if (dr <= 1.0) {
                    double dx = entity.getX() - explosionX;
                    double dy = entity.getY() - explosionY;
                    double dz = entity.getZ() - explosionZ;
                    double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
                    if (d != 0.0) {
                        dx /= d;
                        dy /= d;
                        dz /= d;
                        double dens = getSeenPercent(vec31, entity);
                        double var36 = (1.0 - dr) * dens;
                        int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                        entity.hurt(damagesource, (float) damage);
                        entity.setDeltaMovement(entity.getDeltaMovement().add(dx * var36, dy * var36, dz * var36));
                    }
                }
            }
        }
    }

    public void doBlockExplosion() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }

        ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
        ObjectArrayList<BlockPos> positions = new ObjectArrayList<>(getToBlow());
        Util.shuffle(positions, worldObj.random);
        for (BlockPos blockpos : positions) {
            BlockState blockstate = worldObj.getBlockState(blockpos);
            Block block = blockstate.getBlock();
            if (blockstate.isAir()) continue;
            BlockPos blockpos1 = blockpos.immutable();
            worldObj.getProfiler().push("explosion_blocks");
            if (block.dropFromExplosion(this) && worldObj instanceof ServerLevel) {
                BlockEntity tileentity = blockstate.hasBlockEntity() ? worldObj.getBlockEntity(blockpos) : null;
                LootParams.Builder builder =
                        new LootParams.Builder((ServerLevel) worldObj)
                                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos))
                                .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, tileentity)
                                .withOptionalParameter(LootContextParams.THIS_ENTITY, this.source)
                                .withParameter(LootContextParams.EXPLOSION_RADIUS, explosionSize);

                blockstate.getDrops(builder).forEach((s) -> {
                    addBlockDrops(objectarraylist, s, blockpos1);
                });
            }

            worldObj.setBlockAndUpdate(blockpos, Blocks.AIR.defaultBlockState());
            block.wasExploded(worldObj, blockpos, this);
        }

        for (Pair<ItemStack, BlockPos> pair : objectarraylist) {
            Block.popResource(worldObj, pair.getSecond(), pair.getFirst());
        }
    }

    public void doFlaming() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : getToBlow()) {
            if (rand.nextInt(3) != 0 || !worldObj.getBlockState(blockpos).isAir() ||
                !worldObj.getBlockState(blockpos.below()).isSolidRender(worldObj, blockpos.below())) continue;
            worldObj.setBlockAndUpdate(blockpos, BaseFireBlock.getState(worldObj, blockpos));
        }
    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles) {
        worldObj.playSound(null, explosionX, explosionY, explosionZ,
                SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0f,
                (1.0f + (worldObj.random.nextFloat() - worldObj.random.nextFloat()) * 0.2f) * 0.7f);
        if (bigparticles) {
            worldObj.addParticle(ParticleTypes.EXPLOSION, explosionX, explosionY,
                    explosionZ, 0.0, 0.0, 0.0);
        }
        if (!smallparticles) {
            return;
        }
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : getToBlow()) {
            double px = blockpos.getX() + worldObj.random.nextFloat();
            double py = blockpos.getY() + worldObj.random.nextFloat();
            double pz = blockpos.getZ() + worldObj.random.nextFloat();
            double dx = px - explosionX;
            double dy = py - explosionY;
            double dz = pz - explosionZ;
            double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5 / (distance / explosionSize + 0.1);
            d7 *= worldObj.random.nextFloat() * worldObj.random.nextFloat() + 0.3f;
            dx *= d7;
            dy *= d7;
            dz *= d7;
            worldObj.addParticle(ParticleTypes.POOF, (px + explosionX) / 2.0,
                    (py + explosionY) / 2.0, (pz + explosionZ) / 2.0, dx, dy, dz);
            worldObj.addParticle(ParticleTypes.SMOKE, px, py, pz, dx, dy, dz);
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
                        float strength = explosionSize * (0.7f + worldObj.random.nextFloat() * 0.6f);
                        double dx = explosionX;
                        double dy = explosionY;
                        double dz = explosionZ;
                        float f = 0.3f;
                        while (strength > 0.0f) {
                            BlockPos blockpos = BlockPos.containing(dx, dy, dz);
                            BlockState iblockstate = worldObj.getBlockState(blockpos);
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
        getToBlow().addAll(set);
        blocksCalculated = true;
    }

}
