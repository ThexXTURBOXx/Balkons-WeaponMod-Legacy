package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends AbstractGui {
    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (!BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded.get() ||
            e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        Minecraft mc = Minecraft.getInstance();
        PlayerEntity p = mc.player;
        if (p == null) return;

        renderForHand(Hand.MAIN_HAND, p);
        renderForHand(Hand.OFF_HAND, p);
    }

    private void renderForHand(Hand hand, PlayerEntity p) {
        Minecraft mc = Minecraft.getInstance();
        int currentItem = p.inventory.currentItem;

        IItemWeapon item = null;
        ItemStack is = hand == Hand.OFF_HAND
                ? p.getHeldItemOffhand()
                : p.inventory.getStackInSlot(currentItem);

        if (!is.isEmpty() && is.getItem() instanceof IItemWeapon) item = (IItemWeapon) is.getItem();
        if (item == null) return;

        RangedComponent rc = item.getRangedComponent();
        MeleeComponent mec = item.getMeleeComponent();

        boolean set = false;
        float f = 0;
        int offset = 0;
        if (rc != null) {
            if (p.getActiveHand() == hand) {
                if (RangedComponent.isReloaded(is)) {
                    f = 1.0f;
                    offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
                } else {
                    f = MathHelper.clamp(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 0, 1);
                }
                set = true;
            }
        } else if (mec != null) {
            if (mec.shouldRenderCooldown()) {
                f = MathHelper.clamp(mec.getCooldown(), 0, 1);
                set = true;
            }
        }

        if (!set) return;

        HandSide offHandSide = p.getPrimaryHand().opposite();
        MainWindow window = mc.getMainWindow();
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
