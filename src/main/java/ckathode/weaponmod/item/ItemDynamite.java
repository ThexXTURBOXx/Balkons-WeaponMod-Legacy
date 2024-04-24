package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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
    public ActionResult<ItemStack> use(@Nonnull World world, PlayerEntity entityplayer,
                                       @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!entityplayer.abilities.instabuild) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.TNT_PRIMED,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!world.isClientSide) {
            EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + random.nextInt(10));
            entitydynamite.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 0.7f, 4.0f);
            world.addFreshEntity(entitydynamite);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

}
