package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityCrossbowBolt;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderCrossbowBolt extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderBolt((EntityCrossbowBolt) entity, d, d1, d2, f, f1);
    }

    public void renderBolt(EntityCrossbowBolt entitybolt, double d, double d1, double d2, float f, float f1) {
        bindEntityTexture(entitybolt);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entitybolt.prevRotationYaw + (entitybolt.rotationYaw - entitybolt.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entitybolt.prevRotationPitch + (entitybolt.rotationPitch - entitybolt.prevRotationPitch) * f1,
                0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f11 = entitybolt.arrowShake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glTranslatef(-1.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-5.0, 2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-5.0, 2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-5.0, -2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-5.0, -2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-6.0, -2.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(6.0, -2.0, 0.0, 0.5, 0.0);
            tess.addVertexWithUV(6.0, 2.0, 0.0, 0.5, 0.15625);
            tess.addVertexWithUV(-6.0, 2.0, 0.0, 0.0, 0.15625);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.BOLT;
    }
}
