package ckathode.weaponmod.render;

import ckathode.weaponmod.BalkonsWeaponMod;
import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityJavelin;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderJavelin extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderJavelin((EntityJavelin) entity, d, d1, d2, f, f1);
    }

    public void renderJavelin(EntityJavelin entityjavelin, double d, double d1, double d2, float f, float f1) {
        //if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
        bindEntityTexture(entityjavelin);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        double length = 20.0;
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f11 = entityjavelin.arrowShake - f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-length, -2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-length, -2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-length, 2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-length, 2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-length, 2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-length, 2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-length, -2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-length, -2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-length, -2.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(length, -2.0, 0.0, 1.0, 0.0);
            tess.addVertexWithUV(length, 2.0, 0.0, 1.0, 0.15625);
            tess.addVertexWithUV(-length, 2.0, 0.0, 0.0, 0.15625);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        /*} else {
            RenderItem itemRender = RenderItem.getInstance();
            GL11.glPushMatrix();
            bindEntityTexture(entityjavelin);
            GL11.glTranslated(d, d1, d2);
            GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glScalef(1.7f, 1.7f, 1.7f);
            GL11.glRotatef(entityjavelin.prevRotationYaw + (entityjavelin.rotationYaw - entityjavelin
            .prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entityjavelin.prevRotationPitch + (entityjavelin.rotationPitch - entityjavelin
            .prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f13 = entityjavelin.arrowShake - f1;
            if (f13 > 0.0f) {
                float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
                GL11.glRotatef(f14, 0.0f, 0.0f, 1.0f);
            }
            GL11.glTranslatef(-0.25f, -0.25f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            itemRender.doRender(EntityItemFake.INSTANCE.setItem(getStackToRender(entityjavelin)), d, d1, d2, f, f1);
            GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glPopMatrix();
        }*/
    }

    public ItemStack getStackToRender(EntityJavelin entity) {
        return new ItemStack(BalkonsWeaponMod.javelin);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.JAVELIN;
    }
}
