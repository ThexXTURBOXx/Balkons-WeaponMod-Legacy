package ckathode.weaponmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
    public boolean hitEntity(ItemStack itemstack, LivingEntity entityliving, LivingEntity entityliving1) {
        boolean flag = super.hitEntity(itemstack, entityliving, entityliving1);
        if (flag) {
            entityliving.setFire(12 + weapon.getItemRand().nextInt(3));
        }
        return flag;
    }

    @Override
    public UseAction getUseAction(ItemStack itemstack) {
        return UseAction.NONE;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        if (player.areEyesInFluid(FluidTags.WATER)) return;
        boolean mainHand = player.getHeldItemMainhand() == itemstack;
        boolean offHand = player.getHeldItemOffhand() == itemstack;
        if (!mainHand && !offHand) return;

        float f = 1.0f;
        float f1 = offHand ^ (player.getPrimaryHand() == HandSide.LEFT) ? -28.0f : 28.0f;
        float particleX =
                -MathHelper.sin(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        float particleY = -MathHelper.sin((player.rotationPitch / 180F) * 3.141593F) + player.getEyeHeight();
        float particleZ =
                MathHelper.cos(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.FLAME, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(ParticleTypes.SMOKE, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
    }
}
