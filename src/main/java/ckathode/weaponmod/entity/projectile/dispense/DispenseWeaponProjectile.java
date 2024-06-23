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
    public ItemStack dispenseStack(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        World world = blocksource.getWorld();
        IPosition pos = BlockDispenser.func_149939_a(blocksource);
        EnumFacing face = BlockDispenser.func_149937_b(blocksource.getBlockMetadata());
        IProjectile projectile = getProjectileEntity(world, pos, itemstack);
        projectile.setThrowableHeading(face.getFrontOffsetX(), face.getFrontOffsetY() + getYVel(),
                face.getFrontOffsetZ(), func_82500_b(), func_82498_a());
        world.spawnEntityInWorld((Entity) projectile);
        itemstack.splitStack(1);
        return itemstack;
    }

    @Nonnull
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack itemstack) {
        return getProjectileEntity(world, pos);
    }

    public double getYVel() {
        return 0.1;
    }

}
