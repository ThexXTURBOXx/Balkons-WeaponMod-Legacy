package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

public class DispenseBlunderShot extends BehaviorDefaultDispenseItem {
    private final Random rand = new Random();

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, ItemStack itemstack) {
        EnumFacing face = BlockDispenser.getFacing(blocksource.getBlockMetadata());
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getWorld(), pos.getX() + face.getFrontOffsetX(),
                pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), face.getFrontOffsetX(),
                face.getFrontOffsetY(), face.getFrontOffsetZ());
        itemstack.splitStack(1);
        return itemstack;
    }

    @Override
    protected void playDispenseSound(IBlockSource blocksource) {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.6F));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getFrontOffsetX(),
                pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0, 0.0, 0.0);
    }
}
