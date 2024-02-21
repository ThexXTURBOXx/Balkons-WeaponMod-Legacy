package ckathode.weaponmod.entity.projectile.dispense;

import java.util.*;
import ckathode.weaponmod.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class DispenseCannonBall extends BehaviorDefaultDispenseItem
{
    private Random rand;
    private boolean normalDispense;
    
    public DispenseCannonBall() {
        this.rand = new Random();
        this.normalDispense = false;
    }
    
    public ItemStack dispenseStack(final IBlockSource blocksource, final ItemStack itemstack) {
        boolean canfire = false;
        this.normalDispense = false;
        final TileEntity tileentity = blocksource.getWorld().getTileEntity(blocksource.getBlockPos());
        if (tileentity instanceof TileEntityDispenser) {
            final TileEntityDispenser dispenser = (TileEntityDispenser)tileentity;
            Item itemtocheck = null;
            if (itemstack.getItem() == Items.GUNPOWDER) {
                itemtocheck = BalkonsWeaponMod.cannonBall;
            }
            else if (itemstack.getItem() == BalkonsWeaponMod.cannonBall) {
                itemtocheck = Items.GUNPOWDER;
            }
            for (int i = 0; i < dispenser.getSizeInventory(); ++i) {
                final ItemStack itemstack2 = dispenser.getStackInSlot(i);
                if (itemstack2 != null && itemstack2.getItem() == itemtocheck) {
                    dispenser.decrStackSize(i, 1);
                    canfire = true;
                    break;
                }
            }
        }
        if (!canfire) {
            this.normalDispense = true;
            return super.dispenseStack(blocksource, itemstack);
        }
        final EnumFacing face = (EnumFacing)blocksource.getBlockState().getValue((IProperty)BlockDispenser.FACING);
        final double xvel = face.getXOffset() * 1.5;
        final double yvel = face.getYOffset() * 1.5;
        final double zvel = face.getZOffset() * 1.5;
        final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
        final EntityCannonBall entitycannonball = new EntityCannonBall(blocksource.getWorld(), pos.getX() + xvel, pos.getY() + yvel, pos.getZ() + zvel);
        entitycannonball.shoot(xvel, yvel + 0.15, zvel, 2.0f, 2.0f);
        blocksource.getWorld().spawnEntity(entitycannonball);
        itemstack.splitStack(1);
        return itemstack;
    }
    
    protected void playDispenseSound(final IBlockSource blocksource) {
        if (this.normalDispense) {
            super.playDispenseSound(blocksource);
            return;
        }
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 8.0f, 1.0f / (this.rand.nextFloat() * 0.8f + 0.9f));
        blocksource.getWorld().playSound(null, blocksource.getBlockPos(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.NEUTRAL, 8.0f, 1.0f / (this.rand.nextFloat() * 0.4f + 0.6f));
    }
    
    protected void spawnDispenseParticles(final IBlockSource blocksource, final EnumFacing face) {
        super.spawnDispenseParticles(blocksource, face);
        if (!this.normalDispense) {
            final IPosition pos = BlockDispenser.getDispensePosition(blocksource);
            blocksource.getWorld().spawnParticle(EnumParticleTypes.FLAME, pos.getX() + face.getXOffset(), pos.getY() + face.getYOffset(), pos.getZ() + face.getZOffset(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
}
