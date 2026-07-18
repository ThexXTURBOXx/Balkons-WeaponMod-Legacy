package ckathode.weaponmod.item;

import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MeleeCompFirerod extends MeleeComponent {
    public MeleeCompFirerod() {
        super(MeleeSpecs.FIREROD, Item.ToolMaterial.WOOD);
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase attacker) {
        boolean flag = super.hitEntity(itemstack, entityliving, attacker);
        if (flag) applyFire(entityliving, itemstack);
        return flag;
    }

    public void applyFire(EntityLivingBase entity, ItemStack stack) {
        entity.setFire(Math.max(entity.fire, 0) + 12 +
                       2 * EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, stack) +
                       weapon.getItemRand().nextInt(3));
        if (entity instanceof EntityCreeper && !entity.worldObj.isRemote)
            ((EntityCreeper) entity).ignite();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.NONE;
    }

    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag) {
        super.onUpdate(itemstack, world, entity, i, flag);
        if (!(entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) entity; // render particles only for players
        if (player.isInsideOfMaterial(Material.WATER)) return;
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
            world.spawnParticle(EnumParticleTypes.FLAME, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
        if (weapon.getItemRand().nextInt(5) == 0) {
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, player.posX + particleX, player.posY + particleY,
                    player.posZ + particleZ, 0.0D, 0.0D, 0.0D);
        }
    }
}
