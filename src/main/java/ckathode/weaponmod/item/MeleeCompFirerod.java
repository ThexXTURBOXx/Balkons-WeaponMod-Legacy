package ckathode.weaponmod.item;

import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Particles;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent {
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, ItemTier.WOOD);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
        boolean flag = super.hitEntity(itemstack, entityliving, attacker);
        if (flag) applyFire(entityliving, itemstack);
        return flag;
    }

    public void applyFire(EntityLivingBase entity, ItemStack stack) {
        entity.setFire(Math.max(entity.fire / 20, 0) + 12 +
                       2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) +
                       weapon.getItemRand().nextInt(3));
        if (entity instanceof EntityCreeper && !entity.world.isRemote)
            ((EntityCreeper) entity).ignite();
    }

    @Override
    public EnumAction getUseAction(ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof EntityPlayer)) return; // render particles only for players
        EntityPlayer player = (EntityPlayer) entity;
        if (player.areEyesInFluid(FluidTags.WATER)) return;
        boolean mainHand = player.getHeldItemMainhand() == itemstack;
        boolean offHand = player.getHeldItemOffhand() == itemstack;
        if (!mainHand && !offHand) return;
        if (!world.isRemote || Minecraft.getInstance().gameSettings.thirdPersonView != 0) return;

        float yaw = player.rotationYaw * (float) Math.PI / 180F;
        float pitch = player.rotationPitch * (float) Math.PI / 180F;

        float sy = MathHelper.sin(yaw), cy = MathHelper.cos(yaw);
        float sp = MathHelper.sin(pitch), cp = MathHelper.cos(pitch);

        double fx = -sy * cp, fy = -sp, fz = cy * cp; // forward (look)
        double rx = -cy, ry = 0, rz = -sy; // right
        double ux = -sy * sp, uy = cp, uz = cy * sp; // up

        double forward = 0.8;
        double right = offHand ^ (player.getPrimaryHand() == EnumHandSide.LEFT) ? -0.45 : 0.45;
        double up = -0.05;

        double px = player.posX + fx * forward + rx * right + ux * up;
        double py = player.posY + player.getEyeHeight() + fy * forward + ry * right + uy * up;
        double pz = player.posZ + fz * forward + rz * right + uz * up;

        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(Particles.FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(Particles.SMOKE, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }
}
