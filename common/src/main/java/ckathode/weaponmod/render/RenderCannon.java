package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCannon extends WMRenderer<EntityCannon, RenderCannon.CannonRenderState> {

    private final ModelCannonBarrel modelBarrel;
    private final ModelCannonStandard modelStandard;
    private final ModelCannonLegacy modelLegacy;

    public RenderCannon(Context context) {
        super(context);
        modelBarrel = new ModelCannonBarrel(context.bakeLayer(ModelCannonBarrel.CANNON_BARREL_LAYER));
        modelStandard = new ModelCannonStandard(context.bakeLayer(ModelCannonStandard.CANNON_STANDARD_LAYER));
        modelLegacy = new ModelCannonLegacy(context.bakeLayer(ModelCannonLegacy.CANNON_LEGACY_LAYER));
        shadowRadius = 0.7f;
    }

    @Override
    public void submit(CannonRenderState entityRenderState, PoseStack poseStack,
                       SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        int lm = entityRenderState.lightCoords;
        poseStack.pushPose();

        if (WeaponModConfig.get().legacyCannonModel) {
            poseStack.translate(0, 0.1, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(-entityRenderState.yRot));
        } else {
            poseStack.translate(0, 2.375f, 0);
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0f - entityRenderState.yRot));
        }

        float f2 = entityRenderState.hurtTime;
        float f3 = entityRenderState.currentDamage;
        if (f3 < 0.0f) f3 = 0.0f;
        if (f2 > 0.0f)
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entityRenderState.rockDirection / 5.0f));
        int color = 0xFFCCCCCC;
        if (entityRenderState.superPowered && entityRenderState.tickCount % 5 < 2) color = 0xFFFFFFFF;

        if (WeaponModConfig.get().legacyCannonModel) {
            poseStack.scale(-1.0f, -1.0f, 1.0f);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0f));
            submitNodeCollector.submitModel(modelLegacy, entityRenderState, poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.CANNON_LEGACY), lm, OverlayTexture.NO_OVERLAY,
                    color, null, entityRenderState.outlineColor, null);
        } else {
            float rot = Math.min(entityRenderState.xRot, 20.0f);
            poseStack.scale(-1.6f, -1.6f, 1.6f);
            poseStack.pushPose();
            poseStack.translate(0.0f, 1.0f, 0.0f);
            poseStack.mulPose(Axis.XP.rotationDegrees(rot));
            poseStack.translate(0.0f, -1.0f, 0.0f);
            submitNodeCollector.submitModel(modelBarrel, entityRenderState, poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.CANNON), lm, OverlayTexture.NO_OVERLAY,
                    color, null, entityRenderState.outlineColor, null);
            poseStack.popPose();
            submitNodeCollector.submitModel(modelStandard, entityRenderState, poseStack,
                    RenderTypes.entityCutout(WeaponModResources.Entity.CANNON), lm, OverlayTexture.NO_OVERLAY,
                    color, null, entityRenderState.outlineColor, null);
        }

        poseStack.popPose();
        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }

    @NotNull
    @Override
    public CannonRenderState createRenderState() {
        return new CannonRenderState();
    }

    @Override
    public void extractRenderState(EntityCannon entity, CannonRenderState entityRenderState, float f) {
        super.extractRenderState(entity, entityRenderState, f);
        entityRenderState.tickCount = entity.tickCount;
        entityRenderState.hurtTime = entity.getHurtTime();
        entityRenderState.currentDamage = entity.getCurrentDamage();
        entityRenderState.rockDirection = entity.getRockDirection();
        entityRenderState.superPowered = entity.isSuperPowered();
    }

    public static class CannonRenderState extends WMRenderer.WMRendererState {
        public int tickCount;
        public int hurtTime;
        public int currentDamage;
        public int rockDirection;
        public boolean superPowered;
    }

}
