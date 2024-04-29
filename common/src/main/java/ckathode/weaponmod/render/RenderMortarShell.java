package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderMortarShell extends WMRenderer<EntityMortarShell> {

    public RenderMortarShell(EntityRenderDispatcher renderManager) {
        super(renderManager);
        shadowRadius = 0.3f;
    }

    @Override
    public void render(@NotNull EntityMortarShell entitymortarshell, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitymortarshell)));
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.scale(0.2f, 0.2f, 0.2f);
        ms.mulPose(Vector3f.XP.rotationDegrees(180.0f));
        PoseStack.Pose last = ms.last();
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, 0.5f, -0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, -0.5f, -0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, 0.5f, 0.5f, 1.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        drawVertex(last, builder, 0.5f, -0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.05625f, lm);
        ms.popPose();
        super.render(entitymortarshell, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityMortarShell entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }

}
