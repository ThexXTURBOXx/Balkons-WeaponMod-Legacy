package ckathode.weaponmod.entity.projectile;

import net.minecraft.world.item.ItemStack;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    float[] getColorFromMaterialID(int materialId);
}
