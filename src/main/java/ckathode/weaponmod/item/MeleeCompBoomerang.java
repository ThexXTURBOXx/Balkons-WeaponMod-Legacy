package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(IItemTier itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, World world, LivingEntity entityliving, int i) {
        if (entityliving instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityliving;
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
            if (!world.isClientSide) {
                EntityBoomerang entityboomerang = new EntityBoomerang(world, entityplayer, itemstack);
                entityboomerang.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, f,
                        5.0f);
                entityboomerang.setCritArrow(crit);
                entityboomerang.setKnockback(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK,
                        itemstack));
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                    entityboomerang.setSecondsOnFire(100);
                }
                world.addFreshEntity(entityboomerang);
            }
            world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 0.6f,
                    1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 1.0f));
            if (!entityplayer.abilities.instabuild) {
                ItemStack itemstack2 = itemstack.copy();
                itemstack2.shrink(1);
                if (itemstack2.isEmpty()) {
                    itemstack2 = ItemStack.EMPTY;
                }
                entityplayer.inventory.items.set(entityplayer.inventory.selected, itemstack2);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != Hand.MAIN_HAND) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        if (!entityplayer.abilities.instabuild && itemstack.isEmpty()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        entityplayer.startUsingItem(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
