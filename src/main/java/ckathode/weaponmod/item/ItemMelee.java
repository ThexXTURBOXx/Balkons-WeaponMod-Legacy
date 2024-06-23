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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMelee extends ItemSword implements IItemWeapon {
    public final MeleeComponent meleeComponent;
    public final String rawId;
    private IIcon readyIcon, halberdStateIcon;
    private Boolean readyIconExists, halberdStateIconExists;

    public ItemMelee(String id, MeleeComponent meleecomponent) {
        super((meleecomponent.weaponMaterial == null) ? Item.ToolMaterial.WOOD : meleecomponent.weaponMaterial);
        rawId = id;
        GameRegistry.registerItem(this, id, BalkonsWeaponMod.MOD_ID);
        setTextureName(BalkonsWeaponMod.MOD_ID + ":" + id);
        setUnlocalizedName(id);
        (meleeComponent = meleecomponent).setItem(this);
        meleecomponent.setThisItemProperties();
        setCreativeTab(CreativeTabs.tabCombat);
        readyIcon = null;
        halberdStateIcon = null;
        readyIconExists = null;
        halberdStateIconExists = null;
    }

    @Override
    public float func_150931_i() {
        return meleeComponent.getEntityDamageMaterialPart();
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

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack itemstack) {
        return meleeComponent.getItemUseAction(itemstack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack itemstack) {
        return meleeComponent.getMaxItemUseDuration(itemstack);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack itemstack, @Nonnull EntityPlayer player,
                                     @Nonnull Entity entity) {
        return meleeComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer entityplayer) {
        return meleeComponent.onItemRightClick(world, entityplayer, stack);
    }

    @Override
    public void onUsingTick(ItemStack itemstack, EntityPlayer entityplayer, int count) {
        meleeComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer entityplayer, int i) {
        meleeComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        meleeComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        meleeComponent.addItemAttributeModifiers(multimap);
        return multimap;
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
        return null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return meleeComponent.shouldRotateAroundWhenRendering();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (readyIconExists == null)
            readyIconExists = WMItemVariants.itemVariantExists(readyIcon);
        if (readyIconExists && player != null && player.isUsingItem())
            return readyIcon;

        if (halberdStateIconExists == null)
            halberdStateIconExists = WMItemVariants.itemVariantExists(halberdStateIcon);
        if (halberdStateIconExists && MeleeCompHalberd.getHalberdState(stack))
            return halberdStateIcon;

        return super.getIcon(stack, renderPass, player, usingItem, useRemaining);
    }

    @Override
    public void registerIcons(IIconRegister register) {
        itemIcon = register.registerIcon(getIconString());
        List<IIcon> icons = WMItemVariants.registerItemVariants(register, this, "_ready", "_state");
        readyIcon = icons.get(0);
        halberdStateIcon = icons.get(1);
    }
}
