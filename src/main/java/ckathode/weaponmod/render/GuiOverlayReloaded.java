package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.MeleeComponent;
import ckathode.weaponmod.item.RangedComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiOverlayReloaded extends Gui {

    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (!BalkonsWeaponMod.instance.modConfig.guiOverlayReloaded ||
            e.type != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer p = mc.thePlayer;
        if (p == null) return;
        if (!p.isUsingItem()) return;
        int currentItem = p.inventory.currentItem;
        ItemStack is = p.getCurrentEquippedItem();
        if (is == null) return;
        Item item = is.getItem();
        if (!(item instanceof IItemWeapon)) return;

        RangedComponent rc = ((IItemWeapon) item).getRangedComponent();
        MeleeComponent mec = ((IItemWeapon) item).getMeleeComponent();
        if (is != p.inventory.getStackInSlot(currentItem)) return;

        boolean set = false;
        float f = 0;
        int offset = 0;
        if (rc != null) {
            if (RangedComponent.isReloaded(is)) {
                f = 1.0f;
                offset = RangedComponent.isReadyToFire(is) ? 48 : 24;
            } else {
                f = MathHelper.clamp_float(p.getItemInUseDuration() / (float) rc.getReloadDuration(is), 0, 1);
            }
            set = true;
        } else if (mec != null) {
            if (mec.shouldRenderCooldown()) {
                f = MathHelper.clamp_float(mec.getCooldown(), 0, 1);
                set = true;
            }
        }

        if (!set) return;

        ScaledResolution res = e.resolution;
        int x0 = res.getScaledWidth() / 2 - 91 - 1 + currentItem * 20;
        int y0 = res.getScaledHeight() + 1;
        int height = (int) (f * 24);

        zLevel = -90; // at the same level as the hotbar itself
        mc.getRenderManager().renderEngine.bindTexture(WeaponModResources.Gui.OVERLAY);
        drawTexturedModalRect(x0, y0 - height, 0, offset + 24 - height, 24, height);
    }
}
