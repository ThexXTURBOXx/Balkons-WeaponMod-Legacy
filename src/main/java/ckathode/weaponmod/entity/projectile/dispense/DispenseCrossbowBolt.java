package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull final World world, final IPosition pos,
                                              @Nonnull final ItemStack stack) {
        return new EntityCrossbowBolt(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public float getVelocity() {
        return 3.0f;
    }

    @Override
    public float getDeviation() {
        return 2.0f;
    }

    @Override
    protected void playDispenseSound(@Nonnull final IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
