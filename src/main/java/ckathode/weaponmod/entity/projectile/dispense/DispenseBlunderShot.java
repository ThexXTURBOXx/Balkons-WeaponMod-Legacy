package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;

public class DispenseBlunderShot extends BehaviorDefaultDispenseItem {
    private final Random rand = new Random();

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack) {
        EnumFacing face = blocksource.getBlockState().getValue(BlockDispenser.FACING);
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getWorld(), pos.getX() + face.getXOffset(),
                pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), face.getXOffset(), face.getYOffset(),
                face.getZOffset());
        itemstack.splitStack(1);
        return itemstack;
    }

    @Override
    protected void playDispenseSound(IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getXOffset(),
                pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0);
    }
}
