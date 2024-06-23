package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

    public abstract void setThisItemProperties();

    public abstract float getEntityDamageMaterialPart();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(ItemStack p0, Block p1);

    public abstract boolean canHarvestBlock(Block p0);

    public abstract boolean onBlockDestroyed(ItemStack p0, World p1, Block p2,
                                             int p3, int p4, int p5, EntityLivingBase p6);

    public abstract boolean hitEntity(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract float getAttackDelay(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract float getKnockBack(ItemStack p0, EntityLivingBase p1, EntityLivingBase p2);

    public abstract int getItemEnchantability();

    public abstract void addItemAttributeModifiers(Multimap<String, AttributeModifier> p0);

    public abstract EnumAction getItemUseAction(ItemStack p0);

    public abstract int getMaxItemUseDuration(ItemStack p0);

    public abstract boolean onLeftClickEntity(ItemStack p0, EntityPlayer p1, Entity p2);

    public abstract ItemStack onItemRightClick(World p0, EntityPlayer p1, ItemStack p2);

    public abstract void onUsingTick(ItemStack p0, EntityLivingBase p1, int p2);

    public abstract void onPlayerStoppedUsing(ItemStack p0, World p1, EntityLivingBase p2,
                                              int p3);

    public abstract void onUpdate(ItemStack p0, World p1, Entity p2, int p3,
                                  boolean p4);

    @SideOnly(Side.CLIENT)
    public abstract boolean shouldRotateAroundWhenRendering();
}
