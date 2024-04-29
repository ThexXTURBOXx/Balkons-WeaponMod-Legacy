package ckathode.weaponmod.entity.projectile;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    @Environment(EnvType.CLIENT)
    float[] getColorFromMaterialID(int materialId);
}
