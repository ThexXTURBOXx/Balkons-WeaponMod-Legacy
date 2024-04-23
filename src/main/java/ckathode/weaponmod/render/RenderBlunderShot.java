package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderBlunderShot extends WMRenderer<EntityBlunderShot> {

    public RenderBlunderShot(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityBlunderShot entityblundershot, float f, float f1,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        ms.push();
        IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entityblundershot)));
        ms.scale(0.04f, 0.04f, 0.04f);
        MatrixStack.Entry last = ms.getLast();
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.0f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.3125f, 0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, -1.0f, 0.0f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, 1.0f, 1.0f, 0.3125f, 0.0f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, 1.0f, 0.3125f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        drawVertex(last, builder, 0.0f, -1.0f, -1.0f, 0.0f, 0.3125f, -0.05625f, 0.0f, 0.0f, lm);
        for (int j = 0; j < 4; ++j) {
            ms.rotate(Vector3f.XP.rotationDegrees(90.0f));
            last = ms.getLast();
            drawVertex(last, builder, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, -1.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, 1.0f, 1.0f, 0.0f, 0.3125f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
            drawVertex(last, builder, -1.0f, 1.0f, 0.0f, 0.0f, 0.3125f, 0.0f, 0.0f, 0.05625f, lm);
        }
        ms.pop();
        ms.push();
        GlStateManager.disableTexture();
        GlStateManager.disableCull();
        GlStateManager.lineWidth(1.0f);
        Matrix4f mat = ms.getLast().getMatrix();
        builder = bufs.getBuffer(RenderType.getLines());
        builder.pos(mat, (float) entityblundershot.posX, (float) entityblundershot.posY,
                        (float) entityblundershot.posZ)
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        builder.pos(mat, (float) entityblundershot.prevPosX, (float) entityblundershot.prevPosY,
                        (float) entityblundershot.prevPosZ)
                .color(1.0f, 1.0f, 0.8f, 1.0f)
                .normal(0.0f, 0.0f, 0.05625f)
                .endVertex();
        GlStateManager.enableTexture();
        GlStateManager.enableCull();
        ms.pop();
        super.render(entityblundershot, f, f1, ms, bufs, lm);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(@Nonnull EntityBlunderShot entity) {
        return WeaponModResources.Entity.BULLET;
    }

}
