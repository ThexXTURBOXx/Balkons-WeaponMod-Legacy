package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class DispenseBlunderShot extends DefaultDispenseItemBehavior {

    private final Random rand = new Random();

    @NotNull
    @Override
    public ItemStack execute(BlockSource blocksource, ItemStack itemstack) {
        Direction face = blocksource.state().getValue(DispenserBlock.FACING);
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.level(), pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), face.getStepX(), face.getStepY(),
                face.getStepZ());
        itemstack.shrink(1);
        return itemstack;
    }

    @Override
    protected void playSound(BlockSource blocksource) {
        blocksource.level().playSound(null, blocksource.pos(), SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void playAnimation(@NotNull BlockSource blocksource, @NotNull Direction face) {
        super.playAnimation(blocksource, face);
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        if (blocksource.level().isClientSide()) {
            blocksource.level().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                    pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
        }
    }

}
