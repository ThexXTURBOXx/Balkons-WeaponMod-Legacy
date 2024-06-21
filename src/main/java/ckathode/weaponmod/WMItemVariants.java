package ckathode.weaponmod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public final class WMItemVariants {

    private static final Set<ModelResourceLocation> ITEM_VARIANTS = new HashSet<>();

    public static void registerItemVariants(Item item, String... variants) {
        registerItemVariants(item.getRegistryName(), "inventory", item, variants);
    }

    public static void registerItemVariants(String id, String string2, Item item, String... variants) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(
                BalkonsWeaponMod.MOD_ID + ":" + item.getUnlocalizedName().substring(5), string2));
        ModelResourceLocation[] mrls = Arrays.stream(variants)
                .map(v -> new ModelResourceLocation(id + v, string2))
                .toArray(ModelResourceLocation[]::new);
        ModelBakery.registerItemVariants(item, mrls);
        ITEM_VARIANTS.addAll(Arrays.asList(mrls));
    }

    public static boolean itemVariantExists(ModelResourceLocation mrl) {
        return ITEM_VARIANTS.contains(mrl);
    }

}
