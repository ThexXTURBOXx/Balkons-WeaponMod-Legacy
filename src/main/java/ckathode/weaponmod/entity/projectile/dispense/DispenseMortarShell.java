package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenseMortarShell extends DispenseWeaponProjectile {
    private final Random rand = new Random();

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos) {
        return new EntityMortarShell(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "random.explode", 3.0f, 1.0f / (rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = BlockDispenser.func_149939_a(blocksource);
        blocksource.getWorld().spawnParticle("flame", pos.getX() + face.getFrontOffsetX(),
                pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0, 0.0, 0.0);
    }
}
