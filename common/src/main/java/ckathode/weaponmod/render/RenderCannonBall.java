package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderCannonBall extends WMRenderer<EntityCannonBall> {

    public RenderCannonBall(Context context) {
        super(context);
        shadowRadius = 0.5f;
    }

    @Override
    public void render(@NotNull EntityCannonBall entitycannonball, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entitycannonball)));
        ms.mulPose(Vector3f.YP.rotationDegrees(180.0f - f));
        ms.scale(-1.0f, -1.0f, 1.0f);
        ms.scale(0.7f, 0.7f, 0.7f);
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
        super.render(entitycannonball, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityCannonBall entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }

}
