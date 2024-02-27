package ckathode.weaponmod.render;

import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends Gui {
    private final Minecraft mc;

    public GuiOverlayReloaded(Minecraft minecraft) {
        mc = minecraft;
    }

    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent e) {
        if (e instanceof RenderGameOverlayEvent.Post || e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        EntityPlayer p = mc.player;
        if (p == null) return;
        ItemStack is = p.getActiveItemStack();
        if (is.isEmpty()) return;
        Item item = is.getItem();
        if (!(item instanceof IItemWeapon)) return;
        RangedComponent rc = ((IItemWeapon) item).getRangedComponent();
        if (rc == null) return;

        EnumHandSide offHandSide = p.getPrimaryHand().opposite();
        EnumHand hand = p.getActiveHand();

        GlStateManager.pushLightingAttrib();
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableLighting();

        float f;
        int color;
        if (RangedComponent.isReloaded(is)) {
            f = 1.0f;
            color = RangedComponent.isReadyToFire(is) ? 0x60c60000 : 0x60348e00;
        } else {
            f = Math.min(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 1.0f);
            color = 0x60eaa800;
        }

        MainWindow window = Minecraft.getInstance().mainWindow;
        int x0 = window.getScaledWidth() / 2 + (hand == EnumHand.OFF_HAND ?
                (offHandSide == EnumHandSide.LEFT ? -117 : 101)
                : -88 + p.inventory.currentItem * 20);
        int y0 = window.getScaledHeight() - 3;

        drawRect(x0, y0, x0 + 16, y0 - (int) (f * 16.0f), color);
        GlStateManager.popAttrib();
    }
}
