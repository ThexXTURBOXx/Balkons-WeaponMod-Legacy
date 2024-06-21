package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemVariants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShooter extends ItemBow implements IItemWeapon {
    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;
    public final String rawId;
    private final ModelResourceLocation reloadModel;
    private final ModelResourceLocation loadedModel;
    private Boolean reloadModelExists, loadedModelExists;

    public ItemShooter(String id, RangedComponent rangedcomponent, MeleeComponent meleecomponent) {
        rawId = id;
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        setUnlocalizedName(id);
        rangedComponent = rangedcomponent;
        meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
        rangedcomponent.setThisItemProperties();
        reloadModel = new ModelResourceLocation(new ResourceLocation(BalkonsWeaponMod.MOD_ID,
                rawId + "_reload"), "inventory");
        loadedModel = new ModelResourceLocation(new ResourceLocation(BalkonsWeaponMod.MOD_ID,
                rawId + "-loaded"), "inventory");
        reloadModelExists = null;
        loadedModelExists = null;
    }

    @Override
    public float getStrVsBlock(ItemStack itemstack, Block block) {
        return meleeComponent.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(Block block) {
        return meleeComponent.canHarvestBlock(block);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        return meleeComponent.hitEntity(itemstack, entityliving, attacker);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos pos,
                                    EntityLivingBase entityliving) {
        return meleeComponent.onBlockDestroyed(itemstack, world, block, pos, entityliving);
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
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        if (reloadModelExists == null)
            reloadModelExists = WMItemVariants.itemVariantExists(reloadModel);
        if (reloadModelExists && player != null && player.isUsingItem() && !RangedComponent.isReloaded(stack))
            return reloadModel;

        if (loadedModelExists == null)
            loadedModelExists = WMItemVariants.itemVariantExists(loadedModel);
        if (loadedModelExists && RangedComponent.isReloaded(stack))
            return loadedModel;

        return super.getModel(stack, player, useRemaining);
    }
}
