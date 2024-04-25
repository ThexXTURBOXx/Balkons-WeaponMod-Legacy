package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import java.util.Random;
import javax.annotation.Nonnull;
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

public class DispenseCannonBall extends DefaultDispenseItemBehavior {
    private final Random rand = new Random();
    private boolean normalDispense = false;

    @Nonnull
    @Override
    public ItemStack execute(BlockSource blocksource, @Nonnull ItemStack itemstack) {
        boolean canfire = false;
        normalDispense = false;
        BlockEntity tileentity = blocksource.getLevel().getBlockEntity(blocksource.getPos());
        if (tileentity instanceof DispenserBlockEntity) {
            DispenserBlockEntity dispenser = (DispenserBlockEntity) tileentity;
            Item itemtocheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemtocheck = BalkonsWeaponModForge.cannonBall;
            } else if (itemstack.getItem() == BalkonsWeaponModForge.cannonBall) {
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
    protected void playSound(@Nonnull BlockSource blocksource) {
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
    protected void playAnimation(@Nonnull BlockSource blocksource, @Nonnull Direction face) {
        super.playAnimation(blocksource, face);
        if (!normalDispense) {
            Position pos = DispenserBlock.getDispensePosition(blocksource);
            blocksource.getLevel().addParticle(ParticleTypes.FLAME, pos.x() + face.getStepX(),
                    pos.y() + face.getStepY(), pos.z() + face.getStepZ(), 0.0, 0.0, 0.0);
        }
    }
}
