package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
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

public class MeleeCompKnife extends MeleeComponent {
    public MeleeCompKnife(IItemTier itemTier) {
        super(MeleeSpecs.KNIFE, itemTier);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!BalkonsWeaponMod.instance.modConfig.canThrowKnife.get()) {
            return super.use(world, entityplayer, hand);
        }
        if (!world.isClientSide) {
            EntityKnife entityknife = new EntityKnife(world, entityplayer, itemstack);
            entityknife.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.8f, 3.0f);
            entityknife.setKnockback(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityknife.setSecondsOnFire(100);
            }
            world.addFreshEntity(entityknife);
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
        return BalkonsWeaponMod.instance.modConfig.canThrowKnife.get()
                ? UseAction.NONE : super.getUseAnimation(itemstack);
    }
}
