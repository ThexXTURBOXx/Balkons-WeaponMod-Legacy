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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderFlail extends Render<EntityFlail> {
    public RenderFlail(final RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(@Nonnull final EntityFlail entityflail, final double d, final double d1, final double d2,
                         final float f, final float f1) {
        this.bindEntityTexture(entityflail);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.translate((float) d, (float) d1, (float) d2);
        GlStateManager.rotate(entityflail.prevRotationYaw + (entityflail.rotationYaw - entityflail.prevRotationYaw) * f1 - 90.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(entityflail.prevRotationPitch + (entityflail.rotationPitch - entityflail.prevRotationPitch) * f1, 0.0f, 0.0f, 1.0f);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        final float[] color = entityflail.getMaterialColor();
        final int i = 0;
        final float f2 = 0.0f;
        final float f3 = 0.5f;
        final float f4 = 0.0f;
        final float f5 = 0.15625f;
        final float f6 = 0.0f;
        final float f7 = 0.15625f;
        final float f8 = 0.15625f;
        final float f9 = 0.3125f;
        final float f10 = 0.15f;
        GlStateManager.enableRescaleNormal();
        final float f11 = -f1;
        if (f11 > 0.0f) {
            final float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
            GlStateManager.rotate(f12, 0.0f, 0.0f, 1.0f);
        }
        GlStateManager.color(color[0], color[1], color[2]);
        GlStateManager.rotate(45.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.15f, 0.15f, 0.15f);
        GlStateManager.translate(-4.0f, 0.0f, 0.0f);
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entityflail));
        }
        GlStateManager.glNormal3f(0.15f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1.5, -2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(1.5, -2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(1.5, 2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(1.5, 2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        GlStateManager.glNormal3f(-0.15f, 0.0f, 0.0f);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos(1.5, 2.0, -2.0).tex(0.0, 0.15625).endVertex();
        vertexbuffer.pos(1.5, 2.0, 2.0).tex(0.15625, 0.15625).endVertex();
        vertexbuffer.pos(1.5, -2.0, 2.0).tex(0.15625, 0.3125).endVertex();
        vertexbuffer.pos(1.5, -2.0, -2.0).tex(0.0, 0.3125).endVertex();
        tessellator.draw();
        for (int j = 0; j < 4; ++j) {
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.glNormal3f(0.0f, 0.0f, 0.15f);
            vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            vertexbuffer.pos(-8.0, -2.0, 0.0).tex(0.0, 0.0).endVertex();
            vertexbuffer.pos(8.0, -2.0, 0.0).tex(0.5, 0.0).endVertex();
            vertexbuffer.pos(8.0, 2.0, 0.0).tex(0.5, 0.15625).endVertex();
            vertexbuffer.pos(-8.0, 2.0, 0.0).tex(0.0, 0.15625).endVertex();
            tessellator.draw();
        }
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
        if (entityflail.shootingEntity instanceof EntityLivingBase && !this.renderOutlines) {
            final int k = (((EntityLivingBase) entityflail.shootingEntity).getPrimaryHand() == EnumHandSide.RIGHT) ?
                    1 : -1;
            final float f13 = ((EntityLivingBase) entityflail.shootingEntity).getSwingProgress(f1);
            final float f14 = MathHelper.sin(MathHelper.sqrt(f13) * 3.1415927f);
            final float f15 =
                    (((EntityLivingBase) entityflail.shootingEntity).prevRenderYawOffset + (((EntityLivingBase) entityflail.shootingEntity).renderYawOffset - ((EntityLivingBase) entityflail.shootingEntity).prevRenderYawOffset) * f1) * 0.017453292f;
            final double d3 = MathHelper.sin(f15);
            final double d4 = MathHelper.cos(f15);
            final double d5 = k * 0.35;
            final double d6 = 0.8;
            double d7;
            double d8;
            double d9;
            double d10;
            if (this.renderManager.options != null && this.renderManager.options.thirdPersonView <= 0 && entityflail.shootingEntity == Minecraft.getMinecraft().player) {
                float f16 = this.renderManager.options.fovSetting;
                f16 /= 100.0f;
                Vec3d vec3d = new Vec3d(k * -0.36 * f16, -0.045 * f16, 0.4);
                vec3d = vec3d.rotatePitch(-(entityflail.shootingEntity.prevRotationPitch + (entityflail.shootingEntity.rotationPitch - entityflail.shootingEntity.prevRotationPitch) * f1) * 0.017453292f);
                vec3d = vec3d.rotateYaw(-(entityflail.shootingEntity.prevRotationYaw + (entityflail.shootingEntity.rotationYaw - entityflail.shootingEntity.prevRotationYaw) * f1) * 0.017453292f);
                vec3d = vec3d.rotateYaw(f14 * 0.5f);
                vec3d = vec3d.rotatePitch(-f14 * 0.7f);
                d7 = entityflail.shootingEntity.prevPosX + (entityflail.shootingEntity.posX - entityflail.shootingEntity.prevPosX) * f1 + vec3d.x;
                d8 = entityflail.shootingEntity.prevPosY + (entityflail.shootingEntity.posY - entityflail.shootingEntity.prevPosY) * f1 + vec3d.y;
                d9 = entityflail.shootingEntity.prevPosZ + (entityflail.shootingEntity.posZ - entityflail.shootingEntity.prevPosZ) * f1 + vec3d.z;
                d10 = entityflail.shootingEntity.getEyeHeight();
            } else {
                d7 = entityflail.shootingEntity.prevPosX + (entityflail.shootingEntity.posX - entityflail.shootingEntity.prevPosX) * f1 - d4 * d5 - d3 * 0.8;
                d8 = entityflail.shootingEntity.prevPosY + entityflail.shootingEntity.getEyeHeight() + (entityflail.shootingEntity.posY - entityflail.shootingEntity.prevPosY) * f1 - 0.45;
                d9 = entityflail.shootingEntity.prevPosZ + (entityflail.shootingEntity.posZ - entityflail.shootingEntity.prevPosZ) * f1 - d3 * d5 + d4 * 0.8;
                d10 = (entityflail.shootingEntity.isSneaking() ? -0.1875 : 0.0);
            }
            final double d11 = entityflail.prevPosX + (entityflail.posX - entityflail.prevPosX) * f1;
            final double d12 = entityflail.prevPosY + (entityflail.posY - entityflail.prevPosY) * f1 + 0.25;
            final double d13 = entityflail.prevPosZ + (entityflail.posZ - entityflail.prevPosZ) * f1;
            final double d14 = (float) (d7 - d11);
            final double d15 = (float) (d8 - d12) + d10;
            final double d16 = (float) (d9 - d13);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            vertexbuffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
            final int l = 16;
            for (int i2 = 0; i2 <= 16; ++i2) {
                final float f17 = i2 / 16.0f;
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
    protected ResourceLocation getEntityTexture(@Nonnull final EntityFlail entity) {
        return WeaponModResources.Textures.FLAIL;
    }
}
