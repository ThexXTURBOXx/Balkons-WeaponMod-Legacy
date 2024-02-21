package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;

public class DispenseMortarShell extends DispenseWeaponProjectile
{
    protected IProjectile getProjectileEntity(final World world, final IPosition pos, final ItemStack stack) {
        return new EntityMortarShell(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.2f + 0.2f));
    }
    
    @Override
    protected void spawnDispenseParticles(final IBlockSource blocksource, final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getXOffset(), pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0, new int[0]);
    }
}
