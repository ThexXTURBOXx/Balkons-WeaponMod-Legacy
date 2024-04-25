package ckathode.weaponmod.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IExtendedReachItem {
    float getExtendedReach(Level p0, LivingEntity p1, ItemStack p2);
}
