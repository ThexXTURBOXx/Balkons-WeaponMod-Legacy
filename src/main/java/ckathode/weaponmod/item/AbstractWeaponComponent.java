package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractWeaponComponent {

    public Item item;
    IItemWeapon weapon;

    public AbstractWeaponComponent() {
        item = null;
        weapon = null;
    }

    void setItem(IItemWeapon itemweapon) {
        if (itemweapon instanceof Item)
            item = (Item) itemweapon;
        weapon = itemweapon;
        onSetItem();
    }

    protected abstract void onSetItem();

    public boolean isRepairableInCraft() {
        return true;
    }

    public boolean isRepairableInAnvil(ItemStack toRepair, ItemStack repairMaterial) {
        IItemTier weaponMaterial = getWeaponMaterial();
        if (weaponMaterial == null) return false;
        Ingredient mat = weaponMaterial.getRepairMaterial();
        return mat != null && mat.test(repairMaterial);
    }

    public abstract Properties setProperties(Properties properties);

    @Nullable
    public IItemTier getWeaponMaterial() {
        return null;
    }

    public abstract float getEntityDamageMaterialPart();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(ItemStack stack, IBlockState state);

    public abstract boolean canHarvestBlock(IBlockState state);

    public abstract boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
                                             EntityLivingBase entity);

    public abstract boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker);

    public abstract float getAttackDelay(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker);

    public abstract float getKnockBack(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker);

    public boolean canApplyEnchantment(ItemStack stack, Enchantment enchantment) {
        return enchantment.type.canEnchantItem(stack.getItem());
    }

    public abstract int getItemEnchantability();

    public abstract void addItemAttributeModifiers(Multimap<String, AttributeModifier> attributes);

    public abstract EnumAction getUseAction(ItemStack stack);

    public abstract int getUseDuration(ItemStack stack);

    public abstract boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity);

    public abstract ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player,
                                                             EnumHand hand);

    public abstract void onUsingTick(ItemStack stack, EntityLivingBase entity, int count);

    public abstract void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int i);

    public abstract void inventoryTick(ItemStack stack, World world, Entity entity, int i, boolean flag);

}
