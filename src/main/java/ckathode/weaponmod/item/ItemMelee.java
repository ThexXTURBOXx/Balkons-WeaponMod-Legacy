package ckathode.weaponmod.item;

import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import ckathode.weaponmod.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import java.util.*;

public class ItemMelee extends ItemSword implements IItemWeapon
{
    public final MeleeComponent meleeComponent;

    public ItemMelee(final String id, final MeleeComponent meleecomponent) {
        super((meleecomponent.weaponMaterial == null) ? Item.ToolMaterial.WOOD : meleecomponent.weaponMaterial);
        this.setRegistryName(id);
        this.setTranslationKey(id);
        (this.meleeComponent = meleecomponent).setItem(this);
        meleecomponent.setThisItemProperties();
        this.addPropertyOverride(new ResourceLocation("readyToThrow"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
                return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) ? 1.0f : 0.0f;
            }
        });
        this.addPropertyOverride(new ResourceLocation("state"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
                return MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;
            }
        });
        BalkonsWeaponMod.MOD_ITEMS.add(this);
    }

    public float getAttackDamage() {
        return this.meleeComponent.getEntityDamageMaterialPart();
    }

    public float getDestroySpeed(final ItemStack itemstack, final IBlockState block) {
        return this.meleeComponent.getBlockDamage(itemstack, block);
    }

    public boolean canHarvestBlock(final IBlockState block) {
        return this.meleeComponent.canHarvestBlock(block);
    }

    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase attacker) {
        return this.meleeComponent.hitEntity(itemstack, entityliving, attacker);
    }

    public boolean onBlockDestroyed(final ItemStack itemstack, final World world, final IBlockState block, final BlockPos pos, final EntityLivingBase entityliving) {
        return this.meleeComponent.onBlockDestroyed(itemstack, world, block, pos, entityliving);
    }

    public int getItemEnchantability() {
        return this.meleeComponent.getItemEnchantability();
    }

    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return this.meleeComponent.getItemUseAction(itemstack);
    }

    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return this.meleeComponent.getMaxItemUseDuration(itemstack);
    }

    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        return this.meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        return this.meleeComponent.onItemRightClick(world, entityplayer, hand);
    }

    public void onUsingTick(final ItemStack itemstack, final EntityLivingBase entityplayer, final int count) {
        this.meleeComponent.onUsingTick(itemstack, entityplayer, count);
    }

    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityplayer, final int i) {
        this.meleeComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        this.meleeComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(final EntityEquipmentSlot equipmentSlot, final ItemStack itemstack) {
        final Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            this.meleeComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    public final UUID getUUIDDamage() {
        return ItemMelee.ATTACK_DAMAGE_MODIFIER;
    }

    public final UUID getUUIDSpeed() {
        return ItemMelee.ATTACK_SPEED_MODIFIER;
    }

    public final UUID getUUID() {
        return ItemMelee.WEAPON_MODIFIER;
    }

    public final Random getItemRand() {
        return ItemMelee.itemRand;
    }

    public MeleeComponent getMeleeComponent() {
        return this.meleeComponent;
    }

    public RangedComponent getRangedComponent() {
        return null;
    }
}
