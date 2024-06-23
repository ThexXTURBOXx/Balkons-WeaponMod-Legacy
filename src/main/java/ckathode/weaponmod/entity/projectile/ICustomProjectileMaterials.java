package ckathode.weaponmod.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    @SideOnly(Side.CLIENT)
    float[] getColorFromMaterialID(int materialId);
}
