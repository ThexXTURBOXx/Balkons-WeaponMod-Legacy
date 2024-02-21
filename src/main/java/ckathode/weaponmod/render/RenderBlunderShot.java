package ckathode.weaponmod.render;

import ckathode.weaponmod.entity.projectile.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import ckathode.weaponmod.*;

public class RenderBlunderShot extends Render<EntityBlunderShot>
{
    public RenderBlunderShot(final RenderManager renderManager) {
        super(renderManager);
    }
    
    public void doRender(final EntityBlunderShot entityblundershot, final double d, final double d1, final double d2, final float f, final float f1) {
        this.bindEntityTexture(entityblundershot);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float)d, (float)d1, (float)d2);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        final float f2 = 0.0f;
        final float f3 = 0.3125f;
        final float f4 = 0.05625f;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.04f, 0.04f, 0.04f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entityblundershot));
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
    
    protected ResourceLocation getEntityTexture(final EntityBlunderShot entity) {
        return WeaponModResources.Textures.BULLET;
    }
}
