package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DispenseDynamite extends DispenseWeaponProjectile {

    @NotNull
    @Override
    protected Projectile getProjectile(@NotNull Level world, Position pos,
                                       @NotNull ItemStack stack) {
        return new EntityDynamite(world, pos.x(), pos.y(), pos.z());
    }

    @Override
    protected void playSound(@NotNull BlockSource blocksource) {
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.TNT_PRIMED,
                SoundSource.NEUTRAL, 1.0f, 1.2f);
    }

}
