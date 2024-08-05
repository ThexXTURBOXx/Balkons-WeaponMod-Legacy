package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedReachItem {
    float getExtendedReach(World world, EntityLivingBase attacker, ItemStack stack);
}
