package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class DispenseMusketBullet extends DispenseWeaponProjectile {
    private final Random rand = new Random();

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos) {
        return new EntityMusketBullet(world, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public double getYVel() {
        return 0.0;
    }

    @Override
    protected float func_82498_a() {
        return 3.0f;
    }

    @Override
    public float func_82500_b() {
        return 5.0f;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "random.explode", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.7F));
        blocksource.getWorld().playSoundEffect(blocksource.getX(), blocksource.getY(), blocksource.getZ(),
                "ambient.weather.thunder", 3.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.4F));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        IPosition pos = BlockDispenser.func_149939_a(blocksource);
        blocksource.getWorld().spawnParticle("flame", pos.getX() + face.getFrontOffsetX(),
                pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0, 0.2, 0.0);
    }
}
