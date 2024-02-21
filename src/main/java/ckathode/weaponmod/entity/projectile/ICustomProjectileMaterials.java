package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.*;
import net.minecraftforge.fml.relauncher.*;

public interface ICustomProjectileMaterials
{
    int[] getAllMaterialIDs();
    
    int getMaterialID(final ItemStack p0);
    
    @SideOnly(Side.CLIENT)
    float[] getColorFromMaterialID(final int p0);
}
