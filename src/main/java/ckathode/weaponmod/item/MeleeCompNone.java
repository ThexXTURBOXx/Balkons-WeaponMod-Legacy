package ckathode.weaponmod.item;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class MeleeCompNone extends MeleeComponent
{
    public MeleeCompNone(final Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.NONE, toolmaterial);
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
    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block, final BlockPos pos, final EntityLivingBase entityliving) {
        return true;
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return true;
    }
    
    @Override
    public float getKnockBack(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
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
    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return EnumAction.NONE;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        final ItemStack itemstack = entityplayer.getHeldItem(hand);
        return (ActionResult<ItemStack>)new ActionResult(EnumActionResult.PASS, itemstack);
    }
}
