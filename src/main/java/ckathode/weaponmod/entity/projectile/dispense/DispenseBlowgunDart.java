package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.item.ItemBlowgunDart;
import javax.annotation.Nonnull;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DispenseBlowgunDart extends DispenseWeaponProjectile {

    @Nonnull
    @Override
    protected IProjectile getProjectileEntity(@Nonnull World world, IPosition pos,
                                              @Nonnull ItemStack itemstack) {
        EntityBlowgunDart dart = new EntityBlowgunDart(world, pos.getX(), pos.getY(), pos.getZ());
        Item item = itemstack.getItem();
        if (item instanceof ItemBlowgunDart)
            dart.setDartEffectType((byte) itemstack.getMetadata());
        return dart;
    }

    @Override
    public float getProjectileVelocity() {
        return 3.0f;
    }

    @Override
    protected float getProjectileInaccuracy() {
        return 2.0f;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.NEUTRAL, 1.0f, 1.2f);
    }
}
