package ckathode.weaponmod.entity.projectile.dispense;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class DispenseBlunderShot extends BehaviorDefaultDispenseItem
{
    private Random rand;
    
    public DispenseBlunderShot() {
        this.rand = new Random();
    }
    
    public ItemStack dispenseStack(final IBlockSource blocksource, final ItemStack itemstack) {
        final EnumFacing face = (EnumFacing)blocksource.getBlockState().getValue((IProperty)BlockDispenser.FACING);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EntityBlunderShot.fireFromDispenser(blocksource.getWorld(), pos.getX() + face.getXOffset(), pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), face.getXOffset(), face.getYOffset(), face.getZOffset());
        itemstack.splitStack(1);
        return itemstack;
    }
    
    protected void playDispenseSound(final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.6f));
    }
    
    protected void spawnDispenseParticles(final IBlockSource blocksource, final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getXOffset(), pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0, new int[0]);
    }
}
