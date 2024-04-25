package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(Tier itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        if (entityliving instanceof Player) {
            Player entityplayer = (Player) entityliving;
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
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.6f,
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
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        if (!entityplayer.abilities.instabuild && itemstack.isEmpty()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        entityplayer.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }
}
