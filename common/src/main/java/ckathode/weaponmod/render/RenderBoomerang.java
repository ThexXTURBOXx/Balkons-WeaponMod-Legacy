package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderBoomerang extends WMItemEntityRenderer<EntityBoomerang, RenderBoomerang.BoomerangRenderState> {

    public RenderBoomerang(Context context) {
        super(context);
    }

    @Override
    public void submitNormalRender(BoomerangRenderState entityRenderState, PoseStack poseStack,
                                   SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        int material = entityRenderState.weaponMaterialId;
        float[] color = entityRenderState.materialColor;
        poseStack.translate(-0.5f, 0.0f, -0.5f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderType.entityCutout(WeaponModResources.Entity.BOOMERANG),
                (pose, consumer) -> {
                    drawVertex(pose, consumer, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 0.0f, 0.0f, 0.0f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, lm);
                    if (material != 0) {
                        drawVertex(pose, consumer, 0.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.0f,
                                0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 1.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.0f,
                                0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 1.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f,
                                0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 0.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f,
                                0.0f, 0.0f, 0.05625f, lm);
                    }
                    drawVertex(pose, consumer, 1.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, -1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 1.0f, 0.0f, 1.0f, 0.5f, 0.5f, 0.0f, -1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, -1.0f, 0.0f, lm);
                    drawVertex(pose, consumer, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, lm);
                    if (material != 0) {
                        drawVertex(pose, consumer, 1.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f,
                                0.0f, -1.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 1.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f,
                                0.0f, -1.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 0.0f, 0.0f, 1.0f, color[0], color[1], color[2], 1.0f, 1.0f, 0.0f,
                                0.0f, -1.0f, 0.0f, lm);
                        drawVertex(pose, consumer, 0.0f, 0.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.0f,
                                0.0f, -1.0f, 0.0f, lm);
                    }
                    drawVertex(pose, consumer, 0.2f, -0.08f, 0.8f, 0.5f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.2f, 0.08f, 0.8f, 0.5f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.9f, 0.08f, 0.8f, 0.0f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.9f, -0.08f, 0.8f, 0.0f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
                    if (material != 0) {
                        drawVertex(pose, consumer, 0.2f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f,
                                -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.2f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f,
                                0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.9f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 0.5f,
                                0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.9f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f,
                                -SQRT2, 0.0f, SQRT2, lm);
                    }
                    drawVertex(pose, consumer, 0.2f, -0.08f, 0.8f, 0.5f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.2f, 0.08f, 0.8f, 0.5f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.2f, 0.08f, 0.2f, 0.0f, 0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                    drawVertex(pose, consumer, 0.2f, -0.08f, 0.2f, 0.0f, 0.5f, -SQRT2, 0.0f, SQRT2, lm);
                    if (material != 0) {
                        drawVertex(pose, consumer, 0.2f, -0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f, 0.5f,
                                -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.2f, 0.08f, 0.8f, color[0], color[1], color[2], 1.0f, 1.0f,
                                0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.2f, 0.08f, 0.2f, color[0], color[1], color[2], 1.0f, 0.5f,
                                0.65625f, -SQRT2, 0.0f, SQRT2, lm);
                        drawVertex(pose, consumer, 0.2f, -0.08f, 0.2f, color[0], color[1], color[2], 1.0f, 0.5f, 0.5f,
                                -SQRT2, 0.0f, SQRT2, lm);
                    }
                });
        poseStack.popPose();
    }

    @Override
    public void submitItemRender(BoomerangRenderState entityRenderState, PoseStack poseStack,
                                 SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 0.85f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
        entityRenderState.itemRender.submit(poseStack, submitNodeCollector, entityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    @NotNull
    @Override
    public BoomerangRenderState createRenderState() {
        return new BoomerangRenderState();
    }

    @Override
    public void extractRenderState(EntityBoomerang entity, BoomerangRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.weaponMaterialId = entity.getWeaponMaterialId();
        entityRenderState.materialColor = entity.getMaterialColor();
    }

    @Override
    public ItemStack getRenderStack(EntityBoomerang entity, BoomerangRenderState entityRenderState, float f) {
        return entity.getWeapon();
    }


    public static class BoomerangRenderState extends WMItemEntityRendererState {
        public int weaponMaterialId;
        public float[] materialColor;
    }

}
