package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public class DispenseWeaponProjectile extends ProjectileDispenseBehavior {

    @NotNull
    private final WMDispenserExtension extension;

    public DispenseWeaponProjectile(@NotNull Item projectile, @NotNull WMDispenserExtension extension) {
        super(projectile);
        this.extension = extension;
    }

    @NotNull
    @Override
    public ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack item) {
        ServerLevel level = blockSource.level();
        Direction direction = blockSource.state().getValue(DispenserBlock.FACING);
        Position position = dispenseConfig.positionFunction().getDispensePosition(blockSource, direction);
        Projectile projectile = projectileItem.asProjectile(level, position, item, direction);
        projectileItem.shoot(projectile, direction.getStepX(), direction.getStepY() + getYVel(blockSource, item),
                direction.getStepZ(), dispenseConfig.power(), dispenseConfig.uncertainty());
        level.addFreshEntity(projectile);
        item.shrink(1);
        return item;
    }

    public double getYVel(@NotNull BlockSource blockSource, @NotNull ItemStack item) {
        return extension.getYVel((bs, i) -> 0.1, blockSource, item);
    }

    @Override
    public void playSound(@NotNull BlockSource blockSource) {
        extension.playSound(super::playSound, blockSource);
    }

    @Override
    public void playAnimation(@NotNull BlockSource blockSource, @NotNull Direction direction) {
        extension.playAnimation(super::playAnimation, blockSource, direction);
    }

}
