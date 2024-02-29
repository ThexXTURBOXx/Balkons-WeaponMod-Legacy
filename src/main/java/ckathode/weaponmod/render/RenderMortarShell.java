package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import javax.annotation.Nonnull;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class RenderMortarShell extends Render<EntityMortarShell> {
    public RenderMortarShell(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 0.3f;
    }

    @Override
    public void doRender(@Nonnull EntityMortarShell entitymortarshell, double d, double d1,
                         double d2, float f, float f1) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        bindEntityTexture(entitymortarshell);
        GlStateManager.disableLighting();
        GlStateManager.translated(d, d1, d2);
        GlStateManager.rotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scalef(-1.0f, -1.0f, 1.0f);
        GlStateManager.scalef(0.2f, 0.2f, 0.2f);
        GlStateManager.rotatef(180.0f, 1.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entitymortarshell));
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
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitymortarshell, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityMortarShell entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }
}
