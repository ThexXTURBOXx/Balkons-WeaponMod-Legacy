package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenseJavelin extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos) {
        return new EntityJavelin(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    protected float func_82498_a() {
        return 4.0f;
    }

    @Override
    public float func_82500_b() {
        return 1.1f;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "random.bow", 1.0F, 1.2F);
    }
}
