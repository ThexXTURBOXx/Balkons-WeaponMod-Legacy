package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMusketBullet;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderMusketBullet extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderBullet((EntityMusketBullet) entity, d, d1, d2, f, f1);
    }

    public void renderBullet(EntityMusketBullet entitymusketbullet, double d, double d1, double d2, float f, float f1) {
        bindEntityTexture(entitymusketbullet);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        Tessellator tess = Tessellator.instance;
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glScalef(0.07f, 0.07f, 0.07f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(0.0, -1.0, -1.0, 0.0, 0.0);
        tess.addVertexWithUV(0.0, -1.0, 1.0, 0.3125, 0.0);
        tess.addVertexWithUV(0.0, 1.0, 1.0, 0.3125, 0.3125);
        tess.addVertexWithUV(0.0, 1.0, -1.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(0.0, 1.0, -1.0, 0.0, 0.0);
        tess.addVertexWithUV(0.0, 1.0, 1.0, 0.3125, 0.0);
        tess.addVertexWithUV(0.0, -1.0, 1.0, 0.3125, 0.3125);
        tess.addVertexWithUV(0.0, -1.0, -1.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-1.0, -1.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(1.0, -1.0, 0.0, 0.3125, 0.0);
            tess.addVertexWithUV(1.0, 1.0, 0.0, 0.3125, 0.3125);
            tess.addVertexWithUV(-1.0, 1.0, 0.0, 0.0, 0.3125);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.BULLET;
    }
}
