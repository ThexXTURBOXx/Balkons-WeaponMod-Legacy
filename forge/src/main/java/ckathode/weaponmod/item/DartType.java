package ckathode.weaponmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class DartType {
    public static final DartType[] dartTypes = new DartType[128];
    public static final DartType damage = new DartType((byte) 0, "dart",
            new MobEffectInstance(MobEffects.POISON, 120, 0));
    public static final DartType hunger = new DartType((byte) 1, "dart.hunger",
            new MobEffectInstance(MobEffects.HUNGER, 360, 0));
    public static final DartType slow = new DartType((byte) 2, "dart.slow",
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 360, 1));
    public static final DartType damage2 = new DartType((byte) 3, "dart.damage",
            new MobEffectInstance(MobEffects.POISON, 120, 1));

    public final byte typeID;
    public final String typeName;
    public final MobEffectInstance potionEffect;

    public DartType(byte id, String typename, MobEffectInstance potioneffect) {
        dartTypes[id] = this;
        typeID = id;
        typeName = typename;
        potionEffect = potioneffect;
    }

}
