package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.WMClientEventHandler;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "RETURN"), cancellable = true)
    public void getFieldOfViewModifier(boolean bl, float f, CallbackInfoReturnable<Float> cir,
                                       @Local(name = "modifier") float g) {
        cir.setReturnValue(WMClientEventHandler.getNewFOV((LivingEntity) (Object) this, g, cir.getReturnValueF()));
    }

}
