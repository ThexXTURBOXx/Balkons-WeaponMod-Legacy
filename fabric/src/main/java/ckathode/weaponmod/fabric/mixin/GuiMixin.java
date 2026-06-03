package ckathode.weaponmod.fabric.mixin;

import ckathode.weaponmod.render.GuiOverlayReloaded;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "extractHotbarAndDecorations", at = @At("HEAD"))
    public void renderHotbarAndDecorations(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        GuiOverlayReloaded.renderGUIOverlay(graphics);
    }

}
