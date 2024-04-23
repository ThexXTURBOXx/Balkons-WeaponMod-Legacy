package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends AbstractGui {
    private final Minecraft mc;

    public GuiOverlayReloaded(Minecraft minecraft) {
        mc = minecraft;
    }

    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        PlayerEntity p = mc.player;
        if (p == null) return;
        int currentItem = p.inventory.currentItem;
        ItemStack is = p.getActiveItemStack();
        ItemStack current = p.inventory.getStackInSlot(currentItem);
        ItemStack offHandItem = p.getHeldItemOffhand();
        if (is.isEmpty()) return;
        Item item = is.getItem();
        if (!(item instanceof IItemWeapon)) return;
        RangedComponent rc = ((IItemWeapon) item).getRangedComponent();
        if (rc == null) return;

        HandSide offHandSide = p.getPrimaryHand().opposite();
        Hand hand = p.getActiveHand();
        if (hand == Hand.MAIN_HAND && is != current ||
            hand == Hand.OFF_HAND && is != offHandItem) return;

        float f;
        int offset;
        if (RangedComponent.isReloaded(is)) {
            f = 1.0f;
            offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
        } else {
            f = Math.min(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 1.0f);
            offset = 0;
        }

        MainWindow window = Minecraft.getInstance().getMainWindow();
        int x0 = window.getScaledWidth() / 2 + (hand == Hand.OFF_HAND ?
                (offHandSide == HandSide.LEFT ? -120 : 91)
                : -91 - 1 + currentItem * 20);
        int y0 = window.getScaledHeight() + 1;
        int tx = hand == Hand.OFF_HAND ? (offHandSide == HandSide.LEFT ? 24 : 53) : 0;
        int width = hand == Hand.OFF_HAND ? 29 : 24;
        int height = (int) (f * 24);

        setBlitOffset(-90); // at the same level as the hotbar itself
        mc.getRenderManager().textureManager.bindTexture(WeaponModResources.Gui.OVERLAY);
        blit(x0, y0 - height, tx, offset + 24 - height, width, height);
    }
}
