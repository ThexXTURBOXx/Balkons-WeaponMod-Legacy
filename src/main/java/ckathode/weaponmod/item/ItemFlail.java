package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.PlayerWeaponData;
import ckathode.weaponmod.WMItemVariants;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
    public int getItemEnchantability() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public void onUpdate(@Nonnull ItemStack itemstack, @Nonnull World world,
                         @Nonnull Entity entity, int i, boolean flag) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        if (!isThrown(player)) {
            return;
        }
        ItemStack itemstack2 = player.getCurrentEquippedItem();
        if (itemstack2 == null || !((itemstack2.getItem()) instanceof ItemFlail)) {
            setThrown(player, false);
        } else if (itemstack2.getItem() == this) {
            int id = PlayerWeaponData.getFlailEntityId(player);
            if (id != 0) {
                Entity entity2 = world.getEntityByID(id);
                if (entity2 instanceof EntityFlail) {
                    ((EntityFlail) entity2).setThrower(player);
                    ((EntityFlail) entity2).setThrownItemStack(itemstack);
                }
            }
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        removePreviousFlail(world, entityplayer);
        if (itemstack.stackSize > 0) {
            entityplayer.swingItem();
            if (!entityplayer.capabilities.isCreativeMode) {
                itemstack.damageItem(1, entityplayer);
                if (itemstack.stackSize <= 0) {
                    WMItem.deleteStack(entityplayer.inventory, itemstack);
                }
            }
            if (itemstack.stackSize > 0) {
                throwFlail(itemstack, world, entityplayer);
            } else {
                WMItem.deleteStack(entityplayer.inventory, itemstack);
            }
        } else {
            WMItem.deleteStack(entityplayer.inventory, itemstack);
        }
        return itemstack;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack itemstack, @Nonnull EntityLivingBase entityliving,
                             @Nonnull EntityLivingBase attacker) {
        onItemRightClick(itemstack, attacker.worldObj, (EntityPlayer) attacker);
        return true;
    }

    public void throwFlail(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            EntityFlail entityflail = new EntityFlail(world, entityplayer, itemstack);
            entityflail.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.75f, 3.0f);
            PlayerWeaponData.setFlailEntityId(entityplayer, entityflail.getEntityId());
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
