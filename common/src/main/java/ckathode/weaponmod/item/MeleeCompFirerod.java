package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
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
import org.jetbrains.annotations.Nullable;

public class MeleeCompFirerod extends MeleeComponent {

    public static final String ID = "firerod";
    public static final ItemMelee ITEM = WMItemBuilder.createStandardFirerod(BalkonsWeaponMod.id(ID));

    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, ToolMaterial.WOOD);
    }

    @Override
    public void hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entityliving,
                          @NotNull LivingEntity attacker) {
        super.hurtEnemy(itemstack, entityliving, attacker);
        applyFire(entityliving, itemstack);
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
    public void inventoryTickClient(@NotNull ItemStack itemStack, @NotNull Level level,
                                    @NotNull Player player, @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTickClient(itemStack, level, player, equipmentSlot);
        if (player.isEyeInFluid(FluidTags.WATER)) return;
        if (equipmentSlot == null || equipmentSlot.getType() != EquipmentSlot.Type.HAND) return;
        if (!level.isClientSide() || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) return;

        float yaw = player.getYRot() * (float) Math.PI / 180F;
        float pitch = player.getXRot() * (float) Math.PI / 180F;

        float sy = Mth.sin(yaw), cy = Mth.cos(yaw);
        float sp = Mth.sin(pitch), cp = Mth.cos(pitch);

        double fx = -sy * cp, fy = -sp, fz = cy * cp; // forward (look)
        double rx = -cy, ry = 0, rz = -sy; // right
        double ux = -sy * sp, uy = cp, uz = cy * sp; // up

        double forward = 0.8;
        double right = (equipmentSlot == EquipmentSlot.OFFHAND) ^ (player.getMainArm() == HumanoidArm.LEFT)
                ? -0.45 : 0.45;
        double up = -0.05;

        double px = player.getX() + fx * forward + rx * right + ux * up;
        double py = player.getY() + player.getEyeHeight() + fy * forward + ry * right + uy * up;
        double pz = player.getZ() + fz * forward + rz * right + uz * up;

        if (player.getRandom().nextInt(5) == 0) {
            level.addParticle(ParticleTypes.FLAME, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
        if (player.getRandom().nextInt(5) == 0) {
            level.addParticle(ParticleTypes.SMOKE, px, py, pz, 0.0D, 0.0D, 0.0D);
        }
    }

}
