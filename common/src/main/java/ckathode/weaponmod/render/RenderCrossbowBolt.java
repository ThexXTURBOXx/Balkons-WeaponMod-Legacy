package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCrossbowBolt extends WMRenderer<EntityCrossbowBolt, RenderCrossbowBolt.CrossbowBoltRenderState> {

    public RenderCrossbowBolt(Context context) {
        super(context);
    }

    @Override
    public void submit(CrossbowBoltRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        float f11 = entityRenderState.shakeTime;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f12));
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.scale(0.05625f, 0.05625f, 0.05625f);
        poseStack.translate(-1.0f, 0.0f, 0.0f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderTypes.entityCutout(WeaponModResources.Entity.BOLT),
                (pose, consumer) -> {
                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                });
        for (int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            submitNodeCollector.submitCustomGeometry(poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.BOLT),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, -6.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -6.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                    });
        }
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @NotNull
    @Override
    public CrossbowBoltRenderState createRenderState() {
        return new CrossbowBoltRenderState();
    }

    @Override
    public void extractRenderState(EntityCrossbowBolt entity, CrossbowBoltRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
    }

    public static class CrossbowBoltRenderState extends WMRendererState {
        public float shakeTime;
    }

}
