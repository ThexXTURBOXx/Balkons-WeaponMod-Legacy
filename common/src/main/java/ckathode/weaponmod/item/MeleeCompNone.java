package ckathode.weaponmod.item;

import java.util.Collections;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class MeleeCompNone extends MeleeComponent {

    public MeleeCompNone(Tier itemTier) {
        super(MeleeSpecs.NONE, itemTier);
    }

    @Override
    public float getDamage() {
        return 0.0f;
    }

    @Override
    public float getEntityDamage() {
        return 1.0f;
    }

    @NotNull
    @Override
    public Tool getToolComponent() {
        return new Tool(Collections.emptyList(), 1, 1);
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, Level world, BlockState block,
                             BlockPos pos, LivingEntity entityliving) {
        return true;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entityliving,
                             LivingEntity attacker) {
        return true;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        return 0.0f;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public ItemAttributeModifiers.Builder setAttributes(ItemAttributeModifiers.Builder attributeBuilder) {
        return ItemAttributeModifiers.builder();
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, Player player, Entity entity) {
        return false;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.NONE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entityplayer,
                                                  InteractionHand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

}
