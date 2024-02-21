package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import ckathode.weaponmod.entity.projectile.*;
import ckathode.weaponmod.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class RangedCompBlunderbuss extends RangedComponent
{
    public RangedCompBlunderbuss() {
        super(RangedSpecs.BLUNDERBUSS);
    }
    
    @Override
    public void effectReloadDone(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingArm(EnumHand.MAIN_HAND);
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.PLAYERS, 0.8f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.2f + 0.0f));
    }
    
    @Override
    public void fire(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        if (!world.isRemote) {
            EntityBlunderShot.fireSpreadShot(world, entityplayer, this, itemstack);
        }
        final int damage = 1;
        if (itemstack.getItemDamage() + damage <= itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadHelper.STATE_NONE);
        }
        itemstack.damageItem(damage, entityplayer);
        this.postShootingEffects(itemstack, entityplayer, world);
    }
    
    @Override
    public void effectPlayer(final ItemStack itemstack, final EntityPlayer entityplayer, final World world) {
        final float f = entityplayer.isSneaking() ? -0.1f : -0.2f;
        final double d = -MathHelper.sin(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        final double d2 = MathHelper.cos(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 17.5f : 25.0f);
        entityplayer.addVelocity(d, 0.0, d2);
    }
    
    @Override
    public void effectShoot(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3.0f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 0.6f));
        final float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        final float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        final float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0, new int[0]);
        }
        world.spawnParticle(EnumParticleTypes.FLAME, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0, new int[0]);
    }
    
    public float getMaxZoom() {
        return 0.07f;
    }
}
