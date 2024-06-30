package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RangedCompCrossbow extends RangedComponent {

    public static final String ID = "crossbow";
    public static final ItemShooter ITEM = WMItemBuilder.createStandardCrossbow();

    public RangedCompCrossbow() {
        super(RangedSpecs.CROSSBOW);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.8f,
                1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.4f));
    }

    public void resetReload(Level world, ItemStack itemstack) {
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
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
            EntityCrossbowBolt entitybolt = new EntityCrossbowBolt(world, entityplayer);
            entitybolt.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, 5.0f, 1.5f / f);
            applyProjectileEnchantments(entitybolt, itemstack);
            world.addFreshEntity(entitybolt);
        }
        int damage = 1;
        if (itemstack.getDamageValue() + damage < itemstack.getMaxDamage()) {
            resetReload(world, itemstack);
        }
        itemstack.hurtAndBreak(damage, entityplayer, LivingEntity.getSlotForHand(entityplayer.getUsedItemHand()));
        postShootingEffects(itemstack, entityplayer, world);
        resetReload(world, itemstack);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, Player entityplayer, Level world) {
        entityplayer.setXRot(entityplayer.getXRot() - (entityplayer.isShiftKeyDown() ? 4.0f : 8.0f));
    }

    @Override
    public void effectShoot(Level world, double x, double y, double z, float yaw, float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f,
                1.0f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
    }

}
