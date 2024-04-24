package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseJavelin extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected ProjectileEntity getProjectile(@Nonnull World world, IPosition pos,
                                             @Nonnull ItemStack stack) {
        return new EntityJavelin(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    protected float getUncertainty() {
        return 4.0f;
    }

    @Override
    public float getPower() {
        return 1.1f;
    }

    @Override
    protected void playSound(@Nonnull IBlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.ARROW_SHOOT,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
