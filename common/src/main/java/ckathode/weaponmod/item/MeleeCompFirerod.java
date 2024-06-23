package ckathode.weaponmod.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class MeleeCompFirerod extends MeleeComponent {

    public static final String ID = "firerod";
    public static final ItemMelee ITEM = new ItemMelee(new MeleeCompFirerod());

    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, Tiers.WOOD);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        boolean flag = super.hurtEnemy(itemstack, entityliving, entityliving1);
        if (flag) {
            entityliving.setSecondsOnFire(12 + entityliving.getRandom().nextInt(3));
        }
        return flag;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof Player player)) return;
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
