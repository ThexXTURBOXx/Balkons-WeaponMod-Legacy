package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityDummy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderDummy extends WMRenderer<EntityDummy, RenderDummy.DummyRenderState> {

    private final ModelDummy modelDummy;

    public RenderDummy(Context context) {
        super(context);
        modelDummy = new ModelDummy(context.bakeLayer(ModelDummy.MAIN_LAYER));
        shadowRadius = 0.7f;
    }

    @Override
    public void submit(DummyRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();
        poseStack.translate(0, -0.025f, 0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entityRenderState.yRot));
        float f2 = entityRenderState.timeSinceHit;
        float f3 = entityRenderState.currentDamage;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entityRenderState.rockDirection / 5.0f));
        }
        poseStack.scale(-1.0f, -1.0f, 1.0f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
        submitNodeCollector.submitModel(modelDummy, entityRenderState, poseStack,
                RenderType.entityCutout(WeaponModResources.Entity.DUMMY), lm, OverlayTexture.NO_OVERLAY,
                -1, null, entityRenderState.outlineColor, null);
        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @NotNull
    @Override
    public DummyRenderState createRenderState() {
        return new DummyRenderState();
    }

    @Override
    public void extractRenderState(EntityDummy entity, DummyRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.timeSinceHit = entity.getTimeSinceHit() - f;
        entityRenderState.currentDamage = entity.getCurrentDamage() - f;
        entityRenderState.rockDirection = entity.getRockDirection();
    }

    public static class DummyRenderState extends WMRendererState {
        public float timeSinceHit;
        public float currentDamage;
        public int rockDirection;
    }

}
