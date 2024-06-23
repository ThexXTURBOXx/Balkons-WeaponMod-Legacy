package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityKnife;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderKnife extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderKnife((EntityKnife) entity, d, d1, d2, f, f1);
    }

    public void renderKnife(EntityKnife entityknife, double d, double d1, double d2, float f, float f1) {
        //if (!BalkonsWeaponMod.instance.modConfig.itemModelForEntity) {
        bindEntityTexture(entityknife);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        float[] color = entityknife.getMaterialColor();
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f13 = entityknife.arrowShake - f1;
        if (f13 > 0.0f) {
            float f14 = -MathHelper.sin(f13 * 3.0f) * f13;
            GL11.glRotatef(f14, 0.0f, 0.0f, 1.0f);
        }
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.05625f, 0.05625f, 0.05625f);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-7.0, -2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-7.0, -2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-7.0, 2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-7.0, 2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.05625f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(-7.0, 2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(-7.0, 2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(-7.0, -2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(-7.0, -2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.05625f);
            tess.startDrawingQuads();
            tess.setColorOpaque_F(1F, 1F, 1F);
            tess.addVertexWithUV(-8.0, -2.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(8.0, -2.0, 0.0, 0.5, 0.0);
            tess.addVertexWithUV(8.0, 2.0, 0.0, 0.5, 0.15625);
            tess.addVertexWithUV(-8.0, 2.0, 0.0, 0.0, 0.15625);
            tess.setColorOpaque_F(color[0], color[1], color[2]);
            tess.addVertexWithUV(-8.0, -2.0, 0.0, 0.0, 0.3125);
            tess.addVertexWithUV(8.0, -2.0, 0.0, 0.5, 0.3125);
            tess.addVertexWithUV(8.0, 2.0, 0.0, 0.5, 0.46875);
            tess.addVertexWithUV(-8.0, 2.0, 0.0, 0.0, 0.46875);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        /*} else {
            RenderItem itemRender = RenderItem.getInstance();
            GL11.glPushMatrix();
            bindEntityTexture(entityknife);
            GL11.glTranslated(d, d1, d2);
            GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glScalef(0.85f, 0.85f, 0.85f);
            GL11.glRotatef(entityknife.prevRotationYaw + (entityknife.rotationYaw - entityknife.prevRotationYaw) * f1
             - 90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(entityknife.prevRotationPitch + (entityknife.rotationPitch - entityknife
            .prevRotationPitch) * f1 - 45.0f, 0.0f, 0.0f, 1.0f);
            float f15 = entityknife.arrowShake - f1;
            if (f15 > 0.0f) {
                float f16 = -MathHelper.sin(f15 * 3.0f) * f15;
                GL11.glRotatef(f16, 0.0f, 0.0f, 1.0f);
            }
            GL11.glTranslatef(-0.15f, -0.15f, 0.0f);
            itemRender.doRender(EntityItemFake.INSTANCE.setItem(getStackToRender(entityknife)), d, d1, d2, f, f1);
            GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
            GL11.glPopMatrix();
        }*/
    }

    public ItemStack getStackToRender(EntityKnife entity) {
        return entity.getWeapon();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.KNIFE;
    }
}
