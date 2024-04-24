package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

    public abstract float getDamage();

    public abstract float getEntityDamage();

    public abstract float getDestroySpeed(ItemStack p0, BlockState p1);

    public abstract boolean canHarvestBlock(ItemStack stack, BlockState state);

    public abstract boolean mineBlock(ItemStack p0, World p1, BlockState p2, BlockPos p3, LivingEntity p4);

    public abstract boolean hurtEnemy(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract float getAttackDelay(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract float getKnockBack(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract int getEnchantmentValue();

    public abstract void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> p0);

    public abstract UseAction getUseAnimation(ItemStack p0);

    public abstract int getUseDuration(ItemStack p0);

    public abstract boolean onLeftClickEntity(ItemStack p0, PlayerEntity p1, Entity p2);

    public abstract ActionResult<ItemStack> use(World p0, PlayerEntity p1, Hand p2);

    public abstract void onUsingTick(ItemStack p0, LivingEntity p1, int p2);

    public abstract void releaseUsing(ItemStack p0, World p1, LivingEntity p2, int p3);

    public abstract void inventoryTick(ItemStack p0, World p1, Entity p2, int p3, boolean p4);
}
