package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBlowgunDart extends EntityRenderer<EntityBlowgunDart> {
    public RenderBlowgunDart(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityBlowgunDart entityblowgundart, double d, double d1,
                         double d2, float f, float f1) {
        bindEntityTexture(entityblowgundart);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translated(d, d1, d2);
        GlStateManager.rotatef(entityblowgundart.prevRotationYaw + (entityblowgundart.rotationYaw - entityblowgundart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotatef(entityblowgundart.prevRotationPitch + (entityblowgundart.rotationPitch - entityblowgundart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        byte type = entityblowgundart.getDartEffectId();
        float[] color = entityblowgundart.getDartColor();
        GlStateManager.enableRescaleNormal();
        float f11 = entityblowgundart.arrowShake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scalef(0.05625f, 0.05625f, 0.05625f);
        GlStateManager.translatef(-1.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(getTeamColor(entityblowgundart));
        }
        GlStateManager.normal3f(0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        vertexbuffer.pos(-5.0, -2.0, -2.0).tex(0.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, -2.0, 2.0).tex(0.15625, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, 2.0, 2.0).tex(0.15625, 0.3125).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, 2.0, -2.0).tex(0.0, 0.3125).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        if (type != 0) {
            vertexbuffer.pos(-5.0, -2.0, -2.0).tex(0.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, -2.0, 2.0).tex(0.15625, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, 2.0, 2.0).tex(0.15625, 0.625).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, 2.0, -2.0).tex(0.0, 0.625).color(color[0], color[1], color[2], 1.0f).endVertex();
        }
        tessellator.draw();
        GlStateManager.normal3f(-0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        vertexbuffer.pos(-5.0, 2.0, -2.0).tex(0.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, 2.0, 2.0).tex(0.15625, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, -2.0, 2.0).tex(0.15625, 0.3125).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        vertexbuffer.pos(-5.0, -2.0, -2.0).tex(0.0, 0.3125).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        if (type != 0) {
            vertexbuffer.pos(-5.0, 2.0, -2.0).tex(0.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, 2.0, 2.0).tex(0.15625, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, -2.0, 2.0).tex(0.15625, 0.625).color(color[0], color[1], color[2], 1.0f).endVertex();
            vertexbuffer.pos(-5.0, -2.0, -2.0).tex(0.0, 0.625).color(color[0], color[1], color[2], 1.0f).endVertex();
        }
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.normal3f(0.0f, 0.0f, 0.05625f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            vertexbuffer.pos(-6.0, -2.0, 0.0).tex(0.0, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(6.0, -2.0, 0.0).tex(0.5, 0.0).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(6.0, 2.0, 0.0).tex(0.5, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            vertexbuffer.pos(-6.0, 2.0, 0.0).tex(0.0, 0.15625).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
            if (type != 0) {
                vertexbuffer.pos(-6.0, -2.0, 0.0).tex(0.0, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(6.0, -2.0, 0.0).tex(0.5, 0.3125).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(6.0, 2.0, 0.0).tex(0.5, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
                vertexbuffer.pos(-6.0, 2.0, 0.0).tex(0.0, 0.46875).color(color[0], color[1], color[2], 1.0f).endVertex();
            }
            tessellator.draw();
        }
        if (renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entityblowgundart, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlowgunDart entityblowgundart) {
        return WeaponModResources.Entity.DART;
    }
}
