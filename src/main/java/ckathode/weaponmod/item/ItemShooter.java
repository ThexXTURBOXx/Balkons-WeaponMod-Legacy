package ckathode.weaponmod.item;

import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import ckathode.weaponmod.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemShooter extends ItemBow implements IItemWeapon
{
    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;

    public ItemShooter(final String id, final RangedComponent rangedcomponent, final MeleeComponent meleecomponent) {
        this.setRegistryName(id);
        this.setTranslationKey(id);
        this.rangedComponent = rangedcomponent;
        this.meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
        rangedcomponent.setThisItemProperties();
        this.addPropertyOverride(new ResourceLocation("reload"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
                return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack && !RangedComponent.isReloaded(stack)) ? 1.0f : 0.0f;
            }
        });
        this.addPropertyOverride(new ResourceLocation("reloaded"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            public float apply(final ItemStack stack, @Nullable final World world, @Nullable final EntityLivingBase entity) {
                return RangedComponent.isReloaded(stack) ? 1.0f : 0.0f;
            }
        });
        BalkonsWeaponMod.MOD_ITEMS.add(this);
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

    public Multimap<String, AttributeModifier> getAttributeModifiers(final EntityEquipmentSlot equipmentSlot, final ItemStack itemstack) {
        final Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            this.meleeComponent.addItemAttributeModifiers(multimap);
            this.rangedComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    public boolean onLeftClickEntity(final ItemStack itemstack, final EntityPlayer player, final Entity entity) {
        return this.meleeComponent.onLeftClickEntity(itemstack, player, entity) && this.rangedComponent.onLeftClickEntity(itemstack, player, entity);
    }

    public EnumAction getItemUseAction(final ItemStack itemstack) {
        return this.rangedComponent.getItemUseAction(itemstack);
    }

    public int getMaxItemUseDuration(final ItemStack itemstack) {
        return this.rangedComponent.getMaxItemUseDuration(itemstack);
    }

    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer entityplayer, final EnumHand hand) {
        return this.rangedComponent.onItemRightClick(world, entityplayer, hand);
    }

    public void onUsingTick(final ItemStack itemstack, final EntityLivingBase entityplayer, final int count) {
        this.rangedComponent.onUsingTick(itemstack, entityplayer, count);
    }

    public void onPlayerStoppedUsing(final ItemStack itemstack, final World world, final EntityLivingBase entityplayer, final int i) {
        this.rangedComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        this.meleeComponent.onUpdate(itemstack, world, entity, i, flag);
        this.rangedComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    public final UUID getUUIDDamage() {
        return ItemShooter.ATTACK_DAMAGE_MODIFIER;
    }

    public final UUID getUUIDSpeed() {
        return ItemShooter.ATTACK_SPEED_MODIFIER;
    }

    public final UUID getUUID() {
        return ItemShooter.WEAPON_MODIFIER;
    }

    public final Random getItemRand() {
        return ItemShooter.itemRand;
    }

    public MeleeComponent getMeleeComponent() {
        return this.meleeComponent;
    }

    public RangedComponent getRangedComponent() {
        return this.rangedComponent;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
}
