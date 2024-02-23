package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMelee extends ItemSword implements IItemWeapon {
    public final MeleeComponent meleeComponent;

    public ItemMelee(final String id, final MeleeComponent meleecomponent) {
        super((meleecomponent.weaponMaterial == null) ? Item.ToolMaterial.WOOD : meleecomponent.weaponMaterial);
        this.setRegistryName(id);
        this.setTranslationKey(id);
        (this.meleeComponent = meleecomponent).setItem(this);
        meleecomponent.setThisItemProperties();
        this.addPropertyOverride(new ResourceLocation("readyToThrow"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull final ItemStack stack, @Nullable final World world,
                               @Nullable final EntityLivingBase entity) {
                return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) ? 1.0f : 0.0f;
            }
        });
        this.addPropertyOverride(new ResourceLocation("state"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull final ItemStack stack, @Nullable final World world,
                               @Nullable final EntityLivingBase entity) {
                return MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;
            }
        });
        BalkonsWeaponMod.MOD_ITEMS.add(this);
    }

    @Override
    public float getAttackDamage() {
        return this.meleeComponent.getEntityDamageMaterialPart();
    }

    @Override
    public float getDestroySpeed(@Nonnull final ItemStack itemstack, @Nonnull final IBlockState block) {
        return this.meleeComponent.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(@Nonnull final IBlockState block) {
        return this.meleeComponent.canHarvestBlock(block);
    }

    @Override
    public boolean hitEntity(@Nonnull final ItemStack itemstack, @Nonnull final EntityLivingBase entityliving,
                             @Nonnull final EntityLivingBase attacker) {
        return this.meleeComponent.hitEntity(itemstack, entityliving, attacker);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                                    @Nonnull final IBlockState block, @Nonnull final BlockPos pos,
                                    @Nonnull final EntityLivingBase entityliving) {
        return this.meleeComponent.onBlockDestroyed(itemstack, world, block, pos, entityliving);
    }

    @Override
    public int getItemEnchantability() {
        return this.meleeComponent.getItemEnchantability();
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull final ItemStack itemstack) {
        return this.meleeComponent.getItemUseAction(itemstack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull final ItemStack itemstack) {
        return this.meleeComponent.getMaxItemUseDuration(itemstack);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull final ItemStack itemstack, @Nonnull final EntityPlayer player,
                                     @Nonnull final Entity entity) {
        return this.meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world,
                                                    @Nonnull final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        return this.meleeComponent.onItemRightClick(world, entityplayer, hand);
    }

    @Override
    public void onUsingTick(@Nonnull final ItemStack itemstack, @Nonnull final EntityLivingBase entityplayer,
                            final int count) {
        this.meleeComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                                     @Nonnull final EntityLivingBase entityplayer, final int i) {
        this.meleeComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void onUpdate(@Nonnull final ItemStack itemstack, @Nonnull final World world, @Nonnull final Entity entity
            , final int i, final boolean flag) {
        this.meleeComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull final EntityEquipmentSlot equipmentSlot
            , @Nonnull final ItemStack itemstack) {
        final Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            this.meleeComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    @Override
    public final UUID getUUIDDamage() {
        return ItemMelee.ATTACK_DAMAGE_MODIFIER;
    }

    @Override
    public final UUID getUUIDSpeed() {
        return ItemMelee.ATTACK_SPEED_MODIFIER;
    }

    @Override
    public final UUID getUUID() {
        return ItemMelee.WEAPON_MODIFIER;
    }

    @Override
    public final Random getItemRand() {
        return ItemMelee.itemRand;
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return this.meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return null;
    }
}
