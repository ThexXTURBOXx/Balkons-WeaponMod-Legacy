package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected ProjectileEntity getProjectile(@Nonnull World world, IPosition pos,
                                             @Nonnull ItemStack stack) {
        return new EntityCrossbowBolt(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    public float getPower() {
        return 3.0f;
    }

    @Override
    protected float getUncertainty() {
        return 2.0f;
    }

    @Override
    protected void playSound(@Nonnull IBlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.ARROW_SHOOT,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
