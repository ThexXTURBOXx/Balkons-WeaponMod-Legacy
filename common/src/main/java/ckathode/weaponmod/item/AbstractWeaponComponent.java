package ckathode.weaponmod.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractWeaponComponent {

    public Item item;
    IItemWeapon weapon;

    public AbstractWeaponComponent() {
        item = null;
        weapon = null;
    }

    void setItem(IItemWeapon itemweapon) {
        if (itemweapon instanceof Item i)
            item = i;
        weapon = itemweapon;
        onSetItem();
    }

    protected abstract void onSetItem();

    public abstract ItemAttributeModifiers.Builder setAttributes(ItemAttributeModifiers.Builder attributeBuilder);

    public abstract Properties setProperties(Properties properties);

    public abstract float getDamage();

    public abstract float getEntityDamage();

    public abstract boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos,
                                      LivingEntity entity);

    public abstract boolean hurtEnemy(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getAttackDelay(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getKnockBack(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract int getEnchantmentValue();

    @NotNull
    public abstract ItemUseAnimation getUseAnimation(ItemStack stack);

    public abstract int getUseDuration(ItemStack stack);

    public abstract boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity);

    @NotNull
    public abstract InteractionResult use(ItemStack itemstack, Level level, Player player, InteractionHand hand);

    public abstract void onUsingTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration);

    public abstract boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int i);

    public abstract void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean flag);

}
