package ckathode.weaponmod.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemDynamite extends WMItem
{
    public ItemDynamite(final String id) {
        super(id);
        this.maxStackSize = 64;
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 1.0f, 1.0f / (ItemDynamite.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            final EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer, 40 + ItemDynamite.itemRand.nextInt(10));
            entitydynamite.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.7f, 4.0f);
            world.spawnEntity(entitydynamite);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
