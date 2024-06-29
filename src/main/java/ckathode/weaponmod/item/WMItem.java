package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.UUID;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public boolean isShield(ItemStack stack, @Nullable EntityLivingBase entity) {
        if (entity != null && entity.getActiveItemStack() == stack && stack.getItem() instanceof WMItem &&
            entity.isActiveItemStackBlocking())
            return true;
        return super.isShield(stack, entity);
    }

}
