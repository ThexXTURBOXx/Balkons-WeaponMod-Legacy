package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseMortarShell extends DispenseWeaponProjectile {
    private final Random rand = new Random();

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack stack) {
        return new EntityMortarShell(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull Direction face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        blocksource.getWorld().addParticle(ParticleTypes.FLAME, pos.getX() + face.getXOffset(),
                pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0);
    }
}
