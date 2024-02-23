package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderMusketBullet extends Render<EntityMusketBullet> {
    public RenderMusketBullet(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull final EntityMusketBullet entitymusketbullet, final double d, final double d1,
                         final double d2, final float f, final float f1) {
        this.bindEntityTexture(entitymusketbullet);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float) d, (float) d1, (float) d2);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        final float f2 = 0.0f;
        final float f3 = 0.3125f;
        final float f4 = 0.05625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.07f, 0.07f, 0.07f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitymusketbullet));
        }
        GlStateManager.glNormal3f(0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0.0, -1.0, -1.0).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(0.0, -1.0, 1.0).tex(0.3125, 0.0).endVertex();
        vertexbuffer.pos(0.0, 1.0, 1.0).tex(0.3125, 0.3125).endVertex();
        vertexbuffer.pos(0.0, 1.0, -1.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        GlStateManager.glNormal3f(-0.05625f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(0.0, 1.0, -1.0).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(0.0, 1.0, 1.0).tex(0.3125, 0.0).endVertex();
        vertexbuffer.pos(0.0, -1.0, 1.0).tex(0.3125, 0.3125).endVertex();
        vertexbuffer.pos(0.0, -1.0, -1.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.05625f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-1.0, -1.0, 0.0).tex(0.0, 0.0).endVertex();
            vertexbuffer.pos(1.0, -1.0, 0.0).tex(0.3125, 0.0).endVertex();
            vertexbuffer.pos(1.0, 1.0, 0.0).tex(0.3125, 0.3125).endVertex();
            vertexbuffer.pos(-1.0, 1.0, 0.0).tex(0.0, 0.3125).endVertex();
            tessellator.draw();
        }
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitymusketbullet, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull final EntityMusketBullet entity) {
        return WeaponModResources.Textures.BULLET;
    }
}
