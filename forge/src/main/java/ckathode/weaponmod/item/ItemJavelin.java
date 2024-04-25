package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class ItemJavelin extends WMItem {
    public ItemJavelin(String id) {
        super(id, new Properties().stacksTo(16));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public void releaseUsing(ItemStack itemstack, @Nonnull Level world,
                             @Nonnull LivingEntity entityLiving, int i) {
        Player entityplayer = (Player) entityLiving;
        if (itemstack.isEmpty()) {
            return;
        }
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = !entityplayer.isOnGround() && !entityplayer.isInWater();
        if (!world.isClientSide) {
            EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setCritArrow(crit);
            world.addFreshEntity(entityjavelin);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.abilities.instabuild) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                entityplayer.inventory.removeItem(itemstack);
            }
        }
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack itemstack) {
        return 72000;
    }

    @Nonnull
    @Override
    public UseAnim getUseAnimation(@Nonnull ItemStack itemstack) {
        return UseAnim.BOW;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, Player entityplayer,
                                                  @Nonnull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!entityplayer.abilities.instabuild && itemstack.isEmpty()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        entityplayer.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

}
