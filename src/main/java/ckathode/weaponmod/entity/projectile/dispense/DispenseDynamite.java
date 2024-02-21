package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class DispenseDynamite extends DispenseWeaponProjectile
{
    protected IProjectile getProjectileEntity(final World world, final IPosition pos, final ItemStack stack) {
        return new EntityDynamite(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
