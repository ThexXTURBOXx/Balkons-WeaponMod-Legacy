package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
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
    public static final ItemMelee WOOD_ITEM = WMItemBuilder.createStandardHalberd(Tiers.WOOD);

    public static final String STONE_ID = "halberd.stone";
    public static final ItemMelee STONE_ITEM = WMItemBuilder.createStandardHalberd(Tiers.STONE);

    public static final String IRON_ID = "halberd.iron";
    public static final ItemMelee IRON_ITEM = WMItemBuilder.createStandardHalberd(Tiers.IRON);

    public static final String GOLD_ID = "halberd.gold";
    public static final ItemMelee GOLD_ITEM = WMItemBuilder.createStandardHalberd(Tiers.GOLD);

    public static final String DIAMOND_ID = "halberd.diamond";
    public static final ItemMelee DIAMOND_ITEM = WMItemBuilder.createStandardHalberd(Tiers.DIAMOND);

    public static final String NETHERITE_ID = "halberd.netherite";
    public static final ItemMelee NETHERITE_ITEM = WMItemBuilder.createStandardHalberd(Tiers.NETHERITE);

    public static final String HALBERD_STATE_TYPE_ID = "halb";
    public static final DataComponentType<Boolean> HALBERD_STATE_TYPE =
            DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build();

    public static boolean getHalberdState(ItemStack itemstack) {
        return itemstack.has(HALBERD_STATE_TYPE) && itemstack.get(HALBERD_STATE_TYPE);
    }

    public static void setHalberdState(ItemStack itemstack, boolean flag) {
        itemstack.set(HALBERD_STATE_TYPE, flag);
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
