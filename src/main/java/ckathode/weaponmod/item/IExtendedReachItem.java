package ckathode.weaponmod.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IExtendedReachItem {
    float getExtendedReach(final World p0, final EntityLivingBase p1, final ItemStack p2);
}
