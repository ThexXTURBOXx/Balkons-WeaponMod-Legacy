package ckathode.weaponmod.item;

import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class MeleeCompFirerod extends MeleeComponent {

    public static final String ID = "firerod";
    public static final ItemMelee ITEM = WMItemBuilder.createStandardFirerod();

    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, Tiers.WOOD);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entityliving, LivingEntity attacker) {
        boolean flag = super.hurtEnemy(itemstack, entityliving, attacker);
        if (flag) applyFire(entityliving, itemstack);
        return flag;
    }

    public void applyFire(LivingEntity entity, ItemStack stack) {
        entity.setSecondsOnFire(Math.max(entity.getRemainingFireTicks() / 20, 0) + 12 +
                                2 * EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) +
                                weapon.getItemRand().nextInt(3));
        if (entity instanceof Creeper && !entity.level.isClientSide())
            ((Creeper) entity).ignite();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof Player)) return; // render particles only for players
        Player player = (Player) entity;
        if (player.isInWater()) return;
        boolean mainHand = player.getMainHandItem() == itemstack;
        boolean offHand = player.getOffhandItem() == itemstack;
        if (!mainHand && !offHand) return;
        if (!world.isClientSide() || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;

        float yaw = player.yRot * (float) Math.PI / 180F;
        float pitch = player.xRot * (float) Math.PI / 180F;

        float sy = Mth.sin(yaw), cy = Mth.cos(yaw);
        float sp = Mth.sin(pitch), cp = Mth.cos(pitch);

        double fx = -sy * cp, fy = -sp, fz = cy * cp; // forward (look)
        double rx = -cy, ry = 0, rz = -sy; // right
        double ux = -sy * sp, uy = cp, uz = cy * sp; // up

        double forward = 0.8;
        double right = offHand ^ (player.getMainArm() == HumanoidArm.LEFT) ? -0.45 : 0.45;
        double up = -0.05;

        double px = player.getX() + fx * forward + rx * right + ux * up;
        double py = player.getY() + player.getEyeHeight() + fy * forward + ry * right + uy * up;
        double pz = player.getZ() + fz * forward + rz * right + uz * up;

        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }

}
