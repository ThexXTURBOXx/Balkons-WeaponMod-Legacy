package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RangedCompCrossbow extends RangedComponent {
    public RangedCompCrossbow() {
        super(RangedSpecs.CROSSBOW);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.swingItem();
        world.playSoundAtEntity(entityplayer, "random.click", 0.8F,
                1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.4F));
    }

    public void resetReload(World world, ItemStack itemstack) {
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
    }

    @Override
    public void fire(ItemStack itemstack, World world, EntityLivingBase entityliving, int i) {
        EntityPlayer entityplayer = (EntityPlayer) entityliving;
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, entityplayer);
            entitybolt.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 5.0f, 1.5f / f);
            applyProjectileEnchantments(entitybolt, itemstack);
            world.spawnEntityInWorld(entitybolt);
        }
        int damage = 1;
        if (itemstack.getItemDamage() + damage < itemstack.getMaxDamage()) {
            resetReload(world, itemstack);
        }
        itemstack.damageItem(damage, entityplayer);
        if (itemstack.stackSize <= 0) {
            WMItem.deleteStack(entityplayer.inventory, itemstack);
        }
        postShootingEffects(itemstack, entityplayer, world);
        resetReload(world, itemstack);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 4.0f : 8.0f);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSoundEffect(x, y, z, "random.bow", 1.0F, 1.0F / (weapon.getItemRand().nextFloat() * 0.4F + 0.8F));
    }
}
