package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class RenderFlail extends Render {
    @Override
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
        renderFlail((EntityFlail) entity, d, d1, d2, f, f1);
    }

    public void renderFlail(EntityFlail entityflail, double d, double d1, double d2, float f, float f1) {
        bindEntityTexture(entityflail);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(d, d1, d2);
        GL11.glRotatef(entityflail.prevRotationYaw + (entityflail.rotationYaw - entityflail.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(entityflail.prevRotationPitch + (entityflail.rotationPitch - entityflail.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tess = Tessellator.instance;
        float[] color = entityflail.getMaterialColor();
        GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        float f11 = -f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GL11.glRotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GL11.glColor3f(color[0], color[1], color[2]);
        GL11.glRotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(0.15f, 0.15f, 0.15f);
        GL11.glTranslatef(-4.0f, 0.0f, 0.0f);
        GL11.glNormal3f(0.15f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(1.5, -2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(1.5, -2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(1.5, 2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(1.5, 2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        GL11.glNormal3f(-0.15f, 0.0f, 0.0f);
        tess.startDrawingQuads();
        tess.addVertexWithUV(1.5, 2.0, -2.0, 0.0, 0.15625);
        tess.addVertexWithUV(1.5, 2.0, 2.0, 0.15625, 0.15625);
        tess.addVertexWithUV(1.5, -2.0, 2.0, 0.15625, 0.3125);
        tess.addVertexWithUV(1.5, -2.0, -2.0, 0.0, 0.3125);
        tess.draw();
        for (int j = 0; j < 4; ++j) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glNormal3f(0.0f, 0.0f, 0.15f);
            tess.startDrawingQuads();
            tess.addVertexWithUV(-8.0, -2.0, 0.0, 0.0, 0.0);
            tess.addVertexWithUV(8.0, -2.0, 0.0, 0.5, 0.0);
            tess.addVertexWithUV(8.0, 2.0, 0.0, 0.5, 0.15625);
            tess.addVertexWithUV(-8.0, 2.0, 0.0, 0.0, 0.15625);
            tess.draw();
        }
        GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        EntityLivingBase shooter = entityflail.shootingEntity instanceof EntityLivingBase
                ? (EntityLivingBase) entityflail.shootingEntity : null;
        if (shooter != null) {
            float f13 = shooter.getSwingProgress(f1);
            float f14 = MathHelper.sin(MathHelper.sqrt_float(f13) * 3.1415927f);
            float f15 =
                    (shooter.prevRenderYawOffset + (shooter.renderYawOffset - shooter.prevRenderYawOffset) * f1) * 0.017453292f;
            double d3 = MathHelper.sin(f15);
            double d4 = MathHelper.cos(f15);
            double d5 = 0.35;
            double d7;
            double d8;
            double d9;
            double d10;
            if (renderManager.options != null && renderManager.options.thirdPersonView <= 0 && shooter == Minecraft.getMinecraft().thePlayer) {
                double f16 = renderManager.options.fovSetting;
                f16 /= 100.0f;
                Vec3 vec3d = Vec3.createVectorHelper(-0.36 * f16, -0.045 * f16, 0.4);
                vec3d.rotateAroundX(-(shooter.prevRotationPitch + (shooter.rotationPitch - shooter.prevRotationPitch) * f1) * 0.017453292f);
                vec3d.rotateAroundY(-(shooter.prevRotationYaw + (shooter.rotationYaw - shooter.prevRotationYaw) * f1) * 0.017453292f);
                vec3d.rotateAroundY(f14 * 0.5f);
                vec3d.rotateAroundX(-f14 * 0.7f);
                d7 = shooter.prevPosX + (shooter.posX - shooter.prevPosX) * f1 + vec3d.xCoord;
                d8 = shooter.prevPosY + (shooter.posY - shooter.prevPosY) * f1 + vec3d.yCoord;
                d9 = shooter.prevPosZ + (shooter.posZ - shooter.prevPosZ) * f1 + vec3d.zCoord;
                d10 = shooter.getEyeHeight();
            } else {
                d7 = shooter.prevPosX + (shooter.posX - shooter.prevPosX) * f1 - d4 * d5 - d3 * 0.8;
                d8 = shooter.prevPosY + shooter.getEyeHeight() + (shooter.posY - shooter.prevPosY) * f1 - 0.45;
                d9 = shooter.prevPosZ + (shooter.posZ - shooter.prevPosZ) * f1 - d3 * d5 + d4 * 0.8;
                d10 = (shooter.isSneaking() ? -0.1875 : 0.0);
            }
            double d11 = entityflail.prevPosX + (entityflail.posX - entityflail.prevPosX) * f1;
            double d12 = entityflail.prevPosY + (entityflail.posY - entityflail.prevPosY) * f1 + 0.25;
            double d13 = entityflail.prevPosZ + (entityflail.posZ - entityflail.prevPosZ) * f1;
            double d14 = (float) (d7 - d11);
            double d15 = (float) (d8 - d12) + d10;
            double d16 = (float) (d9 - d13);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            tess.startDrawing(GL11.GL_LINE_STRIP);
            tess.setColorOpaque_I(0);
            for (int i2 = 0; i2 <= 16; ++i2) {
                float f17 = i2 / 16.0f;
                tess.addVertex(d + d14 * f17, d1 + d15 * (f17 * f17 + f17) * 0.5 + 0.25, d2 + d16 * f17);
            }
            tess.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return WeaponModResources.Entity.FLAIL;
    }
}
