package ckathode.weaponmod;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
    public World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    protected boolean blocksCalculated;
    protected static final Random rand;

    public AdvancedExplosion(final World world, final Entity entity, final double d, final double d1, final double d2
            , final float f, final boolean flame, final boolean smoke) {
        super(world, entity, d, d1, d2, f, flame, smoke);
        this.worldObj = world;
        this.exploder = entity;
        this.explosionX = d;
        this.explosionY = d1;
        this.explosionZ = d2;
        this.explosionSize = f;
    }

    public void setAffectedBlockPositions(final List<BlockPos> list) {
        this.getAffectedBlockPositions().addAll(list);
        this.blocksCalculated = true;
    }

    public void doEntityExplosion() {
        this.doEntityExplosion(DamageSource.causeExplosionDamage(this));
    }

    public void doEntityExplosion(final DamageSource damagesource) {
        final float size = this.explosionSize * 2.0f;
        final int k1 = MathHelper.floor(this.explosionX - size - 1.0);
        final int l1 = MathHelper.floor(this.explosionX + size + 1.0);
        final int i2 = MathHelper.floor(this.explosionY - size - 1.0);
        final int i3 = MathHelper.floor(this.explosionY + size + 1.0);
        final int j2 = MathHelper.floor(this.explosionZ - size - 1.0);
        final int j3 = MathHelper.floor(this.explosionZ + size + 1.0);
        final List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder,
                new AxisAlignedBB(k1, i2, j2, l1, i3, j3));
        final Vec3d vec31 = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
        for (final Entity entity : list) {
            if (!entity.isImmuneToExplosions()) {
                final double dr = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / size;
                if (dr <= 1.0) {
                    double dx = entity.posX - this.explosionX;
                    double dy = entity.posY - this.explosionY;
                    double dz = entity.posZ - this.explosionZ;
                    final double d = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
                    if (d != 0.0) {
                        dx /= d;
                        dy /= d;
                        dz /= d;
                        final double dens = this.worldObj.getBlockDensity(vec31, entity.getEntityBoundingBox());
                        final double var36 = (1.0 - dr) * dens;
                        final int damage = (int) ((var36 * var36 + var36) / 2.0 * 8.0 * size + 1.0);
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
        if (!this.blocksCalculated) {
            this.calculateBlockExplosion();
        }
        for (final BlockPos blockpos : this.getAffectedBlockPositions()) {
            final IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
            final Block block = iblockstate.getBlock();
            if (iblockstate.getMaterial() != Material.AIR) {
                if (block.canDropFromExplosion(this)) {
                    block.dropBlockAsItemWithChance(this.worldObj, blockpos, this.worldObj.getBlockState(blockpos),
                            1.0f / this.explosionSize, 0);
                }
                this.worldObj.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 3);
                block.onBlockExploded(this.worldObj, blockpos, this);
            }
        }
    }

    public void doFlaming() {
        if (!this.blocksCalculated) {
            this.calculateBlockExplosion();
        }
        for (final BlockPos blockpos : this.getAffectedBlockPositions()) {
            if (this.worldObj.getBlockState(blockpos).getMaterial() == Material.AIR && this.worldObj.getBlockState(blockpos.down()).isFullBlock() && AdvancedExplosion.rand.nextInt(3) == 0) {
                this.worldObj.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
            }
        }
    }

    public void doParticleExplosion(final boolean smallparticles, final boolean bigparticles) {
        this.worldObj.playSound(null, this.explosionX, this.explosionY, this.explosionZ,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f,
                (1.0f + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2f) * 0.7f);
        if (bigparticles) {
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.explosionX, this.explosionY,
                    this.explosionZ, 0.0, 0.0, 0.0);
        }
        if (!smallparticles) {
            return;
        }
        if (!this.blocksCalculated) {
            this.calculateBlockExplosion();
        }
        for (final BlockPos blockpos : this.getAffectedBlockPositions()) {
            final IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
            final Block block = iblockstate.getBlock();
            final double px = blockpos.getX() + this.worldObj.rand.nextFloat();
            final double py = blockpos.getY() + this.worldObj.rand.nextFloat();
            final double pz = blockpos.getZ() + this.worldObj.rand.nextFloat();
            double dx = px - this.explosionX;
            double dy = py - this.explosionY;
            double dz = pz - this.explosionZ;
            final double distance = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
            dx /= distance;
            dy /= distance;
            dz /= distance;
            double d7 = 0.5 / (distance / this.explosionSize + 0.1);
            d7 *= this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3f;
            dx *= d7;
            dy *= d7;
            dz *= d7;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (px + this.explosionX) / 2.0,
                    (py + this.explosionY) / 2.0, (pz + this.explosionZ) / 2.0, dx, dy, dz);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, dx, dy, dz);
        }
    }

    protected void calculateBlockExplosion() {
        final byte maxsize = 16;
        final Set<BlockPos> set = Sets.newHashSet();
        for (int j = 0; j < maxsize; ++j) {
            for (int k = 0; k < maxsize; ++k) {
                for (int l = 0; l < maxsize; ++l) {
                    if (j == 0 || j == maxsize - 1 || k == 0 || k == maxsize - 1 || l == 0 || l == maxsize - 1) {
                        double rx = j / 15.0f * 2.0f - 1.0f;
                        double ry = k / 15.0f * 2.0f - 1.0f;
                        double rz = l / 15.0f * 2.0f - 1.0f;
                        final double rd = Math.sqrt(rx * rx + ry * ry + rz * rz);
                        rx /= rd;
                        ry /= rd;
                        rz /= rd;
                        float strength = this.explosionSize * (0.7f + this.worldObj.rand.nextFloat() * 0.6f);
                        double dx = this.explosionX;
                        double dy = this.explosionY;
                        double dz = this.explosionZ;
                        final float f = 0.3f;
                        while (strength > 0.0f) {
                            final BlockPos blockpos = new BlockPos(dx, dy, dz);
                            final IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
                            if (iblockstate.getMaterial() != Material.AIR) {
                                strength -= (iblockstate.getBlock().getExplosionResistance(this.worldObj, blockpos,
                                        null, this) + 0.3f) * f;
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
        this.getAffectedBlockPositions().addAll(set);
        this.blocksCalculated = true;
    }

    static {
        rand = new Random();
    }
}
