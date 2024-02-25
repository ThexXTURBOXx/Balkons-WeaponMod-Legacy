package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DispenseMusketBullet extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull final World world, final IPosition pos,
                                              @Nonnull final ItemStack stack) {
        return new EntityMusketBullet(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public double getYVel() {
        return 0.0;
    }

    @Override
    public float getDeviation() {
        return 3.0f;
    }

    @Override
    public float getVelocity() {
        return 5.0f;
    }

    @Override
    protected void playDispenseSound(@Nonnull final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.7f));
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.4f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull final IBlockSource blocksource, @Nonnull final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().addParticle(Particles.FLAME, pos.getX() + face.getXOffset(),
                pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.2, 0.0);
    }
}
