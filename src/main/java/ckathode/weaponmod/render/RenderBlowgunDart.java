package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBlowgunDart;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderBlowgunDart extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderDart((EntityBlowgunDart) entity, d, d1, d2, f, f1);
    }

    public void renderDart(EntityBlowgunDart entityblowgundart, double d, double d1, double d2, float f, float f1) {
        bindEntityTexture(entityblowgundart);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entityblowgundart.prevRotationYaw + (entityblowgundart.rotationYaw - entityblowgundart.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entityblowgundart.prevRotationPitch + (entityblowgundart.rotationPitch - entityblowgundart.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        byte type = entityblowgundart.getDartEffectId();
        float[] color = entityblowgundart.getDartColor();
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f11 = entityblowgundart.arrowShake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.3125);
        if (type != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.46875);
            tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.46875);
            tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.625);
            tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.625);
        }
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.3125);
        if (type != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.46875);
            tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.46875);
            tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.625);
            tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.625);
        }
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startDrawingQuads();
            tess.setColorOpaque_F(1F, 1F, 1F);
            tess.addVertexWithUV(-6.0, -2.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(6.0, -2.0, 0.0, 0.5, 0.0);
            tess.addVertexWithUV(6.0, 2.0, 0.0, 0.5, 0.15625);
            tess.addVertexWithUV(-6.0, 2.0, 0.0, 0.0, 0.15625);
            if (type != 0) {
                tess.setColorOpaque_F(color[0], color[1], color[2]);
                tess.addVertexWithUV(-6.0, -2.0, 0.0, 0.0, 0.3125);
                tess.addVertexWithUV(6.0, -2.0, 0.0, 0.5, 0.3125);
                tess.addVertexWithUV(6.0, 2.0, 0.0, 0.5, 0.46875);
                tess.addVertexWithUV(-6.0, 2.0, 0.0, 0.0, 0.46875);
            }
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.DART;
    }
}
