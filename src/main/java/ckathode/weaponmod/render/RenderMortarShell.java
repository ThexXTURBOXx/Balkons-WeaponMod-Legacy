package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityMortarShell;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderMortarShell extends Render {
    public RenderMortarShell() {
        shadowSize = 0.3f;
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderShell((EntityMortarShell) entity, d, d1, d2, f, f1);
    }

    public void renderShell(EntityMortarShell entitymortarshell, double d, double d1, double d2, float f, float f1) {
        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();
        bindEntityTexture(entitymortarshell);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(180.0f - f, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glScalef(0.2f, 0.2f, 0.2f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-0.5, 0.5, -0.5, 0.0, 1.0);
        tess.addVertexWithUV(0.5, 0.5, -0.5, 1.0, 1.0);
        tess.addVertexWithUV(0.5, -0.5, -0.5, 1.0, 0.0);
        tess.addVertexWithUV(-0.5, -0.5, -0.5, 0.0, 0.0);
        tess.addVertexWithUV(-0.5, -0.5, 0.5, 0.0, 0.0);
        tess.addVertexWithUV(0.5, -0.5, 0.5, 1.0, 0.0);
        tess.addVertexWithUV(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.addVertexWithUV(-0.5, 0.5, 0.5, 0.0, 1.0);
        tess.addVertexWithUV(-0.5, -0.5, -0.5, 0.0, 0.0);
        tess.addVertexWithUV(0.5, -0.5, -0.5, 1.0, 0.0);
        tess.addVertexWithUV(0.5, -0.5, 0.5, 1.0, 1.0);
        tess.addVertexWithUV(-0.5, -0.5, 0.5, 0.0, 1.0);
        tess.addVertexWithUV(-0.5, 0.5, 0.5, 0.0, 1.0);
        tess.addVertexWithUV(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.addVertexWithUV(0.5, 0.5, -0.5, 1.0, 0.0);
        tess.addVertexWithUV(-0.5, 0.5, -0.5, 0.0, 0.0);
        tess.addVertexWithUV(-0.5, -0.5, 0.5, 0.0, 0.0);
        tess.addVertexWithUV(-0.5, 0.5, 0.5, 1.0, 0.0);
        tess.addVertexWithUV(-0.5, 0.5, -0.5, 1.0, 1.0);
        tess.addVertexWithUV(-0.5, -0.5, -0.5, 0.0, 1.0);
        tess.addVertexWithUV(0.5, -0.5, -0.5, 0.0, 0.0);
        tess.addVertexWithUV(0.5, 0.5, -0.5, 1.0, 0.0);
        tess.addVertexWithUV(0.5, 0.5, 0.5, 1.0, 1.0);
        tess.addVertexWithUV(0.5, -0.5, 0.5, 0.0, 1.0);
        tess.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.CANNONBALL;
    }
}
