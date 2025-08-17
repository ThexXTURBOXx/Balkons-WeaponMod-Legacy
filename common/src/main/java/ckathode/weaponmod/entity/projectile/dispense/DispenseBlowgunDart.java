package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DispenseBlowgunDart extends DispenseWeaponProjectile {

    @NotNull
    @Override
    protected Projectile getProjectile(@NotNull Level world, Position pos,
                                       @NotNull ItemStack itemstack) {
        EntityBlowgunDart dart = new EntityBlowgunDart(world, pos.x(), pos.y(), pos.z());
        dart.setThrownItemStack(itemstack);
        return dart;
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
    protected void playSound(@NotNull BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.ARROW_SHOOT,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }

}
