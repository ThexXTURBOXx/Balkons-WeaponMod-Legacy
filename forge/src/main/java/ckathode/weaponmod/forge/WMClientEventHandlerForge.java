package ckathode.weaponmod.forge;

import ckathode.weaponmod.WMClientEventHandler;
import ckathode.weaponmod.render.GuiOverlayReloaded;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class WMClientEventHandlerForge {

    public static class MainEvents {

        @SubscribeEvent
        public void onFOVUpdateEvent(ComputeFovModifierEvent e) {
            e.setNewFovModifier(WMClientEventHandler.getNewFOV(e.getPlayer(), e.getFovModifier(),
                    e.getNewFovModifier()));
        }

    }

    public static class ModEvents {

        @SubscribeEvent
        public void registerGameOverlay(RegisterGuiOverlaysEvent e) {
            e.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "overlay",
                    (forgeGui, ms, f, i, j) -> GuiOverlayReloaded.renderGUIOverlay(ms));
        }

    }

}
