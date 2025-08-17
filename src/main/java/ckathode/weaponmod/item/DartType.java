package ckathode.weaponmod.item;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import java.util.Arrays;
import java.util.List;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public final class DartType {

    public static final TShortObjectMap<DartType> DART_TYPES = new TShortObjectHashMap<>();

    public static final DartType DAMAGE = new DartType((short) 0, "dart",
            new float[]{0.2f, 0.8f, 0.3f}, new PotionEffect(MobEffects.POISON, 120, 0));
    public static final DartType HUNGER = new DartType((short) 1, "dart.hunger",
            new float[]{0.9f, 0.7f, 1.0f}, new PotionEffect(MobEffects.HUNGER, 360, 0));
    public static final DartType SLOW = new DartType((short) 2, "dart.slow",
            new float[]{0.6f, 1.0f, 0.9f}, new PotionEffect(MobEffects.SLOWNESS, 360, 1));
    public static final DartType DAMAGE_2 = new DartType((short) 3, "dart.damage",
            new float[]{0.8f, 0.5f, 0.2f}, new PotionEffect(MobEffects.POISON, 120, 1));

    public final short typeID;
    public final String typeName;
    public final float[] color;
    public final List<PotionEffect> potionEffects;

    /**
     * By calling this function, you also automatically register your dart type, item etc.
     * (you need to register some stuff like crafting recipes on your own, though).
     * Just be sure to call this function <b>before</b> BWM:L pre-inits.
     * Otherwise, you need to register some stuff on your own.
     *
     * @param typeID        The type ID to use - this is also the metadata for the created dart item.
     * @param typeName      A name for the dart used to locate the correct model to use.
     *                      Convention: "dart" or "dart.something".
     * @param color         The colour of the dart when rendering the corresponding entity.
     * @param potionEffects When the dart hits an entity, apply these effects (can also be empty).
     */
    public DartType(short typeID, String typeName, float[] color, PotionEffect... potionEffects) {
        DART_TYPES.put(typeID, this);
        this.typeID = typeID;
        this.typeName = typeName;
        this.color = color;
        this.potionEffects = Arrays.asList(potionEffects);
    }

}
