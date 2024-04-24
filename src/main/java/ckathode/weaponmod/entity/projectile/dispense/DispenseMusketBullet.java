package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class DispenseMusketBullet extends DispenseWeaponProjectile {
    private final Random rand = new Random();

    @Nonnull
    @Override
    protected ProjectileEntity getProjectile(@Nonnull World world, IPosition pos,
                                             @Nonnull ItemStack stack) {
        return new EntityMusketBullet(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    public double getYVel() {
        return 0.0;
    }

    @Override
    protected float getUncertainty() {
        return 3.0f;
    }

    @Override
    public float getPower() {
        return 5.0f;
    }

    @Override
    protected void playSound(@Nonnull IBlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.7f));
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                SoundCategory.NEUTRAL, 3.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.4f));
    }

    @Override
    protected void playAnimation(@Nonnull IBlockSource blocksource, @Nonnull Direction face) {
        super.playAnimation(blocksource, face);
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.2, 0.0);
    }
}
