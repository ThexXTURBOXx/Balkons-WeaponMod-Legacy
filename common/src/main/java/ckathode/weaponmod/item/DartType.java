package ckathode.weaponmod.item;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;

import static ckathode.weaponmod.item.ItemBlowgunDart.ID_PREFIX;

/**
 * By calling this function, you also automatically register your dart type, item etc.
 * (you need to register some stuff like crafting recipes on your own, though).
 * Just be sure to call this function <b>before</b> BWM:L pre-inits.
 * Otherwise, you need to register some stuff on your own.
 *
 * @param typeName       The registry name for the dart. Convention: ID_PREFIX or ID_PREFIX+".something".
 * @param color          The colour of the dart when rendering the corresponding entity.
 * @param potionContents The default potion data component to use for the item - can also be just PotionContents.EMPTY.
 */
public record DartType(String typeName, float[] color, PotionContents potionContents) {

    public static final List<DartType> DART_TYPES = new ArrayList<>();

    public static final DartType DAMAGE = new DartType(ID_PREFIX, new float[]{0.2f, 0.8f, 0.3f},
            new MobEffectInstance(MobEffects.POISON, 120, 0));
    public static final DartType HUNGER = new DartType(ID_PREFIX + ".hunger", new float[]{0.9f, 0.7f, 1.0f},
            new MobEffectInstance(MobEffects.HUNGER, 360, 0));
    public static final DartType SLOW = new DartType(ID_PREFIX + ".slow", new float[]{0.6f, 1.0f, 0.9f},
            new MobEffectInstance(MobEffects.SLOWNESS, 360, 1));
    public static final DartType DAMAGE_2 = new DartType(ID_PREFIX + ".damage", new float[]{0.8f, 0.5f, 0.2f},
            new MobEffectInstance(MobEffects.POISON, 120, 1));

    public DartType {
        DART_TYPES.add(this);
    }

    public DartType(String typeName, float[] color, MobEffectInstance... potionEffects) {
        this(typeName, color, new PotionContents(Holder.direct(new Potion(typeName, potionEffects))));
    }

}
