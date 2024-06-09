package ckathode.weaponmod.entity.projectile.dispense;

import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.world.World;

public abstract class DispenseWeaponProjectile extends ProjectileDispenseBehavior {

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        World world = blocksource.getWorld();
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        Direction face = blocksource.getBlockState().get(DispenserBlock.FACING);
        IProjectile projectile = getProjectileEntity(world, pos, itemstack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + getYVel(), face.getZOffset(), getProjectileVelocity(),
                getProjectileInaccuracy());
        world.addEntity((Entity) projectile);
        itemstack.shrink(1);
        return itemstack;
    }

    public double getYVel() {
        return 0.1;
    }

}
