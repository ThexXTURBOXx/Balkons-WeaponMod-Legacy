package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

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
    public void effectReloadDone(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        entityplayer.swing(Hand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.COMPARATOR_CLICK, SoundCategory.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }

    @Override
    public void fire(ItemStack itemstack, World world, LivingEntity entityliving, int i) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
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
    public void effectPlayer(ItemStack itemstack, PlayerEntity entityplayer, World world) {
        float f = entityplayer.isShiftKeyDown() ? -0.05f : -0.1f;
        double d = -MathHelper.sin(entityplayer.yRot * 0.017453292f) * MathHelper.cos(0.0f) * f;
        double d2 = MathHelper.cos(entityplayer.yRot * 0.017453292f) * MathHelper.cos(0.0f) * f;
        entityplayer.xRot -= (entityplayer.isShiftKeyDown() ? 7.5f : 15.0f);
        entityplayer.push(d, 0.0, d2);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.GENERIC_EXPLODE, SoundCategory.PLAYERS, 3.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.7f));
        world.playSound(null, x, y, z, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundCategory.PLAYERS, 3.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
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
