package ckathode.weaponmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
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
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1) {
        boolean flag = super.hitEntity(itemstack, entityliving, entityliving1);
        if (flag) {
            entityliving.setFire(12 + weapon.getItemRand().nextInt(3));
        }
        return flag;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;
        if (player.areEyesInFluid(FluidTags.WATER)) return;
        boolean mainHand = player.getHeldItemMainhand() == itemstack;
        boolean offHand = player.getHeldItemOffhand() == itemstack;
        if (!mainHand && !offHand) return;

        float f = 1.0f;
        float f1 = offHand ^ (player.getPrimaryHand() == EnumHandSide.LEFT) ? -28.0f : 28.0f;
        float particleX =
                -MathHelper.sin(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        float particleY = -MathHelper.sin((player.rotationPitch / 180F) * 3.141593F) + player.getEyeHeight();
        float particleZ =
                MathHelper.cos(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(Particles.FLAME, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.addParticle(Particles.SMOKE, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
    }
}
