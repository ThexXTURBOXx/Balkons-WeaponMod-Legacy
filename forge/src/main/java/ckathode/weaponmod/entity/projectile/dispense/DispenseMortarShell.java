package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseMortarShell extends DispenseWeaponProjectile {
    private final Random rand = new Random();

    @Nonnull
    @Override
    protected Projectile getProjectile(@Nonnull Level world, Position pos,
                                       @Nonnull ItemStack stack) {
        return new EntityMortarShell(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    protected void playSound(@Nonnull BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.2f + 0.2f));
    }

    @Override
    protected void playAnimation(@Nonnull BlockSource blocksource, @Nonnull Direction face) {
        super.playAnimation(blocksource, face);
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
    }
}
