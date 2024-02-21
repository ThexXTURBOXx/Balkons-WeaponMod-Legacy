package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile
{
    protected IProjectile getProjectileEntity(final World world, final IPosition pos, final ItemStack stack) {
        return new EntityCrossbowBolt(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    public float getVelocity() {
        return 3.0f;
    }
    
    @Override
    public float getDeviation() {
        return 2.0f;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
