package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DispenseCrossbowBolt extends DispenseWeaponProjectile {
    @Nonnull
    @Override
    protected Projectile getProjectile(@Nonnull Level world, Position pos,
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
    protected void playSound(@Nonnull BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.ARROW_SHOOT,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }
}
