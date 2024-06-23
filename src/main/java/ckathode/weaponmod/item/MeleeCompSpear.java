package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.entity.projectile.EntitySpear;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MeleeCompSpear extends MeleeComponent implements IExtendedReachItem {
    public MeleeCompSpear(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.SPEAR, toolmaterial);
    }

    @Override
    public ItemStack onItemRightClick(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        if (itemstack == null) {
            return itemstack;
        }
        if (!BalkonsWeaponMod.instance.modConfig.canThrowSpear) {
            return super.onItemRightClick(world, entityplayer, itemstack);
        }
        if (!world.isRemote) {
            EntitySpear entityspear = new EntitySpear(world, entityplayer, itemstack.copy());
            entityspear.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 0.8f, 3.0f);
            entityspear.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId,
                    itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0) {
                entityspear.setFire(100);
            }
            world.spawnEntityInWorld(entityspear);
        }
        world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
        if (!entityplayer.capabilities.isCreativeMode) {
            itemstack = itemstack.copy();
            itemstack.splitStack(1);
        }
        return itemstack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return BalkonsWeaponMod.instance.modConfig.canThrowSpear ? EnumAction.none : super.getItemUseAction(itemstack);
    }

    @Override
    public float getExtendedReach(World world, EntityLivingBase living, ItemStack itemstack) {
        return 4.0f;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }
}
