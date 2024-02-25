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
        item = null;
        weapon = null;
    }

    void setItem(IItemWeapon itemweapon) {
        item = (Item) itemweapon;
        weapon = itemweapon;
        onSetItem();
    }

    protected abstract void onSetItem();

    public abstract Properties setProperties(Properties properties);

    public abstract float getEntityDamageMaterialPart();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(ItemStack p0, IBlockState p1);

    public abstract boolean canHarvestBlock(IBlockState p0);

    public abstract boolean onBlockDestroyed(ItemStack p0, World p1, IBlockState p2,
                                             BlockPos p3, EntityLivingBase p4);

    public abstract boolean hitEntity(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract float getAttackDelay(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract float getKnockBack(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract int getItemEnchantability();

    public abstract void addItemAttributeModifiers(Multimap<String, AttributeModifier> p0);

    public abstract EnumAction getUseAction(ItemStack p0);

    public abstract int getUseDuration(ItemStack p0);

    public abstract boolean onLeftClickEntity(ItemStack p0, EntityPlayer p1, Entity p2);

    public abstract ActionResult<ItemStack> onItemRightClick(World p0, EntityPlayer p1, EnumHand p2);

    public abstract void onUsingTick(ItemStack p0, EntityLivingBase p1, int p2);

    public abstract void onPlayerStoppedUsing(ItemStack p0, World p1, EntityLivingBase p2,
                                              int p3);

    public abstract void inventoryTick(ItemStack p0, World p1, Entity p2, int p3,
                                       boolean p4);
}
