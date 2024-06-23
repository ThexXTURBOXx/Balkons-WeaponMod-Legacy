package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityBoomerang;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderBoomerang extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderBoomerang((EntityBoomerang) entity, d, d1, d2, f, f1);
    }

    public void renderBoomerang(EntityBoomerang entityboomerang, double d, double d1, double d2, float f, float f1) {
        //if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
        bindEntityTexture(entityboomerang);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        GL11.glRotatef(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        Tessellator tess = Tessellator.instance;
        int mat = entityboomerang.getWeaponMaterialId();
        float[] color = entityboomerang.getMaterialColor();
        GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(0.0, 0.0, 1.0, 0.5, 0.0);
        tess.addVertexWithUV(1.0, 0.0, 1.0, 0.0, 0.0);
        tess.addVertexWithUV(1.0, 0.0, 0.0, 0.0, 0.5);
        tess.addVertexWithUV(0.0, 0.0, 0.0, 0.5, 0.5);
        if (mat != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(0.0, 0.0, 1.0, 1.0, 0.0);
            tess.addVertexWithUV(1.0, 0.0, 1.0, 0.5, 0.0);
            tess.addVertexWithUV(1.0, 0.0, 0.0, 0.5, 0.5);
            tess.addVertexWithUV(0.0, 0.0, 0.0, 1.0, 0.5);
        }
        tess.draw();
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(1.0, 0.0, 0.0, 0.0, 0.5);
        tess.addVertexWithUV(1.0, 0.0, 1.0, 0.5, 0.5);
        tess.addVertexWithUV(0.0, 0.0, 1.0, 0.5, 0.0);
        tess.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        if (mat != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(1.0, 0.0, 0.0, 0.5, 0.5);
            tess.addVertexWithUV(1.0, 0.0, 1.0, 1.0, 0.5);
            tess.addVertexWithUV(0.0, 0.0, 1.0, 1.0, 0.0);
            tess.addVertexWithUV(0.0, 0.0, 0.0, 0.5, 0.0);
        }
        tess.draw();
        float sqrt2 = (float) Math.sqrt(2.0);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glNormal3f(-sqrt2, 0.0f, sqrt2);
        tess.startDrawingQuads();
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(0.2, -0.08, 0.8, 0.5, 0.5);
        tess.addVertexWithUV(0.2, 0.08, 0.8, 0.5, 0.65625);
        tess.addVertexWithUV(0.9, 0.08, 0.8, 0.0, 0.65625);
        tess.addVertexWithUV(0.9, -0.08, 0.8, 0.0, 0.5);
        if (mat != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(0.2, -0.08, 0.8, 1.0, 0.5);
            tess.addVertexWithUV(0.2, 0.08, 0.8, 1.0, 0.65625);
            tess.addVertexWithUV(0.9, 0.08, 0.8, 0.5, 0.65625);
            tess.addVertexWithUV(0.9, -0.08, 0.8, 0.5, 0.5);
        }
        tess.setColorOpaque_F(1F, 1F, 1F);
        tess.addVertexWithUV(0.2, -0.08, 0.8, 0.5, 0.5);
        tess.addVertexWithUV(0.2, 0.08, 0.8, 0.5, 0.65625);
        tess.addVertexWithUV(0.2, 0.08, 0.2, 0.0, 0.65625);
        tess.addVertexWithUV(0.2, -0.08, 0.2, 0.0, 0.5);
        if (mat != 0) {
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(0.2, -0.08, 0.8, 1.0, 0.5);
            tess.addVertexWithUV(0.2, 0.08, 0.8, 1.0, 0.65625);
            tess.addVertexWithUV(0.2, 0.08, 0.2, 0.5, 0.65625);
            tess.addVertexWithUV(0.2, -0.08, 0.2, 0.5, 0.5);
        }
        tess.draw();
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
       /* } else {
            RenderItem itemRender = RenderItem.getInstance();
            itemRender.setRenderManager(renderManager);
            GL11.glPushMatrix();
            bindEntityTexture(entityboomerang);
            GL11.glTranslated(d, d1, d2);
            GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glScalef(0.85f, 0.85f, 0.85f);
            GL11.glRotatef(entityboomerang.prevRotationPitch + (entityboomerang.rotationPitch - entityboomerang
            .prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(entityboomerang.prevRotationYaw + (entityboomerang.rotationYaw - entityboomerang
            .prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            itemRender.doRender(EntityItemFake.INSTANCE.setItem(getStackToRender(entityboomerang)), d, d1, d2, f, f1);
            GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glPopMatrix();
        }*/
    }

    public ItemStack getStackToRender(EntityBoomerang entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.BOOMERANG;
    }
}
