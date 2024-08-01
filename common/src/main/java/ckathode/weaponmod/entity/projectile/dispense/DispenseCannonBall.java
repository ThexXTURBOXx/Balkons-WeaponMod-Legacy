package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import org.jetbrains.annotations.NotNull;

public class DispenseCannonBall extends DefaultDispenseItemBehavior {

    private final Random rand = new Random();
    private boolean normalDispense = false;

    @NotNull
    @Override
    public ItemStack execute(BlockSource blocksource, @NotNull ItemStack itemstack) {
        boolean canFire = false;
        normalDispense = false;
        BlockEntity blockEntity = blocksource.level().getBlockEntity(blocksource.pos());
        if (blockEntity instanceof DispenserBlockEntity dispenser) {
            Item itemToCheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemToCheck = WMRegistries.ITEM_CANNON_BALL.get();
            } else if (itemstack.getItem() == WMRegistries.ITEM_CANNON_BALL.get()) {
                itemToCheck = Items.GUNPOWDER;
            }
            for (int i = 0; i < dispenser.getContainerSize(); ++i) {
                ItemStack itemStack2 = dispenser.getItem(i);
                if (!itemStack2.isEmpty() && itemStack2.getItem() == itemToCheck) {
                    dispenser.removeItem(i, 1);
                    canFire = true;
                    break;
                }
            }
        }
        if (!canFire) {
            normalDispense = true;
            return super.execute(blocksource, itemstack);
        }
        Direction face = blocksource.state().getValue(DispenserBlock.FACING);
        double xVel = face.getStepX() * 1.5;
        double yVel = face.getStepY() * 1.5;
        double zVel = face.getStepZ() * 1.5;
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.level(), pos.x() + xVel,
                pos.y() + yVel, pos.z() + zVel, null);
        entitycannonball.shoot(xVel, yVel + 0.15, zVel, 2.0f, 2.0f);
        blocksource.level().addFreshEntity(entitycannonball);
        itemstack.shrink(1);
        return itemstack;
    }

    @Override
    protected void playSound(@NotNull BlockSource blocksource) {
        if (normalDispense) {
            super.playSound(blocksource);
            return;
        }
        blocksource.level().playSound(null, blocksource.pos(), SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.8f + 0.9f));
        blocksource.level().playSound(null, blocksource.pos(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                SoundSource.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void playAnimation(@NotNull BlockSource blocksource, @NotNull Direction face) {
        super.playAnimation(blocksource, face);
        if (!normalDispense) {
            Position pos = DispenserBlock.getDispensePosition(blocksource);
            if (blocksource.level().isClientSide()) {
                blocksource.level().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                        pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
            }
        }
    }

}
