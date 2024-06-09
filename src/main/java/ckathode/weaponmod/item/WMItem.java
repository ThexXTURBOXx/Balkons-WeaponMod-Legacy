package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.UUID;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(String id) {
        this(id, new Properties());
    }

    public WMItem(String id, Properties properties) {
        super(properties.group(ItemGroup.COMBAT));
        setRegistryName(new ResourceLocation(BalkonsWeaponMod.MOD_ID, id));
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.ATTACK_DAMAGE_MODIFIER;
    }

    public static UUID getAttackSpeedModifierUUID() {
        return Item.ATTACK_SPEED_MODIFIER;
    }
}
