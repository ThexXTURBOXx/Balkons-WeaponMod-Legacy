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
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemShooter extends ItemBow implements IItemWeapon {
    protected static final int MAX_DELAY = 72000;
    public final RangedComponent rangedComponent;
    public final MeleeComponent meleeComponent;

    public ItemShooter(final String id, final RangedComponent rangedcomponent, final MeleeComponent meleecomponent) {
        this(id, rangedcomponent, meleecomponent, new Properties());
    }

    public ItemShooter(final String id, final RangedComponent rangedcomponent, final MeleeComponent meleecomponent,
                       final Properties properties) {
        super(rangedcomponent.setProperties(properties).group(ItemGroup.COMBAT));
        this.setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
        // TODO: Needed? this.setTranslationKey(id);
        this.rangedComponent = rangedcomponent;
        this.meleeComponent = meleecomponent;
        rangedcomponent.setItem(this);
        meleecomponent.setItem(this);
        this.addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reload"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(@Nonnull final ItemStack stack, @Nullable final World world,
                              @Nullable final EntityLivingBase entity) {
                return (entity != null && entity.isHandActive() && entity.getActiveItemStack() == stack && !RangedComponent.isReloaded(stack)) ? 1.0f : 0.0f;
            }
        });
        this.addPropertyOverride(new ResourceLocation(BalkonsWeaponMod.MOD_ID, "reloaded"), new IItemPropertyGetter() {
            @Override
            @OnlyIn(Dist.CLIENT)
            public float call(@Nonnull final ItemStack stack, @Nullable final World world,
                              @Nullable final EntityLivingBase entity) {
                return RangedComponent.isReloaded(stack) ? 1.0f : 0.0f;
            }
        });
        BalkonsWeaponMod.MOD_ITEMS.add(this);
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
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull final EntityEquipmentSlot equipmentSlot
            , @Nonnull final ItemStack itemstack) {
        final Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            this.meleeComponent.addItemAttributeModifiers(multimap);
            this.rangedComponent.addItemAttributeModifiers(multimap);
        }
        return multimap;
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull final ItemStack itemstack, @Nonnull final EntityPlayer player,
                                     @Nonnull final Entity entity) {
        return this.meleeComponent.onLeftClickEntity(itemstack, player, entity) && this.rangedComponent.onLeftClickEntity(itemstack, player, entity);
    }

    @Nonnull
    @Override
    public EnumAction getUseAction(@Nonnull final ItemStack itemstack) {
        return this.rangedComponent.getUseAction(itemstack);
    }

    @Override
    public int getUseDuration(@Nonnull final ItemStack itemstack) {
        return this.rangedComponent.getUseDuration(itemstack);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull final World world,
                                                    @Nonnull final EntityPlayer entityplayer,
                                                    @Nonnull final EnumHand hand) {
        return this.rangedComponent.onItemRightClick(world, entityplayer, hand);
    }

    @Override
    public void onUsingTick(@Nonnull final ItemStack itemstack, @Nonnull final EntityLivingBase entityplayer,
                            final int count) {
        this.rangedComponent.onUsingTick(itemstack, entityplayer, count);
    }

    @Override
    public void onPlayerStoppedUsing(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                                     @Nonnull final EntityLivingBase entityplayer, final int i) {
        this.rangedComponent.onPlayerStoppedUsing(itemstack, world, entityplayer, i);
    }

    @Override
    public void inventoryTick(@Nonnull final ItemStack itemstack, @Nonnull final World world,
                              @Nonnull final Entity entity, final int i, final boolean flag) {
        this.meleeComponent.inventoryTick(itemstack, world, entity, i, flag);
        this.rangedComponent.inventoryTick(itemstack, world, entity, i, flag);
    }

    @Override
    public final UUID getUUIDDamage() {
        return ItemShooter.ATTACK_DAMAGE_MODIFIER;
    }

    @Override
    public final UUID getUUIDSpeed() {
        return ItemShooter.ATTACK_SPEED_MODIFIER;
    }

    @Override
    public final UUID getUUID() {
        return ItemShooter.WEAPON_MODIFIER;
    }

    @Override
    public final Random getItemRand() {
        return random;
    }

    @Override
    public MeleeComponent getMeleeComponent() {
        return this.meleeComponent;
    }

    @Override
    public RangedComponent getRangedComponent() {
        return this.rangedComponent;
    }

}
