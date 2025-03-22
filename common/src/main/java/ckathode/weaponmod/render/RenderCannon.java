package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModConfig;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCannon extends WMRenderer<EntityCannon> {

    private final ModelCannonBarrel modelBarrel = new ModelCannonBarrel();
    private final ModelCannonStandard modelStandard = new ModelCannonStandard();
    private final ModelCannonLegacy modelLegacy = new ModelCannonLegacy();

    public RenderCannon(EntityRenderDispatcher renderManager) {
        super(renderManager);
        shadowRadius = 0.7f;
    }

    @Override
    public void render(@NotNull EntityCannon entitycannon, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        f = interpolateRotation(entitycannon.yRotO, entitycannon.yRot, f1);

        if (WeaponModConfig.get().legacyCannonModel) {
            ms.translate(0, 0.1, 0);
            ms.mulPose(Vector3f.YP.rotationDegrees(-f));
        } else {
            ms.translate(0, 2.375f, 0);
            ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        }

        float f2 = entitycannon.getHurtTime() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) f3 = 0.0f;
        if (f2 > 0.0f)
            ms.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f));
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitycannon)));
        float f4 = 1f;
        if (entitycannon.isSuperPowered() && entitycannon.tickCount % 5 < 2) f4 = 1.5f;

        if (WeaponModConfig.get().legacyCannonModel) {
            ms.scale(-1.0f, -1.0f, 1.0f);
            ms.mulPose(Vector3f.XP.rotationDegrees(180.0f));
            modelLegacy.barrel.xRot = Math.max(-entitycannon.xRot / 120.0f, -0.25f);
            modelLegacy.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                    entitycannon.getBrightness() * f4, 1);
        } else {
            float rot = entitycannon.xRotO + (entitycannon.xRot - entitycannon.xRotO) * f1;
            rot = Math.min(rot, 20.0f);
            ms.scale(-1.6f, -1.6f, 1.6f);
            ms.pushPose();
            ms.translate(0.0f, 1.0f, 0.0f);
            ms.mulPose(Vector3f.XP.rotationDegrees(rot));
            ms.translate(0.0f, -1.0f, 0.0f);
            modelBarrel.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                    entitycannon.getBrightness() * f4, 1);
            ms.popPose();
            float yawRadians = -(float) Math.toRadians(f);
            modelStandard.base1.yRot = yawRadians;
            modelStandard.base2.yRot = yawRadians;
            modelStandard.baseStand.yRot = yawRadians;
            modelStandard.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                    entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                    entitycannon.getBrightness() * f4, 1);
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
