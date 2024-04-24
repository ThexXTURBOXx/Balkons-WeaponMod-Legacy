package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {
    public MeleeCompSpear(IItemTier itemTier) {
        super(MeleeSpecs.SPEAR, itemTier);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!BalkonsWeaponMod.instance.modConfig.canThrowSpear.get()) {
            return super.use(world, entityplayer, hand);
        }
        if (!world.isClientSide) {
            EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack);
            entityspear.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.8f, 3.0f);
            entityspear.setKnockback(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityspear.setSecondsOnFire(100);
            }
            world.addFreshEntity(entityspear);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.abilities.instabuild) {
            itemstack = itemstack.copy();
            itemstack.setCount(0);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowSpear.get()
                ? UseAction.NONE : super.getUseAnimation(itemstack);
    }

    @Override
    public float getExtendedReach(World world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }
}
