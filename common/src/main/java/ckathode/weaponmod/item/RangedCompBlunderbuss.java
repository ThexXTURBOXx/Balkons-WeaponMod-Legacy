package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RangedCompBlunderbuss extends RangedComponent {

    public static final String ID = "blunderbuss";
    public static final ItemShooter ITEM = WMItemBuilder.createStandardBlunderbuss();

    public RangedCompBlunderbuss() {
        super(RangedSpecs.BLUNDERBUSS);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.WOODEN_DOOR_CLOSE, SoundSource.PLAYERS, 0.8f,
                1.0f / (entityplayer.getRandom().nextFloat() * 0.2f + 0.0f));
    }

    @Override
    public void fire(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        Player entityplayer = (Player) entityliving;
        if (!world.isClientSide) {
            EntityBlunderShot.fireSpreadShot(world, entityplayer, this, itemstack);
        }
        int damage = 1;
        if (itemstack.getDamageValue() + damage < itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        itemstack.hurtAndBreak(damage, entityplayer, LivingEntity.getSlotForHand(entityplayer.getUsedItemHand()));
        postShootingEffects(itemstack, entityplayer, world);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, Player entityplayer, Level world) {
        float f = entityplayer.isShiftKeyDown() ? -0.1f : -0.2f;
        double d = -Mth.sin(entityplayer.getYRot() * 0.017453292f) * Mth.cos(0.0f) * f;
        double d2 = Mth.cos(entityplayer.getYRot() * 0.017453292f) * Mth.cos(0.0f) * f;
        entityplayer.setXRot(entityplayer.getXRot() - (entityplayer.isShiftKeyDown() ? 17.5f : 25.0f));
        entityplayer.push(d, 0.0, d2);
    }

    @Override
    public void effectShoot(Level world, double x, double y, double z, float yaw, float pitch) {
        world.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.PLAYERS, 3.0f,
                1.0f / (world.getRandom().nextFloat() * 0.4f + 0.6f));
        float particleX = -Mth.sin((yaw + 23.0f) * 0.017453292f) * Mth.cos(pitch * 0.017453292f);
        float particleY = -Mth.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = Mth.cos((yaw + 23.0f) * 0.017453292f) * Mth.cos(pitch * 0.017453292f);
        for (int i = 0; i < 3; ++i) {
            world.addParticle(ParticleTypes.SMOKE, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
        }
        world.addParticle(ParticleTypes.FLAME, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public float getMaxZoom() {
        return 0.07f;
    }

}
