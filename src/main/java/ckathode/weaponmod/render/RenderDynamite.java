package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityDynamite;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderDynamite extends Render<EntityDynamite> {
    public final float pitch;

    public RenderDynamite(final RenderManager renderManager) {
        super(renderManager);
        this.pitch = 40.0f;
    }

    @Override
    public void doRender(@Nonnull final EntityDynamite entitydynamite, final double d, final double d1,
                         final double d2, final float f, final float f1) {
        this.bindEntityTexture(entitydynamite);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translated(d, d1, d2);
        GlStateManager.rotatef(entitydynamite.rotationYaw + 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotatef(entitydynamite.prevRotationPitch + (entitydynamite.rotationPitch - entitydynamite.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
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
            GlStateManager.rotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scalef(0.05625f, 0.05625f, 0.05625f);
        GlStateManager.translatef(-4.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitydynamite));
        }
        GlStateManager.normal3f(0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        GlStateManager.normal3f(-0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-7.0, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(-7.0, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(-7.0, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.normal3f(0.0f, 0.0f, 0.05625f);
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

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntityDynamite entity) {
        return WeaponModResources.Textures.DYNAMITE;
    }
}
