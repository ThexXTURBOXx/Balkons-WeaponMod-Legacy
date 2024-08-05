package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseJavelin extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack stack) {
        return new EntityJavelin(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected float getProjectileInaccuracy() {
        return 4.0f;
    }

    @Override
    public float getProjectileVelocity() {
        return 1.1f;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
