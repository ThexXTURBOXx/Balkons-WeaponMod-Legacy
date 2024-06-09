package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompKnife extends MeleeComponent {
    public MeleeCompKnife(IItemTier itemTier) {
        super(MeleeSpecs.KNIFE, itemTier);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer,
                                                    EnumHand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!BalkonsWeaponMod.instance.modConfig.canThrowKnife.get()) {
            return super.onItemRightClick(world, entityplayer, hand);
        }
        if (!world.isRemote) {
            EntityKnife entityknife = new EntityKnife(world, entityplayer, itemstack.copy());
            entityknife.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            entityknife.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityknife.setFire(100);
            }
            world.spawnEntity(entityknife);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack = itemstack.copy();
            itemstack.shrink(1);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public EnumAction getUseAction(ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowKnife.get()
                ? EnumAction.NONE : super.getUseAction(itemstack);
    }
}
