package ckathode.weaponmod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public final class WMItemVariants {

    private static final Set<String> ITEM_VARIANTS = new HashSet<>();
    private static final Set<IIcon> ITEM_VARIANT_ICONS = new HashSet<>();

    public static void expectItemVariants(Item item, String... variants) {
        expectItemVariants(item.getIconString(), variants);
    }

    public static void expectItemVariants(String id, String... variants) {
        ITEM_VARIANTS.addAll(Arrays.stream(variants)
                .map(v -> id + v)
                .collect(Collectors.toList()));
    }

    public static List<IIcon> registerItemVariants(IIconRegister register, Item item, String... variants) {
        return registerItemVariants(register, item.getIconString(), variants);
    }

    public static List<IIcon> registerItemVariants(IIconRegister register, String id, String... variants) {
        List<IIcon> icons = Arrays.stream(variants)
                .map(v -> id + v)
                .map(v -> ITEM_VARIANTS.contains(v) ? register.registerIcon(v) : null)
                .collect(Collectors.toList());
        ITEM_VARIANT_ICONS.addAll(icons);
        return icons;
    }

    public static boolean itemVariantExists(String icon) {
        return icon != null && ITEM_VARIANTS.contains(icon);
    }

    public static boolean itemVariantExists(IIcon icon) {
        return icon != null && ITEM_VARIANT_ICONS.contains(icon);
    }

}
