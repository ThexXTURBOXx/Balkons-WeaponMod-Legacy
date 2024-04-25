package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RangedCompMusket extends RangedComponent {
    protected ItemMusket musket;

    public RangedCompMusket() {
        super(RangedSpecs.MUSKET);
    }

    @Override
    protected void onSetItem() {
        super.onSetItem();
        if (item instanceof ItemMusket) {
            musket = (ItemMusket) item;
        }
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }

    @Override
    public void fire(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        Player entityplayer = (Player) entityliving;
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f > 1.0f) {
            f = 1.0f;
        }
        f += 0.02f;
        if (!world.isClientSide) {
            EntityMusketBullet entitymusketbullet = new EntityMusketBullet(world, entityplayer);
            entitymusketbullet.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 5.0f,
                    1.0f / f);
            applyProjectileEnchantments(entitymusketbullet, itemstack);
            world.addFreshEntity(entitymusketbullet);
        }
        int deltadamage = 1;
        boolean flag = itemstack.getDamageValue() + deltadamage > itemstack.getMaxDamage();
        itemstack.hurtAndBreak(deltadamage, entityplayer, s -> s.broadcastBreakEvent(s.getUsedItemHand()));
        if (flag && musket != null) {
            int bayonetdamage = (itemstack.getTag() == null) ? 0 : itemstack.getTag().getShort(
                    "bayonetDamage");
            ItemStack newStack = new ItemStack(musket.bayonetItem, 1);
            newStack.setDamageValue(bayonetdamage);
            entityplayer.inventory.add(newStack);
        } else {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        postShootingEffects(itemstack, entityplayer, world);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, Player entityplayer, Level world) {
        float f = entityplayer.isShiftKeyDown() ? -0.05f : -0.1f;
        double d = -Mth.sin(entityplayer.yRot * 0.017453292f) * Mth.cos(0.0f) * f;
        double d2 = Mth.cos(entityplayer.yRot * 0.017453292f) * Mth.cos(0.0f) * f;
        entityplayer.xRot -= (entityplayer.isShiftKeyDown() ? 7.5f : 15.0f);
        entityplayer.push(d, 0.0, d2);
    }

    @Override
    public void effectShoot(Level world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 3.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.7f));
        world.playSound(null, x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
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
        return 0.15f;
    }
}
