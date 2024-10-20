package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends Gui {
    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (!BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded.get() ||
            e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        Minecraft mc = Minecraft.getInstance();
        EntityPlayer p = mc.player;
        if (p == null) return;
        if (!p.isHandActive()) return;

        renderForHand(EnumHand.MAIN_HAND, p);
        renderForHand(EnumHand.OFF_HAND, p);
    }

    private void renderForHand(EnumHand hand, EntityPlayer p) {
        if (p.getActiveHand() != hand) return;

        Minecraft mc = Minecraft.getInstance();
        int currentItem = p.inventory.currentItem;

        IItemWeapon item = null;
        ItemStack is = hand == EnumHand.OFF_HAND
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
            if (RangedComponent.isReloaded(is)) {
                f = 1.0f;
                offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
            } else {
                f = MathHelper.clamp(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 0, 1);
            }
            set = true;
        } else if (mec != null) {
            if (mec.shouldRenderCooldown()) {
                f = MathHelper.clamp(mec.getCooldown(), 0, 1);
                set = true;
            }
        }

        if (!set) return;

        EnumHandSide offHandSide = p.getPrimaryHand().opposite();
        MainWindow window = mc.mainWindow;
        int x0 = window.getScaledWidth() / 2 + (hand == EnumHand.OFF_HAND ?
                (offHandSide == EnumHandSide.LEFT ? -120 : 91)
                : -91 - 1 + currentItem * 20);
        int y0 = window.getScaledHeight() + 1;
        int tx = hand == EnumHand.OFF_HAND ? (offHandSide == EnumHandSide.LEFT ? 24 : 53) : 0;
        int width = hand == EnumHand.OFF_HAND ? 29 : 24;
        int height = (int) (f * 24);

        zLevel = -90; // at the same level as the hotbar itself
        mc.getRenderManager().textureManager.bindTexture(WeaponModResources.Gui.OVERLAY);
        drawTexturedModalRect(x0, y0 - height, tx, offset + 24 - height, width, height);
    }

}
