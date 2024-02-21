package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import ckathode.weaponmod.*;

public class RenderDynamite extends Render<EntityDynamite>
{
    public float pitch;
    
    public RenderDynamite(final RenderManager renderManager) {
        super(renderManager);
        this.pitch = 40.0f;
    }
    
    public void doRender(final EntityDynamite entitydynamite, final double d, final double d1, final double d2, final float f, final float f1) {
        this.bindEntityTexture(entitydynamite);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float)d, (float)d1, (float)d2);
        GlStateManager.rotate(entitydynamite.rotationYaw + 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entitydynamite.prevRotationPitch + (entitydynamite.rotationPitch - entitydynamite.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
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
        final float f11 = -f1;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotate(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.05625f, 0.05625f, 0.05625f);
        GlStateManager.translate(-4.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitydynamite));
        }
        GlStateManager.glNormal3f(0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
            vertexbuffer.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
            vertexbuffer.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
            vertexbuffer.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
            tessellator.draw();
        }
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitydynamite, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final EntityDynamite entity) {
        return WeaponModResources.Textures.DYNAMITE;
    }
}
