package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntitySpear;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {
    public MeleeCompSpear(Tier itemTier) {
        super(MeleeSpecs.SPEAR, itemTier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!BalkonsWeaponModForge.instance.modConfig.canThrowSpear.get()) {
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
                SoundSource.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.abilities.instabuild) {
            itemstack = itemstack.copy();
            itemstack.setCount(0);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return BalkonsWeaponModForge.instance.modConfig.canThrowSpear.get()
                ? UseAnim.NONE : super.getUseAnimation(itemstack);
    }

    @Override
    public float getExtendedReach(Level world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }
}
