package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.Random;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
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
        boolean canfire = false;
        normalDispense = false;
        BlockEntity tileentity = blocksource.getLevel().getBlockEntity(blocksource.getPos());
        if (tileentity instanceof DispenserBlockEntity) {
            DispenserBlockEntity dispenser = (DispenserBlockEntity) tileentity;
            Item itemtocheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemtocheck = WMRegistries.ITEM_CANNON_BALL.get();
            } else if (itemstack.getItem() == WMRegistries.ITEM_CANNON_BALL.get()) {
                itemtocheck = Items.GUNPOWDER;
            }
            for (int i = 0; i < dispenser.getContainerSize(); ++i) {
                ItemStack itemstack2 = dispenser.getItem(i);
                if (!itemstack2.isEmpty() && itemstack2.getItem() == itemtocheck) {
                    dispenser.removeItem(i, 1);
                    canfire = true;
                    break;
                }
            }
        }
        if (!canfire) {
            normalDispense = true;
            return super.execute(blocksource, itemstack);
        }
        Direction face = blocksource.getBlockState().getValue(DispenserBlock.FACING);
        double xvel = face.getStepX() * 1.5;
        double yvel = face.getStepY() * 1.5;
        double zvel = face.getStepZ() * 1.5;
        Position pos = DispenserBlock.getDispensePosition(blocksource);
        EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.getLevel(), pos.x() + xvel,
                pos.y() + yvel, pos.z() + zvel);
        entitycannonball.shoot(xvel, yvel + 0.15, zvel, 2.0f, 2.0f);
        blocksource.getLevel().addFreshEntity(entitycannonball);
        itemstack.split(1);
        return itemstack;
    }

    @Override
    protected void playSound(@NotNull BlockSource blocksource) {
        if (normalDispense) {
            super.playSound(blocksource);
            return;
        }
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.GENERIC_EXPLODE,
                SoundSource.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.8f + 0.9f));
        blocksource.getLevel().playSound(null, blocksource.getPos(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                SoundSource.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void playAnimation(@NotNull BlockSource blocksource, @NotNull Direction face) {
        super.playAnimation(blocksource, face);
        if (!normalDispense) {
            Position pos = DispenserBlock.getDispensePosition(blocksource);
            blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                    pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
        }
    }

}
