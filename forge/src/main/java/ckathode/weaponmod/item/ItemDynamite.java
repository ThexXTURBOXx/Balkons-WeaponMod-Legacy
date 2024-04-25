package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDynamite extends WMItem {
    public ItemDynamite(String id) {
        super(id, new Properties().stacksTo(64));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, Player entityplayer,
                                                  @Nonnull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!entityplayer.abilities.instabuild) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.TNT_PRIMED,
                SoundSource.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + random.nextInt(10));
            entitydynamite.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.7f, 4.0f);
            world.addFreshEntity(entitydynamite);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

}
