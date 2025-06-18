package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GuiOverlayReloaded {

    public static void renderGUIOverlay(GuiGraphics guiGraphics) {
        if (!WeaponModConfig.get().guiOverlayReloaded) return;

        Minecraft mc = Minecraft.getInstance();
        Player p = mc.player;
        if (p == null) return;

        renderForHand(InteractionHand.MAIN_HAND, p, guiGraphics);
        renderForHand(InteractionHand.OFF_HAND, p, guiGraphics);
    }

    private static void renderForHand(InteractionHand hand, Player p, GuiGraphics guiGraphics) {
        int currentItem = p.getInventory().getSelectedSlot();

        IItemWeapon item = null;
        ItemStack is = hand == InteractionHand.OFF_HAND
                ? p.getOffhandItem()
                : p.getInventory().getItem(currentItem);

        if (!is.isEmpty() && is.getItem() instanceof IItemWeapon) item = (IItemWeapon) is.getItem();
        if (item == null) return;

        RangedComponent rc = item.getRangedComponent();
        MeleeComponent mec = item.getMeleeComponent();

        boolean set = false;
        float f = 0;
        int offset = 0;
        if (rc != null) {
            if (p.getUsedItemHand() == hand) {
                if (RangedComponent.isReloaded(is)) {
                    f = 1.0f;
                    offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
                } else {
                    f = Mth.clamp(p.getTicksUsingItem() / (float) rc.getReloadDuration(is), 0, 1);
                }
                set = true;
            }
        } else if (mec != null) {
            if (mec.shouldRenderCooldown()) {
                f = Mth.clamp(mec.getCooldown(), 0, 1);
                set = true;
            }
        }

        if (!set) return;

        HumanoidArm offHandSide = p.getMainArm().getOpposite();
        int x0 = guiGraphics.guiWidth() / 2 + (hand == InteractionHand.OFF_HAND ?
                (offHandSide == HumanoidArm.LEFT ? -120 : 91)
                : -91 - 1 + currentItem * 20);
        int y0 = guiGraphics.guiHeight() + 1;
        int tx = hand == InteractionHand.OFF_HAND ? (offHandSide == HumanoidArm.LEFT ? 24 : 53) : 0;
        int width = hand == InteractionHand.OFF_HAND ? 29 : 24;
        int height = (int) (f * 24);

        guiGraphics.pose().pushMatrix();
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, WeaponModResources.Gui.OVERLAY, x0, y0 - height,
                tx, offset + 24 - height, width, height, 256, 256);
        guiGraphics.pose().popMatrix();
    }

}
