package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {

    public static final String WOOD_ID = "spear.wood";
    public static final ItemMelee WOOD_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.WOOD, BalkonsWeaponMod.id(WOOD_ID));

    public static final String STONE_ID = "spear.stone";
    public static final ItemMelee STONE_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.STONE, BalkonsWeaponMod.id(STONE_ID));

    public static final String IRON_ID = "spear.iron";
    public static final ItemMelee IRON_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.IRON, BalkonsWeaponMod.id(IRON_ID));

    public static final String GOLD_ID = "spear.gold";
    public static final ItemMelee GOLD_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.GOLD, BalkonsWeaponMod.id(GOLD_ID));

    public static final String DIAMOND_ID = "spear.diamond";
    public static final ItemMelee DIAMOND_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(DIAMOND_ID));

    public static final String NETHERITE_ID = "spear.netherite";
    public static final ItemMelee NETHERITE_ITEM =
            WMItemBuilder.createStandardSpear(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(NETHERITE_ID));

    public MeleeCompSpear(ToolMaterial itemTier) {
        super(MeleeSpecs.SPEAR, itemTier);
    }

    @Override
    public @NotNull InteractionResult use(ItemStack itemstack, Level world,
                                          Player entityplayer, InteractionHand hand) {
        if (!WeaponModConfig.get().canThrowSpear) {
            return super.use(itemstack, world, entityplayer, hand);
        }
        if (!world.isClientSide()) {
            EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack.copy());
            entityspear.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 0.8f, 3.0f);
            applyProjectileEnchantments(entityspear, itemstack);
            world.addFreshEntity(entityspear);
        }
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!entityplayer.isCreative()) {
            itemstack.shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return WeaponModConfig.get().canThrowSpear ? ItemUseAnimation.NONE : super.getUseAnimation(itemstack);
    }

    @Override
    public float getExtendedReach(Level world, LivingEntity living, ItemStack itemstack) {
        return 4.0f;
    }

}
