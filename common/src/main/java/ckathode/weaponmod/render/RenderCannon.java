package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
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
        shadowRadius = 1.0f;
    }

    @Override
    public void render(CannonRenderState entityRenderState, PoseStack ms, MultiBufferSource bufs, int lm) {
        ms.pushPose();
        if (WeaponModConfig.get().legacyCannonModel) {
            modelLegacy.barrel.xRot = Math.max(-entityRenderState.xRot / 120.0f, -0.25f);
            ms.translate(0, 0.1, 0);
            ms.mulPose(Axis.YP.rotationDegrees(-entityRenderState.yRot));
            float f3 = entityRenderState.hurtTime;
            float f4 = entityRenderState.currentDamage;
            if (f4 < 0.0f) {
                f4 = 0.0f;
            }
            if (f3 > 0.0f) {
                ms.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f3) * f3 * f4 / 10.0f * entityRenderState.rockDirection / 5.0f));
            }
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.CANNON_LEGACY));
            ms.scale(-1.0f, -1.0f, 1.0f);
            ms.mulPose(Axis.XP.rotationDegrees(180.0f));
            int color = 0xFFCCCCCC;
            if (entityRenderState.superPowered && entityRenderState.tickCount % 5 < 2) color = 0xFFFFFFFF;
            modelLegacy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        } else {
            float rot = Math.min(entityRenderState.xRot, 20.0f);
            ms.translate(0, 2.375f, 0);
            ms.mulPose(Axis.YP.rotationDegrees(180.0f - entityRenderState.yRot));
            float f2 = entityRenderState.hurtTime;
            float f3 = entityRenderState.currentDamage;
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            if (f2 > 0.0f) {
                ms.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entityRenderState.rockDirection / 5.0f));
            }
            VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(WeaponModResources.Entity.CANNON));
            ms.scale(-1.6f, -1.6f, 1.6f);
            int color = 0xFFCCCCCC;
            if (entityRenderState.superPowered && entityRenderState.tickCount % 5 < 2) color = 0xFFFFFFFF;
            ms.pushPose();
            ms.translate(0.0f, 1.0f, 0.0f);
            ms.mulPose(Axis.XP.rotationDegrees(rot));
            ms.translate(0.0f, -1.0f, 0.0f);
            modelBarrel.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
            ms.popPose();
            float yawRadians = -(float) Math.toRadians(entityRenderState.yRot);
            modelStandard.base1.yRot = yawRadians;
            modelStandard.base2.yRot = yawRadians;
            modelStandard.baseStand.yRot = yawRadians;
            modelStandard.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        }
        ms.popPose();
        super.render(entityRenderState, ms, bufs, lm);
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
