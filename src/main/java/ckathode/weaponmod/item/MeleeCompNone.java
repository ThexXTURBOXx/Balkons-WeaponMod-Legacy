package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
    public float getEntityDamageMaterialPart() {
        return 0.0f;
    }

    @Override
    public float getEntityDamage() {
        return 1.0f;
    }

    @Override
    public float getBlockDamage(ItemStack itemstack, BlockState block) {
        return 1.0f;
    }

    @Override
    public boolean canHarvestBlock(BlockState block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, BlockState block,
                                    BlockPos pos, LivingEntity entityliving) {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, LivingEntity entityliving,
                             LivingEntity attacker) {
        return true;
    }

    @Override
    public float getKnockBack(ItemStack itemstack, LivingEntity entityliving,
                              LivingEntity attacker) {
        return 0.0f;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addItemAttributeModifiers(Multimap<String, AttributeModifier> multimap) {
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, PlayerEntity player, Entity entity) {
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entityplayer,
                                                    Hand hand) {
        ItemStack itemstack = entityplayer.getHeldItem(hand);
        return new ActionResult<>(ActionResultType.PASS, itemstack);
    }
}
