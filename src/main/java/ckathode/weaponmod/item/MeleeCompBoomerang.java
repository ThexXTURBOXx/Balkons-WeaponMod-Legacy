package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(IItemTier itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        if (entityliving instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) entityliving;
            if (itemstack.isEmpty()) {
                return;
            }
            int j = getUseDuration(itemstack) - i;
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
                EntityBoomerang entityboomerang = new EntityBoomerang(world, entityplayer, itemstack);
                entityboomerang.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, f,
                        5.0f);
                entityboomerang.setIsCritical(crit);
                entityboomerang.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK,
                        itemstack));
                if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                    entityboomerang.setFire(100);
                }
                world.spawnEntity(entityboomerang);
            }
            world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                    SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.6f,
                    1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 1.0f));
            if (!entityplayer.abilities.isCreativeMode) {
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
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        if (!entityplayer.abilities.isCreativeMode && itemstack.isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
