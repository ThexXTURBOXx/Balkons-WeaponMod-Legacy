package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MeleeCompFirerod extends MeleeComponent {

    public static final String ID = "firerod";
    public static final ItemMelee ITEM = WMItemBuilder.createStandardFirerod(BalkonsWeaponMod.id(ID));

    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, ToolMaterial.WOOD);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entityliving, LivingEntity attacker) {
        boolean flag = super.hurtEnemy(itemstack, entityliving, attacker);
        if (flag) applyFire(entityliving, itemstack);
        return flag;
    }

    public void applyFire(LivingEntity entity, ItemStack stack) {
        Holder<Enchantment> fireAspect = entity.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT).orElse(null);
        int enchBonus = fireAspect != null ? 2 * EnchantmentHelper.getItemEnchantmentLevel(fireAspect, stack) : 0;
        entity.igniteForTicks(Math.max(entity.getRemainingFireTicks(), 0) +
                              (12 + enchBonus + entity.getRandom().nextInt(3)) * 20);
        if (entity instanceof Creeper creeper && !entity.level().isClientSide())
            creeper.ignite();
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof Player player)) return; // render particles only for players
        if (player.isEyeInFluid(FluidTags.WATER)) return;
        boolean mainHand = player.getMainHandItem() == itemstack;
        boolean offHand = player.getOffhandItem() == itemstack;
        if (!mainHand && !offHand) return;
        if (!world.isClientSide() || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;

        float yaw = player.getYRot() * (float) Math.PI / 180F;
        float pitch = player.getXRot() * (float) Math.PI / 180F;

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

        if (player.getRandom().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
        if (player.getRandom().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }

}
