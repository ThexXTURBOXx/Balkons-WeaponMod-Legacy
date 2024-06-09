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
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EnumFacing face = blocksource.getBlockState().getValue(BlockDispenser.FACING);
        IProjectile projectile = getProjectileEntity(world, pos, itemstack);
        projectile.shoot(face.getXOffset(), face.getYOffset() + getYVel(), face.getZOffset(), getProjectileVelocity(),
                getProjectileInaccuracy());
        world.spawnEntity((Entity) projectile);
        itemstack.shrink(1);
        return itemstack;
    }

    public double getYVel() {
        return 0.1;
    }

}
