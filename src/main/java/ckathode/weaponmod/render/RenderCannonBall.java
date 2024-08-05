package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCannonBall;
import com.mojang.blaze3d.platform.GlStateManager;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderCannonBall extends EntityRenderer<EntityCannonBall> {
    public RenderCannonBall(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.5f;
    }

    @Override
    public void doRender(@Nonnull EntityCannonBall entitycannonball, double d, double d1,
                         double d2, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        bindEntityTexture(entitycannonball);
        GlStateManager.disableLighting();
        GlStateManager.translated(d, d1, d2);
        GlStateManager.rotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scalef(-1.0f, -1.0f, 1.0f);
        GlStateManager.scalef(0.7f, 0.7f, 0.7f);
        GlStateManager.rotatef(180.0f, 1.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.setupSolidRenderingTextureCombine(getTeamColor(entitycannonball));
        }
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
        if (renderOutlines) {
            GlStateManager.tearDownSolidRenderingTextureCombine();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitycannonball, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityCannonBall entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }
}
