package ckathode.weaponmod.entity.projectile.dispense;

import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class DispenseWeaponProjectile extends BehaviorProjectileDispense {
    protected final Random rand;

    public DispenseWeaponProjectile() {
        this.rand = new Random();
    }

    @Nonnull
    @Override
    public ItemStack dispenseStack(final IBlockSource blocksource, @Nonnull final ItemStack itemstack) {
        final World world = blocksource.getWorld();
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        final EnumFacing face = blocksource.getBlockState().get(BlockDispenser.FACING);
        final IProjectile projectile = this.getProjectileEntity(world, pos, itemstack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + this.getYVel(), face.getZOffset(), this.getVelocity()
                , this.getDeviation());
        world.spawnEntity((Entity) projectile);
        itemstack.split(1);
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

    @Override
    protected void playDispenseSound(@Nonnull final IBlockSource blocksource) {
        super.playDispenseSound(blocksource);
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull final IBlockSource blocksource, @Nonnull final EnumFacing facing) {
        super.spawnDispenseParticles(blocksource, facing);
    }
}
