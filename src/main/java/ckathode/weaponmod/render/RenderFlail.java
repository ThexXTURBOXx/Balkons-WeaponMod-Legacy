package ckathode.weaponmod.render;

import ckathode.weaponmod.WeaponModResources;
import ckathode.weaponmod.entity.projectile.EntityFlail;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderFlail extends WMRenderer<EntityFlail> {

    public RenderFlail(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(EntityFlail entityflail, float p, float partialTicks,
                       MatrixStack ms, IRenderTypeBuffer bufs, int lm) {
        PlayerEntity shooter = entityflail.shootingEntity != null
                ? entityflail.getEntityWorld().getPlayerByUuid(entityflail.shootingEntity) : null;
        if (shooter != null) {
            ms.push();
            ms.push();
            ms.rotate(Vector3f.YP.rotationDegrees(entityflail.prevRotationYaw + (entityflail.rotationYaw - entityflail.prevRotationYaw) * partialTicks - 90.0f));
            ms.rotate(Vector3f.ZP.rotationDegrees(entityflail.prevRotationPitch + (entityflail.rotationPitch - entityflail.prevRotationPitch) * partialTicks));
            float[] color = entityflail.getMaterialColor();
            float f11 = -partialTicks;
            if (f11 > 0.0f) {
                float f12 = -MathHelper.sin(f11 * 3.0f) * f11;
                ms.rotate(Vector3f.ZP.rotationDegrees(f12));
            }
            ms.rotate(Vector3f.XP.rotationDegrees(45.0f));
            ms.scale(0.15f, 0.15f, 0.15f);
            ms.translate(-4.0f, 0.0f, 0.0f);
            MatrixStack.Entry last = ms.getLast();
            IVertexBuilder builder = bufs.getBuffer(RenderType.getEntityCutout(getEntityTexture(entityflail)));
            drawVertex(last, builder, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.15625f, 0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.3125f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f, 0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, -0.15f, 0.0f,
                    0.0f, lm);
            drawVertex(last, builder, 1.5f, 2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.15625f, -0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, 2.0f, color[0], color[1], color[2], 1, 0.15625f, 0.3125f, -0.15f,
                    0.0f, 0.0f, lm);
            drawVertex(last, builder, 1.5f, -2.0f, -2.0f, color[0], color[1], color[2], 1, 0.0f, 0.3125f, -0.15f, 0.0f,
                    0.0f, lm);
            for (int j = 0; j < 4; ++j) {
                ms.rotate(Vector3f.XP.rotationDegrees(90.0f));
                last = ms.getLast();
                drawVertex(last, builder, -8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f, 0.0f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, 8.0f, -2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f, 0.0f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, 8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.5f, 0.15625f, 0.0f, 0.0f,
                        0.15f, lm);
                drawVertex(last, builder, -8.0f, 2.0f, 0.0f, color[0], color[1], color[2], 1, 0.0f, 0.15625f, 0.0f,
                        0.0f, 0.15f, lm);
            }
            ms.pop();
            int i = shooter.getPrimaryHand() == HandSide.RIGHT ? 1 : -1;

            float f = shooter.getSwingProgress(partialTicks);
            float f1 = MathHelper.sin(MathHelper.sqrt(f) * 3.1415927F);
            float f2 =
                    MathHelper.lerp(partialTicks, shooter.prevRenderYawOffset, shooter.renderYawOffset) * 0.017453292F;
            double d0 = MathHelper.sin(f2);
            double d1 = MathHelper.cos(f2);
            double d2 = (double) i * 0.35;
            double d3 = 0.8;
            double d4;
            double d5;
            double d6;
            float f3;
            double d9;
            if ((this.renderManager.options == null || this.renderManager.options.thirdPersonView <= 0) && shooter == Minecraft.getInstance().player) {
                d9 = renderManager.options.fov;
                d9 /= 100.0;
                Vec3d vec3d = new Vec3d((double) i * -0.36 * d9, -0.045 * d9, 0.4);
                vec3d = vec3d.rotatePitch(-MathHelper.lerp(partialTicks, shooter.prevRotationPitch,
                        shooter.rotationPitch) * 0.017453292F);
                vec3d = vec3d.rotateYaw(-MathHelper.lerp(partialTicks, shooter.prevRotationYaw, shooter.rotationYaw) * 0.017453292F);
                vec3d = vec3d.rotateYaw(f1 * 0.5F);
                vec3d = vec3d.rotatePitch(-f1 * 0.7F);
                d4 = MathHelper.lerp(partialTicks, shooter.prevPosX, shooter.getPosX()) + vec3d.x;
                d5 = MathHelper.lerp(partialTicks, shooter.prevPosY, shooter.getPosY()) + vec3d.y;
                d6 = MathHelper.lerp(partialTicks, shooter.prevPosZ, shooter.getPosZ()) + vec3d.z;
                f3 = shooter.getEyeHeight();
            } else {
                d4 = MathHelper.lerp(partialTicks, shooter.prevPosX, shooter.getPosX()) - d1 * d2 - d0 * d3;
                d5 = shooter.prevPosY + (double) shooter.getEyeHeight() + (shooter.getPosY() - shooter.prevPosY) * (double) partialTicks - 0.45;
                d6 = MathHelper.lerp(partialTicks, shooter.prevPosZ, shooter.getPosZ()) - d0 * d2 + d1 * d3;
                f3 = shooter.isCrouching() ? -0.1875F : 0.0F;
            }

            d9 = MathHelper.lerp(partialTicks, entityflail.prevPosX, entityflail.getPosX());
            double d10 = MathHelper.lerp(partialTicks, entityflail.prevPosY, entityflail.getPosY()) + 0.25;
            double d8 = MathHelper.lerp(partialTicks, entityflail.prevPosZ, entityflail.getPosZ());
            float f4 = (float) (d4 - d9);
            float f5 = (float) (d5 - d10) + f3;
            float f6 = (float) (d6 - d8);
            builder = bufs.getBuffer(RenderType.getLines());
            Matrix4f mat = ms.getLast().getMatrix();

            for (int k = 0; k < 16; ++k) {
                stringVertex(f4, f5, f6, builder, mat, fraction(k, 16));
                stringVertex(f4, f5, f6, builder, mat, fraction(k + 1, 16));
            }

            ms.pop();
            super.render(entityflail, p, partialTicks, ms, bufs, lm);
        }
    }

    private static float fraction(int p_229105_0_, int p_229105_1_) {
        return (float) p_229105_0_ / (float) p_229105_1_;
    }

    private static void vertex(IVertexBuilder builder, Matrix4f mat, Matrix3f nm, int lm, float x, int y,
                               int uvx, int uvy) {
        builder.pos(mat, x - 0.5F, (float) y - 0.5F, 0.0F).color(255, 255, 255, 255).tex((float) uvx, (float) uvy).overlay(OverlayTexture.NO_OVERLAY).lightmap(lm).normal(nm, 0.0F, 1.0F, 0.0F).endVertex();
    }

    private static void stringVertex(float f4, float f5, float f6, IVertexBuilder builder, Matrix4f mat, float frac) {
        builder.pos(mat, f4 * frac, f5 * (frac * frac + frac) * 0.5F + 0.25F,
                f6 * frac).color(0, 0, 0, 255).endVertex();
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ResourceLocation getEntityTexture(@Nonnull EntityFlail entity) {
        return WeaponModResources.Entity.FLAIL;
    }

}
