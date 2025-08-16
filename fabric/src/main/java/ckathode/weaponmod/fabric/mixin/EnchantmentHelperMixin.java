package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.item.IItemWeapon;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Unique
    private static ItemStack stack;

    @Unique
    private static Enchantment enchantment;

    @Inject(method = "getAvailableEnchantmentResults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant" +
                                                "(Lnet/minecraft/world/item/Item;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private static void fetchLocals(int level, ItemStack stack, boolean allowTreasure,
                                    CallbackInfoReturnable<List<EnchantmentInstance>> cir,
                                    List<EnchantmentInstance> list, Item item, boolean bl, Iterator<Enchantment> iter,
                                    Enchantment enchantment) {
        EnchantmentHelperMixin.stack = stack;
        EnchantmentHelperMixin.enchantment = enchantment;
    }

    @Redirect(method = "getAvailableEnchantmentResults",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentCategory;canEnchant" +
                                                "(Lnet/minecraft/world/item/Item;)Z"))
    private static boolean canEnchant(EnchantmentCategory instance, Item item) {
        if (stack.getItem() instanceof IItemWeapon weapon) {
            return weapon.canApplyEnchantment(stack, enchantment);
        }
        return instance.canEnchant(item);
    }

}
