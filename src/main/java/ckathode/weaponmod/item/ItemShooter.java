package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemVariants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemShooter extends ItemBow implements IItemWeapon {
    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;
    public final String rawId;
    private IIcon reloadIcon, loadedIcon;
    private Boolean reloadIconExists, loadedIconExists;

    public ItemShooter(String id, RangedComponent rangedcomponent, MeleeComponent meleecomponent) {
        rawId = id;
        GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
        setTextureName(BalkonsWeaponMod.MOD_ID + ":" + id);
        setUnlocalizedName(id);
        rangedComponent = rangedcomponent;
        meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
        rangedcomponent.setThisItemProperties();
        reloadIcon = null;
        loadedIcon = null;
        reloadIconExists = null;
        loadedIconExists = null;
    }

    @Override
    public float func_150893_a(ItemStack itemstack, Block block) {
        return meleeComponent.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean func_150897_b(Block block) {
        return meleeComponent.canHarvestBlock(block);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        return meleeComponent.hitEntity(itemstack, entityliving, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block,
                                    int x, int y, int z, EntityLivingBase entityliving) {
        return meleeComponent.onBlockDestroyed(itemstack, world, block, x, y, z, entityliving);
    }

    @Override
    public int getItemEnchantability() {
        return meleeComponent.getItemEnchantability();
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        meleeComponent.addItemAttributeModifiers(multimap);
        rangedComponent.addItemAttributeModifiers(multimap);
        return multimap;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack itemstack, @Nonnull EntityPlayer player,
                                     @Nonnull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity) && rangedComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack itemstack) {
        return rangedComponent.getItemUseAction(itemstack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack itemstack) {
        return rangedComponent.getMaxItemUseDuration(itemstack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
        return rangedComponent.onItemRightClick(worldIn, playerIn, itemStackIn);
    }

    @Override
    public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
        rangedComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
        rangedComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        meleeComponent.onUpdate(itemstack, world, entity, i, flag);
        rangedComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    @Override
    public Random getItemRand() {
        return itemRand;
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return rangedComponent;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return rangedComponent.shouldRotateAroundWhenRendering();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (reloadIconExists == null)
            reloadIconExists = WMItemVariants.itemVariantExists(reloadIcon);
        if (reloadIconExists && player != null && player.isUsingItem() && !RangedComponent.isReloaded(stack))
            return reloadIcon;

        if (loadedIconExists == null)
            loadedIconExists = WMItemVariants.itemVariantExists(loadedIcon);
        if (loadedIconExists && RangedComponent.isReloaded(stack))
            return loadedIcon;

        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(getIconString());
        List<IIcon> icons = WMItemVariants.registerItemVariants(register, this, "_reload", "-loaded");
        reloadIcon = icons.get(0);
        loadedIcon = icons.get(1);
    }
}
