package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.fabric.WMFabricHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Redirect(method = "getAvailableEnchantmentResults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant" +
                                                "(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean canEnchant(EnchantmentCategory instance, Item item,
                                      @Local(argsOnly = true) ItemStack stack, @Local Enchantment enchantment) {
        return WMFabricHooks.canDefinitelyEnchant(stack, enchantment) || instance.canEnchant(item);
    }

}
