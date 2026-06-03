package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderDynamite extends WMRenderer<EntityDynamite, RenderDynamite.DynamiteRenderState> {

    public RenderDynamite(Context context) {
        super(context);
    }

    @Override
    public void submit(DynamiteRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot + 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        float f11 = -entityRenderState.partialTicks;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f12));
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.scale(0.05625f, 0.05625f, 0.05625f);
        poseStack.translate(-4.0f, 0.0f, 0.0f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderTypes.entityCutout(WeaponModResources.Entity.DYNAMITE),
                (pose, consumer) -> {
                    drawVertex(pose, consumer, -7.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -7.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                });
        for (int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            submitNodeCollector.submitCustomGeometry(poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.DYNAMITE),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, -8.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -8.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                    });
        }
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @NotNull
    @Override
    public DynamiteRenderState createRenderState() {
        return new DynamiteRenderState();
    }

    public static class DynamiteRenderState extends WMRendererState {
    }

}
