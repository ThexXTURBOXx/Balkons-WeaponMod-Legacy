package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDynamite extends WMItem {

    public static final String ID = "dynamite";
    public static final ItemDynamite ITEM = WMItemBuilder.createStandardDynamite();

    public ItemDynamite() {
        super(WMItem.getBaseProperties(null).stacksTo(64));
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world, Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!entityplayer.isCreative()) {
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
