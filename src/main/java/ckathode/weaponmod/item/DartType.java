package ckathode.weaponmod.item;

import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class DartType {
    public static final DartType[] dartTypes = new DartType[128];
    public static final DartType damage = new DartType((byte) 0, "dart", new PotionEffect(MobEffects.POISON, 120, 0));
    public static final DartType hunger = new DartType((byte) 1, "dart.hunger", new PotionEffect(MobEffects.HUNGER, 360, 0));
    public static final DartType slow = new DartType((byte) 2, "dart.slow", new PotionEffect(MobEffects.SLOWNESS, 360, 1));
    public static final DartType damage2 = new DartType((byte) 3, "dart.damage", new PotionEffect(MobEffects.POISON, 120, 1));
    public final byte typeID;
    public final String typeName;
    public final PotionEffect potionEffect;

    public static DartType getDartTypeFromStack(final ItemStack itemstack) {
        final int damage = itemstack.getItemDamage();
        if (damage >= 0 && damage < DartType.dartTypes.length) {
            return DartType.dartTypes[damage];
        }
        return null;
    }

    public DartType(byte id, String typename, PotionEffect potioneffect) {
        dartTypes[id] = this;
        typeID = id;
        typeName = typename;
        potionEffect = potioneffect;
    }

}
