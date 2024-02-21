package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class MeleeCompBoomerang extends MeleeComponent
{
    public MeleeCompBoomerang(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.BOOMERANG, toolmaterial);
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
        if (entityliving instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)entityliving;
            if (!itemstack.isEmpty()) {}
            final int j = this.getMaxItemUseDuration(itemstack) - i;
            float f = j / 20.0f;
            f = (f * f + f * 2.0f) / 3.0f;
            if (f < 0.1f) {
                return;
            }
            boolean crit = false;
            if (f > 1.5f) {
                f = 1.5f;
                crit = true;
            }
            f *= 1.5f;
            if (!world.isRemote) {
                final EntityBoomerang entityboomerang = new EntityBoomerang(world, entityplayer, itemstack);
                entityboomerang.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, f, 5.0f);
                entityboomerang.setIsCritical(crit);
                entityboomerang.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                    entityboomerang.setFire(100);
                }
                world.spawnEntity(entityboomerang);
            }
            world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.6f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 1.0f));
            if (!entityplayer.capabilities.isCreativeMode) {
                ItemStack itemstack2 = itemstack.copy();
                itemstack2.shrink(1);
                if (itemstack2.isEmpty()) {
                    itemstack2 = ItemStack.EMPTY;
                }
                entityplayer.inventory.mainInventory.set(entityplayer.inventory.currentItem, itemstack2);
            }
        }
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return 72000;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        if (!entityplayer.capabilities.isCreativeMode && itemstack.isEmpty()) {
            return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
}
