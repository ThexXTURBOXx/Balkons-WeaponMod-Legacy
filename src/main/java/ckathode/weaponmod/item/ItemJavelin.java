package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemJavelin extends WMItem {
    public ItemJavelin(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public ItemJavelin(String modId, String id) {
        super(modId, id, new Properties().maxStackSize(16));
    }

    @Override
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, @Nonnull World world,
                                     @Nonnull LivingEntity entityLiving, int i) {
        PlayerEntity entityplayer = (PlayerEntity) entityLiving;
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
        boolean crit = !entityplayer.onGround && !entityplayer.isInWater();
        if (!world.isRemote) {
            EntityJavelin entityjavelin = new EntityJavelin(world, entityplayer);
            entityjavelin.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * (1.0f + (crit ? 0.5f : 0.0f)), 3.0f);
            entityjavelin.setIsCritical(crit);
            world.addEntity(entityjavelin);
        }
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT
                , SoundCategory.PLAYERS, 1.0f, 1.0f / (random.nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack.shrink(1);
            if (itemstack.isEmpty()) {
                entityplayer.inventory.deleteStack(itemstack);
            }
        }
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack itemstack) {
        return 72000;
    }

    @Nonnull
    @Override
    public UseAction getUseAction(@Nonnull ItemStack itemstack) {
        return UseAction.BOW;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, PlayerEntity entityplayer,
                                                    @Nonnull Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        if (!entityplayer.isCreative() && itemstack.isEmpty()) {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
        }
        entityplayer.setActiveHand(hand);
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

}
