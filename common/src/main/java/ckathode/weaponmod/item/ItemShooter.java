package ckathode.weaponmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemShooter extends BowItem implements IItemWeapon {

    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;

    public ItemShooter(RangedComponent rangedcomponent, MeleeComponent meleecomponent, @NotNull ResourceLocation id) {
        this(rangedcomponent, meleecomponent, WMItem.getBaseProperties(meleecomponent.weaponMaterial, id));
    }

    public ItemShooter(RangedComponent rangedcomponent, MeleeComponent meleecomponent,
                       Properties properties) {
        super(rangedcomponent.setProperties(meleecomponent.setProperties(properties))
                .attributes(rangedcomponent.setAttributes(meleecomponent.setAttributes(ItemAttributeModifiers.builder())).build())
                .enchantable(meleecomponent.getEnchantmentValue())
                .arch$tab(CreativeModeTabs.COMBAT));
        rangedComponent = rangedcomponent;
        meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
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

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                     @NotNull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity) && rangedComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @NotNull
    @Override
    public ItemUseAnimation getUseAnimation(@NotNull ItemStack itemstack) {
        return rangedComponent.getUseAnimation(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack, LivingEntity livingEntity) {
        return rangedComponent.getUseDuration(itemstack);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull Level world, @NotNull Player entityplayer, @NotNull InteractionHand hand) {
        return rangedComponent.use(world, entityplayer, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        rangedComponent.onUsingTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public boolean releaseUsing(@NotNull ItemStack itemstack, @NotNull Level world,
                                @NotNull LivingEntity entityplayer, int i) {
        return rangedComponent.releaseUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel serverLevel, Entity entity,
                              @Nullable EquipmentSlot equipmentSlot) {
        meleeComponent.inventoryTick(itemStack, serverLevel, entity, equipmentSlot);
        rangedComponent.inventoryTick(itemStack, serverLevel, entity, equipmentSlot);
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return rangedComponent;
    }

}
