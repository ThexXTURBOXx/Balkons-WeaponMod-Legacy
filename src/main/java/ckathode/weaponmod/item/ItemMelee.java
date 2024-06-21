package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemVariants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import javax.annotation.Nonnull;
import net.minecraft.block.Block;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ItemMelee extends ItemSword implements IItemWeapon {
    public final MeleeComponent meleeComponent;
    public final String rawId;
    private final ModelResourceLocation readyModel;
    private final ModelResourceLocation halberdStateModel;
    private Boolean readyModelExists, halberdStateModelExists;

    public ItemMelee(String id, MeleeComponent meleecomponent) {
        super((meleecomponent.weaponMaterial == null) ? Item.ToolMaterial.WOOD : meleecomponent.weaponMaterial);
        rawId = id;
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        setUnlocalizedName(id);
        (meleeComponent = meleecomponent).setItem(this);
        meleecomponent.setThisItemProperties();
        setCreativeTab(CreativeTabs.tabCombat);
        readyModel = new ModelResourceLocation(new ResourceLocation(BalkonsWeaponMod.MOD_ID,
                rawId + "_ready"), "inventory");
        halberdStateModel = new ModelResourceLocation(new ResourceLocation(BalkonsWeaponMod.MOD_ID,
                rawId + "_state"), "inventory");
        readyModelExists = null;
        halberdStateModelExists = null;
    }

    @Override
    public float getDamageVsEntity() {
        return meleeComponent.getEntityDamageMaterialPart();
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
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        if (readyModelExists == null)
            readyModelExists = WMItemVariants.itemVariantExists(readyModel);
        if (readyModelExists && player != null && player.isUsingItem())
            return readyModel;

        if (halberdStateModelExists == null)
            halberdStateModelExists = WMItemVariants.itemVariantExists(halberdStateModel);
        if (halberdStateModelExists && MeleeCompHalberd.getHalberdState(stack))
            return halberdStateModel;

        return super.getModel(stack, player, useRemaining);
    }
}
