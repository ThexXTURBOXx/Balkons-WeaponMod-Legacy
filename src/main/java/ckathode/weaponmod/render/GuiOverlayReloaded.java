package ckathode.weaponmod.render;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends Gui {
    private final Minecraft mc;

    public GuiOverlayReloaded(final Minecraft minecraft) {
        this.mc = minecraft;
    }

    @SubscribeEvent
    public void renderGUIOverlay(final RenderGameOverlayEvent e) {
        if (e instanceof RenderGameOverlayEvent.Post || e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        final EntityPlayer p = this.mc.player;
        if (p != null) {
            final ItemStack is = p.getHeldItemMainhand();
            if (!is.isEmpty() && is.getItem() instanceof IItemWeapon && ((IItemWeapon) is.getItem()).getRangedComponent() != null) {
                GlStateManager.pushLightingAttrib();
                final RangedComponent rc = ((IItemWeapon) is.getItem()).getRangedComponent();
                final boolean rld = RangedComponent.isReloaded(is);
                GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableLighting();
                float f;
                int color;
                if (rld) {
                    f = 1.0f;
                    if (p.getActiveItemStack() == is && RangedComponent.isReadyToFire(is)) {
                        color = 1623588864;
                    } else {
                        color = 1614056960;
                    }
                } else if (p.getActiveItemStack() == is) {
                    f = Math.min(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 1.0f);
                    color = 1625991168;
                } else {
                    f = 0.0f;
                    color = 0;
                }
                int i;
                for (i = 0; i < 9 && p.inventory.getStackInSlot(i) != is; ++i) {
                }
                final int x0 = Minecraft.getInstance().mainWindow.getScaledWidth() / 2 - 88 + i * 20;
                final int y0 = Minecraft.getInstance().mainWindow.getScaledHeight() - 3;
                drawRect(x0, y0, x0 + 16, y0 - (int) (f * 16.0f), color);
                GlStateManager.popAttrib();
            }
        }
    }
}
