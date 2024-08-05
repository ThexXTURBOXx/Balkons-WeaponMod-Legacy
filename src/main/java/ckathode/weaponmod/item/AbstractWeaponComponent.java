package ckathode.weaponmod.item;

import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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

    public abstract float getEntityDamageMaterialPart();

    public abstract float getEntityDamage();

    public abstract float getBlockDamage(ItemStack stack, BlockState state);

    public abstract boolean canHarvestBlock(BlockState state);

    public abstract boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos,
                                             LivingEntity entity);

    public abstract boolean hitEntity(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getAttackDelay(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract float getKnockBack(ItemStack stack, LivingEntity victim, LivingEntity attacker);

    public abstract int getItemEnchantability();

    public abstract void addItemAttributeModifiers(Multimap<String, AttributeModifier> attributes);

    public abstract UseAction getUseAction(ItemStack stack);

    public abstract int getUseDuration(ItemStack stack);

    public abstract boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity);

    public abstract ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand);

    public abstract void onUsingTick(ItemStack stack, LivingEntity entity, int count);

    public abstract void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int i);

    public abstract void inventoryTick(ItemStack stack, World world, Entity entity, int i, boolean flag);
}
