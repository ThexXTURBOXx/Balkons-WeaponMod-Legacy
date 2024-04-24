package ckathode.weaponmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem {
    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.hasTag() && itemstack.getTag().getBoolean("halb");
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundNBT());
        }
        itemstack.getTag().putBoolean("halb", flag);
    }

    public MeleeCompHalberd(IItemTier itemTier) {
        super(MeleeSpecs.HALBERD, itemTier);
    }

    @Override
    public float getAttackDelay(ItemStack itemstack, LivingEntity entityliving,
                                LivingEntity attacker) {
        float ad = super.getAttackDelay(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? 0.0f : ad;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        float kb = super.getKnockBack(itemstack, entityliving, attacker);
        return getHalberdState(itemstack) ? (kb / 2.0f) : kb;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        setHalberdState(itemstack, !getHalberdState(itemstack));
        return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
    }

    @Override
    public float getExtendedReach(World world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }
}
