package ckathode.weaponmod.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedReachItem {
    float getExtendedReach(World p0, LivingEntity p1, ItemStack p2);
}
