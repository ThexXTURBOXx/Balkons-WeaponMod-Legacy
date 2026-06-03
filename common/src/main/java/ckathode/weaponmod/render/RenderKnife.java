package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderKnife extends WMItemEntityRenderer<EntityKnife, RenderKnife.KnifeRenderState> {

    public RenderKnife(Context context) {
        super(context);
    }

    @Override
    public void submitNormalRender(KnifeRenderState entityRenderState, PoseStack poseStack,
                                   SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot));
        float[] color = entityRenderState.materialColor;
        float f13 = entityRenderState.shakeTime;
        if (f13 > 0.0f) {
            float f14 = -Mth.sin(f13 * 3.0f) * f13;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f14));
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(45.0f));
        poseStack.scale(0.05625f, 0.05625f, 0.05625f);
        poseStack.translate(-4.0f, 0.0f, 0.0f);
        submitNodeCollector.submitCustomGeometry(poseStack,
                RenderTypes.entityCutout(WeaponModResources.Entity.KNIFE),
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
                    RenderTypes.entityCutout(WeaponModResources.Entity.KNIFE),
                    (pose, consumer) -> {
                        drawVertex(pose, consumer, -8.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -8.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f,
                                0.3125f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f,
                                0.3125f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, 8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f,
                                0.46875f, 0.0f, 0.0f, 0.05625f, lm);
                        drawVertex(pose, consumer, -8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f,
                                0.46875f, 0.0f, 0.0f, 0.05625f, lm);
                    });
        }
        poseStack.popPose();
    }

    @Override
    public void submitItemRender(KnifeRenderState entityRenderState, PoseStack poseStack,
                                 SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        poseStack.pushPose();
        poseStack.scale(0.85f, 0.85f, 0.85f);
        poseStack.mulPose(Axis.YP.rotationDegrees(entityRenderState.yRot - 90.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entityRenderState.xRot - 45.0f));
        float f15 = entityRenderState.shakeTime;
        if (f15 > 0.0f) {
            float f16 = -Mth.sin(f15 * 3.0f) * f15;
            poseStack.mulPose(Axis.ZP.rotationDegrees(f16));
        }
        poseStack.translate(-0.15f, -0.15f, 0.0f);
        entityRenderState.itemRender.submit(poseStack, submitNodeCollector, entityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

    @Override
    public ItemStack getRenderStack(EntityKnife entity, KnifeRenderState entityRenderState, float f) {
        return entity.getWeapon();
    }

    @NotNull
    @Override
    public KnifeRenderState createRenderState() {
        return new KnifeRenderState();
    }

    @Override
    public void extractRenderState(EntityKnife entity, KnifeRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.shakeTime = entity.shakeTime - f;
        entityRenderState.weaponMaterialId = entity.getWeaponMaterialId();
        entityRenderState.materialColor = entity.getMaterialColor();
    }

    public static class KnifeRenderState extends WMItemEntityRendererState {
        public float shakeTime;
        public int weaponMaterialId;
        public float[] materialColor;
    }

}
