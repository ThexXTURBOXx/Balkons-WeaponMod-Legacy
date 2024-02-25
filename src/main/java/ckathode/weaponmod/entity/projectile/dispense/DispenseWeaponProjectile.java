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
        rand = new Random();
    }

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        World world = blocksource.getWorld();
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EnumFacing face = blocksource.getBlockState().get(BlockDispenser.FACING);
        IProjectile projectile = getProjectileEntity(world, pos, itemstack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + getYVel(), face.getZOffset(), getVelocity()
                , getDeviation());
        world.spawnEntity((Entity) projectile);
        itemstack.split(1);
        return itemstack;
    }

    protected IProjectile getProjectileEntityWorld(World world, IPosition pos, ItemStack itemstack) {
        return getProjectileEntity(world, pos, itemstack);
    }

    public double getYVel() {
        return 0.1;
    }

    public float getVelocity() {
        return getProjectileVelocity();
    }

    public float getDeviation() {
        return getProjectileInaccuracy();
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        super.playDispenseSound(blocksource);
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing facing) {
        super.spawnDispenseParticles(blocksource, facing);
    }
}
