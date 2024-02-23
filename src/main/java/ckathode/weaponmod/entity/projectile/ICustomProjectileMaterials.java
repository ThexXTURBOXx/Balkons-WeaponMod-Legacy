package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(final ItemStack p0);

    @SideOnly(Side.CLIENT)
    float[] getColorFromMaterialID(final int p0);
}
