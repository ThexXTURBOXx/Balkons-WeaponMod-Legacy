package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class RenderBlowgunDart extends WMRenderer<EntityBlowgunDart> {

    public RenderBlowgunDart(Context context) {
        super(context);
    }

    @Override
    public void render(@NotNull EntityBlowgunDart entityblowgundart, float f, float f1,
                       @NotNull PoseStack ms, @NotNull MultiBufferSource bufs, int lm) {
        ms.pushPose();
        VertexConsumer builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityblowgundart)));
        ms.mulPose(Axis.YP.rotationDegrees(entityblowgundart.yRotO + (entityblowgundart.getYRot() - entityblowgundart.yRotO) * f1 - 90.0f));
        ms.mulPose(Axis.ZP.rotationDegrees(entityblowgundart.xRotO + (entityblowgundart.getXRot() - entityblowgundart.xRotO) * f1));
        byte type = entityblowgundart.getDartEffectId();
        float[] color = entityblowgundart.getDartColor();
        float f11 = entityblowgundart.shakeTime - f1;
        if (f11 > 0.0f) {
            float f12 = -Mth.sin(f11 * 3.0f) * f11;
            ms.mulPose(Axis.ZP.rotationDegrees(f12));
        }
        ms.mulPose(Axis.XP.rotationDegrees(45.0f));
        ms.scale(0.05625f, 0.05625f, 0.05625f);
        ms.translate(-1.0f, 0.0f, 0.0f);
        PoseStack.Pose last = ms.last();
        drawVertex(last, builder, -5.0f, -2.0f, -2.0f, 0.0f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, -2.0f, 2.0f, 0.15625f, 0.15625f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, 2.0f, 2.0f, 0.15625f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, 2.0f, -2.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        if (type != 0) {
            drawVertex(last, builder, -5.0f, -2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f,
                    0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, -2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.46875f,
                    0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, 2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.625f,
                    0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, 2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.625f, 0.05625f,
                    0.0f, 0.0f, lm);
        }
        drawVertex(last, builder, -5.0f, 2.0f, -2.0f, 0.0f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, 2.0f, 2.0f, 0.15625f, 0.15625f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, -2.0f, 2.0f, 0.15625f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, -5.0f, -2.0f, -2.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        if (type != 0) {
            drawVertex(last, builder, -5.0f, 2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f,
                    -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, 2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.46875f,
                    -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, -2.0f, 2.0f, color[0], color[1], color[2], 1.0f, 0.15625f, 0.625f,
                    -0.05625f, 0.0f, 0.0f, lm);
            drawVertex(last, builder, -5.0f, -2.0f, -2.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.625f,
                    -0.05625f, 0.0f, 0.0f, lm);
        }
        for (int j = 0; j < 4; ++j) {
            ms.mulPose(Axis.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -6.0f, -2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 6.0f, -2.0f, 0.0f, 0.5f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 6.0f, 2.0f, 0.0f, 0.5f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -6.0f, 2.0f, 0.0f, 0.0f, 0.15625f, 0.0f, 0.0f, 0.05625f, lm);
            if (type != 0) {
                drawVertex(last, builder, -6.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.3125f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 6.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.3125f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, 6.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.5f, 0.46875f, 0.0f,
                        0.0f, 0.05625f, lm);
                drawVertex(last, builder, -6.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1.0f, 0.0f, 0.46875f, 0.0f,
                        0.0f, 0.05625f, lm);
            }
        }
        ms.popPose();
        super.render(entityblowgundart, f, f1, ms, bufs, lm);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull EntityBlowgunDart entityblowgundart) {
        return WeaponModResources.Entity.DART;
    }

}
