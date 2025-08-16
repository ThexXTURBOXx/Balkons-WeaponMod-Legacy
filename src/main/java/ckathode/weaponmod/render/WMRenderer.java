package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public abstract class WMRenderer<T extends Entity> extends Render<T> {

    protected final RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();

    protected WMRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @SuppressWarnings("deprecation")
    protected void renderItem(ItemStack stack) {
        // use fully-qualified name to avoid deprecation warning
        itemRender.renderItem(stack, net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.NONE);
    }

}
