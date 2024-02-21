package ckathode.weaponmod.entity.projectile;

import net.minecraft.item.*;
import java.util.*;

public class MaterialRegistry
{
    private static final float[] NO_MATERIAL_COLOR;
    private static Map<Integer, ICustomProjectileMaterials> customMaterials;
    
    public static void registerCustomProjectileMaterial(final ICustomProjectileMaterials customprojectilematerial) {
        if (MaterialRegistry.customMaterials == null) {
            MaterialRegistry.customMaterials = new HashMap<Integer, ICustomProjectileMaterials>(4);
        }
        final int[] allMaterialIDs;
        final int[] ai = allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (final int i : allMaterialIDs) {
            MaterialRegistry.customMaterials.put(i, customprojectilematerial);
        }
    }
    
    public static int getMaterialID(final ItemStack itemstack) {
        if (MaterialRegistry.customMaterials != null) {
            for (final ICustomProjectileMaterials mat : MaterialRegistry.customMaterials.values()) {
                final int i = mat.getMaterialID(itemstack);
                if (i > 4) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static float[] getColorFromMaterialID(final int id) {
        if (MaterialRegistry.customMaterials != null) {
            final ICustomProjectileMaterials mat = MaterialRegistry.customMaterials.get(id);
            if (mat != null) {
                return mat.getColorFromMaterialID(id);
            }
        }
        return MaterialRegistry.NO_MATERIAL_COLOR;
    }
    
    static {
        NO_MATERIAL_COLOR = new float[] { 1.0f, 1.0f, 1.0f };
    }
}
