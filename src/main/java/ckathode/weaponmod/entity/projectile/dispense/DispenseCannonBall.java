package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class DispenseCannonBall extends DefaultDispenseItemBehavior {
    private final Random rand = new Random();
    private boolean normalDispense = false;

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        boolean canfire = false;
        normalDispense = false;
        TileEntity tileentity = blocksource.getWorld().getTileEntity(blocksource.getBlockPos());
        if (tileentity instanceof DispenserTileEntity) {
            DispenserTileEntity dispenser = (DispenserTileEntity) tileentity;
            Item itemtocheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemtocheck = BalkonsWeaponMod.cannonBall;
            } else if (itemstack.getItem() == BalkonsWeaponMod.cannonBall) {
                itemtocheck = Items.GUNPOWDER;
            }
            for (int i = 0; i < dispenser.getSizeInventory(); ++i) {
                ItemStack itemstack2 = dispenser.getStackInSlot(i);
                if (!itemstack2.isEmpty() && itemstack2.getItem() == itemtocheck) {
                    dispenser.decrStackSize(i, 1);
                    canfire = true;
                    break;
                }
            }
        }
        if (!canfire) {
            normalDispense = true;
            return super.dispenseStack(blocksource, itemstack);
        }
        Direction face = blocksource.getBlockState().get(DispenserBlock.FACING);
        double xvel = face.getXOffset() * 1.5;
        double yvel = face.getYOffset() * 1.5;
        double zvel = face.getZOffset() * 1.5;
        IPosition pos = DispenserBlock.getDispensePosition(blocksource);
        EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.getWorld(), pos.getX() + xvel,
                pos.getY() + yvel, pos.getZ() + zvel);
        entitycannonball.shoot(xvel, yvel + 0.15, zvel, 2.0f, 2.0f);
        blocksource.getWorld().addEntity(entitycannonball);
        itemstack.split(1);
        return itemstack;
    }

    @Override
    protected void playDispenseSound(@Nonnull IBlockSource blocksource) {
        if (normalDispense) {
            super.playDispenseSound(blocksource);
            return;
        }
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.8f + 0.9f));
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                SoundCategory.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull Direction face) {
        super.spawnDispenseParticles(blocksource, face);
        if (!normalDispense) {
            IPosition pos = DispenserBlock.getDispensePosition(blocksource);
            blocksource.getWorld().addParticle(ParticleTypes.FLAME, pos.getX() + face.getXOffset(),
                    pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0);
        }
    }
}
