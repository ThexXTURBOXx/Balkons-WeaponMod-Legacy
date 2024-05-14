package ckathode.weaponmod.item;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.entity.projectile.EntitySpear;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {

    public static final String WOOD_ID = "spear.wood";
    public static final ItemMelee WOOD_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.WOOD));

    public static final String STONE_ID = "spear.stone";
    public static final ItemMelee STONE_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.STONE));

    public static final String IRON_ID = "spear.iron";
    public static final ItemMelee IRON_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.IRON));

    public static final String GOLD_ID = "spear.gold";
    public static final ItemMelee GOLD_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.GOLD));

    public static final String DIAMOND_ID = "spear.diamond";
    public static final ItemMelee DIAMOND_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.DIAMOND));

    public static final String NETHERITE_ID = "spear.netherite";
    public static final ItemMelee NETHERITE_ITEM = new ItemMelee(new MeleeCompSpear(Tiers.NETHERITE));

    public MeleeCompSpear(Tier itemTier) {
        super(MeleeSpecs.SPEAR, itemTier);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!WeaponModConfig.get().canThrowSpear) {
            return super.use(world, entityplayer, hand);
        }
        if (!world.isClientSide) {
            EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack.copy());
            entityspear.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.8f, 3.0f);
            entityspear.setKnockback(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, itemstack));
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, itemstack) > 0) {
                entityspear.setSecondsOnFire(100);
            }
            world.addFreshEntity(entityspear);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack = itemstack.copy();
            itemstack.shrink(1);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return WeaponModConfig.get().canThrowSpear ? UseAnim.NONE : super.getUseAnimation(itemstack);
    }

    @Override
    public float getExtendedReach(Level world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }

}
