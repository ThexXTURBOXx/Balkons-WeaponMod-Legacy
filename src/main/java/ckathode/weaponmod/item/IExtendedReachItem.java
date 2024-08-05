package ckathode.weaponmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedReachItem {
    float getExtendedReach(World world, LivingEntity attacker, ItemStack stack);
}
