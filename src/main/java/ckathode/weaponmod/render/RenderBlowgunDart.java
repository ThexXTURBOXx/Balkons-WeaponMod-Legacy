package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

public class RenderBlowgunDart extends WMRenderer<EntityBlowgunDart> {

    public RenderBlowgunDart(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityBlowgunDart entityblowgundart, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.pushPose();
        IVertexBuilder builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityblowgundart)));
        ms.mulPose(Vector3f.YP.rotationDegrees(entityblowgundart.yRotO + (entityblowgundart.yRot - entityblowgundart.yRotO) * f1 - 90.0f));
        ms.mulPose(Vector3f.ZP.rotationDegrees(entityblowgundart.xRotO + (entityblowgundart.xRot - entityblowgundart.xRotO) * f1));
        byte type = entityblowgundart.getDartEffectId();
        float[] color = entityblowgundart.getDartColor();
        float f11 = entityblowgundart.shakeTime - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            ms.mulPose(Vector3f.ZP.rotationDegrees(f12));
        }
        ms.mulPose(Vector3f.XP.rotationDegrees(45.0f));
        ms.scale(0.05625f, 0.05625f, 0.05625f);
        ms.translate(-1.0f, 0.0f, 0.0f);
        MatrixStack.Entry last = ms.last();
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
            ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
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
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getTextureLocation(@Nonnull EntityBlowgunDart entityblowgundart) {
        return WeaponModResources.Entity.DART;
    }

}
