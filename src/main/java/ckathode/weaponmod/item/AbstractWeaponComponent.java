package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractWeaponComponent {
    public Item item;
    IItemWeapon weapon;

    public AbstractWeaponComponent() {
        this.item = null;
        this.weapon = null;
    }

    final void setItem(final IItemWeapon itemweapon) {
        this.item = (Item) itemweapon;
        this.weapon = itemweapon;
        this.onSetItem();
    }

    protected abstract void onSetItem();

    public abstract Properties setProperties(Properties properties);

    public abstract float getEntityDamageMaterialPart();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(final ItemStack p0, final IBlockState p1);

    public abstract boolean canHarvestBlock(final IBlockState p0);

    public abstract boolean onBlockDestroyed(final ItemStack p0, final World p1, final IBlockState p2,
                                             final BlockPos p3, final EntityLivingBase p4);

    public abstract boolean hitEntity(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);

    public abstract float getAttackDelay(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);

    public abstract float getKnockBack(final ItemStack p0, final EntityLivingBase p1, final EntityLivingBase p2);

    public abstract int getItemEnchantability();

    public abstract void addItemAttributeModifiers(final Multimap<String, AttributeModifier> p0);

    public abstract EnumAction getUseAction(final ItemStack p0);

    public abstract int getUseDuration(final ItemStack p0);

    public abstract boolean onLeftClickEntity(final ItemStack p0, final EntityPlayer p1, final Entity p2);

    public abstract ActionResult<ItemStack> onItemRightClick(final World p0, final EntityPlayer p1, final EnumHand p2);

    public abstract void onUsingTick(final ItemStack p0, final EntityLivingBase p1, final int p2);

    public abstract void onPlayerStoppedUsing(final ItemStack p0, final World p1, final EntityLivingBase p2,
                                              final int p3);

    public abstract void inventoryTick(final ItemStack p0, final World p1, final Entity p2, final int p3,
                                       final boolean p4);
}
