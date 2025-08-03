package ckathode.weaponmod.item;

import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MeleeCompBoomerang extends MeleeComponent {
    public MeleeCompBoomerang(Item.ToolMaterial toolmaterial) {
        super(MeleeSpecs.BOOMERANG, toolmaterial);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world,
                                     EntityLivingBase entityliving, int i) {
        if (itemstack == null) {
            return;
        }
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        boolean crit = false;
        if (f > 1.5f) {
            f = 1.5f;
            crit = true;
        }
        f *= 1.5f;
        if (!world.isRemote) {
            EntityBoomerang entityboomerang = new EntityBoomerang(world, entityliving, itemstack.copy());
            entityboomerang.setAim(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0f, f,
                    5.0f);
            entityboomerang.setIsCritical(crit);
            entityboomerang.setKnockbackStrength(EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId,
                    itemstack));
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemstack) > 0) {
                entityboomerang.setFire(100);
            }
            world.spawnEntityInWorld(entityboomerang);
        }
        world.playSoundAtEntity(entityliving, "random.bow", 0.6F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 1.0F));
        if (!(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).capabilities.isCreativeMode) {
            WMItem.decrStackSize(itemstack, 1, entityliving);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }

    @Override
    public ItemStack onItemRightClick(World world, EntityPlayer entityplayer, ItemStack itemstack) {
        if (entityplayer.inventory.hasItem(item)) {
            entityplayer.setItemInUse(itemstack, getMaxItemUseDuration(itemstack));
        }
        return itemstack;
    }
}
