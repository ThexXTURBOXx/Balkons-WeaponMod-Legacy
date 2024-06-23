package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class RangedCompBlowgun extends RangedComponent {
    public RangedCompBlowgun() {
        super(RangedSpecs.BLOWGUN);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "random.click", 0.8F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.4F));
    }

    @Override
    public void fire(ItemStack itemstack, World world, EntityLivingBase entityliving, int i) {
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        ItemStack dartstack = findAmmo(entityplayer);
        if (dartstack == null) {
            if (entityplayer.capabilities.isCreativeMode) {
                dartstack = new ItemStack(BalkonsWeaponMod.dart, 1);
            } else {
                return;
            }
        }
        ItemStack dartStackCopy = dartstack.copy();
        if (!entityplayer.capabilities.isCreativeMode
            && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemstack) == 0) {
            dartstack.splitStack(1);
            if (dartstack.stackSize <= 0) {
                WMItem.deleteStack(entityplayer.inventory, dartstack);
            }
        }
        if (!world.isRemote) {
            EntityBlowgunDart entityblowgundart = new EntityBlowgunDart(world, entityplayer);
            entityblowgundart.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * 1.5f, 1.0f);
            Item item = dartStackCopy.getItem();
            if (item instanceof ItemBlowgunDart)
                entityblowgundart.setDartEffectType((byte) dartStackCopy.getItemDamage());
            applyProjectileEnchantments(entityblowgundart, itemstack);
            world.spawnEntityInWorld(entityblowgundart);
        }
        int damage = 1;
        if (itemstack.getItemDamage() + damage < itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        itemstack.damageItem(damage, entityplayer);
        if (itemstack.stackSize <= 0) {
            WMItem.deleteStack(entityplayer.inventory, itemstack);
        }
        postShootingEffects(itemstack, entityplayer, world);
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
    }

    @Override
    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        return hasAmmo(itemstack, world, entityplayer);
    }

    @Override
    public void soundEmpty(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.2F + 0.5F));
    }

    @Override
    public void soundCharge(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        world.playSoundAtEntity(entityplayer, "random.breath", 1.0F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.2F + 0.5F));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        world.spawnParticle("explode", x + particleX, y + particleY, z + particleZ, 0.0, 0.0,
                0.0);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
    }

    @Override
    public float getMaxZoom() {
        return 0.1f;
    }
}
