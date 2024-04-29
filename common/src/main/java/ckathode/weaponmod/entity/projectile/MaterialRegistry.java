package ckathode.weaponmod.entity.projectile;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

public class MaterialRegistry {

    private static final float[] NO_MATERIAL_COLOR = new float[]{1.0f, 1.0f, 1.0f};
    private static final Map<Integer, ICustomProjectileMaterials> CUSTOM_MATERIALS =
            new HashMap<>(Tiers.values().length);

    public static void registerCustomProjectileMaterial(ICustomProjectileMaterials customprojectilematerial) {
        int[] allMaterialIDs = customprojectilematerial.getAllMaterialIDs();
        for (int i : allMaterialIDs) {
            CUSTOM_MATERIALS.put(i, customprojectilematerial);
        }
    }

    public static int getMaterialID(ItemStack itemstack) {
        for (ICustomProjectileMaterials mat : CUSTOM_MATERIALS.values()) {
            int i = mat.getMaterialID(itemstack);
            if (i >= Tiers.values().length) {
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

    public static int getOrdinal(Tier tier) {
        if (tier instanceof Tiers) {
            return ((Tiers) tier).ordinal();
        }
        return -1;
    }

}
