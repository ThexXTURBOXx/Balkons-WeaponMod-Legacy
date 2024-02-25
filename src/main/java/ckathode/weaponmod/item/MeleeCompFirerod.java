package ckathode.weaponmod.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Particles;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent {
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, ItemTier.WOOD);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving,
                             EntityLivingBase entityliving1) {
        boolean flag = super.hitEntity(itemstack, entityliving, entityliving1);
        if (flag) {
            entityliving.setFire(12 + weapon.getItemRand().nextInt(3));
        }
        return flag;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, World world, Entity entity, int i,
                              boolean flag) {
        super.inventoryTick(itemstack, world, entity, i, flag);
        if (entity instanceof EntityPlayer && ((EntityPlayer) entity).inventory.getCurrentItem() == itemstack && !entity.areEyesInFluid(FluidTags.WATER)) {
            float particleX =
                    -MathHelper.sin((entity.rotationYaw + 28.0f) * 0.017453292f) * MathHelper.cos(entity.rotationPitch * 0.017453292f) * 1.0f;
            float particleY = -MathHelper.sin(entity.rotationPitch * 0.017453292f) + 1.6f;
            float particleZ =
                    MathHelper.cos((entity.rotationYaw + 28.0f) * 0.017453292f) * MathHelper.cos(entity.rotationPitch * 0.017453292f) * 1.0f;
            if (weapon.getItemRand().nextInt(5) == 0) {
                world.addParticle(Particles.FLAME, entity.posX + particleX, entity.posY + particleY,
                        entity.posZ + particleZ, 0.0, 0.0, 0.0);
            }
            if (weapon.getItemRand().nextInt(5) == 0) {
                world.addParticle(Particles.SMOKE, entity.posX + particleX, entity.posY + particleY,
                        entity.posZ + particleZ, 0.0, 0.0, 0.0);
            }
        }
    }
}
