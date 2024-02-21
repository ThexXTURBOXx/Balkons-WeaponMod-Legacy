package ckathode.weaponmod.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import ckathode.weaponmod.*;
import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem
{
    public MeleeCompSpear(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.SPEAR, toolmaterial);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!BalkonsWeaponMod.instance.modConfig.canThrowSpear) {
            return super.onItemRightClick(world, entityplayer, hand);
        }
        if (!world.isRemote) {
            final EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack);
            entityspear.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            entityspear.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityspear.setFire(100);
            }
            world.spawnEntity(entityspear);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack = itemstack.copy();
            itemstack.setCount(0);
        }
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowSpear ? EnumAction.NONE : super.getItemUseAction(itemstack);
    }
    
    @Override
    public float getExtendedReach(final World world, final EntityLivingBase living, final ItemStack itemstack) {
        return 4.0f;
    }
}
