package ckathode.weaponmod.entity.projectile;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;

public class MaterialRegistry {
    private static final float[] NO_MATERIAL_COLOR = new float[]{1.0f, 1.0f, 1.0f};
    private static Map<Integer, ICustomProjectileMaterials> customMaterials;

    public static void registerCustomProjectileMaterial(ICustomProjectileMaterials customprojectilematerial) {
        if (MaterialRegistry.customMaterials == null) {
            MaterialRegistry.customMaterials = new HashMap<>(4);
        }
        int[] allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (int i : allMaterialIDs) {
            MaterialRegistry.customMaterials.put(i, customprojectilematerial);
        }
    }

    public static int getMaterialID(ItemStack itemstack) {
        if (MaterialRegistry.customMaterials != null) {
            for (ICustomProjectileMaterials mat : MaterialRegistry.customMaterials.values()) {
                int i = mat.getMaterialID(itemstack);
                if (i > 4) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static float[] getColorFromMaterialID(int id) {
        if (MaterialRegistry.customMaterials != null) {
            ICustomProjectileMaterials mat = MaterialRegistry.customMaterials.get(id);
            if (mat != null) {
                return mat.getColorFromMaterialID(id);
            }
        }
        return MaterialRegistry.NO_MATERIAL_COLOR;
    }

    public static int getOrdinal(IItemTier tier) {
        if (tier instanceof ItemTier) {
            return ((ItemTier) tier).ordinal();
        }
        return -1;
    }

}
