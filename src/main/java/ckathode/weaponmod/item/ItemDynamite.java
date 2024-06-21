package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDynamite extends WMItem {
    public ItemDynamite(String id) {
        super(id);
        maxStackSize = 64;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.splitStack(1);
            if (itemstack.stackSize <= 0) {
                deleteStack(entityplayer.inventory, itemstack);
            }
        }
        world.playSoundAtEntity(entityplayer, "game.tnt.primed", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + itemRand.nextInt(10));
            entitydynamite.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.7f, 4.0f);
            world.spawnEntityInWorld(entitydynamite);
        }
        return itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
