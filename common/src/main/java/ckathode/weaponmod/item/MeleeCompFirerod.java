package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
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
        entity.igniteForSeconds(Math.max(entity.getRemainingFireTicks(), 0) + 12 + enchBonus +
                                entity.getRandom().nextInt(3));
        if (entity instanceof Creeper && !entity.level().isClientSide())
            ((Creeper) entity).ignite();
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public void inventoryTick(@NotNull ItemStack itemStack, @NotNull ServerLevel serverLevel,
                              @NotNull Entity entity, @Nullable EquipmentSlot equipmentSlot) {
        super.inventoryTick(itemStack, serverLevel, entity, equipmentSlot);
        if (!(entity instanceof Player player)) return; // render particles only for players
        if (player.isInWater()) return;
        boolean mainHand = player.getMainHandItem() == itemStack;
        boolean offHand = player.getOffhandItem() == itemStack;
        if (!mainHand && !offHand) return;

        float f = 1.0f;
        float f1 = offHand ^ (player.getMainArm() == HumanoidArm.LEFT) ? -28.0f : 28.0f;
        float particleX =
                -Mth.sin(((player.getYRot() + f1) / 180F) * 3.141593F) * Mth.cos((player.getXRot() / 180F) * 3.141593F) * f;
        float particleY = -Mth.sin((player.getXRot() / 180F) * 3.141593F) + player.getEyeHeight();
        float particleZ =
                Mth.cos(((player.getYRot() + f1) / 180F) * 3.141593F) * Mth.cos((player.getXRot() / 180F) * 3.141593F) * f;
        if (serverLevel.isClientSide()) {
            if (player.getRandom().nextInt(5) == 0) {
                serverLevel.addParticle(ParticleTypes.FLAME, player.getX() + particleX, player.getY() + particleY,
                        player.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
            }
            if (player.getRandom().nextInt(5) == 0) {
                serverLevel.addParticle(ParticleTypes.SMOKE, player.getX() + particleX, player.getY() + particleY,
                        player.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
