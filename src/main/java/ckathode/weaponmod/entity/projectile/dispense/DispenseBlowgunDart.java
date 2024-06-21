package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class DispenseBlowgunDart extends DispenseWeaponProjectile {

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack itemstack) {
        EntityBlowgunDart dart = getProjectileEntity(world, pos);
        dart.setDartEffectType((byte) itemstack.getMetadata());
        return dart;
    }

    @Override
    protected EntityBlowgunDart getProjectileEntity(World world, IPosition pos) {
        return new EntityBlowgunDart(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public float func_82500_b() {
        return 3.0f;
    }

    @Override
    protected float func_82498_a() {
        return 2.0f;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "random.bow", 1.0F, 1.2F);
    }
}
