package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    @SideOnly(Side.CLIENT)
    float[] getColorFromMaterialID(int materialId);
}
