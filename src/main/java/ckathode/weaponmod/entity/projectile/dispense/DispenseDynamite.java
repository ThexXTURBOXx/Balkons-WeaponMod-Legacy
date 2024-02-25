package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DispenseDynamite extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack stack) {
        return new EntityDynamite(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
