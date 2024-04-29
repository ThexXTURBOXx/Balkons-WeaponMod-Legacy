package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.render.GuiOverlayReloaded;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "renderHotbar", at = @At("HEAD"))
    public void renderHotbar(float partialTicks, PoseStack matrixStack, CallbackInfo ci) {
        GuiOverlayReloaded.renderGUIOverlay(matrixStack);
    }

}
