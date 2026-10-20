package ckathode.weaponmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.UseAction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent {
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, ItemTier.WOOD);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, LivingEntity entityliving, LivingEntity attacker) {
        boolean flag = super.hitEntity(itemstack, entityliving, attacker);
        if (flag) applyFire(entityliving, itemstack);
        return flag;
    }

    public void applyFire(LivingEntity entity, ItemStack stack) {
        entity.setFire(Math.max(entity.getFireTimer() / 20, 0) + 12 +
                       2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) +
                       weapon.getItemRand().nextInt(3));
        if (entity instanceof CreeperEntity && !entity.world.isRemote())
            ((CreeperEntity) entity).ignite();
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof PlayerEntity)) return; // render particles only for players
        PlayerEntity player = (PlayerEntity) entity;
        if (player.areEyesInFluid(FluidTags.WATER)) return;
        boolean mainHand = player.getHeldItemMainhand() == itemstack;
        boolean offHand = player.getHeldItemOffhand() == itemstack;
        if (!mainHand && !offHand) return;
        if (!world.isRemote() || Minecraft.getInstance().gameSettings.thirdPersonView != 0) return;

        float yaw = player.rotationYaw * (float) Math.PI / 180F;
        float pitch = player.rotationPitch * (float) Math.PI / 180F;

        float sy = MathHelper.sin(yaw), cy = MathHelper.cos(yaw);
        float sp = MathHelper.sin(pitch), cp = MathHelper.cos(pitch);

        double fx = -sy * cp, fy = -sp, fz = cy * cp; // forward (look)
        double rx = -cy, ry = 0, rz = -sy; // right
        double ux = -sy * sp, uy = cp, uz = cy * sp; // up

        double forward = 0.8;
        double right = offHand ^ (player.getPrimaryHand() == HandSide.LEFT) ? -0.45 : 0.45;
        double up = -0.05;

        double px = player.posX + fx * forward + rx * right + ux * up;
        double py = player.posY + player.getEyeHeight() + fy * forward + ry * right + uy * up;
        double pz = player.posZ + fz * forward + rz * right + uz * up;

        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }
}
