package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ItemMelee extends SwordItem implements IItemWeapon {

    public static final String KATANA_WOOD_ID = "katana.wood";
    public static final ItemMelee KATANA_WOOD_ITEM = WMItemBuilder.createStandardKatana(Tiers.WOOD);

    public static final String KATANA_STONE_ID = "katana.stone";
    public static final ItemMelee KATANA_STONE_ITEM = WMItemBuilder.createStandardKatana(Tiers.STONE);

    public static final String KATANA_IRON_ID = "katana.iron";
    public static final ItemMelee KATANA_IRON_ITEM = WMItemBuilder.createStandardKatana(Tiers.IRON);

    public static final String KATANA_GOLD_ID = "katana.gold";
    public static final ItemMelee KATANA_GOLD_ITEM = WMItemBuilder.createStandardKatana(Tiers.GOLD);

    public static final String KATANA_DIAMOND_ID = "katana.diamond";
    public static final ItemMelee KATANA_DIAMOND_ITEM = WMItemBuilder.createStandardKatana(Tiers.DIAMOND);

    public static final String KATANA_NETHERITE_ID = "katana.netherite";
    public static final ItemMelee KATANA_NETHERITE_ITEM = WMItemBuilder.createStandardKatana(Tiers.NETHERITE);

    public final MeleeComponent meleeComponent;

    public ItemMelee(@NotNull MeleeComponent meleecomponent) {
        this(meleecomponent, WMItem.getBaseProperties(meleecomponent.weaponMaterial));
    }

    public ItemMelee(@NotNull MeleeComponent meleecomponent, Properties properties) {
        super(meleecomponent.getWeaponMaterial(), meleecomponent.setProperties(properties
                .attributes(meleecomponent.setAttributes(ItemAttributeModifiers.builder()).build())
                .arch$tab(CreativeModeTabs.COMBAT)));
        (meleeComponent = meleecomponent).setItem(this);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                             @NotNull LivingEntity attacker) {
        return meleeComponent.hurtEnemy(itemstack, entityliving, attacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull BlockState block, @NotNull BlockPos pos,
                             @NotNull LivingEntity entityliving) {
        return meleeComponent.mineBlock(itemstack, world, block, pos, entityliving);
    }

    @Override
    public int getEnchantmentValue() {
        return meleeComponent.getEnchantmentValue();
    }

    @NotNull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return meleeComponent.getUseAnimation(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return meleeComponent.getUseDuration(itemstack);
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                     @NotNull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world,
                                                  @NotNull Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        return meleeComponent.use(world, entityplayer, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        meleeComponent.onUsingTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull LivingEntity entityplayer, int i) {
        meleeComponent.releaseUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world,
                              @NotNull Entity entity, int i, boolean flag) {
        meleeComponent.inventoryTick(itemstack, world, entity, i, flag);
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return null;
    }

}
