package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMortarShell;
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

public class DispenseMortarShell extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull final World world, final IPosition pos,
                                              @Nonnull final ItemStack stack) {
        return new EntityMortarShell(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void playDispenseSound(@Nonnull final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull final IBlockSource blocksource, @Nonnull final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().addParticle(Particles.FLAME, pos.getX() + face.getXOffset(),
                pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0);
    }
}
