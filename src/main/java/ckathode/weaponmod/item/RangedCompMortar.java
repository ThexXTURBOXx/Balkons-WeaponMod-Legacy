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

public class RangedCompMortar extends RangedComponent
{
    public RangedCompMortar() {
        super(RangedSpecs.MORTAR);
    }
    
    @Override
    public void effectReloadDone(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingArm(EnumHand.MAIN_HAND);
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.PLAYERS, 0.8f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.2f + 0.4f));
    }
    
    @Override
    public void fire(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer)entityliving;
        final int j = this.getMaxItemUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            final EntityMortarShell entitymortarshell = new EntityMortarShell(world, entityplayer);
            entitymortarshell.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 1.4f, 1.0f / f);
            this.applyProjectileEnchantments(entitymortarshell, itemstack);
            world.spawnEntity(entitymortarshell);
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
        final float f = entityplayer.isSneaking() ? -0.15f : -0.25f;
        final double d = -MathHelper.sin(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        final double d2 = MathHelper.cos(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 20.0f : 30.0f);
        entityplayer.addVelocity(d, 0.0, d2);
    }
    
    @Override
    public void effectShoot(final World world, final double x, final double y, final double z, final float yaw, final float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3.0f, 1.0f / (this.weapon.getItemRand().nextFloat() * 0.2f + 0.2f));
        final float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        final float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        final float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0, new int[0]);
        }
        world.spawnParticle(EnumParticleTypes.FLAME, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0, new int[0]);
    }
    
    public float getMaxZoom() {
        return 0.03f;
    }
}
