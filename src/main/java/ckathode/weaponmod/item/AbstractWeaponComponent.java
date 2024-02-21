package ckathode.weaponmod.item;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public abstract class AbstractWeaponComponent
{
    public Item item;
    IItemWeapon weapon;
    
    public AbstractWeaponComponent() {
        this.item = null;
        this.weapon = null;
    }
    
    final void setItem(final IItemWeapon itemweapon) {
        this.item = (Item)itemweapon;
        this.weapon = itemweapon;
        this.onSetItem();
    }
    
    protected abstract void onSetItem();
    
    public abstract void setThisItemProperties();
    
    public abstract float getEntityDamageMaterialPart();
    
    public abstract float getEntityDamage();
    
    public abstract float getBlockDamage(final ItemStack p0, final IBlockState p1);
    
    public abstract boolean canHarvestBlock(final IBlockState p0);
    
    public abstract boolean onBlockDestroyed(final ItemStack p0, final World p1, final IBlockState p2, final BlockPos p3, final EntityLivingBase p4);
    
    public abstract boolean hitEntity(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);
    
    public abstract float getAttackDelay(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);
    
    public abstract float getKnockBack(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);
    
    public abstract int getItemEnchantability();
    
    public abstract void addItemAttributeModifiers(final Multimap<String, AttributeModifier> p0);
    
    public abstract EnumAction getItemUseAction(final ItemStack p0);
    
    public abstract int getMaxItemUseDuration(final ItemStack p0);
    
    public abstract boolean onLeftClickEntity(final ItemStack p0, final EntityPlayer p1, final Entity p2);
    
    public abstract ActionResult<ItemStack> onItemRightClick(final World p0, final EntityPlayer p1, final EnumHand p2);
    
    public abstract void onUsingTick(final ItemStack p0, final EntityLivingBase p1, final int p2);
    
    public abstract void onPlayerStoppedUsing(final ItemStack p0, final World p1, final EntityLivingBase p2, final int p3);
    
    public abstract void onUpdate(final ItemStack p0, final World p1, final Entity p2, final int p3, final boolean p4);
}
