package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
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

    public ItemMelee(String id, MeleeComponent meleecomponent) {
        super((meleecomponent.weaponMaterial == null) ? Item.ToolMaterial.WOOD : meleecomponent.weaponMaterial);
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        setTranslationKey(id);
        (meleeComponent = meleecomponent).setItem(this);
        meleecomponent.setThisItemProperties();
        setCreativeTab(CreativeTabs.COMBAT);
        addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "ready-to-throw"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, @Nullable World world,
                               @Nullable EntityLivingBase entity) {
                return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack) ? 1.0f : 0.0f;
            }
        });
        addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "state"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, @Nullable World world,
                               @Nullable EntityLivingBase entity) {
                return MeleeCompHalberd.getHalberdState(stack) ? 1.0f : 0.0f;
            }
        });
        addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "block"), new IItemPropertyGetter() {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(@Nonnull ItemStack stack, @Nullable World world,
                               @Nullable EntityLivingBase entity) {
                return entity != null && entity.getActiveItemStack() == stack ? 1.0f : 0.0f;
            }
        });
    }

    @Override
    public float getAttackDamage() {
        return meleeComponent.getEntityDamageMaterialPart();
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack itemstack, @Nonnull IBlockState block) {
        return meleeComponent.getBlockDamage(itemstack, block);
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState block) {
        return meleeComponent.canHarvestBlock(block);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        return meleeComponent.hitEntity(itemstack, entityliving, attacker);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack itemstack, @Nonnull World world,
                                    @Nonnull IBlockState block, @Nonnull BlockPos pos,
                                    @Nonnull EntityLivingBase entityliving) {
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

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world,
                                                    @Nonnull EntityPlayer entityplayer,
                                                    @Nonnull EnumHand hand) {
        return meleeComponent.onItemRightClick(world, entityplayer, hand);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityplayer,
                            int count) {
        meleeComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull ItemStack itemstack, @Nonnull World world,
                                     @Nonnull EntityLivingBase entityplayer, int i) {
        meleeComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        meleeComponent.onUpdate(itemstack, world, entity, i, flag);
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot equipmentSlot
            , @Nonnull ItemStack itemstack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            meleeComponent.addItemAttributeModifiers(multimap);
        }
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
}
