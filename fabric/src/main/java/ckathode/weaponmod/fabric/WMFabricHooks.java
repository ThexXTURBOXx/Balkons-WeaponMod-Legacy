package ckathode.weaponmod.fabric;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public class WMFabricHooks {

    public static boolean canDefinitelyEnchant(ItemStack stack, Enchantment enchantment) {
        if (stack.getItem() instanceof IItemWeapon) {
            IItemWeapon weapon = (IItemWeapon) stack.getItem();
            MeleeComponent meleeComponent = weapon.getMeleeComponent();
            RangedComponent rangedComponent = weapon.getRangedComponent();
            return (meleeComponent != null && meleeComponent.canApplyEnchantment(stack, enchantment)) ||
                   (rangedComponent != null && rangedComponent.canApplyEnchantment(stack, enchantment));
        }
        return false;
    }

}
