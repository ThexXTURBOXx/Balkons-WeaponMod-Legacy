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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCannon extends WMRenderer<EntityCannon> {

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
    public void render(@NotNull EntityCannon entitycannon, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        f = interpolateRotation(entitycannon.yRotO, entitycannon.getYRot(), f1);

        if (WeaponModConfig.get().legacyCannonModel) {
            ms.translate(0, 0.1, 0);
            ms.mulPose(Axis.YP.rotationDegrees(-f));
        } else {
            ms.translate(0, 2.375f, 0);
            ms.mulPose(Axis.YP.rotationDegrees(180.0f - f));
        }

        float f2 = entitycannon.getHurtTime() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) f3 = 0.0f;
        if (f2 > 0.0f)
            ms.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f));
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitycannon)));
        int color = 0xFFCCCCCC;
        if (entitycannon.isSuperPowered() && entitycannon.tickCount % 5 < 2) color = 0xFFFFFFFF;

        if (WeaponModConfig.get().legacyCannonModel) {
            ms.scale(-1.0f, -1.0f, 1.0f);
            ms.mulPose(Axis.XP.rotationDegrees(180.0f));
            modelLegacy.barrel.xRot = Math.max(-entitycannon.getXRot() / 120.0f, -0.25f);
            modelLegacy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        } else {
            float rot = entitycannon.xRotO + (entitycannon.getXRot() - entitycannon.xRotO) * f1;
            rot = Math.min(rot, 20.0f);
            ms.scale(-1.6f, -1.6f, 1.6f);
            ms.pushPose();
            ms.translate(0.0f, 1.0f, 0.0f);
            ms.mulPose(Axis.XP.rotationDegrees(rot));
            ms.translate(0.0f, -1.0f, 0.0f);
            modelBarrel.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
            ms.popPose();
            float yawRadians = -(float) Math.toRadians(f);
            modelStandard.base1.yRot = yawRadians;
            modelStandard.base2.yRot = yawRadians;
            modelStandard.baseStand.yRot = yawRadians;
            modelStandard.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY, color);
        }

        ms.popPose();
        super.render(entitycannon, f, f1, ms, bufs, lm);
    }

    private float interpolateRotation(float from, float to, float by) {
        return (from + Mth.wrapDegrees(to - from) * by) % 360.0f;
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityCannon entity) {
        return WeaponModConfig.get().legacyCannonModel
                ? WeaponModResources.Entity.CANNON_LEGACY
                : WeaponModResources.Entity.CANNON;
    }

}
