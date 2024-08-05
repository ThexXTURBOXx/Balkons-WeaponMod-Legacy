package ckathode.weaponmod.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IExtendedReachItem {
    float getExtendedReach(Level level, LivingEntity attacker, ItemStack stack);
}
