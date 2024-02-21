package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import ckathode.weaponmod.*;

public class RenderCannonBall extends Render<EntityCannonBall>
{
    public RenderCannonBall(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5f;
    }
    
    public void doRender(final EntityCannonBall entitycannonball, final double d, final double d1, final double d2, final float f, final float f1) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.pushMatrix();
        this.bindEntityTexture(entitycannonball);
        GlStateManager.disableLighting();
        GlStateManager.translate((float)d, (float)d1, (float)d2);
        GlStateManager.rotate(180.0f - f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-1.0f, -1.0f, 1.0f);
        GlStateManager.scale(0.7f, 0.7f, 0.7f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entitycannonball));
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
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        super.doRender(entitycannonball, d, d1, d2, f, f1);
    }
    
    protected ResourceLocation getEntityTexture(final EntityCannonBall entity) {
        return WeaponModResources.Textures.CANNONBALL;
    }
}
