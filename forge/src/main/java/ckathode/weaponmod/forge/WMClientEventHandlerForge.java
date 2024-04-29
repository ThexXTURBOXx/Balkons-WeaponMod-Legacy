package ckathode.weaponmod.forge;

import ckathode.weaponmod.WMClientEventHandler;
import ckathode.weaponmod.render.GuiOverlayReloaded;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class WMClientEventHandlerForge {

    @SubscribeEvent
    public void preRenderGameOverlay(RenderGameOverlayEvent.Pre e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        GuiOverlayReloaded.renderGUIOverlay(e.getMatrixStack());
    }

    @SubscribeEvent
    public void onFOVUpdateEvent(FOVUpdateEvent e) {
        e.setNewfov(WMClientEventHandler.getNewFOV(e.getEntity(), e.getFov(), e.getNewfov()));
    }

}
