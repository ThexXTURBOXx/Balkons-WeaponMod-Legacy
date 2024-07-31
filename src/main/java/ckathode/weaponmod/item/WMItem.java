package ckathode.weaponmod.item;

import ckathode.weaponmod.BalkonsWeaponMod;
import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WMItem extends Item {
    public WMItem(String id) {
        this(BalkonsWeaponMod.MOD_ID, id);
    }

    public WMItem(String modId, String id) {
        setRegistryName(new ResourceLocation(modId, id));
        setTranslationKey(id);
        setCreativeTab(CreativeTabs.COMBAT);
    }

    public static UUID getAttackDamageModifierUUID() {
        return Item.ATTACK_DAMAGE_MODIFIER;
    }

    public static UUID getAttackSpeedModifierUUID() {
        return Item.ATTACK_SPEED_MODIFIER;
    }

    @Override
    public boolean isShield(@Nonnull ItemStack stack, @Nullable EntityLivingBase entity) {
        if (entity != null && entity.getActiveItemStack() == stack && stack.getItem() instanceof WMItem &&
            entity.isActiveItemStackBlocking())
            return true;
        return super.isShield(stack, entity);
    }
}
