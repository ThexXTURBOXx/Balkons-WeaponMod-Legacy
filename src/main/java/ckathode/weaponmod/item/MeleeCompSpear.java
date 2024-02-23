package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {
    public MeleeCompSpear(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.SPEAR, toolmaterial);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer,
                                                    final EnumHand hand) {
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
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack = itemstack.copy();
            itemstack.setCount(0);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
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
