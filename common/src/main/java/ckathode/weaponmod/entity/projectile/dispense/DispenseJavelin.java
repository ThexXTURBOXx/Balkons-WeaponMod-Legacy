package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import org.jetbrains.annotations.NotNull;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DispenseJavelin extends DispenseWeaponProjectile {
    @NotNull
    @Override
    protected Projectile getProjectile(@NotNull Level world, Position pos,
                                       @NotNull ItemStack stack) {
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
    protected void playSound(@NotNull BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.ARROW_SHOOT,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }
}
