package ckathode.weaponmod;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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

    public AdvancedExplosion(World world, Entity entity, double x, double y, double z,
                             float size, boolean flame, boolean smoke) {
        super(world, entity, x, y, z, size, flame, smoke);
        worldObj = world;
        exploder = entity;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        explosionSize = size;
    }

    public void setAffectedBlockPositions(List<BlockPos> list) {
        getAffectedBlockPositions().addAll(list);
        blocksCalculated = true;
    }

    public void doEntityExplosion() {
        doEntityExplosion(DamageSource.causeExplosionDamage(this));
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
                new AxisAlignedBB(k1, i2, j2, l1, i3, j3));
        Vec3d vec31 = new Vec3d(explosionX, explosionY, explosionZ);
        for (Entity entity : list) {
            if (!entity.isImmuneToExplosions()) {
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
                        double dens = worldObj.getBlockDensity(vec31, entity.getEntityBoundingBox());
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
    }

    public void doBlockExplosion() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : getAffectedBlockPositions()) {
            if (!worldObj.isAirBlock(blockpos)) {
                IBlockState iblockstate = worldObj.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                if (block.canDropFromExplosion(this)) {
                    block.dropBlockAsItemWithChance(worldObj, blockpos, iblockstate, 1.0f / explosionSize, 0);
                }
                worldObj.setBlockToAir(blockpos);
                block.onBlockExploded(worldObj, blockpos, this);
            }
        }
    }

    public void doFlaming() {
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : getAffectedBlockPositions()) {
            if (worldObj.isAirBlock(blockpos) && worldObj.getBlockState(blockpos.down()).isFullBlock() && rand.nextInt(3) == 0) {
                worldObj.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
            }
        }
    }

    public void doParticleExplosion(boolean smallparticles, boolean bigparticles) {
        worldObj.playSound(null, explosionX, explosionY, explosionZ,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f,
                (1.0f + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (bigparticles) {
            worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, explosionX, explosionY,
                    explosionZ, 0.0, 0.0, 0.0);
        }
        if (!smallparticles) {
            return;
        }
        if (!blocksCalculated) {
            calculateBlockExplosion();
        }
        for (BlockPos blockpos : getAffectedBlockPositions()) {
            double px = blockpos.getX() + worldObj.rand.nextFloat();
            double py = blockpos.getY() + worldObj.rand.nextFloat();
            double pz = blockpos.getZ() + worldObj.rand.nextFloat();
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
            worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (px + explosionX) / 2.0,
                    (py + explosionY) / 2.0, (pz + explosionZ) / 2.0, dx, dy, dz);
            worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, dx, dy, dz);
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
                        float strength = explosionSize * (0.7f + worldObj.rand.nextFloat() * 0.6f);
                        double dx = explosionX;
                        double dy = explosionY;
                        double dz = explosionZ;
                        float f = 0.3f;
                        while (strength > 0.0f) {
                            BlockPos blockpos = new BlockPos(dx, dy, dz);
                            if (!worldObj.isAirBlock(blockpos)) {
                                IBlockState iblockstate = worldObj.getBlockState(blockpos);
                                strength -= (iblockstate.getBlock().getExplosionResistance(worldObj, blockpos,
                                        exploder, this) + 0.3f) * f;
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
        getAffectedBlockPositions().addAll(set);
        blocksCalculated = true;
    }

}
