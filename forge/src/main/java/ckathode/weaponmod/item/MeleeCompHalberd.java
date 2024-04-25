package ckathode.weaponmod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem {
    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.hasTag() && itemstack.getTag().getBoolean("halb");
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        if (itemstack.getTag() == null) {
            itemstack.setTag(new CompoundTag());
        }
        itemstack.getTag().putBoolean("halb", flag);
    }

    public MeleeCompHalberd(Tier itemTier) {
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
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        setHalberdState(itemstack, !getHalberdState(itemstack));
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public float getExtendedReach(Level world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }
}
