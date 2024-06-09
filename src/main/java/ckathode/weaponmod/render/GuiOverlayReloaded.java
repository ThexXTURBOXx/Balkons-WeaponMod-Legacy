package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
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
    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (!BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded.get() ||
            e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        Minecraft mc = Minecraft.getInstance();
        EntityPlayer p = mc.player;
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

        EnumHandSide offHandSide = p.getPrimaryHand().opposite();
        EnumHand hand = p.getActiveHand();
        if (hand == EnumHand.MAIN_HAND && is != current ||
            hand == EnumHand.OFF_HAND && is != offHandItem) return;

        float f;
        int offset;
        if (RangedComponent.isReloaded(is)) {
            f = 1.0f;
            offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
        } else {
            f = Math.min(p.getItemInUseMaxCount() / (float) rc.getReloadDuration(is), 1.0f);
            offset = 0;
        }

        MainWindow window = Minecraft.getInstance().mainWindow;
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
