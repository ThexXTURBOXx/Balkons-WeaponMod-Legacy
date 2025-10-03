package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import ckathode.weaponmod.item.ItemBlowgunDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderBlowgunDart extends WMRenderer<EntityBlowgunDart, RenderBlowgunDart.BlowgunDartRenderState> {

    public RenderBlowgunDart(Context context) {
        super(context);
    }

    @Override
    public void submit(BlowgunDartRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        float[] color = entityRenderState.dartColor;
        float f11 = entityRenderState.shakeTime;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f12));
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.scale(0.05625f, 0.05625f, 0.05625f);
        poseStack.translate(-1.0f, 0.0f, 0.0f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderType.entityCutout(WeaponModResources.Entity.DART),
                (pose, consumer) -> {
                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);

                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f,
                            0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f,
                            0.46875f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.625f,
                            0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.625f,
                            0.05625f, 0.0f, 0.0f, lm);

                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);

                    drawVertex(pose, consumer, -5.0f, 2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f,
                            -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, 2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f,
                            0.46875f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.625f,
                            -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -5.0f, -2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.625f,
                            -0.05625f, 0.0f, 0.0f, lm);
                });

        for (int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            submitNodeCollector.submitCustomGeometry(poseStack,
                    RenderType.entityCutout(WeaponModResources.Entity.DART),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, -6.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -6.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);

                        drawVertex(pose, consumer, -6.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f,
                                0.3125f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.3125f,
                                0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 6.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.46875f,
                                0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -6.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f,
                                0.46875f, 0.0f, 0.0f, 0.05625f, lm);
                    });
        }
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @NotNull
    @Override
    public BlowgunDartRenderState createRenderState() {
        return new BlowgunDartRenderState();
    }

    @Override
    public void extractRenderState(EntityBlowgunDart entity, BlowgunDartRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
        entityRenderState.dartColor = ItemBlowgunDart.getColor(entity.getWeapon());
    }

    public static class BlowgunDartRenderState extends WMRendererState {
        public float shakeTime;
        public float[] dartColor;
    }

}
