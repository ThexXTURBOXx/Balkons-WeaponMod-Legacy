package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityKnife;
import ckathode.weaponmod.forge.BalkonsWeaponModForge;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompKnife extends MeleeComponent {
    public MeleeCompKnife(Tier itemTier) {
        super(MeleeSpecs.KNIFE, itemTier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!BalkonsWeaponModForge.instance.modConfig.canThrowKnife.get()) {
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
                SoundSource.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.abilities.instabuild) {
            itemstack = itemstack.copy();
            itemstack.setCount(0);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return BalkonsWeaponModForge.instance.modConfig.canThrowKnife.get()
                ? UseAnim.NONE : super.getUseAnimation(itemstack);
    }
}
