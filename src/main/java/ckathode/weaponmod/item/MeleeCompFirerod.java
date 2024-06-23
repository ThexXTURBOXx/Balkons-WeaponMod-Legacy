package ckathode.weaponmod.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent {
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, Item.ToolMaterial.WOOD);
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
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.none;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.onUpdate(itemstack, world, entity, i, flag);
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity;
        if (player.isInsideOfMaterial(Material.water)) return;
        if (player.inventory.getCurrentItem() != itemstack) return;

        float f = 1.0f;
        float f1 = 28.0f;
        float particleX =
                -MathHelper.sin(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        float particleY = -MathHelper.sin((player.rotationPitch / 180F) * 3.141593F) + player.getEyeHeight();
        float particleZ =
                MathHelper.cos(((player.rotationYaw + f1) / 180F) * 3.141593F) * MathHelper.cos((player.rotationPitch / 180F) * 3.141593F) * f;
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.spawnParticle("flame", player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.spawnParticle("smoke", player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
    }
}
