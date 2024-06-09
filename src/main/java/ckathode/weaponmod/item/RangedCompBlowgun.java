package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.ReloadHelper.ReloadState;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class RangedCompBlowgun extends RangedComponent {
    public RangedCompBlowgun() {
        super(RangedSpecs.BLOWGUN);
    }

    @Override
    public void effectReloadDone(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        entityplayer.swingArm(Hand.MAIN_HAND);
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.PLAYERS, 0.8f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.4f));
    }

    @Override
    public void fire(ItemStack itemstack, World world, LivingEntity entityliving, int i) {
        PlayerEntity entityplayer = (PlayerEntity) entityliving;
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
            dartstack = new ItemStack(BalkonsWeaponMod.darts.get(DartType.damage), 1);
        }
        ItemStack dartStackCopy = dartstack.copy();
        if (!entityplayer.isCreative()
            && EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) == 0) {
            dartstack.shrink(1);
            if (dartstack.isEmpty()) {
                entityplayer.inventory.deleteStack(dartstack);
            }
        }
        if (!world.isRemote) {
            EntityBlowgunDart entityblowgundart = new EntityBlowgunDart(world, entityplayer);
            entityblowgundart.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0f,
                    f * 1.5f, 1.0f);
            Item item = dartStackCopy.getItem();
            if (item instanceof ItemBlowgunDart)
                entityblowgundart.setDartEffectType(((ItemBlowgunDart) item).getDartType());
            applyProjectileEnchantments(entityblowgundart, itemstack);
            world.addEntity(entityblowgundart);
        }
        int damage = 1;
        if (itemstack.getDamage() + damage < itemstack.getMaxDamage()) {
            RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
        }
        itemstack.damageItem(damage, entityplayer, s -> s.sendBreakAnimation(s.getActiveHand()));
        postShootingEffects(itemstack, entityplayer, world);
        RangedComponent.setReloadState(itemstack, ReloadState.STATE_NONE);
    }

    @Override
    public boolean hasAmmoAndConsume(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        return hasAmmo(itemstack, world, entityplayer);
    }

    @Override
    public void soundEmpty(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                SoundCategory.PLAYERS, 1.0f, 1.0f / (weapon.getItemRand().nextFloat() * 0.2f + 0.5f));
    }

    @Override
    public void soundCharge(ItemStack itemstack, World world, PlayerEntity entityplayer) {
        world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ,
                SoundEvents.ENTITY_PLAYER_BREATH, SoundCategory.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.4f + 0.8f));
    }

    @Override
    public void effectShoot(World world, double x, double y, double z, float yaw,
                            float pitch) {
        world.playSound(null, x, y, z, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f,
                1.0f / (weapon.getItemRand().nextFloat() * 0.2f + 0.5f));
        float particleX = -MathHelper.sin((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        float particleY = -MathHelper.sin(pitch * 0.017453292f) + 1.6f;
        float particleZ = MathHelper.cos((yaw + 23.0f) * 0.017453292f) * MathHelper.cos(pitch * 0.017453292f);
        world.addParticle(ParticleTypes.POOF, x + particleX, y + particleY, z + particleZ, 0.0, 0.0, 0.0);
    }

    @Override
    public void effectPlayer(ItemStack itemstack, PlayerEntity entityplayer, World world) {
    }

    @Override
    public float getMaxZoom() {
        return 0.1f;
    }
}
