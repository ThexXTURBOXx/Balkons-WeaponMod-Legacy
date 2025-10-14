package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemMelee extends Item implements IItemWeapon {

    public static final String KATANA_WOOD_ID = "katana.wood";
    public static final ItemMelee KATANA_WOOD_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.WOOD, BalkonsWeaponMod.id(KATANA_WOOD_ID));

    public static final String KATANA_STONE_ID = "katana.stone";
    public static final ItemMelee KATANA_STONE_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.STONE, BalkonsWeaponMod.id(KATANA_STONE_ID));

    public static final String KATANA_COPPER_ID = "katana.copper";
    public static final ItemMelee KATANA_COPPER_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.COPPER, BalkonsWeaponMod.id(KATANA_COPPER_ID));

    public static final String KATANA_IRON_ID = "katana.iron";
    public static final ItemMelee KATANA_IRON_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.IRON, BalkonsWeaponMod.id(KATANA_IRON_ID));

    public static final String KATANA_GOLD_ID = "katana.gold";
    public static final ItemMelee KATANA_GOLD_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.GOLD, BalkonsWeaponMod.id(KATANA_GOLD_ID));

    public static final String KATANA_DIAMOND_ID = "katana.diamond";
    public static final ItemMelee KATANA_DIAMOND_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.DIAMOND, BalkonsWeaponMod.id(KATANA_DIAMOND_ID));

    public static final String KATANA_NETHERITE_ID = "katana.netherite";
    public static final ItemMelee KATANA_NETHERITE_ITEM =
            WMItemBuilder.createStandardKatana(ToolMaterial.NETHERITE, BalkonsWeaponMod.id(KATANA_NETHERITE_ID));

    public final MeleeComponent meleeComponent;

    public ItemMelee(@NotNull MeleeComponent meleecomponent, @NotNull ResourceLocation id) {
        this(meleecomponent, WMItem.getBaseProperties(meleecomponent.weaponMaterial, id));
    }

    public ItemMelee(@NotNull MeleeComponent meleecomponent, Properties properties) {
        super(meleecomponent.setProperties(properties
                .attributes(meleecomponent.setAttributes(ItemAttributeModifiers.builder()).build())
                .enchantable(meleecomponent.getEnchantmentValue())
                .arch$tab(CreativeModeTabs.COMBAT)));
        (meleeComponent = meleecomponent).setItem(this);
    }

    @Override
    public void postHurtEnemy(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        itemStack.hurtAndBreak(1, livingEntity2, EquipmentSlot.MAINHAND);
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                          @NotNull LivingEntity attacker) {
        meleeComponent.hurtEnemy(itemstack, entityliving, attacker);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull BlockState block, @NotNull BlockPos pos,
                             @NotNull LivingEntity entityliving) {
        return meleeComponent.mineBlock(itemstack, world, block, pos, entityliving);
    }

    @NotNull
    @Override
    public ItemUseAnimation getUseAnimation(@NotNull ItemStack itemstack) {
        return meleeComponent.getUseAnimation(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack, LivingEntity livingEntity) {
        return meleeComponent.getUseDuration(itemstack);
    }

    @Override
    public boolean leftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player, @NotNull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull Level world, @NotNull Player entityplayer, @NotNull InteractionHand hand) {
        return meleeComponent.use(entityplayer.getItemInHand(hand), world, entityplayer, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        meleeComponent.onUsingTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public boolean releaseUsing(@NotNull ItemStack itemstack, @NotNull Level world,
                                @NotNull LivingEntity entityplayer, int i) {
        return meleeComponent.releaseUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity,
                              @Nullable EquipmentSlot equipmentSlot) {
        meleeComponent.inventoryTick(itemStack, serverLevel, entity, equipmentSlot);
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
