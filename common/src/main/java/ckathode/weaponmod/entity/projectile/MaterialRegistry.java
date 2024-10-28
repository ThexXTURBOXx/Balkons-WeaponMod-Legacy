package ckathode.weaponmod.entity.projectile;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class MaterialRegistry {

    private static final int VANILLA_MATERIAL_COUNT = 6;
    private static final float[] NO_MATERIAL_COLOR = new float[]{1.0f, 1.0f, 1.0f};
    private static final Map<Integer, ICustomProjectileMaterials> CUSTOM_MATERIALS =
            new HashMap<>(VANILLA_MATERIAL_COUNT);

    public static void registerCustomProjectileMaterial(ICustomProjectileMaterials customprojectilematerial) {
        int[] allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (int i : allMaterialIDs) {
            CUSTOM_MATERIALS.put(i, customprojectilematerial);
        }
    }

    public static int getMaterialID(ItemStack itemstack) {
        for (ICustomProjectileMaterials mat : CUSTOM_MATERIALS.values()) {
            int i = mat.getMaterialID(itemstack);
            if (i >= VANILLA_MATERIAL_COUNT) {
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
        return NO_MATERIAL_COLOR;
    }

    public static int getOrdinal(ToolMaterial tier) {
        if (tier.equals(ToolMaterial.WOOD)) return 0;
        if (tier.equals(ToolMaterial.STONE)) return 1;
        if (tier.equals(ToolMaterial.IRON)) return 2;
        if (tier.equals(ToolMaterial.DIAMOND)) return 3;
        if (tier.equals(ToolMaterial.GOLD)) return 4;
        if (tier.equals(ToolMaterial.NETHERITE)) return 5;
        return -1;
    }

}
