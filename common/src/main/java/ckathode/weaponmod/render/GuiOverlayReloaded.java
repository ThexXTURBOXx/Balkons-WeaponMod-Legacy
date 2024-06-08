package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class GuiOverlayReloaded {

    public static void renderGUIOverlay(GuiGraphics guiGraphics) {
        if (!WeaponModConfig.get().guiOverlayReloaded) return;

        Minecraft mc = Minecraft.getInstance();
        Player p = mc.player;
        if (p == null) return;
        int currentItem = p.getInventory().selected;
        ItemStack is = p.getUseItem();
        ItemStack current = p.getInventory().getItem(currentItem);
        ItemStack offHandItem = p.getOffhandItem();
        if (is.isEmpty()) return;
        Item item = is.getItem();
        if (!(item instanceof IItemWeapon)) return;
        RangedComponent rc = ((IItemWeapon) item).getRangedComponent();
        if (rc == null) return;

        HumanoidArm offHandSide = p.getMainArm().getOpposite();
        InteractionHand hand = p.getUsedItemHand();
        if (hand == InteractionHand.MAIN_HAND && is != current ||
            hand == InteractionHand.OFF_HAND && is != offHandItem) return;

        float f;
        int offset;
        if (RangedComponent.isReloaded(is)) {
            f = 1.0f;
            offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
        } else {
            f = Math.min(p.getTicksUsingItem() / (float) rc.getReloadDuration(is), 1.0f);
            offset = 0;
        }

        Window window = Minecraft.getInstance().getWindow();
        int x0 = window.getGuiScaledWidth() / 2 + (hand == InteractionHand.OFF_HAND ?
                (offHandSide == HumanoidArm.LEFT ? -120 : 91)
                : -91 - 1 + currentItem * 20);
        int y0 = window.getGuiScaledHeight() + 1;
        int tx = hand == InteractionHand.OFF_HAND ? (offHandSide == HumanoidArm.LEFT ? 24 : 53) : 0;
        int width = hand == InteractionHand.OFF_HAND ? 29 : 24;
        int height = (int) (f * 24);

        guiGraphics.pose().pushPose();
        // -90 = at the same level as the hotbar itself
        guiGraphics.pose().translate(0, 0, -90);
        guiGraphics.blit(WeaponModResources.Gui.OVERLAY, x0, y0 - height, 0,
                tx, offset + 24 - height, width, height, 256, 256);
        guiGraphics.pose().popPose();
    }

}
