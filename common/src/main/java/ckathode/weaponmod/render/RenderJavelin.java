package ckathode.weaponmod.render;

import ckathode.weaponmod.WMRegistries;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderJavelin extends WMItemEntityRenderer<EntityJavelin, RenderJavelin.JavelinRenderState> {

    public RenderJavelin(Context context) {
        super(context);
    }

    @Override
    public void submitNormalRender(JavelinRenderState entityRenderState, PoseStack poseStack,
                                   SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        float length = 20.0f;
        float f11 = entityRenderState.shakeTime;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f12));
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.scale(0.05625f, 0.05625f, 0.05625f);
        poseStack.translate(-4.0f, 0.0f, 0.0f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderTypes.entityCutout(WeaponModResources.Entity.JAVELIN),
                (pose, consumer) -> {
                    drawVertex(pose, consumer, -length, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                    drawVertex(pose, consumer, -length, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
                });
        for (int j = 0; j < 4; ++j) {
            poseStack.mulPose(Axis.XP.rotationDegrees(90.0f));
            submitNodeCollector.submitCustomGeometry(poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.JAVELIN),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, -length, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, length, -2.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, length, 2.0f, 0.0f, 1.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -length, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                    });
        }
        poseStack.popPose();
    }

    @Override
    public void submitItemRender(JavelinRenderState entityRenderState, PoseStack poseStack,
                                 SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.scale(1.7f, 1.7f, 1.7f);
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot - 45.0f));
        float f13 = entityRenderState.shakeTime;
        if (f13 > 0.0f) {
            float f14 = -Mth.sin(f13 * 3.0f) * f13;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f14));
        }
        poseStack.translate(-0.25f, -0.25f, 0.0f);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        entityRenderState.itemRender.submit(poseStack, submitNodeCollector, entityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    @Override
    public ItemStack getRenderStack(EntityJavelin entity, JavelinRenderState entityRenderState, float f) {
        return new ItemStack(WMRegistries.ITEM_JAVELIN.get());
    }

    @NotNull
    @Override
    public JavelinRenderState createRenderState() {
        return new JavelinRenderState();
    }

    @Override
    public void extractRenderState(EntityJavelin entity, JavelinRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
    }

    public static class JavelinRenderState extends WMItemEntityRendererState {
        public float shakeTime;
    }

}
