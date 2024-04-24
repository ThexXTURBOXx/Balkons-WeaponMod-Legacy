package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class DispenseBlunderShot extends DefaultDispenseItemBehavior {
    private final Random rand = new Random();

    @Nonnull
    @Override
    public ItemStack execute(IBlockSource blocksource, ItemStack itemstack) {
        Direction face = blocksource.getBlockState().getValue(DispenserBlock.FACING);
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getLevel(), pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), face.getStepX(), face.getStepY(),
                face.getStepZ());
        itemstack.split(1);
        return itemstack;
    }

    @Override
    protected void playSound(IBlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void playAnimation(@Nonnull IBlockSource blocksource, @Nonnull Direction face) {
        super.playAnimation(blocksource, face);
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
    }
}
