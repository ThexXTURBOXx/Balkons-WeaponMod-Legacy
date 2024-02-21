package ckathode.weaponmod.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.*;

public class ItemJavelin extends WMItem
{
    public ItemJavelin(final String id) {
        super(id);
        this.maxStackSize = 16;
    }
    
    public int getItemEnchantability() {
        return 0;
    }
    
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityLiving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer)entityLiving;
        if (itemstack.isEmpty()) {
            return;
        }
        final int j = this.getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = false;
        if (!entityplayer.onGround && !entityplayer.isInWater()) {
            crit = true;
        }
        if (!world.isRemote) {
            final EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setIsCritical(crit);
            world.spawnEntity(entityjavelin);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (ItemJavelin.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                entityplayer.inventory.deleteStack(itemstack);
            }
        }
    }
    
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.BOW;
    }
    
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.capabilities.isCreativeMode && itemstack.isEmpty()) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
