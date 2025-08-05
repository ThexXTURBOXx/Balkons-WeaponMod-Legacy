package ckathode.weaponmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import static ckathode.weaponmod.item.ItemBlowgunDart.ID_PREFIX;

public record DartType(byte typeID, String typeName, MobEffectInstance potionEffect) {

    public static final DartType[] dartTypes = new DartType[128];
    public static final DartType damage = new DartType((byte) 0, ID_PREFIX,
            new MobEffectInstance(MobEffects.POISON, 120, 0));
    public static final DartType hunger = new DartType((byte) 1, ID_PREFIX + ".hunger",
            new MobEffectInstance(MobEffects.HUNGER, 360, 0));
    public static final DartType slow = new DartType((byte) 2, ID_PREFIX + ".slow",
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 360, 1));
    public static final DartType damage2 = new DartType((byte) 3, ID_PREFIX + ".damage",
            new MobEffectInstance(MobEffects.POISON, 120, 1));

    public DartType {
        dartTypes[typeID] = this;
    }

}
