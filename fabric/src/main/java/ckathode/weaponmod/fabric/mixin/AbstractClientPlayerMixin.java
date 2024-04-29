package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.WMClientEventHandler;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "RETURN"), cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD)
    public void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir, float f) {
        cir.setReturnValue(WMClientEventHandler.getNewFOV((LivingEntity) (Object) this, f, cir.getReturnValueF()));
    }

}
