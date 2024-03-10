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
    public RenderBlowgunDart(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityBlowgunDart entityblowgundart, double d, double d1,
                         double d2, float f, float f1) {
        bindEntityTexture(entityblowgundart);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate(d, d1, d2);
        GlStateManager.rotate(entityblowgundart.prevRotationYaw + (entityblowgundart.rotationYaw - entityblowgundart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entityblowgundart.prevRotationPitch + (entityblowgundart.rotationPitch - entityblowgundart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        byte type = entityblowgundart.getDartEffectId();
        float[] color = entityblowgundart.getDartColor();
        GlStateManager.enableRescaleNormal();
        float f11 = entityblowgundart.arrowShake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotate(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.05625f, 0.05625f, 0.05625f);
        GlStateManager.translate(-1.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entityblowgundart));
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
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
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
