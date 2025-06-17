package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompBoomerang extends MeleeComponent {

    public static final String WOOD_ID = "boomerang.wood";
    public static final ItemMelee WOOD_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.WOOD, BalkonsWeaponMod.id(WOOD_ID));

    public static final String STONE_ID = "boomerang.stone";
    public static final ItemMelee STONE_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.STONE, BalkonsWeaponMod.id(STONE_ID));

    public static final String IRON_ID = "boomerang.iron";
    public static final ItemMelee IRON_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.IRON, BalkonsWeaponMod.id(IRON_ID));

    public static final String GOLD_ID = "boomerang.gold";
    public static final ItemMelee GOLD_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.GOLD, BalkonsWeaponMod.id(GOLD_ID));

    public static final String DIAMOND_ID = "boomerang.diamond";
    public static final ItemMelee DIAMOND_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(DIAMOND_ID));

    public static final String NETHERITE_ID = "boomerang.netherite";
    public static final ItemMelee NETHERITE_ITEM =
            WMItemBuilder.createStandardBoomerang(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(NETHERITE_ID));

    public MeleeCompBoomerang(ToolMaterial itemTier) {
        super(MeleeSpecs.BOOMERANG, itemTier);
    }

    @Override
    public boolean releaseUsing(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        if (entityliving instanceof Player entityplayer) {
            if (itemstack.isEmpty()) {
                return false;
            }
            int j = getUseDuration(itemstack) - i;
            float f = j / 20.0f;
            f = (f * f + f * 2.0f) / 3.0f;
            if (f < 0.1f) {
                return false;
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
                Holder<Enchantment> fireAspect = entityplayer.registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT).orElse(null);
                if (fireAspect != null && EnchantmentHelper.getItemEnchantmentLevel(fireAspect, itemstack) > 0) {
                    entityboomerang.igniteForSeconds(100);
                }
                world.addFreshEntity(entityboomerang);
            }
            world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                    SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 0.6f,
                    1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 1.0f));
            if (!entityplayer.isCreative()) {
                WMItem.decrStackSize(itemstack, 1, entityliving);
            }
        }
        return true;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player entityplayer,
                                          InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.FAIL;
        }
        if (!entityplayer.isCreative() && itemstack.isEmpty()) {
            return InteractionResult.FAIL;
        }
        entityplayer.startUsingItem(hand);
        return InteractionResult.SUCCESS;
    }

}
