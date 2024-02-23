package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBlowgunDart extends Render<EntityBlowgunDart> {
    public RenderBlowgunDart(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull final EntityBlowgunDart entityblowgundart, final double d, final double d1,
                         final double d2, final float f, final float f1) {
        this.bindEntityTexture(entityblowgundart);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float) d, (float) d1, (float) d2);
        GlStateManager.rotate(entityblowgundart.prevRotationYaw + (entityblowgundart.rotationYaw - entityblowgundart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entityblowgundart.prevRotationPitch + (entityblowgundart.rotationPitch - entityblowgundart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        final int type = entityblowgundart.getDartEffectType();
        final float[] color = entityblowgundart.getDartColor();
        final int i = 0;
        final float f2 = 0.0f;
        final float f3 = 0.5f;
        final float f4 = 0.0f;
        final float f5 = 0.15625f;
        final float f6 = 0.0f;
        final float f7 = 0.15625f;
        final float f8 = 0.15625f;
        final float f9 = 0.3125f;
        final float f10 = 0.05625f;
        GlStateManager.enableRescaleNormal();
        final float f11 = entityblowgundart.arrowShake - f1;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotate(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.05625f, 0.05625f, 0.05625f);
        GlStateManager.translate(-1.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entityblowgundart));
        }
        GlStateManager.glNormal3f(0.05625f, 0.0f, 0.0f);
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
        GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f);
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
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f);
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
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entityblowgundart, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntityBlowgunDart entityblowgundart) {
        return WeaponModResources.Textures.DART;
    }
}
