package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompBoomerang extends MeleeComponent {

    public static final String WOOD_ID = "boomerang.wood";
    public static final ItemMelee WOOD_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.WOOD));

    public static final String STONE_ID = "boomerang.stone";
    public static final ItemMelee STONE_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.STONE));

    public static final String IRON_ID = "boomerang.iron";
    public static final ItemMelee IRON_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.IRON));

    public static final String GOLD_ID = "boomerang.gold";
    public static final ItemMelee GOLD_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.GOLD));

    public static final String DIAMOND_ID = "boomerang.diamond";
    public static final ItemMelee DIAMOND_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.DIAMOND));

    public static final String NETHERITE_ID = "boomerang.netherite";
    public static final ItemMelee NETHERITE_ITEM = new ItemMelee(new MeleeCompBoomerang(Tiers.NETHERITE));

    public MeleeCompBoomerang(Tier itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public void releaseUsing(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        if (entityliving instanceof Player entityplayer) {
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
            if (!world.isClientSide) {
                EntityBoomerang entityboomerang = new EntityBoomerang(world, entityplayer, itemstack.copy());
                entityboomerang.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                        0.0f, f, 5.0f);
                entityboomerang.setCritArrow(crit);
                entityboomerang.setKnockback(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK,
                        itemstack));
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                    entityboomerang.setSecondsOnFire(100);
                }
                world.addFreshEntity(entityboomerang);
            }
            world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.6f,
                    1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 1.0f));
            if (!entityplayer.isCreative()) {
                itemstack.shrink(1);
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        if (!entityplayer.isCreative() && itemstack.isEmpty()) {
            return new InteractionResultHolder<>(InteractionResult.FAIL, itemstack);
        }
        entityplayer.startUsingItem(hand);
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

}
