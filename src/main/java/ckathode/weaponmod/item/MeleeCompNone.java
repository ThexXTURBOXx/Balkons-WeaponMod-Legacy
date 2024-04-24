package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MeleeCompNone extends MeleeComponent {
    public MeleeCompNone(IItemTier itemTier) {
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

    @Override
    public float getDestroySpeed(ItemStack itemstack, BlockState block) {
        return 1.0f;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState block) {
        return false;
    }

    @Override
    public boolean mineBlock(ItemStack itemstack, World world, BlockState block,
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
    public void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> multimap) {
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        return false;
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemstack) {
        return UseAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity entityplayer,
                                       Hand hand) {
        ItemStack itemstack = entityplayer.getItemInHand(hand);
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }
}
