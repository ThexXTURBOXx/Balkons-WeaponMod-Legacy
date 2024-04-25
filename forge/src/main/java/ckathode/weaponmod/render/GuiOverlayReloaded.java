package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.item.IItemWeapon;
import ckathode.weaponmod.item.RangedComponent;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class GuiOverlayReloaded extends GuiComponent {
    private final Minecraft mc;

    public GuiOverlayReloaded(Minecraft minecraft) {
        mc = minecraft;
    }

    @SubscribeEvent
    public void renderGUIOverlay(RenderGameOverlayEvent.Pre e) {
        if (e.getType() != RenderGameOverlayEvent.ElementType.HOTBAR)
            return;

        Player p = mc.player;
        if (p == null) return;
        int currentItem = p.inventory.selected;
        ItemStack is = p.getUseItem();
        ItemStack current = p.inventory.getItem(currentItem);
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

        setBlitOffset(-90); // at the same level as the hotbar itself
        mc.getEntityRenderDispatcher().textureManager.bind(WeaponModResources.Gui.OVERLAY);
        blit(e.getMatrixStack(), x0, y0 - height, tx, offset + 24 - height, width, height);
    }
}
