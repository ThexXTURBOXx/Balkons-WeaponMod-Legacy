package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.EntityCannon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderCannon extends EntityRenderer<EntityCannon> {

    private final ModelCannonBarrel modelBarrel;
    private final ModelCannonStandard modelStandard;

    public RenderCannon(EntityRenderDispatcher renderManager) {
        super(renderManager);
        modelBarrel = new ModelCannonBarrel();
        modelStandard = new ModelCannonStandard();
        shadowRadius = 1.0f;
    }

    @Override
    public void render(@NotNull EntityCannon entitycannon, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        float rot = entitycannon.xRotO + (entitycannon.xRot - entitycannon.xRotO) * f1;
        rot = Math.min(rot, 20.0f);
        ms.translate(0, 2.375f, 0);
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        float f2 = entitycannon.getHurtTime() - f1;
        float f3 = entitycannon.getCurrentDamage() - f1;
        if (f3 < 0.0f) {
            f3 = 0.0f;
        }
        if (f2 > 0.0f) {
            ms.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2) * f2 * f3 / 10.0f * entitycannon.getRockDirection() / 5.0f));
        }
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitycannon)));
        ms.scale(-1.6f, -1.6f, 1.6f);
        float f4 = 1f;
        if (entitycannon.isSuperPowered() && entitycannon.tickCount % 5 < 2) f4 = 1.5f;
        ms.pushPose();
        ms.translate(0.0f, 1.0f, 0.0f);
        ms.mulPose(Vector3f.XP.rotationDegrees(rot));
        ms.translate(0.0f, -1.0f, 0.0f);
        modelBarrel.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                entitycannon.getBrightness() * f4, 1);
        ms.popPose();
        float yaw = -(float) Math.toRadians(f);
        modelStandard.base1.yRot = yaw;
        modelStandard.base2.yRot = yaw;
        modelStandard.baseStand.yRot = yaw;
        modelStandard.renderToBuffer(ms, builder, lm, OverlayTexture.NO_OVERLAY,
                entitycannon.getBrightness() * f4, entitycannon.getBrightness() * f4,
                entitycannon.getBrightness() * f4, 1);
        ms.popPose();
        super.render(entitycannon, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityCannon entity) {
        return WeaponModResources.Entity.CANNON;
    }

}
