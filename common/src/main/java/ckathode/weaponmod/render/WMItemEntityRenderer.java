package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public abstract class WMItemEntityRenderer<T extends Entity, S extends WMItemEntityRenderer.WMItemEntityRendererState>
        extends WMRenderer<T, S> {

    protected final ItemModelResolver itemModelResolver;

    protected WMItemEntityRenderer(Context context) {
        super(context);
        itemModelResolver = context.getItemModelResolver();
    }

    @Override
    public void submit(S entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (!WeaponModConfig.get().itemModelForEntity) {
            submitNormalRender(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
        } else {
            submitItemRender(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
        }
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    public abstract void submitNormalRender(S entityRenderState, PoseStack poseStack,
                                            SubmitNodeCollector submitNodeCollector,
                                            CameraRenderState cameraRenderState);

    public abstract void submitItemRender(S entityRenderState, PoseStack poseStack,
                                          SubmitNodeCollector submitNodeCollector,
                                          CameraRenderState cameraRenderState);

    public abstract ItemStack getRenderStack(T entity, S entityRenderState, float f);

    @Override
    public void extractRenderState(T entity, S entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        ItemStack weapon = getRenderStack(entity, entityRenderState, f);
        itemModelResolver.updateForTopItem(entityRenderState.itemRender, weapon,
                ItemDisplayContext.NONE, entity.level(), null, 0);
    }

    public static class WMItemEntityRendererState extends WMRenderer.WMRendererState {
        public final ItemStackRenderState itemRender = new ItemStackRenderState();
    }

}
