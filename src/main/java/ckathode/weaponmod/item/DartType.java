package ckathode.weaponmod.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class DartType {
    public static final DartType[] dartTypes = new DartType[128];
    public static final DartType damage = new DartType((byte) 0, "dart",
            new PotionEffect(Potion.poison.id, 120, 0), "blockCactus");
    public static final DartType hunger = new DartType((byte) 1, "dart.hunger",
            new PotionEffect(Potion.hunger.id, 360, 0), Items.rotten_flesh);
    public static final DartType slow = new DartType((byte) 2, "dart.slow",
            new PotionEffect(Potion.moveSlowdown.id, 360, 1), "slimeball");
    public static final DartType damage2 = new DartType((byte) 3, "dart.damage",
            new PotionEffect(Potion.poison.id, 120, 1), Items.spider_eye);
    public final byte typeID;
    public final String typeName;
    public final PotionEffect potionEffect;
    public final Object craftItem;

    public static DartType getDartTypeFromStack(final ItemStack itemstack) {
        final int damage = itemstack.getItemDamage();
        if (damage >= 0 && damage < DartType.dartTypes.length) {
            return DartType.dartTypes[damage];
        }
        return null;
    }

    public DartType(byte id, String typename, PotionEffect potioneffect, Object craftitem) {
        dartTypes[id] = this;
        typeID = id;
        typeName = typename;
        potionEffect = potioneffect;
        craftItem = craftitem;
    }

}
