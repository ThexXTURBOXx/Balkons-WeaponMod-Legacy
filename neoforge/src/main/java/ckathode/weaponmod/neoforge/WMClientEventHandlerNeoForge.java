package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WMClientEventHandler;
import ckathode.weaponmod.render.GuiOverlayReloaded;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class WMClientEventHandlerNeoForge {

    public static class MainEvents {

        @SubscribeEvent
        public void onFOVUpdateEvent(ComputeFovModifierEvent e) {
            e.setNewFovModifier(WMClientEventHandler.getNewFOV(e.getPlayer(), e.getFovModifier(),
                    e.getNewFovModifier()));
        }

    }

    public static class ModEvents {

        @SubscribeEvent
        public void registerGameOverlay(RegisterGuiLayersEvent e) {
            e.registerBelow(VanillaGuiLayers.HOTBAR, BalkonsWeaponMod.id("overlay"),
                    (guiGraphics, partialTicks) -> GuiOverlayReloaded.renderGUIOverlay(guiGraphics));
        }

    }

}
