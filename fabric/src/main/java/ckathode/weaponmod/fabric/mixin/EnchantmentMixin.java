package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.fabric.WMFabricHooks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Inject(method = "canEnchant", at = @At(value = "HEAD"), cancellable = true)
    public void canEnchant(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (WMFabricHooks.canDefinitelyEnchant(stack, (Enchantment) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

}
