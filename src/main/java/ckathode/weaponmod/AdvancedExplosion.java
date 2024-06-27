package ckathode.weaponmod;

import ckathode.weaponmod.entity.projectile.EntityProjectile;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class AdvancedExplosion extends Explosion {
    protected static final Random rand = new Random();
    public final World worldObj;
    public final double explosionX;
    public final double explosionY;
    public final double explosionZ;
    public final Entity exploder;
    public final float explosionSize;
    protected boolean blocksCalculated;
    public final boolean isFlaming;
    public final boolean isSmoking;

    public AdvancedExplosion(World world, Entity entity, double x, double y, double z,
                             float size, boolean flame, boolean smoke) {
        super(world, entity, x, y, z, size);
        worldObj = world;
        exploder = entity;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        explosionSize = size;
        isFlaming = flame;
        isSmoking = smoke;
    }

    public void setAffectedBlockPositions(List<ChunkPosition> list) {
        affectedBlockPositions = list;
        blocksCalculated = true;
    }

    public void doEntityExplosion() {
        doEntityExplosion(DamageSource.setExplosionSource(this));
    }

    public void doEntityExplosion(DamageSource damagesource) {
        float size = explosionSize * 2.0f;
        int k1 = MathHelper.floor_double(explosionX - size - 1.0);
        int l1 = MathHelper.floor_double(explosionX + size + 1.0);
        int i2 = MathHelper.floor_double(explosionY - size - 1.0);
        int i3 = MathHelper.floor_double(explosionY + size + 1.0);
        int j2 = MathHelper.floor_double(explosionZ - size - 1.0);
        int j3 = MathHelper.floor_double(explosionZ + size + 1.0);
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder,
                AxisAlignedBB.getBoundingBox(k1, i2, j2, l1, i3, j3));
        Vec3 vec31 = Vec3.createVectorHelper(explosionX, explosionY, explosionZ);
        for (Entity entity : list) {
            double dr = entity.getDistance(explosionX, explosionY, explosionZ) / size;
            if (dr <= 1.0) {
                double dx = entity.posX - explosionX;
                double dy = entity.posY - explosionY;
                double dz = entity.posZ - explosionZ;
                double d = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
                if (d != 0.0) {
                    dx /= d;
                    dy /= d;
                    dz /= d;
                    double dens = worldObj.getBlockDensity(vec31, EntityProjectile.getBoundingBox(entity));
                    double var36 = (1.0 - dr) * dens;
                    int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
                    entity.attackEntityFrom(damagesource, (float) damage);
                    entity.motionX += dx * var36;
                    entity.motionY += dy * var36;
                    entity.motionZ += dz * var36;
                }
            }
        }
    }

    public void doBlockExplosion() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (ChunkPosition blockpos : (List<ChunkPosition>) affectedBlockPositions) {
            int x = blockpos.chunkPosX;
            int y = blockpos.chunkPosY;
            int z = blockpos.chunkPosZ;
            Block block = worldObj.getBlock(x, y, z);
            if (!worldObj.isAirBlock(x, y, z) && block != null) {
                if (block.canDropFromExplosion(this)) {
                    block.dropBlockAsItemWithChance(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z),
                            1.0f / explosionSize, 0);
                }
                worldObj.setBlockToAir(x, y, z);
                block.onBlockDestroyedByExplosion(worldObj, x, y, z, this);
            }
        }
    }

    public void doFlaming() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (ChunkPosition blockpos : (List<ChunkPosition>) affectedBlockPositions) {
            int x = blockpos.chunkPosX;
            int y = blockpos.chunkPosY;
            int z = blockpos.chunkPosZ;
            if (worldObj.isAirBlock(x, y, z) && !worldObj.isAirBlock(x, y - 1, z)
                && worldObj.getBlock(x, y - 1, z).func_149730_j() && rand.nextInt(3) == 0) {
                worldObj.setBlock(x, y, z, Blocks.fire);
            }
        }
    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles) {
        this.worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4.0f,
                (1.0f + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (bigparticles) {
            worldObj.spawnParticle("hugeexplosion", explosionX, explosionY,
                    explosionZ, 0.0, 0.0, 0.0);
        }
        if (!smallparticles) {
            return;
        }
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (ChunkPosition blockpos : (List<ChunkPosition>) affectedBlockPositions) {
            double px = blockpos.chunkPosX + worldObj.rand.nextFloat();
            double py = blockpos.chunkPosY + worldObj.rand.nextFloat();
            double pz = blockpos.chunkPosZ + worldObj.rand.nextFloat();
            double dx = px - explosionX;
            double dy = py - explosionY;
            double dz = pz - explosionZ;
            double distance = MathHelper.sqrt_double(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5 / (distance / explosionSize + 0.1);
            d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3f;
            dx *= d7;
            dy *= d7;
            dz *= d7;
            worldObj.spawnParticle("explode", (px + explosionX) / 2.0,
                    (py + explosionY) / 2.0, (pz + explosionZ) / 2.0, dx, dy, dz);
            worldObj.spawnParticle("smoke", px, py, pz, dx, dy, dz);
        }
    }

    protected void calculateBlockExplosion() {
        byte maxsize = 16;
        Set<ChunkPosition> set = Sets.newHashSet();
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
                        float strength = explosionSize * (0.7f + worldObj.rand.nextFloat() * 0.6f);
                        double dx = explosionX;
                        double dy = explosionY;
                        double dz = explosionZ;
                        float f = 0.3f;
                        while (strength > 0.0f) {
                            int x = MathHelper.floor_double(dx);
                            int y = MathHelper.floor_double(dy);
                            int z = MathHelper.floor_double(dz);
                            Block block = worldObj.getBlock(x, y, z);
                            if (!worldObj.isAirBlock(x, y, z) && block != null) {
                                strength -= (block.getExplosionResistance(exploder, worldObj, x, y, z,
                                        explosionX, explosionY, explosionZ) + 0.3f) * f;
                            }
                            if (strength > 0.0f) {
                                set.add(new ChunkPosition(x, y, z));
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
        affectedBlockPositions.addAll(set);
        blocksCalculated = true;
    }

}
