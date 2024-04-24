package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseDynamite extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected ProjectileEntity getProjectile(@Nonnull World world, IPosition pos,
                                             @Nonnull ItemStack stack) {
        return new EntityDynamite(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    protected void playSound(@Nonnull IBlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.TNT_PRIMED,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
