package ckathode.weaponmod.entity.projectile.dispense;

import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

public abstract class DispenseWeaponProjectile extends AbstractProjectileDispenseBehavior {

    @NotNull
    @Override
    public ItemStack execute(BlockSource blocksource, @NotNull ItemStack itemstack) {
        Level world = blocksource.getLevel();
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        Direction face = blocksource.getBlockState().getValue(DispenserBlock.FACING);
        Projectile projectile = getProjectile(world, pos, itemstack);
        projectile.shoot(face.getStepX(), face.getStepY() + getYVel(), face.getStepZ(), getPower(),
                getUncertainty());
        world.addFreshEntity(projectile);
        itemstack.shrink(1);
        return itemstack;
    }

    public double getYVel() {
        return 0.1;
    }

}
