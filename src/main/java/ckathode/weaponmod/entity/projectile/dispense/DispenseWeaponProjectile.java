package ckathode.weaponmod.entity.projectile.dispense;

import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public abstract class DispenseWeaponProjectile extends ProjectileDispenseBehavior {

    @Nonnull
    @Override
    public ItemStack execute(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        World world = blocksource.getLevel();
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        Direction face = blocksource.getBlockState().getValue(DispenserBlock.FACING);
        ProjectileEntity projectile = getProjectile(world, pos, itemstack);
        projectile.shoot(face.getStepX(), face.getStepY() + getYVel(), face.getStepZ(), getPower(),
                getUncertainty());
        world.addFreshEntity(projectile);
        itemstack.split(1);
        return itemstack;
    }

    public double getYVel() {
        return 0.1;
    }

}
