package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class RangedCompCrossbow extends RangedComponent {
    public RangedCompCrossbow() {
        super(RangedSpecs.CROSSBOW);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, EntityLivingBase entityliving) {
        entityliving.swingArm(EnumHand.MAIN_HAND);
        world.playSound(null, entityliving.posX, entityliving.posY, entityliving.posZ,
                SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 0.8f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
    }

    public void resetReload(World world, ItemStack itemstack) {
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
    }

    @Override
    public void fire(ItemStack itemstack, World world, EntityLivingBase entityliving, int i) {
        int j = getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, entityliving);
            entitybolt.setAim(entityliving, entityliving.rotationPitch, entityliving.rotationYaw, 0.0f, 5.0f, 1.5f / f);
            applyProjectileEnchantments(entitybolt, itemstack);
            world.spawnEntityInWorld(entitybolt);
        }
        int damage = 1;
        if (itemstack.getItemDamage() + damage < itemstack.getMaxDamage()) {
            resetReload(world, itemstack);
        }
        itemstack.damageItem(damage, entityliving);
        postShootingEffects(itemstack, entityliving, world);
        resetReload(world, itemstack);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 4.0f : 8.0f);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }
}
