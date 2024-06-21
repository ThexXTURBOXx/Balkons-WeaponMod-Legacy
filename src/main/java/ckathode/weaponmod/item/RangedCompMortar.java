package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RangedCompMortar extends RangedComponent {
    public RangedCompMortar() {
        super(RangedSpecs.MORTAR);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        entityplayer.swingArm(EnumHand.MAIN_HAND);
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.PLAYERS, 0.8f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.2f + 0.4f));
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
            EntityMortarShell entitymortarshell = new EntityMortarShell(world, entityplayer);
            entitymortarshell.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 1.4f,
                    1.0f / f);
            applyProjectileEnchantments(entitymortarshell, itemstack);
            world.spawnEntity(entitymortarshell);
        }
        int damage = 1;
        if (itemstack.getItemDamage() + damage < itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        itemstack.damageItem(damage, entityplayer);
        if (itemstack.stackSize <= 0) {
            entityplayer.inventory.deleteStack(itemstack);
        }
        postShootingEffects(itemstack, entityplayer, world);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, EntityPlayer entityplayer, World world) {
        float f = entityplayer.isSneaking() ? -0.15f : -0.25f;
        double d = -MathHelper.sin(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        double d2 = MathHelper.cos(entityplayer.rotationYaw * 0.017453292f) * MathHelper.cos(0.0f) * f;
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 20.0f : 30.0f);
        entityplayer.addVelocity(d, 0.0, d2);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 3.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.2f + 0.2f));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + particleX, y + particleY, z + particleZ, 0.0, 0.0,
                    0.0);
        }
        world.spawnParticle(EnumParticleTypes.FLAME, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public float getMaxZoom() {
        return 0.03f;
    }
}
