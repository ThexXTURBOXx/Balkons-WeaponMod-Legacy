package ckathode.weaponmod.item;

import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class DartType
{
    public static DartType[] dartTypes;
    public static DartType damage;
    public static DartType hunger;
    public static DartType slow;
    public static DartType damage2;
    public final int typeID;
    public final String typeName;
    public PotionEffect potionEffect;
    
    public static DartType getDartTypeFromStack(final ItemStack itemstack) {
        final int damage = itemstack.getItemDamage();
        if (damage >= 0 && damage < DartType.dartTypes.length) {
            return DartType.dartTypes[damage];
        }
        return null;
    }
    
    public DartType(final int id, final String typename, final PotionEffect potioneffect) {
        DartType.dartTypes[id] = this;
        this.typeID = id;
        this.typeName = typename;
        this.potionEffect = potioneffect;
    }
    
    static {
        DartType.dartTypes = new DartType[128];
        DartType.damage = new DartType(0, "dart", new PotionEffect(MobEffects.POISON, 120, 0));
        DartType.hunger = new DartType(1, "dart_hunger", new PotionEffect(MobEffects.HUNGER, 360, 0));
        DartType.slow = new DartType(2, "dart_slow", new PotionEffect(MobEffects.SLOWNESS, 360, 1));
        DartType.damage2 = new DartType(3, "dart_damage", new PotionEffect(MobEffects.POISON, 120, 1));
    }
}
