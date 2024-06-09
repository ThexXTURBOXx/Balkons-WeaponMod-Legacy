package ckathode.weaponmod.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ItemShooter extends BowItem implements IItemWeapon {

    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;

    public ItemShooter(RangedComponent rangedcomponent, MeleeComponent meleecomponent) {
        this(rangedcomponent, meleecomponent, WMItem.getBaseProperties(meleecomponent.weaponMaterial));
    }

    public ItemShooter(RangedComponent rangedcomponent, MeleeComponent meleecomponent,
                       Properties properties) {
        super(rangedcomponent.setProperties(properties).tab(CreativeModeTab.TAB_COMBAT));
        rangedComponent = rangedcomponent;
        meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemstack, @NotNull BlockState block) {
        return meleeComponent.getDestroySpeed(itemstack, block);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState state) {
        return meleeComponent.canHarvestBlock(state);
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

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            meleeComponent.addItemAttributeModifiers(multimap);
            rangedComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    @Override
    public boolean onLeftClickEntity(@NotNull ItemStack itemstack, @NotNull Player player,
                                     @NotNull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity) && rangedComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @NotNull
    @Override
    public UseAnim getUseAnimation(@NotNull ItemStack itemstack) {
        return rangedComponent.getUseAnimation(itemstack);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return rangedComponent.getUseDuration(itemstack);
    }

    @NotNull
    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level world,
                                                  @NotNull Player entityplayer,
                                                  @NotNull InteractionHand hand) {
        return rangedComponent.use(world, entityplayer, hand);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        rangedComponent.onUsingTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack itemstack, @NotNull Level world,
                             @NotNull LivingEntity entityplayer, int i) {
        rangedComponent.releaseUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world,
                              @NotNull Entity entity, int i, boolean flag) {
        meleeComponent.inventoryTick(itemstack, world, entity, i, flag);
        rangedComponent.inventoryTick(itemstack, world, entity, i, flag);
    }

    @Override
    public Random getItemRand() {
        return random;
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
