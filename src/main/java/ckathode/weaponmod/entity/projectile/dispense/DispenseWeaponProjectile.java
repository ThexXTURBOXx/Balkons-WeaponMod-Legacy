package ckathode.weaponmod.entity.projectile.dispense;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;

public abstract class DispenseWeaponProjectile extends BehaviorProjectileDispense
{
    protected Random rand;

    public DispenseWeaponProjectile() {
        this.rand = new Random();
    }

    public ItemStack dispenseStack(final IBlockSource blocksource, final ItemStack itemstack) {
        final World world = blocksource.getWorld();
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        final EnumFacing face = blocksource.getBlockState().getValue(BlockDispenser.FACING);
        final IProjectile projectile = this.getProjectileEntity(world, pos, itemstack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + this.getYVel(), face.getZOffset(), this.getVelocity(), this.getDeviation());
        world.spawnEntity((Entity)projectile);
        itemstack.splitStack(1);
        return itemstack;
    }

    protected IProjectile getProjectileEntityWorld(final World world, final IPosition pos, final ItemStack itemstack) {
        return this.getProjectileEntity(world, pos, itemstack);
    }

    public double getYVel() {
        return 0.1;
    }

    public float getVelocity() {
        return this.getProjectileVelocity();
    }

    public float getDeviation() {
        return this.getProjectileInaccuracy();
    }

    protected void playDispenseSound(final IBlockSource blocksource) {
        super.playDispenseSound(blocksource);
    }

    protected void spawnDispenseParticles(final IBlockSource blocksource, final EnumFacing facing) {
        super.spawnDispenseParticles(blocksource, facing);
    }
}
