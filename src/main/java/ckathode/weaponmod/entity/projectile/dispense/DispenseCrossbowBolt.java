package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(World world, IPosition pos) {
        return new EntityCrossbowBolt(world, pos.getX(), pos.getY(), pos.getZ());
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
