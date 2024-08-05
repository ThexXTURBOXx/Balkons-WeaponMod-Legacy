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

    public abstract float getDestroySpeed(ItemStack stack, BlockState state);

    public abstract boolean canHarvestBlock(BlockState state);

    public abstract boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos,
                                      LivingEntity entity);

    public abstract boolean hurtEnemy(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getAttackDelay(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getKnockBack(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract int getEnchantmentValue();

    public abstract void addItemAttributeModifiers(Multimap<Attribute, AttributeModifier> attributes);

    public abstract UseAnim getUseAnimation(ItemStack stack);

    public abstract int getUseDuration(ItemStack stack);

    public abstract boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity);

    public abstract InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand);

    public abstract void onUsingTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration);

    public abstract void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int i);

    public abstract void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean flag);
}
