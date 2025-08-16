package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(IItemTier itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     LivingEntity entityliving, int i) {
        if (itemstack.isEmpty()) {
            return;
        }
        int j = getUseDuration(itemstack) - i;
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
            entityboomerang.shoot(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0f, f, 5.0f);
            applyProjectileEnchantments(entityboomerang, itemstack);
            entityboomerang.setIsCritical(crit);
            world.addEntity(entityboomerang);
        }
        world.playSound(null, entityliving.posX, entityliving.posY, entityliving.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 0.6f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 1.0f));
        if (!(entityliving instanceof PlayerEntity) || !((PlayerEntity) entityliving).isCreative()) {
            WMItem.decrStackSize(itemstack, 1, entityliving);
        }
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world,
                                                    PlayerEntity entityplayer, Hand hand) {
        if (hand != Hand.MAIN_HAND) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }
}
