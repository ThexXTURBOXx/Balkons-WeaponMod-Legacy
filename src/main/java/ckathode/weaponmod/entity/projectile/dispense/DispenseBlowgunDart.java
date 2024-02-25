package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.item.ItemBlowgunDart;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DispenseBlowgunDart extends DispenseWeaponProjectile {
    @Override
    protected IProjectile getProjectileEntityWorld(final World world, final IPosition pos, final ItemStack itemstack) {
        final EntityBlowgunDart dart = (EntityBlowgunDart) this.getProjectileEntity(world, pos, itemstack);
        Item item = itemstack.getItem();
        if (item instanceof ItemBlowgunDart)
            dart.setDartEffectType(((ItemBlowgunDart) item).getDartType());
        return dart;
    }

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull final World world, final IPosition pos,
                                              @Nonnull final ItemStack itemstack) {
        return new EntityBlowgunDart(world, pos.getX(), pos.getY(), pos.getZ());
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
