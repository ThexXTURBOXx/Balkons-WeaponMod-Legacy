package ckathode.weaponmod.item;

import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

public class MeleeCompFirerod extends MeleeComponent
{
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, Item.ToolMaterial.WOOD);
    }
    
    @Override
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase entityliving, final EntityLivingBase entityliving1) {
        final boolean flag = super.hitEntity(itemstack, entityliving, entityliving1);
        if (flag) {
            entityliving.setFire(12 + this.weapon.getItemRand().nextInt(3));
        }
        return flag;
    }
    
    @Override
    public void onUpdate(final ItemStack itemstack, final World world, final Entity entity, final int i, final boolean flag) {
        super.onUpdate(itemstack, world, entity, i, flag);
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).inventory.getCurrentItem() == itemstack && !entity.isInsideOfMaterial(Material.WATER)) {
            final float f = 1.0f;
            final float f2 = 27.0f;
            final float particleX = -MathHelper.sin((entity.rotationYaw + 28.0f) * 0.017453292f) * MathHelper.cos(entity.rotationPitch * 0.017453292f) * 1.0f;
            final float particleY = -MathHelper.sin(entity.rotationPitch * 0.017453292f) + 1.6f;
            final float particleZ = MathHelper.cos((entity.rotationYaw + 28.0f) * 0.017453292f) * MathHelper.cos(entity.rotationPitch * 0.017453292f) * 1.0f;
            if (this.weapon.getItemRand().nextInt(5) == 0) {
                world.spawnParticle(EnumParticleTypes.FLAME, entity.posX + particleX, entity.posY + particleY, entity.posZ + particleZ, 0.0, 0.0, 0.0, new int[0]);
            }
            if (this.weapon.getItemRand().nextInt(5) == 0) {
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entity.posX + particleX, entity.posY + particleY, entity.posZ + particleZ, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
}
