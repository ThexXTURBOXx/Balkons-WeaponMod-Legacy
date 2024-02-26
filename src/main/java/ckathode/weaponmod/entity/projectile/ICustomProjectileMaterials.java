package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICustomProjectileMaterials {
    int[] getAllMaterialIDs();

    int getMaterialID(ItemStack stack);

    @OnlyIn(Dist.CLIENT)
    float[] getColorFromMaterialID(int materialId);
}
