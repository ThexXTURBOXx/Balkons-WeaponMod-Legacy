package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlunderShot;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderBlunderShot extends Render<EntityBlunderShot> {
    public RenderBlunderShot(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityBlunderShot entityblundershot, double d, double d1,
                         double d2, float f, float f1) {
        bindEntityTexture(entityblundershot);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate(d, d1, d2);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.04f, 0.04f, 0.04f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entityblundershot));
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
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.color(1.0f, 1.0f, 0.8f, 1.0f);
        GlStateManager.glLineWidth(1.0f);
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos(entityblundershot.posX, entityblundershot.posY, entityblundershot.posZ);
        vertexbuffer.pos(entityblundershot.prevPosX, entityblundershot.prevPosY, entityblundershot.prevPosZ);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entityblundershot, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityBlunderShot entity) {
        return WeaponModResources.Entity.BULLET;
    }
}
