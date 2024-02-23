package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemJavelin extends WMItem {
    public ItemJavelin(final String id) {
        super(id);
        this.maxStackSize = 16;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void onPlayerStoppedUsing(final ItemStack itemstack, @Nonnull final World world,
                                     @Nonnull final EntityLivingBase entityLiving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer) entityLiving;
        if (itemstack.isEmpty()) {
            return;
        }
        final int j = this.getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        boolean crit = !entityplayer.onGround && !entityplayer.isInWater();
        if (!world.isRemote) {
            final EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setIsCritical(crit);
            world.spawnEntity(entityjavelin);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (ItemJavelin.itemRand.nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                entityplayer.inventory.deleteStack(itemstack);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull final ItemStack itemstack) {
        return 72000;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull final ItemStack itemstack) {
        return EnumAction.BOW;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world, final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.capabilities.isCreativeMode && itemstack.isEmpty()) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
