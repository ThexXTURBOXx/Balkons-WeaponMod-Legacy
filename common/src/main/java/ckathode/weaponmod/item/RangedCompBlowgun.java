package ckathode.weaponmod.item;

import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.WMItemBuilder;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class RangedCompBlowgun extends RangedComponent {

    public static final String ID = "blowgun";
    public static final ItemShooter ITEM = WMItemBuilder.createStandardBlowgun();

    public RangedCompBlowgun() {
        super(RangedSpecs.BLOWGUN);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, Level world, Player entityplayer) {
        entityplayer.swing(InteractionHand.MAIN_HAND);
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.COMPARATOR_CLICK, SoundSource.PLAYERS, 0.8f,
                1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.4f));
    }

    @Override
    public void fire(ItemStack itemstack, Level world, LivingEntity entityliving, int i) {
        Player entityplayer = (Player) entityliving;
        int j = getUseDuration(itemstack) - i;
        float f = j / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;
        if (f < 0.1f) {
            return;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        ItemStack dartstack = findAmmo(entityplayer);
        if (dartstack.isEmpty() && entityplayer.isCreative()) {
            dartstack = new ItemStack(ItemBlowgunDart.ITEMS.get(DartType.damage), 1);
        }
        ItemStack dartStackCopy = dartstack.copy();
        if (!entityplayer.isCreative()
            && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY, itemstack) == 0) {
            dartstack.shrink(1);
            if (dartstack.isEmpty()) {
                entityplayer.getInventory().removeItem(dartstack);
            }
        }
        if (!world.isClientSide) {
            EntityBlowgunDart entityblowgundart = new EntityBlowgunDart(world, entityplayer);
            entityblowgundart.shootFromRotation(entityplayer, entityplayer.getXRot(), entityplayer.getYRot(),
                    0.0f, f * 1.5f, 1.0f);
            Item item = dartStackCopy.getItem();
            if (item instanceof ItemBlowgunDart)
                entityblowgundart.setDartEffectType(((ItemBlowgunDart) item).getDartType());
            applyProjectileEnchantments(entityblowgundart, itemstack);
            world.addFreshEntity(entityblowgundart);
        }
        int damage = 1;
        if (itemstack.getDamageValue() + damage < itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        itemstack.hurtAndBreak(damage, entityplayer, LivingEntity.getSlotForHand(entityplayer.getUsedItemHand()));
        postShootingEffects(itemstack, entityplayer, world);
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
    }

    @Override
    public boolean hasAmmoAndConsume(ItemStack itemstack, Level world, Player entityplayer) {
        return hasAmmo(itemstack, world, entityplayer);
    }

    @Override
    public void soundEmpty(ItemStack itemstack, Level world, Player entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(), SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS, 1.0f, 1.0f / (entityplayer.getRandom().nextFloat() * 0.2f + 0.5f));
    }

    @Override
    public void soundCharge(ItemStack itemstack, Level world, Player entityplayer) {
        world.playSound(null, entityplayer.getX(), entityplayer.getY(), entityplayer.getZ(),
                SoundEvents.PLAYER_BREATH, SoundSource.PLAYERS, 1.0f,
                1.0f / (entityplayer.getRandom().nextFloat() * 0.4f + 0.8f));
    }

    @Override
    public void effectShoot(Level world, double x, double y, double z, float yaw, float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0f,
                1.0f / (world.getRandom().nextFloat() * 0.2f + 0.5f));
        float particleX = -Mth.sin((yaw + 23.0f) * 0.017453292f) * Mth.cos(pitch * 0.017453292f);
        float particleY = -Mth.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = Mth.cos((yaw + 23.0f) * 0.017453292f) * Mth.cos(pitch * 0.017453292f);
        world.addParticle(ParticleTypes.POOF, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, Player entityplayer, Level world) {
    }

    @Override
    public float getMaxZoom() {
        return 0.1f;
    }

}
