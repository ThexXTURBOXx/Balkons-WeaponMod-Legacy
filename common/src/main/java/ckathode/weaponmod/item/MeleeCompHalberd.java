package ckathode.weaponmod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class MeleeCompHalberd extends MeleeComponent implements IExtendedReachItem {

    public static final String WOOD_ID = "halberd.wood";
    public static final ItemMelee WOOD_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.WOOD));

    public static final String STONE_ID = "halberd.stone";
    public static final ItemMelee STONE_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.STONE));

    public static final String IRON_ID = "halberd.iron";
    public static final ItemMelee IRON_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.IRON));

    public static final String GOLD_ID = "halberd.gold";
    public static final ItemMelee GOLD_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.GOLD));

    public static final String DIAMOND_ID = "halberd.diamond";
    public static final ItemMelee DIAMOND_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.DIAMOND));

    public static final String NETHERITE_ID = "halberd.netherite";
    public static final ItemMelee NETHERITE_ITEM = new ItemMelee(new MeleeCompHalberd(Tiers.NETHERITE));

    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.hasTag() && itemstack.getTag().getBoolean("halb");
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        if (!itemstack.hasTag()) {
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
