package ckathode.weaponmod.neoforge;

import ckathode.weaponmod.WMClientEventHandler;
import ckathode.weaponmod.render.GuiOverlayReloaded;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static ckathode.weaponmod.BalkonsWeaponMod.MOD_ID;

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
        public void registerGameOverlay(RegisterGuiLayersEvent e) {
            e.registerBelow(VanillaGuiLayers.HOTBAR, new ResourceLocation(MOD_ID, "overlay"),
                    (guiGraphics, partialTicks) -> GuiOverlayReloaded.renderGUIOverlay(guiGraphics));
        }

    }

}
