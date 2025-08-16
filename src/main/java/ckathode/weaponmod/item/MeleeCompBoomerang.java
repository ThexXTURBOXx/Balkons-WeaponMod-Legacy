package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.BOOMERANG, toolmaterial);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        if (itemstack.isEmpty()) {
            return;
        }
        int j = getMaxItemUseDuration(itemstack) - i;
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
        if (!world.isRemote) {
            EntityBoomerang entityboomerang = new EntityBoomerang(world, entityliving, itemstack.copy());
            entityboomerang.setAim(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0f, f, 5.0f);
            applyProjectileEnchantments(entityboomerang, itemstack);
            entityboomerang.setIsCritical(crit);
            world.spawnEntity(entityboomerang);
        }
        world.playSound(null, entityliving.posX, entityliving.posY, entityliving.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.6f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 1.0f));
        if (!(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).isCreative()) {
            WMItem.decrStackSize(itemstack, 1, entityliving);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    EntityPlayer entityplayer, EnumHand hand) {
        if (hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
    }
}
