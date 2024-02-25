package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import javax.annotation.Nonnull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderFlail extends Render<EntityFlail> {
    public RenderFlail(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull EntityFlail entityflail, double d, double d1, double d2,
                         float f, float f1) {
        bindEntityTexture(entityflail);
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translated(d, d1, d2);
        GlStateManager.rotatef(entityflail.prevRotationYaw + (entityflail.rotationYaw - entityflail.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotatef(entityflail.prevRotationPitch + (entityflail.rotationPitch - entityflail.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        float[] color = entityflail.getMaterialColor();
        GlStateManager.enableRescaleNormal();
        float f11 = -f1;
        if (f11 > 0.0f) {
            float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotatef(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.color3f(color[0], color[1], color[2]);
        GlStateManager.rotatef(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scalef(0.15f, 0.15f, 0.15f);
        GlStateManager.translatef(-4.0f, 0.0f, 0.0f);
        if (renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(getTeamColor(entityflail));
        }
        GlStateManager.normal3f(0.15f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1.5, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(1.5, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(1.5, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(1.5, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        GlStateManager.normal3f(-0.15f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1.5, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(1.5, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(1.5, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(1.5, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.normal3f(0.0f, 0.0f, 0.15f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
            vertexbuffer.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
            vertexbuffer.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
            vertexbuffer.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
            tessellator.draw();
        }
        if (renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        Entity shooter = entityflail.getShooter();
        if (shooter instanceof EntityLivingBase && !renderOutlines) {
            int k = (((EntityLivingBase) shooter).getPrimaryHand() == EnumHandSide.RIGHT) ?
                    1 : -1;
            float f13 = ((EntityLivingBase) shooter).getSwingProgress(f1);
            float f14 = MathHelper.sin(MathHelper.sqrt(f13) * 3.1415927f);
            float f15 =
                    (((EntityLivingBase) shooter).prevRenderYawOffset + (((EntityLivingBase) shooter).renderYawOffset - ((EntityLivingBase) shooter).prevRenderYawOffset) * f1) * 0.017453292f;
            double d3 = MathHelper.sin(f15);
            double d4 = MathHelper.cos(f15);
            double d5 = k * 0.35;
            double d7;
            double d8;
            double d9;
            double d10;
            if (renderManager.options != null && renderManager.options.thirdPersonView <= 0 && shooter == Minecraft.getInstance().player) {
                double f16 = renderManager.options.fovSetting;
                f16 /= 100.0f;
                Vec3d vec3d = new Vec3d(k * -0.36 * f16, -0.045 * f16, 0.4);
                vec3d = vec3d.rotatePitch(-(shooter.prevRotationPitch + (shooter.rotationPitch - shooter.prevRotationPitch) * f1) * 0.017453292f);
                vec3d = vec3d.rotateYaw(-(shooter.prevRotationYaw + (shooter.rotationYaw - shooter.prevRotationYaw) * f1) * 0.017453292f);
                vec3d = vec3d.rotateYaw(f14 * 0.5f);
                vec3d = vec3d.rotatePitch(-f14 * 0.7f);
                d7 = shooter.prevPosX + (shooter.posX - shooter.prevPosX) * f1 + vec3d.x;
                d8 = shooter.prevPosY + (shooter.posY - shooter.prevPosY) * f1 + vec3d.y;
                d9 = shooter.prevPosZ + (shooter.posZ - shooter.prevPosZ) * f1 + vec3d.z;
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
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            for (int i2 = 0; i2 <= 16; ++i2) {
                float f17 = i2 / 16.0f;
                vertexbuffer.pos(d + d14 * f17, d1 + d15 * (f17 * f17 + f17) * 0.5 + 0.25, d2 + d16 * f17).color(0,
                        0, 0, 255).endVertex();
            }
            tessellator.draw();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            super.doRender(entityflail, d, d1, d2, f, f1);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(@Nonnull EntityFlail entity) {
        return WeaponModResources.Textures.FLAIL;
    }
}
