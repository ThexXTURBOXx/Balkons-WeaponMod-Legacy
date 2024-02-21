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

public class DispenseMusketBullet extends DispenseWeaponProjectile
{
    protected IProjectile getProjectileEntity(final World world, final IPosition pos, final ItemStack stack) {
        return new EntityMusketBullet(world, pos.getX(), pos.getY(), pos.getZ());
    }
    
    @Override
    public double getYVel() {
        return 0.0;
    }
    
    @Override
    public float getDeviation() {
        return 3.0f;
    }
    
    @Override
    public float getVelocity() {
        return 5.0f;
    }
    
    @Override
    protected void playDispenseSound(final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.7f));
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.NEUTRAL, 3.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.4f));
    }
    
    @Override
    protected void spawnDispenseParticles(final IBlockSource blocksource, final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getXOffset(), pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.2, 0.0, new int[0]);
    }
}
