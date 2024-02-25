package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper;
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
    public void effectReloadDone(final ItemStack itemstack, final World world, final EntityPlayer entityplayer) {
        entityplayer.swingArm(EnumHand.MAIN_HAND);
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 0.8f,
                1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
    }

    public void resetReload(final World world, final ItemStack itemstack) {
        RangedComponent.setReloadState(itemstack, ReloadHelper.STATE_NONE);
    }

    @Override
    public void fire(final ItemStack itemstack, final World world, final EntityLivingBase entityliving, final int i) {
        final EntityPlayer entityplayer = (EntityPlayer) entityliving;
        final int j = this.getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isRemote) {
            final EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, entityplayer);
            entitybolt.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f, 5.0f, 1.5f / f);
            this.applyProjectileEnchantments(entitybolt, itemstack);
            world.spawnEntity(entitybolt);
        }
        final int damage = 1;
        if (itemstack.getDamage() + damage <= itemstack.getMaxDamage()) {
            this.resetReload(world, itemstack);
        }
        itemstack.damageItem(damage, entityplayer);
        this.postShootingEffects(itemstack, entityplayer, world);
        this.resetReload(world, itemstack);
    }

    @Override
    public void effectPlayer(final ItemStack itemstack, final EntityPlayer entityplayer, final World world) {
        entityplayer.rotationPitch -= (entityplayer.isSneaking() ? 4.0f : 8.0f);
    }

    @Override
    public void effectShoot(final World world, final double x, final double y, final double z, final float yaw,
                            final float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f,
                1.0f / (this.weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }
}
