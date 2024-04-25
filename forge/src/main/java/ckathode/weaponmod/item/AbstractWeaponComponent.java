package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

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

    public abstract boolean mineBlock(ItemStack p0, Level p1, BlockState p2, BlockPos p3, LivingEntity p4);

    public abstract boolean hurtEnemy(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract float getAttackDelay(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract float getKnockBack(ItemStack p0, LivingEntity p1, LivingEntity p2);

    public abstract int getEnchantmentValue();

    public abstract void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> p0);

    public abstract UseAnim getUseAnimation(ItemStack p0);

    public abstract int getUseDuration(ItemStack p0);

    public abstract boolean onLeftClickEntity(ItemStack p0, Player p1, Entity p2);

    public abstract InteractionResultHolder<ItemStack> use(Level p0, Player p1, InteractionHand p2);

    public abstract void onUsingTick(ItemStack p0, LivingEntity p1, int p2);

    public abstract void releaseUsing(ItemStack p0, Level p1, LivingEntity p2, int p3);

    public abstract void inventoryTick(ItemStack p0, Level p1, Entity p2, int p3, boolean p4);
}
