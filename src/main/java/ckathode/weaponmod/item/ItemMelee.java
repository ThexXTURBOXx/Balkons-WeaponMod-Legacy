package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMelee extends SwordItem implements IItemWeapon {
    public final MeleeComponent meleeComponent;

    public ItemMelee(String id, MeleeComponent meleecomponent) {
        this(id, meleecomponent, new Properties());
    }

    public ItemMelee(String id, MeleeComponent meleecomponent, Properties properties) {
        super((meleecomponent.weaponMaterial == null) ? ItemTier.WOOD : meleecomponent.weaponMaterial, 3,
                -2.4F, meleecomponent.setProperties(properties).tab(ItemGroup.TAB_COMBAT));
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        (meleeComponent = meleecomponent).setItem(this);
    }

    @Override
    public float getDamage() {
        return meleeComponent.getDamage();
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack itemstack, @Nonnull BlockState block) {
        return meleeComponent.getDestroySpeed(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return meleeComponent.canHarvestBlock(stack, state);
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityliving,
                             @Nonnull LivingEntity attacker) {
        return meleeComponent.hurtEnemy(itemstack, entityliving, attacker);
    }

    @Override
    public boolean mineBlock(@Nonnull ItemStack itemstack, @Nonnull World world,
                             @Nonnull BlockState block, @Nonnull BlockPos pos,
                             @Nonnull LivingEntity entityliving) {
        return meleeComponent.mineBlock(itemstack, world, block, pos, entityliving);
    }

    @Override
    public int getEnchantmentValue() {
        return meleeComponent.getEnchantmentValue();
    }

    @Nonnull
    @Override
    public UseAction getUseAnimation(@Nonnull ItemStack itemstack) {
        return meleeComponent.getUseAnimation(itemstack);
    }

    @Override
    public int getUseDuration(@Nonnull ItemStack itemstack) {
        return meleeComponent.getUseDuration(itemstack);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack itemstack, @Nonnull PlayerEntity player,
                                     @Nonnull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(@Nonnull World world,
                                       @Nonnull PlayerEntity entityplayer,
                                       @Nonnull Hand hand) {
        return meleeComponent.use(world, entityplayer, hand);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack itemstack, @Nonnull LivingEntity entityplayer,
                            int count) {
        meleeComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void releaseUsing(@Nonnull ItemStack itemstack, @Nonnull World world,
                             @Nonnull LivingEntity entityplayer, int i) {
        meleeComponent.releaseUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack itemstack, @Nonnull World world,
                              @Nonnull Entity entity, int i, boolean flag) {
        meleeComponent.inventoryTick(itemstack, world, entity, i, flag);
    }

    @Nonnull
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlotType equipmentSlot,
                                                                        @Nonnull ItemStack itemstack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EquipmentSlotType.MAINHAND) {
            meleeComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    @Override
    public UUID getUUIDDamage() {
        return ItemMelee.BASE_ATTACK_DAMAGE_UUID;
    }

    @Override
    public UUID getUUIDSpeed() {
        return ItemMelee.BASE_ATTACK_SPEED_UUID;
    }

    @Override
    public UUID getUUID() {
        return ItemMelee.WEAPON_MODIFIER;
    }

    @Override
    public Random getItemRand() {
        return random;
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return null;
    }
}
