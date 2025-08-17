package ckathode.weaponmod.item;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import java.util.Arrays;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public final class DartType {

    public static final TShortObjectMap<DartType> DART_TYPES = new TShortObjectHashMap<>();

    public static final DartType DAMAGE = new DartType((short) 0, "dart", Blocks.cactus,
            new float[]{0.2f, 0.8f, 0.3f}, new PotionEffect(Potion.poison.id, 120, 0));
    public static final DartType HUNGER = new DartType((short) 1, "dart.hunger", Items.rotten_flesh,
            new float[]{0.9f, 0.7f, 1.0f}, new PotionEffect(Potion.hunger.id, 360, 0));
    public static final DartType SLOW = new DartType((short) 2, "dart.slow", "slimeball",
            new float[]{0.6f, 1.0f, 0.9f}, new PotionEffect(Potion.moveSlowdown.id, 360, 1));
    public static final DartType DAMAGE_2 = new DartType((short) 3, "dart.damage", Items.spider_eye,
            new float[]{0.8f, 0.5f, 0.2f}, new PotionEffect(Potion.poison.id, 120, 1));

    public final short typeID;
    public final String typeName;
    public final Object craftItem;
    public final float[] color;
    public final List<PotionEffect> potionEffects;

    public IIcon itemIcon;

    /**
     * By calling this function, you also automatically register your dart type, item, recipes etc.
     * Just be sure to call this function <b>before</b> BWM:L pre-inits.
     * Otherwise, you need to register some stuff on your own.
     *
     * @param typeID        The type ID to use - this is also the metadata for the created dart item.
     * @param typeName      A name for the dart used to locate the correct icon to use.
     *                      Convention: "dart" or "dart.something".
     * @param craftItem     The item to use when crafting the dart. Can also be a OreDictionary string.
     * @param color         The colour of the dart when rendering the corresponding entity.
     * @param potionEffects When the dart hits an entity, apply these effects (can also be empty).
     */
    public DartType(short typeID, String typeName, Object craftItem, float[] color, PotionEffect... potionEffects) {
        DART_TYPES.put(typeID, this);
        this.typeID = typeID;
        this.typeName = typeName;
        this.craftItem = craftItem;
        this.color = color;
        this.potionEffects = Arrays.asList(potionEffects);
    }

    public String getIconVariantName() {
        return this.typeName.replaceFirst("dart\\.?", ".").replaceFirst("\\.$", "");
    }

}
