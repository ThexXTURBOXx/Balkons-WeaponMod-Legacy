package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDynamite extends WMItem {
    public ItemDynamite(final String id) {
        super(id, new Properties().maxStackSize(64));
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world, final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.abilities.isCreativeMode) {
            itemstack.shrink(1);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_TNT_PRIMED,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!world.isRemote) {
            final EntityDynamite entitydynamite = new EntityDynamite(world, entityplayer,
                    40 + random.nextInt(10));
            entitydynamite.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.7f, 4.0f);
            world.spawnEntity(entitydynamite);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

}
