package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class RangedCompCrossbow extends RangedComponent {
    public RangedCompCrossbow() {
        super(RangedSpecs.CROSSBOW);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        entityplayer.swing(Hand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.COMPARATOR_CLICK, SoundCategory.PLAYERS, 0.8f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
    }

    public void resetReload(World world, ItemStack itemstack) {
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
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
            EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, entityplayer);
            entitybolt.shootFromRotation(entityplayer, entityplayer.xRot, entityplayer.yRot, 0.0f, 5.0f, 1.5f / f);
            applyProjectileEnchantments(entitybolt, itemstack);
            world.addFreshEntity(entitybolt);
        }
        int damage = 1;
        if (itemstack.getDamageValue() + damage <= itemstack.getMaxDamage()) {
            resetReload(world, itemstack);
        }
        itemstack.hurtAndBreak(damage, entityplayer, s -> s.broadcastBreakEvent(s.getUsedItemHand()));
        postShootingEffects(itemstack, entityplayer, world);
        resetReload(world, itemstack);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, PlayerEntity entityplayer, World world) {
        entityplayer.xRot -= (entityplayer.isShiftKeyDown() ? 4.0f : 8.0f);
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }
}
