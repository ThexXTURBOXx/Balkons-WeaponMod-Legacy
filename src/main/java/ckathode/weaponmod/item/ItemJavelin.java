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
    public ItemJavelin(String id) {
        super(id);
        maxStackSize = 16;
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack itemstack, @Nonnull World world,
                                     @Nonnull EntityLivingBase entityLiving, int i) {
        EntityPlayer entityplayer = (EntityPlayer) entityLiving;
        if (itemstack.stackSize <= 0) {
            entityplayer.inventory.deleteStack(itemstack);
            return;
        }
        int j = getMaxItemUseDuration(itemstack) - i;
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
            EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setIsCritical(crit);
            world.spawnEntityInWorld(entityjavelin);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (itemRand.nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack.splitStack(1);
            if (itemstack.stackSize <= 0) {
                entityplayer.inventory.deleteStack(itemstack);
            }
        }
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack itemstack) {
        return 72000;
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack itemstack) {
        return EnumAction.BOW;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack itemstack, @Nonnull World world,
                                                    EntityPlayer entityplayer, @Nonnull EnumHand hand) {
        if (!entityplayer.isCreative() && itemstack.stackSize <= 0) {
            entityplayer.inventory.deleteStack(itemstack);
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
