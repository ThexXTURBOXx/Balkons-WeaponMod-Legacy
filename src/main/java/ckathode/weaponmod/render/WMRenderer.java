package ckathode.weaponmod.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public abstract class WMRenderer<T extends Entity> extends EntityRenderer<T> {

    protected final ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();

    protected WMRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @SuppressWarnings("deprecation")
    protected void renderItem(ItemStack stack) {
        // use fully-qualified name to avoid deprecation warning
        itemRender.renderItem(stack, net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType.NONE);
    }

}
