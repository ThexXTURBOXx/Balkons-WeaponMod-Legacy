package ckathode.weaponmod.entity.projectile.dispense;

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

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blockSource, @Nonnull ItemStack stack) {
        World world = blockSource.getWorld();
        IPosition pos = BlockDispenser.getDispensePosition(blockSource);
        EnumFacing face = blockSource.getBlockState().getValue(BlockDispenser.FACING);
        IProjectile projectile = getProjectileEntity(world, pos, stack);
        projectile.setThrowableHeading(face.getFrontOffsetX(), face.getFrontOffsetY() + getYVel(),
                face.getFrontOffsetZ(), getProjectileVelocity(), getProjectileInaccuracy());
        world.spawnEntity((Entity) projectile);
        stack.shrink(1);
        return stack;
    }

    public double getYVel() {
        return 0.1;
    }

}
