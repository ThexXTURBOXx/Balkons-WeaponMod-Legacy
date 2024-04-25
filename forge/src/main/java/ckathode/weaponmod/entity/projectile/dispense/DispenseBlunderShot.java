package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseBlunderShot extends DefaultDispenseItemBehavior {
    private final Random rand = new Random();

    @Nonnull
    @Override
    public ItemStack execute(BlockSource blocksource, ItemStack itemstack) {
        Direction face = blocksource.getBlockState().getValue(DispenserBlock.FACING);
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getLevel(), pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), face.getStepX(), face.getStepY(),
                face.getStepZ());
        itemstack.split(1);
        return itemstack;
    }

    @Override
    protected void playSound(BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void playAnimation(@Nonnull BlockSource blocksource, @Nonnull Direction face) {
        super.playAnimation(blocksource, face);
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
    }
}
