package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MeleeCompNone extends MeleeComponent {
    public MeleeCompNone(final IItemTier itemTier) {
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
    public float getBlockDamage(final ItemStack itemstack, final IBlockState block) {
        return 1.0f;
    }

    @Override
    public boolean canHarvestBlock(final IBlockState block) {
        return false;
    }

    @Override
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block,
                                    final BlockPos pos, final EntityLivingBase entityliving) {
        return true;
    }

    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving,
                             final EntityLivingBase attacker) {
        return true;
    }

    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving,
                              final EntityLivingBase attacker) {
        return 0.0f;
    }

    @Override
    public int getItemEnchantability() {
        return 1;
    }

    @Override
    public void addItemAttributeModifiers(final Multimap<String, AttributeModifier> multimap) {
    }

    @Override
    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        return false;
    }

    @Override
    public EnumAction getUseAction(final ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer,
                                                    final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        return new ActionResult<>(EnumActionResult.PASS, itemstack);
    }
}
