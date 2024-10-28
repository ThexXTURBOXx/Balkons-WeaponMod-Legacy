package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompKnife extends MeleeComponent {

    public static final String WOOD_ID = "knife.wood";
    public static final ItemMelee WOOD_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.WOOD, BalkonsWeaponMod.id(WOOD_ID));

    public static final String STONE_ID = "knife.stone";
    public static final ItemMelee STONE_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.STONE, BalkonsWeaponMod.id(STONE_ID));

    public static final String IRON_ID = "knife.iron";
    public static final ItemMelee IRON_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.IRON, BalkonsWeaponMod.id(IRON_ID));

    public static final String GOLD_ID = "knife.gold";
    public static final ItemMelee GOLD_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.GOLD, BalkonsWeaponMod.id(GOLD_ID));

    public static final String DIAMOND_ID = "knife.diamond";
    public static final ItemMelee DIAMOND_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(DIAMOND_ID));

    public static final String NETHERITE_ID = "knife.netherite";
    public static final ItemMelee NETHERITE_ITEM =
            WMItemBuilder.createStandardKnife(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(NETHERITE_ID));

    public MeleeCompKnife(ToolMaterial itemTier) {
        super(MeleeSpecs.KNIFE, itemTier);
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player entityplayer, InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        if (!WeaponModConfig.get().canThrowKnife) {
            return super.use(world, entityplayer, hand);
        }
        if (!world.isClientSide) {
            EntityKnife entityknife = new EntityKnife(world, entityplayer, itemstack.copy());
            entityknife.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.8f, 3.0f);
            Holder<Enchantment> fireAspect = entityplayer.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                    .getOrThrow(Enchantments.FIRE_ASPECT);
            if (EnchantmentHelper.getItemEnchantmentLevel(fireAspect, itemstack) > 0) {
                entityknife.igniteForSeconds(100);
            }
            world.addFreshEntity(entityknife);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack = itemstack.copy();
            itemstack.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return WeaponModConfig.get().canThrowKnife ? ItemUseAnimation.NONE : super.getUseAnimation(itemstack);
    }

}
