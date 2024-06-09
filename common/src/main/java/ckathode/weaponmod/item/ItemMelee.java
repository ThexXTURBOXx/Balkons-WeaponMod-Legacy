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
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ItemMelee extends SwordItem implements IItemWeapon {

    public static final String KATANA_WOOD_ID = "katana.wood";
    public static final ItemMelee KATANA_WOOD_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.WOOD));

    public static final String KATANA_STONE_ID = "katana.stone";
    public static final ItemMelee KATANA_STONE_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.STONE));

    public static final String KATANA_IRON_ID = "katana.iron";
    public static final ItemMelee KATANA_IRON_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.IRON));

    public static final String KATANA_GOLD_ID = "katana.gold";
    public static final ItemMelee KATANA_GOLD_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.GOLD));

    public static final String KATANA_DIAMOND_ID = "katana.diamond";
    public static final ItemMelee KATANA_DIAMOND_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.DIAMOND));

    public static final String KATANA_NETHERITE_ID = "katana.netherite";
    public static final ItemMelee KATANA_NETHERITE_ITEM =
            new ItemMelee(new MeleeComponent(MeleeComponent.MeleeSpecs.KATANA, Tiers.NETHERITE));

    public final MeleeComponent meleeComponent;

    public ItemMelee(@NotNull MeleeComponent meleecomponent) {
        this(meleecomponent, WMItem.getBaseProperties(meleecomponent.weaponMaterial));
    }

    public ItemMelee(@NotNull MeleeComponent meleecomponent, Properties properties) {
        super((meleecomponent.weaponMaterial == null) ? Tiers.WOOD : meleecomponent.weaponMaterial, 3,
                -2.4F, meleecomponent.setProperties(properties).tab(CreativeModeTab.TAB_COMBAT));
        (meleeComponent = meleecomponent).setItem(this);
    }

    @Override
    public float getDamage() {
        return meleeComponent.getDamage();
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack itemstack, @NotNull BlockState block) {
        return meleeComponent.getDestroySpeed(itemstack, block);
    }

    @Override
    public boolean isCorrectToolForDrops(BlockState block) {
        return meleeComponent.canHarvestBlock(block);
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

    @NotNull
    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            meleeComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
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
        return null;
    }

}
