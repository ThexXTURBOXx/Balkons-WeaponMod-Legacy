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

public class MeleeCompKnife extends MeleeComponent
{
    public MeleeCompKnife(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.KNIFE, toolmaterial);
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!BalkonsWeaponMod.instance.modConfig.canThrowKnife) {
            return super.onItemRightClick(world, entityplayer, hand);
        }
        if (!world.isRemote) {
            final EntityKnife entityknife = new EntityKnife(world, entityplayer, itemstack);
            entityknife.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            entityknife.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityknife.setFire(100);
            }
            world.spawnEntity(entityknife);
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
        return BalkonsWeaponMod.instance.modConfig.canThrowKnife ? EnumAction.NONE : super.getItemUseAction(itemstack);
    }
}
