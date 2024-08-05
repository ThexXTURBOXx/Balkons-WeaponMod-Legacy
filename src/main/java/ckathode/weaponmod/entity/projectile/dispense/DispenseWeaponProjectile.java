package ckathode.weaponmod.entity.projectile.dispense;

import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public abstract class DispenseWeaponProjectile extends ProjectileDispenseBehavior {

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blockSource, @Nonnull ItemStack stack) {
        World world = blockSource.getWorld();
        IPosition pos = DispenserBlock.getDispensePosition(blockSource);
        Direction face = blockSource.getBlockState().get(DispenserBlock.FACING);
        IProjectile projectile = getProjectileEntity(world, pos, stack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + getYVel(), face.getZOffset(), getProjectileVelocity(),
                getProjectileInaccuracy());
        world.addEntity((Entity) projectile);
        stack.shrink(1);
        return stack;
    }

    public double getYVel() {
        return 0.1;
    }

}
