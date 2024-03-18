package ckathode.weaponmod.item;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class DartType {
    public static final DartType[] dartTypes = new DartType[128];
    public static final DartType damage = new DartType((byte) 0, "dart", new EffectInstance(Effects.POISON, 120, 0));
    public static final DartType hunger = new DartType((byte) 1, "dart.hunger", new EffectInstance(Effects.HUNGER, 360, 0));
    public static final DartType slow = new DartType((byte) 2, "dart.slow", new EffectInstance(Effects.SLOWNESS, 360, 1));
    public static final DartType damage2 = new DartType((byte) 3, "dart.damage", new EffectInstance(Effects.POISON, 120, 1));
    public final byte typeID;
    public final String typeName;
    public final EffectInstance potionEffect;

    public DartType(byte id, String typename, EffectInstance potioneffect) {
        dartTypes[id] = this;
        typeID = id;
        typeName = typename;
        potionEffect = potioneffect;
    }

}
