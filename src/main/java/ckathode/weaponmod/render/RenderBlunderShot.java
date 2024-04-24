package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;

public class RenderBlunderShot extends WMRenderer<EntityBlunderShot> {

    public RenderBlunderShot(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityBlunderShot entityblundershot, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.pushPose();
        IVertexBuilder builder = bufs.getBuffer(RenderType.entityCutout(getTextureLocation(entityblundershot)));
        ms.scale(0.04f, 0.04f, 0.04f);
        MatrixStack.Entry last = ms.last();
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        for (int j = 0; j < 4; ++j) {
            ms.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            last = ms.last();
            drawVertex(last, builder, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, -1.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, 1.0f, 0.0f, 0.3125f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -1.0f, 1.0f, 0.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.popPose();
        ms.pushPose();
        GlStateManager._disableTexture();
        GlStateManager._disableCull();
        GlStateManager._lineWidth(1.0f);
        Matrix4f mat = ms.last().pose();
        builder = bufs.getBuffer(RenderType.lines());
        builder.vertex(mat, (float) entityblundershot.getX(), (float) entityblundershot.getY(),
                        (float) entityblundershot.getZ())
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        builder.vertex(mat, (float) entityblundershot.xo, (float) entityblundershot.yo,
                        (float) entityblundershot.zo)
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        GlStateManager._enableTexture();
        GlStateManager._enableCull();
        ms.popPose();
        super.render(entityblundershot, f, f1, ms, bufs, lm);
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(@Nonnull EntityBlunderShot entity) {
        return WeaponModResources.Entity.BULLET;
    }

}
