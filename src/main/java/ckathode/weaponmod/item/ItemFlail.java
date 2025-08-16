package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemVariants;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFlail extends ItemMelee {
    private final float flailDamage;
    private final ModelResourceLocation thrownModel;
    private Boolean thrownModelExists;

    public ItemFlail(String id, MeleeComponent meleecomponent) {
        this(BalkonsWeaponMod.MOD_ID, id, meleecomponent);
    }

    public ItemFlail(String modId, String id, MeleeComponent meleecomponent) {
        super(modId, id, meleecomponent);
        flailDamage = 4.0f + meleecomponent.weaponMaterial.getDamageVsEntity();
        thrownModel = new ModelResourceLocation(new ResourceLocation(modId, rawId + "-thrown"), "inventory");
        thrownModelExists = null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean isCurrentItem) {
        if (!(entity instanceof EntityPlayer) || !isCurrentItem) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (!isThrown(player)) {
            return;
        }
        if (!ItemStack.areItemStacksEqual(player.getCurrentEquippedItem(), itemstack)) {
            setThrown(player, false);
        } else {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0) {
                        entity2.setFire(2);
                    }
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        removePreviousFlail(world, entityplayer);
        entityplayer.swingItem();
        if (!entityplayer.capabilities.isCreativeMode) {
            WMItem.damageItem(itemstack, 1, entityplayer);
        }
        if (itemstack.stackSize > 0) {
            throwFlail(itemstack, world, entityplayer);
        }
        return itemstack;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        if (attacker instanceof EntityPlayer)
            onItemRightClick(itemstack, attacker.worldObj, (EntityPlayer) attacker);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
            entityflail.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId,
                    itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0) {
                entityflail.setFire(2);
            }
            world.spawnEntityInWorld(entityflail);
            setThrown(entityplayer, true);
        }
    }

    public void setThrown(EntityPlayer entityplayer, boolean flag) {
        PlayerWeaponData.setFlailThrown(entityplayer, flag);
    }

    public boolean isThrown(EntityPlayer entityplayer) {
        return PlayerWeaponData.isFlailThrown(entityplayer);
    }

    private void removePreviousFlail(World world, EntityPlayer entityplayer) {
        int id = PlayerWeaponData.getFlailEntityId(entityplayer);
        if (id != 0) {
            Entity entity = world.getEntityByID(id);
            if (entity instanceof EntityFlail) {
                entity.setDead();
            }
        }
    }

    public float getFlailDamage() {
        return flailDamage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining) {
        if (thrownModelExists == null)
            thrownModelExists = WMItemVariants.itemVariantExists(thrownModel);
        if (thrownModelExists && isThrown(player))
            return thrownModel;

        return super.getModel(stack, player, useRemaining);
    }
}
