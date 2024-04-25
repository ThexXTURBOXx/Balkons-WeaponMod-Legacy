package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderDynamite extends WMRenderer<EntityDynamite> {

    public RenderDynamite(EntityRenderDispatcher renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityDynamite entitydynamite, float f, float f1,
                       PoseStack ms, MultiBufferSource bufs, int lm) {
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitydynamite)));
        ms.pushPose();
        ms.mulPose(Vector3f.YP.rotationDegrees(entitydynamite.yRot + 90.0f));
        ms.mulPose(Vector3f.ZP.rotationDegrees(entitydynamite.xRotO + (entitydynamite.xRot - entitydynamite.xRotO) * f1));
        float f11 = -f1;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            ms.mulPose(Vector3f.ZP.rotationDegrees(f12));
        }
        ms.mulPose(Vector3f.XP.rotationDegrees(45.0f));
        ms.scale(0.05625f, 0.05625f, 0.05625f);
        ms.translate(-4.0f, 0.0f, 0.0f);
        PoseStack.Pose last = ms.last();
        drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -7.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        for (int j = 0; j < 4; ++j) {
            ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -8.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 8.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 8.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -8.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.popPose();
        super.render(entitydynamite, f, f1, ms, bufs, lm);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(@Nonnull EntityDynamite entity) {
        return WeaponModResources.Entity.DYNAMITE;
    }

}
