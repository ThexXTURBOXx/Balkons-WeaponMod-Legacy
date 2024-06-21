package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJavelin extends WMItem {
    public ItemJavelin(String id) {
        super(id);
        maxStackSize = 16;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityLiving, int i) {
        EntityPlayer entityplayer = (EntityPlayer) entityLiving;
        if (itemstack.stackSize <= 0) {
            deleteStack(entityplayer.inventory, itemstack);
            return;
        }
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = !entityplayer.onGround && !entityplayer.isInWater();
        if (!world.isRemote) {
            EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setIsCritical(crit);
            world.spawnEntityInWorld(entityjavelin);
        }
        world.playSoundAtEntity(entityplayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.splitStack(1);
            if (itemstack.stackSize <= 0) {
                deleteStack(entityplayer.inventory, itemstack);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack itemstack) {
        return 72000;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack itemstack) {
        return EnumAction.BOW;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!entityplayer.capabilities.isCreativeMode && itemstack.stackSize <= 0) {
            deleteStack(entityplayer.inventory, itemstack);
            return itemstack;
        }
        entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        return itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
