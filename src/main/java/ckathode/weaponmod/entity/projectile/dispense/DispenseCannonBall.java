package ckathode.weaponmod.entity.projectile.dispense;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;

public class DispenseCannonBall extends BehaviorDefaultDispenseItem {
    private final Random rand = new Random();
    private boolean normalDispense = false;

    @Nonnull
    @Override
    public ItemStack dispenseStack(IBlockSource blocksource, @Nonnull ItemStack itemstack) {
        boolean canfire = false;
        normalDispense = false;
        TileEntity tileentity = blocksource.getWorld().getTileEntity(blocksource.getBlockPos());
        if (tileentity instanceof TileEntityDispenser) {
            TileEntityDispenser dispenser = (TileEntityDispenser) tileentity;
            Item itemtocheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemtocheck = BalkonsWeaponMod.cannonBall;
            } else if (itemstack.getItem() == BalkonsWeaponMod.cannonBall) {
                itemtocheck = Items.GUNPOWDER;
            }
            for (int i = 0; i < dispenser.getSizeInventory(); ++i) {
                ItemStack itemstack2 = dispenser.getStackInSlot(i);
                if (itemstack2 != null && itemstack2.getItem() == itemtocheck) {
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
        EnumFacing face = blocksource.getBlockState().getValue(BlockDispenser.FACING);
        double xvel = face.getFrontOffsetX() * 1.5;
        double yvel = face.getFrontOffsetY() * 1.5;
        double zvel = face.getFrontOffsetZ() * 1.5;
        IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.getWorld(), pos.getX() + xvel,
                pos.getY() + yvel, pos.getZ() + zvel);
        entitycannonball.setThrowableHeading(xvel, yvel + 0.15, zvel, 2.0f, 2.0f);
        blocksource.getWorld().spawnEntity(entitycannonball);
        itemstack.splitStack(1);
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
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_THUNDER,
                SoundCategory.NEUTRAL, 8.0f, 1.0f / (rand.nextFloat() * 0.4f + 0.6f));
    }

    @Override
    protected void spawnDispenseParticles(@Nonnull IBlockSource blocksource, @Nonnull EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        if (!normalDispense) {
            IPosition pos = BlockDispenser.getDispensePosition(blocksource);
            blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getFrontOffsetX(),
                    pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ(), 0.0, 0.0, 0.0);
        }
    }
}
