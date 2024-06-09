package ckathode.weaponmod.entity.projectile;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MaterialRegistry {
    private static final float[] NO_MATERIAL_COLOR = new float[]{1.0f, 1.0f, 1.0f};
    private static final Map<Integer, ICustomProjectileMaterials> CUSTOM_MATERIALS =
            new HashMap<>(Item.ToolMaterial.values().length);

    public static void registerCustomProjectileMaterial(ICustomProjectileMaterials customprojectilematerial) {
        int[] allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (int i : allMaterialIDs) {
            CUSTOM_MATERIALS.put(i, customprojectilematerial);
        }
    }

    public static int getMaterialID(ItemStack itemstack) {
        for (ICustomProjectileMaterials mat : CUSTOM_MATERIALS.values()) {
            int i = mat.getMaterialID(itemstack);
            if (i >= Item.ToolMaterial.values().length) {
                return i;
            }
        }
        return -1;
    }

    public static float[] getColorFromMaterialID(int id) {
        ICustomProjectileMaterials mat = CUSTOM_MATERIALS.get(id);
        if (mat != null) {
            return mat.getColorFromMaterialID(id);
        }
        return MaterialRegistry.NO_MATERIAL_COLOR;
    }

}
