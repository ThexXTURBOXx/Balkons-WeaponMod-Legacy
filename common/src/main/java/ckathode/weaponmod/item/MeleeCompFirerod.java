package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMItemBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
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
        if (flag) {
            Holder<Enchantment> fireAspect = entityliving.registryAccess()
                    .lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT).orElse(null);
            int enchBonus = fireAspect != null
                    ? 2 * EnchantmentHelper.getItemEnchantmentLevel(fireAspect, itemstack) : 0;
            entityliving.igniteForSeconds(12 + enchBonus +
                                          entityliving.getRandom().nextInt(3));
        }
        return flag;
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(ItemStack itemstack) {
        return ItemUseAnimation.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof Player player)) return; // render particles only for players
        if (player.isInWater()) return;
        boolean mainHand = player.getMainHandItem() == itemstack;
        boolean offHand = player.getOffhandItem() == itemstack;
        if (!mainHand && !offHand) return;

        float f = 1.0f;
        float f1 = offHand ^ (player.getMainArm() == HumanoidArm.LEFT) ? -28.0f : 28.0f;
        float particleX =
                -Mth.sin(((player.getYRot() + f1) / 180F) * 3.141593F) * Mth.cos((player.getXRot() / 180F) * 3.141593F) * f;
        float particleY = -Mth.sin((player.getXRot() / 180F) * 3.141593F) + player.getEyeHeight();
        float particleZ =
                Mth.cos(((player.getYRot() + f1) / 180F) * 3.141593F) * Mth.cos((player.getXRot() / 180F) * 3.141593F) * f;
        if (world.isClientSide()) {
            if (player.getRandom().nextInt(5) == 0) {
                world.addParticle(ParticleTypes.FLAME, player.getX() + particleX, player.getY() + particleY,
                        player.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
            }
            if (player.getRandom().nextInt(5) == 0) {
                world.addParticle(ParticleTypes.SMOKE, player.getX() + particleX, player.getY() + particleY,
                        player.getZ() + particleZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
