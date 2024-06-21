package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderCannonBall extends Render<EntityCannonBall> {
    public RenderCannonBall(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.5f;
    }

    @Override
    public void doRender(@Nonnull EntityCannonBall entitycannonball, double d, double d1,
                         double d2, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertexbuffer = tessellator.getWorldRenderer();
        GlStateManager.pushMatrix();
        bindEntityTexture(entitycannonball);
        GlStateManager.disableLighting();
        GlStateManager.translate(d, d1, d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(-0.5, 0.5, -0.5).tex(0.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, -0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, -0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, -0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, 0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, 0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, 0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(-0.5, 0.5, 0.5).tex(0.0, 1.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, -0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, -0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, 0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, 0.5).tex(0.0, 1.0).endVertex();
        vertexbuffer.pos(-0.5, 0.5, 0.5).tex(0.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, 0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, -0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, 0.5, -0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, 0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, 0.5, 0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(-0.5, 0.5, -0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(-0.5, -0.5, -0.5).tex(0.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, -0.5).tex(0.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, -0.5).tex(1.0, 0.0).endVertex();
        vertexbuffer.pos(0.5, 0.5, 0.5).tex(1.0, 1.0).endVertex();
        vertexbuffer.pos(0.5, -0.5, 0.5).tex(0.0, 1.0).endVertex();
        tessellator.draw();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitycannonball, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCannonBall entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }
}
